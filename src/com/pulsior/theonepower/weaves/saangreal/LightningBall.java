package com.pulsior.theonepower.weaves.saangreal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.util.Utility;
import com.pulsior.theonepower.weaves.Weave;

public class LightningBall implements Weave{

	List<Element> elements = new ArrayList<Element>();

	public LightningBall(){
		elements.add(Element.FIRE);
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.FIRE);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		
		final Fireball ball = player.launchProjectile(Fireball.class);

		ball.setYield(0F);

		BukkitRunnable task = new BukkitRunnable(){

			@Override
			public void run() {
				if( ! ball.isDead() ){
					Utility.spawnFireworkEffect(ball.getLocation(), Color.WHITE, Color.GRAY, Type.STAR, false, true, 0);
					List<Entity> entities = ball.getNearbyEntities(15, 15, 15);
					for(Entity e : entities){
						if (e instanceof Zombie || e instanceof Creeper || e instanceof Spider || e instanceof Skeleton || e instanceof Enderman){
							e.getWorld().strikeLightning(e.getLocation() );
						}
					}
				}
			}

		};

		BukkitScheduler scheduler = Bukkit.getScheduler();

		for(long x = 0; x < 100; x = x+2){
			scheduler.scheduleSyncDelayedTask(TheOnePower.plugin, task, x);
		}
		return true;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
