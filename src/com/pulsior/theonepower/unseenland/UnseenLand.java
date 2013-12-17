package com.pulsior.theonepower.unseenland;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.pulsior.theonepower.TheOnePower;

public class UnseenLand {

	Logger log = Bukkit.getLogger();
	public boolean isGenerated = false;
	public List<String> players = new ArrayList<String>();

	World world = Bukkit.getWorld("tel'aran'rhiod");

	public UnseenLand(){
		if(world != null){
			world.setTime(20000);
		}
	}
	
	public UnseenLand(UnseenLandData data){
		if(world != null){
			world.setTime(20000);
		}
		loadData(data);
	}

	public void addPlayer(String playerName){
		Player player = Bukkit.getPlayer(playerName);

		players.add(player.getName());
		player.teleport(Bukkit.getWorld("tel'aran'rhiod").getSpawnLocation());
		player.setPlayerTime(6000, false);
		player.setGameMode(GameMode.ADVENTURE);
		PlayerInventory inventory = player.getInventory();
		inventory.addItem(TheOnePower.returnToken);

	}

	public void removePlayer(String playerName){
		Player player = Bukkit.getPlayer(playerName);
		player.setGameMode(GameMode.SURVIVAL);
		players.remove(playerName);
		Location spawn = player.getBedSpawnLocation();
		player.teleport(spawn);
		player.resetPlayerTime();
	}

	public void loadData(UnseenLandData data){
		for (String name : data.players){
			if(Bukkit.getPlayer(name) != null){
			addPlayer(name);
			}
		}
	}


}
