package com.pulsior.theonepower.listener;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.PlayerInventory;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.unseenland.Memory;
import com.pulsior.theonepower.unseenland.PlayerRegisterTask;

public class EventListener implements Listener {

	TheOnePower plugin;

	String earth = ChatColor.DARK_GREEN + "Earth";
	String air = ChatColor.BLUE + "Air";
	String fire = ChatColor.RED + "Fire";
	String water = ChatColor.AQUA + "Water";
	String spirit = ChatColor.GRAY + "Spirit";

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
					player.setBedSpawnLocation( player.getLocation() );
					TheOnePower.unseenLand.addPlayer(playername);
					log.info("TP to tel'aran! ");
				}


			}
		};
		try{
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task, 100L);
		}
		catch(NullPointerException ex){

		}
	}
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event){
		String name = event.getPlayer().getName();
		if(TheOnePower.channelMap.containsKey(name) ){
			event.setCancelled(true);
		}
		else if(TheOnePower.unseenLand.players.contains(name) ){
			event.setCancelled(true);
		}
	}



	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		String name = event.getPlayer().getName();
		if(TheOnePower.channelMap.containsKey(name ) ){
			event.setCancelled(true);
		}
		else if(TheOnePower.unseenLand.players.contains(name) ){
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
		if( TheOnePower.unseenLand.offlinePlayers.contains(name)  ){
			TheOnePower.unseenLand.offlinePlayers.remove(name);
			TheOnePower.unseenLand.players.add(name);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new PlayerRegisterTask(name), 20L );
		}
		
		if(TheOnePower.unseenLand.memoryMap.get(name) == null){
			TheOnePower.unseenLand.memoryMap.put(name, new ArrayList<Memory>() );
		}
	}
}
