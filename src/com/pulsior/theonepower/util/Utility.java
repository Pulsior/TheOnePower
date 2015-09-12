package com.pulsior.theonepower.util;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

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
		
		yaw += 180;
		
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
	
	/**
	 * Credit for the getTarget methods goes to Njol
	 * @param player
	 * @return
	 */
	
	public static Player getTargetPlayer(Player player) {
        return getTarget(player, player.getWorld().getPlayers());
    }
 
    public static Entity getTargetEntity(Entity entity) {
        return getTarget(entity, entity.getWorld().getEntities());
    }
 
    static <T extends Entity> T getTarget(Entity entity, Iterable<T> entities) {
        T target = null;
        double threshold = 1;
        for (T other:entities) {
            Vector n = other.getLocation().toVector().subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null || target.getLocation().distanceSquared(entity.getLocation()) > other.getLocation().distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }

}





