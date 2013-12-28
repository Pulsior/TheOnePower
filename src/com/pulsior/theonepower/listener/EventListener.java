package com.pulsior.theonepower.listener;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.PlayerInventory;

import com.pulsior.theonepower.Channel;
import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.unseenland.Memory;
import com.pulsior.theonepower.unseenland.PlayerRegisterTask;

/**
 * Event listener for uses other then creating-casting weaves and limiting saidar/embracing players
 * @author Pulsior
 *
 */
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
		final String playerName = player.getName();		
		
		if(TheOnePower.unseenLand.players.contains(playerName)){
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"You can't sleep in tel'aran'rhiod!");
		}
		
		Runnable task = new Runnable(){
			@Override
			public void run() {
				PlayerInventory inventory = player.getInventory();

				if( inventory.contains( TheOnePower.dreamAngreal ) ){
					player.setBedSpawnLocation( player.getLocation() );
					TheOnePower.unseenLand.addPlayer(playerName);
					
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
	
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event){
		Channel channel = TheOnePower.channelMap.get(event.getPlayer().getName());
		if (channel != null){
			channel.toggleItems();
		}
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		LivingEntity entity = event.getEntity();
		EntityType type = entity.getType();
		if(type.equals(EntityType.ZOMBIE) || type.equals(EntityType.SKELETON)){
			if(new Random().nextInt(10) == 0){
				event.getDrops().add(TheOnePower.dreamAngreal);
			}
		}
	}
}
