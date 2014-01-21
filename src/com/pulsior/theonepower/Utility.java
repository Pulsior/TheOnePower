package com.pulsior.theonepower;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftFirework;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

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
	
	public static Direction getDirection(float yaw){
		
		if(-yaw > 135 && -yaw < 225){
			return Direction.NORTH;
		}
		
		if(-yaw > 225 && -yaw < 315){
			return Direction.NORTH;
		}
		
		if(-yaw > 315 || -yaw < 45){
			return Direction.SOUTH;
		}
		
		else{
			return Direction.WEST;
		}
		
	}
	
	public static boolean chance(int percentage){
		Random random = new Random();
		
		int randomInt = random.nextInt(100)+1;
		if(randomInt <= percentage){
			return true;
		}
		
		return false;
	}

}





