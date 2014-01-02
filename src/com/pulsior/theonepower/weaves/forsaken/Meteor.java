package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.util.Utility;
import com.pulsior.theonepower.weaves.Weave;

public class Meteor implements Weave{

	List<Element> elements = new ArrayList<Element>();

	public Meteor(){
		elements.add(Element.FIRE);
		elements.add(Element.EARTH);
		elements.add(Element.FIRE);
		elements.add(Element.EARTH);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		final Fireball ball = player.launchProjectile(Fireball.class);
		
		ball.setYield(8F);
		
		BukkitRunnable task = new BukkitRunnable(){

			@Override
			public void run() {
				if(ball.isDead() == false){
					Utility.spawnFireworkEffect(ball.getLocation(), Color.YELLOW, Color.RED, Type.BURST, true, true, 0);
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
