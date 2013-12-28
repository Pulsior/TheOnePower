package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class ShootFireball implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	
	public ShootFireball(){
		elements.add(Element.FIRE);
		elements.add(Element.FIRE);
	}
	
	@Override
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity) {
		
		if(clickedEntity != null){
			clickedEntity.setFireTicks(200);
		}
		else if(clickedBlock != null){
			createFire(clickedBlock, true);
		}
		else{
			player.launchProjectile(Fireball.class);
			world.playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 1, 0);
		}
	}
	
	@Override
	public List<Element> getElements() {
		return elements;
	}
	
	/**
	 * Create a fire with a weave
	 * @param block
	 * @param large
	 */

	public void createFire(Block block, boolean large){
		if(large){
			Location location = block.getLocation();
			location.add(-1, 1, -1);

			if(location.getBlock().getType().equals(Material.AIR)){
				location.getBlock().setType(Material.FIRE);
			}

			for(int x = 0; x < 2; x++){
				for(int y = 0; y < 3; y++){
					location.add(0, 0, 1);
					if(location.getBlock().getType().equals(Material.AIR)){
						location.getBlock().setType(Material.FIRE);
					}
				}
				location.add(0, 0, -2);
				location.add(1, 0, 0);
				if(location.getBlock().getType().equals(Material.AIR)){
					location.getBlock().setType(Material.FIRE);
				}
			}
		}
	}
}
