package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class HeavenFire implements Weave{

	List<Element> elements = new ArrayList<Element>();

	public HeavenFire(){
		elements.add(Element.FIRE);
		elements.add(Element.EARTH);
		elements.add(Element.FIRE);
		elements.add(Element.FIRE);
		elements.add(Element.SPIRIT);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		final List<Entity> nearbyEntities = player.getNearbyEntities(50, 50, 50);

		BukkitRunnable task = new BukkitRunnable(){

			@Override
			public void run() {
				for(Entity e : nearbyEntities){
					EntityType type = e.getType();
					if(type.equals(EntityType.ZOMBIE) || type.equals(EntityType.SKELETON) || type.equals(EntityType.CREEPER) || type.equals(EntityType.SPIDER) ) {
						throwFireball(e);
					}
				}
			}

		};

		BukkitScheduler scheduler = Bukkit.getScheduler();
		for(long x = 1; x < 5; x++){
			scheduler.scheduleSyncDelayedTask(TheOnePower.plugin, task, x*20);
		}

		return true;
	}

	public void throwFireball(Entity entity){
		Location fireSpawn = entity.getLocation();
		fireSpawn.add(0, 5, 0);
		Vector velocity = entity.getLocation().toVector().subtract(fireSpawn.toVector());
		velocity.multiply(0.5);
		Fireball ball = (Fireball) entity.getWorld().spawnEntity(fireSpawn, EntityType.FIREBALL);
		ball.setVelocity(velocity);

		Fireball ball2 = (Fireball) entity.getWorld().spawnEntity(fireSpawn, EntityType.FIREBALL);
		ball2.setVelocity(velocity);
		
		Fireball ball3 = (Fireball) entity.getWorld().spawnEntity(fireSpawn, EntityType.FIREBALL);
		ball3.setVelocity(velocity);
	}




	@Override
	public List<Element> getElements() {
		return elements;
	}

}
