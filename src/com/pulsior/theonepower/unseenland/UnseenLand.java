package com.pulsior.theonepower.unseenland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.pulsior.theonepower.SaveInventory;
import com.pulsior.theonepower.TheOnePower;

public class UnseenLand {

	Logger log = Bukkit.getLogger();
	public boolean isGenerated = false;
	public boolean enterable = false;
	public List<String> players = new ArrayList<String>();
	UnseenGenTask task = new UnseenGenTask();
	TheOnePower plugin;
	World overworld = Bukkit.getWorld("world");
	World world = Bukkit.getWorld("tel'aran'rhiod");

	public HashMap<String, String> sleepingInventoryMap = new HashMap<String, String>();


	public UnseenLand(TheOnePower plugin){
		this.plugin = plugin;
	}

	public UnseenLand(UnseenLandData data, TheOnePower plugin){
		loadData(data);
		this.plugin = plugin;
		enterable = true;
	}

	public void addPlayer(String playerName){
		Player player = Bukkit.getPlayer(playerName);
		players.add(player.getName());
		Location spawn = player.getBedSpawnLocation();
		if(spawn == null){
			spawn = overworld.getSpawnLocation();
		}
		spawn.setWorld(Bukkit.getWorld("tel'aran'rhiod") );
		player.teleport(spawn);
		player.setPlayerTime(6000, false);
		player.setGameMode(GameMode.ADVENTURE);
		PlayerInventory inventory = player.getInventory();
		player.setAllowFlight(true);
		sleepingInventoryMap.put(playerName, SaveInventory.InventoryToString(inventory));
		inventory.clear();
		inventory.addItem(TheOnePower.returnToken);


	}

	@SuppressWarnings("deprecation")
	public void removePlayer(String playerName){
		Player player = Bukkit.getPlayer(playerName);
		player.setGameMode(GameMode.SURVIVAL);
		players.remove(playerName);
		Location spawn = player.getBedSpawnLocation();
		if(spawn == null){
			spawn = overworld.getSpawnLocation();
		}
		else{
			spawn.setWorld(overworld );
		}
		player.teleport(spawn);
		player.resetPlayerTime();
		player.getInventory().remove(TheOnePower.returnToken );
		player.setAllowFlight(false);

		String invString = sleepingInventoryMap.get( playerName );
		Inventory inventory = player.getInventory();
		ItemStack[] savedInventory = SaveInventory.StringToInventory(invString).getContents();
		for(int y = 0; y < 36; y++){
			ItemStack item = savedInventory[y];
			if (item != null){
				inventory.setItem(y, item);
			}
			else{
				inventory.setItem(y, new ItemStack(Material.AIR));
			}
		}

		player.updateInventory();

		if(players.size() == 0){
			enterable = false;
			log.info("[The One Power] Synchronizing Tel'aran'rhiod with the overworld");
			Bukkit.unloadWorld("tel'aran'rhiod", false);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task);
			enterable = true;
		}

	}

	public void registerPlayer(String playerName){
		Player player = Bukkit.getPlayer(playerName);
		players.add(player.getName());
		player.setPlayerTime(6000, false);
		//player.setAllowFlight(true);

	}

	public void loadData(UnseenLandData data){
		for (String name : data.players){
			if(Bukkit.getPlayer(name) != null){
				registerPlayer(name);
			}
		}
		this.sleepingInventoryMap = data.sleepingInventoryMap;
	}
}
