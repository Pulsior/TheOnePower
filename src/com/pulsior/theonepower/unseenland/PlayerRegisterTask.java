package com.pulsior.theonepower.unseenland;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
