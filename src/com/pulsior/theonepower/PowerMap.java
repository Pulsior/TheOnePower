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

	public HashMap<UUID, Integer> levelMap;
	public HashMap<UUID, Integer> weaveProgressMap;
	public HashMap<UUID, Integer> requiredWeavesMap;

	public PowerMap()
	{
		levelMap = new HashMap<UUID, Integer>();
		weaveProgressMap = new HashMap<UUID, Integer>();
		requiredWeavesMap = new HashMap<UUID, Integer>();
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
		int amtOfLevels = levelMap.get(id);
		amtOfLevels = amtOfLevels + 1;
		levelMap.put(id, amtOfLevels);
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
		levelMap.put(id, 3);
		weaveProgressMap.put(id, 0);
		requiredWeavesMap.put(id, 3);
	}

	/**
	 * Checks whether the level of a player should be increased
	 * 
	 * @param name
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
