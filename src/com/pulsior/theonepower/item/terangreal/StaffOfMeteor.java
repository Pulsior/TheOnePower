package com.pulsior.theonepower.item.terangreal;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.util.Strings;
import com.pulsior.theonepower.util.Utility;

public class StaffOfMeteor extends TerAngreal{

	public StaffOfMeteor() {
		super(Material.BLAZE_ROD);
		setDisplayName(Strings.METEOR_STAFF_NAME);
		setSpawnChance(5);
	}

	@Override
	public void use(Player player, Block block, BlockFace face, Entity entity){
		/*
		final Fireball ball = player.launchProjectile(Fireball.class);

		ball.setYield(8F);

		BukkitRunnable task = new BukkitRunnable(){

			@Override
			public void run() {
				if( ! ball.isDead() ){
					Utility.spawnFireworkEffect(ball.getLocation(), Color.YELLOW, Color.RED, Type.BURST, true, true, 0);
				}
			}

		};

		BukkitScheduler scheduler = Bukkit.getScheduler();

		for(long x = 0; x < 100; x = x+2){
			scheduler.scheduleSyncDelayedTask(TheOnePower.plugin, task, x);
		}
		*/
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}

}
