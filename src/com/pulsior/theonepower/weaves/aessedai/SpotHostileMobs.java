package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.util.Utility;
import com.pulsior.theonepower.weaves.Weave;

public class SpotHostileMobs implements Weave{

	List<Element> elements = new ArrayList<Element>();
	
	public SpotHostileMobs(){
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.EARTH);
		elements.add(Element.SPIRIT);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		List<Entity> entities = player.getNearbyEntities(50, 50, 50);
		
		for(Entity e : entities){
			if(e instanceof Zombie){
				Utility.spawnFireworkEffect(e.getLocation(), Color.GREEN, Color.GREEN, Type.BALL, true, false, 20);
			}
			
			if(e instanceof Skeleton){
				Utility.spawnFireworkEffect(e.getLocation(), Color.SILVER, Color.WHITE, Type.BALL, true, false, 20);
			}
			
			if(e instanceof Creeper){
				Utility.spawnFireworkEffect(e.getLocation(), Color.LIME, Color.LIME, Type.BALL, true, false, 20);
			}
			
			if(e instanceof Spider){
				Utility.spawnFireworkEffect(e.getLocation(), Color.BLACK, Color.RED, Type.BALL, true, false, 20);
			}
			
			if(e instanceof Enderman){
				Utility.spawnFireworkEffect(e.getLocation(), Color.FUCHSIA, Color.BLACK, Type.BALL, true, false, 20);
			}
		}
		
		return true;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
