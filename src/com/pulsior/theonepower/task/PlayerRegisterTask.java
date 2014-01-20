package com.pulsior.theonepower.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Task to register a player. This is a task, so it can be delayed for a few ticks after login
 * @author Pulsior
 *
 */
public class PlayerRegisterTask extends BukkitRunnable{

	String name;
	
	public PlayerRegisterTask(String name){
		this.name = name;
	}
	
	@Override
	public void run() {
		Player player = Bukkit.getPlayer(name);
		player.setPlayerTime(6000, false);
		player.setAllowFlight(true);
	}

}
