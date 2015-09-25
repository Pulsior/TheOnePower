package com.pulsior.theonepower;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PowerMap implements Serializable
{

	/**
	 * Serializable class to save the level a player has, along with other data
	 * regarding level progress
	 */
	private static final long serialVersionUID = 1455786389387906551L;

	public HashMap<UUID, Integer> maxLevelMap;
	public HashMap<UUID, Integer> usedLevelMap;
	public HashMap<UUID, Integer> weaveProgressMap;
	public HashMap<UUID, Integer> requiredWeavesMap;

	public PowerMap()
	{
		maxLevelMap = new HashMap<UUID, Integer>(); //The maximum amount of levels a player can hold
		usedLevelMap = new HashMap<UUID, Integer>(); //The amount of levels that a player has not yet regenerated
		weaveProgressMap = new HashMap<UUID, Integer>(); //The amount of waeves that *have* been performed in order to reach the next level
		requiredWeavesMap = new HashMap<UUID, Integer>(); //The amount of weaves that should be performed to reach the next level
	}

	/**
	 * Add one weave to the progress map, getting the player closer to the next
	 * level
	 * 
	 * @param name
	 */
	public void addWeave(UUID id)
	{
		int amtOfWeaves = weaveProgressMap.get(id);
		amtOfWeaves = amtOfWeaves + 1;
		weaveProgressMap.put(id, amtOfWeaves);
		checkLevel(id);
	}

	/**
	 * Increases the level of a player and sets his progress to zero
	 * 
	 * @param name
	 */
	public void increaseLevel(UUID id)
	{
		int amtOfLevels = maxLevelMap.get(id);
		amtOfLevels = amtOfLevels + 1;
		maxLevelMap.put(id, amtOfLevels);
		TheOnePower.database.getChannel(id).maxLevel = amtOfLevels;
		Player player = Bukkit.getPlayer(id);
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 0);
	}

	/**
	 * Adds a player to the map with default xp data
	 * 
	 * @param name
	 */
	public void addPlayer(UUID id)
	{
		maxLevelMap.put(id, 3);
		usedLevelMap.put(id, 0);
		weaveProgressMap.put(id, 0);
		requiredWeavesMap.put(id, 3);
	}

	/**
	 * Checks whether the level of a player should be increased
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkLevel(UUID id)
	{
		int amountOfWeaves = weaveProgressMap.get(id);
		int requiredWeaves = requiredWeavesMap.get(id);

		if (amountOfWeaves == requiredWeaves)
		{
			weaveProgressMap.put(id, 0);
			requiredWeavesMap.put(id, requiredWeaves + 2);
			increaseLevel(id);
			return true;
		}

		return false;
	}

}
