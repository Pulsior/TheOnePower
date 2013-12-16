package com.pulsior.theonepower.listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.inventory.PlayerInventory;

import com.pulsior.theonepower.TheOnePower;

public class EventListener implements Listener {

	TheOnePower plugin;

	public EventListener(TheOnePower plugin){
		this.plugin = plugin;
	}

	Logger log = Bukkit.getLogger();

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event){
		final Player player = event.getPlayer();
		final String playername = player.getName();
		log.info("Bed event fired!");

		Runnable task = new Runnable(){
			@Override
			public void run() {
				log.info("Task executed!");
				PlayerInventory inventory = player.getInventory();
				
				if( inventory.contains( TheOnePower.dreamAngreal ) ){
					  TheOnePower.unseenLand.addPlayer(playername);
					  log.info("TP to tel'aran! ");
				}


			}
		};
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task, 100L);
	}
}
