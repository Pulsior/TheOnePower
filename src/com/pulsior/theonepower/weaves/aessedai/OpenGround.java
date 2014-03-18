package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.task.OpenGroundTask;
import com.pulsior.theonepower.weaves.Weave;

public class OpenGround implements Weave{

	List<Element> elements = new ArrayList<Element>();

	public OpenGround(){
		elements.add(Element.EARTH);
		elements.add(Element.AIR);
		elements.add(Element.EARTH);
		elements.add(Element.AIR);
		elements.add(Element.EARTH);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		Block target = player.getTargetBlock(null, 50);
		Location location = target.getLocation();
		world.strikeLightning(location);
		location.add(4, 0, 4);
		List<Block> blocks = new ArrayList<Block>();
		
		
		for(int y = 0; y < 7; y++){

			for(int z = 0; z < 7; z++){
				blocks.add( location.getBlock() );
				location.add(0, 0, -1);
			}

			location.add(-1, 0, 6);

		}

		
		List<Block> blocks2 = new ArrayList<Block>();
		blocks2.addAll(blocks);
		
		for(Block b : blocks){
			
			Location loc = b.getLocation();
			
			for(int x = -1; x > -25; x--){
				blocks2.add( loc.add(0, -1, 0).getBlock() );
			}
		}
		
		List<Material> materials = new ArrayList<Material>();
		
		for(Block b : blocks2){
			materials.add( b.getType() );
			b.setType(Material.AIR);
		}
		
		OpenGroundTask task = new OpenGroundTask(blocks2, materials);
		task.runTaskLater(TheOnePower.plugin, 100);
		
		
		
		

		return true;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
