package com.pulsior.theonepower;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftFirework;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class Utility {

	public static Firework spawnFireworkEffect(Location location, Color color, Color fade, Type type, boolean trail, boolean flicker, int lifeSpan){

		Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		FireworkMeta meta = firework.getFireworkMeta();
		FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(color).withFade(fade).with(type).trail(false).build();
		meta.addEffect(effect);
		meta.setPower(1);
		firework.setFireworkMeta(meta);

		( (CraftFirework) firework).getHandle().expectedLifespan = lifeSpan;
		
		return firework;
	}
	
	public static void createGateway(Location location){

		location.add(1, 1, 0);
		
		for(int x = 0; x < 3; x++){

			if(x == 1){
				location.add(1, -1, 0);
			}
			if(x == 2){
				location.add(-2, -1, 0);
			}

			Block block = location.getBlock();
			block.setMetadata("isGateway", new FixedMetadataValue(TheOnePower.plugin, new Boolean(true) ) );
			block.setType(Material.PORTAL);
			location.add(0, 1, 0);
			block = location.getBlock();
			block.setMetadata("isGateway", new FixedMetadataValue(TheOnePower.plugin, new Boolean(true) ) );
			block.setType(Material.PORTAL);
		}
	}
	
	
}
