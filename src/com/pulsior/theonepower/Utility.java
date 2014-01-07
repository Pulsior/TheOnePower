package com.pulsior.theonepower;

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

}
