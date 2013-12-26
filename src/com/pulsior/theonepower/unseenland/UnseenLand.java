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
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.Channel;
import com.pulsior.theonepower.TheOnePower;

public class UnseenLand {

	Logger log = Bukkit.getLogger();
	public boolean isGenerated = false;
	public boolean enterable = false;
	public List<String> players = new ArrayList<String>();
	public List<String> offlinePlayers = new ArrayList<String>();
	UnseenGenTask task = new UnseenGenTask();
	TheOnePower plugin;
	World overworld = Bukkit.getWorld("world");
	World world = Bukkit.getWorld("tel'aran'rhiod");
	
	public HashMap<String, List<Memory> > memoryMap;
	public HashMap<String, ItemStack[]> unseenLandInventoryMap = new HashMap<String, ItemStack[]>();
	public HashMap<String, ItemStack[]> unseenLandArmorMap = new HashMap<String, ItemStack[]>();


	public UnseenLand(TheOnePower plugin){
		this.plugin = plugin;
		memoryMap = new HashMap<String, List <Memory> >();
	}

	public UnseenLand(UnseenLandData data, TheOnePower plugin){
		loadData(data);
		this.plugin = plugin;
		this.memoryMap = data.memoryMap;
		this.unseenLandInventoryMap = data.unseenLandInventoryMap;
		this.unseenLandArmorMap = data.unseenLandArmorMap;
		enterable = true;
	}

	public void addPlayer(String playerName){
		Player player = Bukkit.getPlayer(playerName);
		players.add(player.getName());
		Channel channel = TheOnePower.channelMap.get(playerName);
		if (channel != null){
			channel.close();
		}
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
		unseenLandInventoryMap.put(playerName, inventory.getContents());
		unseenLandArmorMap.put(playerName, inventory.getArmorContents() );
		setArmor(playerName, inventory, true);
		inventory.clear();
		inventory.addItem(TheOnePower.returnToken);
		addMemoriesToInventory(playerName, inventory);
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
		PlayerInventory inventory = player.getInventory();
		ItemStack[] savedInventory = unseenLandInventoryMap.get(playerName);
		for(int y = 0; y < 36; y++){
			ItemStack item = savedInventory[y];
			if (item != null){
				inventory.setItem(y, item);
			}
			else{
				inventory.setItem(y, new ItemStack(Material.AIR));
			}
		}
		
		setArmor(playerName, inventory, false);
		player.updateInventory();
		

		if(players.size() == 0){
			enterable = false;
			log.info("[The One Power] Synchronizing Tel'aran'rhiod with the overworld");
			Bukkit.unloadWorld("tel'aran'rhiod", true);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task);
			enterable = true;
		}

	}

	public void registerPlayer(String playerName){
		Player player = Bukkit.getPlayer(playerName);
		players.add(playerName);
		player.setPlayerTime(6000, false);
		player.setAllowFlight(true);

	}

	public void loadData(UnseenLandData data){
		for (String name : data.players){
			if(Bukkit.getPlayer(name) != null){
				registerPlayer(name);
			}
			else{
				offlinePlayers.add(name);
			}
		}
	}
	
	/**
	 * Add a memory to a player's mind
	 * @param playerName
	 * @param memory
	 * @return
	 */
	public boolean addMemory(String playerName, Memory memory){
		List<Memory> list = memoryMap.get(playerName);
		if( list.size() <= 6 ){
			Memory testMemory = memoryWithEqualName(playerName, memory);
			if(testMemory != null){
				list.remove(testMemory);
			}
			list.add(memory);
			return true;
		}
		return false;
	}
	
	public Memory memoryWithEqualName(String playerName, Memory memory){
		for(Memory mem : memoryMap.get(playerName) ){
			if(mem.name.equalsIgnoreCase(memory.name) ){
				return mem;
			}
		}
		return null;
	}
	
	/**
	 * Add ghast tears representing memories to an inventory
	 * @param playerName
	 * @param inv
	 */
	public void addMemoriesToInventory(String playerName, Inventory inv){
		List<Memory> memories = memoryMap.get(playerName);
		if(memories == null){
			return;
		}
		int counter = 0;
		for(Memory mem : memories){
			ItemStack memoryTear = new ItemStack(Material.GHAST_TEAR);
			ItemMeta meta = memoryTear.getItemMeta();
			meta.setDisplayName(mem.name);
			memoryTear.setItemMeta(meta);
			inv.setItem(counter+2, memoryTear);
			counter++;
		}
	}
	
	public void setArmor(String playerName, PlayerInventory inv, boolean clear){
		if(clear){
			ItemStack stack = new ItemStack(Material.AIR);
			inv.setHelmet(stack);
			inv.setChestplate(stack);
			inv.setLeggings(stack);
			inv.setBoots(stack);
		}
		else{
			inv.setArmorContents(TheOnePower.unseenLand.unseenLandArmorMap.get(playerName));
		}
	}
}
