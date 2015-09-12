package com.pulsior.theonepower.channeling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.api.WeaveRegistry;
import com.pulsior.theonepower.item.AngrealType;
import com.pulsior.theonepower.task.PlayerRegenerationTask;
import com.pulsior.theonepower.weaves.Weave;

/**
 * Class with one instance for every saidar-embracing player, stored in
 * TheOnePower.channelMap
 * 
 * @author Pulsior
 * 
 */

public class Channel implements Serializable
{

	private static final long serialVersionUID = 5639217340010068020L;

	public static final Material EARTH = Material.COAL;
	public static final Material AIR = Material.FEATHER;
	public static final Material FIRE = Material.FIREBALL;
	public static final Material WATER = Material.WATER_BUCKET;
	public static final Material SPIRIT = Material.NETHER_STAR;
	
	public boolean pickUpItems = false;
	public UUID id;
	public List<Element> weave = new ArrayList<Element>();
	public int taskId;
	public ItemStack[] normalInventory;

	String elementString;
	boolean isCasting;

	int normalExpLevel;
	float normalExpProgress;

	Weave lastWeave = null;

	public int maxLevel;
	long taskDuration;

	@SuppressWarnings("deprecation")
	public Channel(UUID id, int extraLevels)
	{

		Player player = Bukkit.getPlayer(id);
		this.id = id;

		player.updateInventory();

		isCasting = false;

		/*
		 * Set up the data in PowerMap
		 */

		if (TheOnePower.power.levelMap.get(id) == null)
		{
			TheOnePower.power.addPlayer(id);
		}

		normalExpProgress = player.getExp();

		/*
		 * Store the inventory in a HashMap
		 */

		PlayerInventory inventory = player.getInventory();
		normalInventory = inventory.getContents();

		TheOnePower.database.addChannel(id, this);

		normalExpLevel = player.getLevel(); // Add the correct levels to the
											// player
		maxLevel = TheOnePower.power.levelMap.get(id);
		maxLevel = maxLevel + extraLevels;
		player.setLevel(maxLevel);
		player.setExp((1F / (float) TheOnePower.power.requiredWeavesMap
				.get(id)) *
				TheOnePower.power.weaveProgressMap.get(id));

		PlayerInventory inv = player.getInventory();
		inv.clear();

		taskDuration = (long) 120 / maxLevel; // Fully regenerating your levels
												// will always take 120 ticks

		/*
		 * Add items to the inventory
		 */

		ItemStack spirit = new ItemStack(SPIRIT);
		ItemMeta meta = spirit.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "Spirit");
		spirit.setItemMeta(meta);
		inv.setItem(8, spirit);

		ItemStack earth = new ItemStack(EARTH);
		meta = earth.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GREEN + "Earth");
		earth.setItemMeta(meta);
		inv.setItem(4, earth);

		ItemStack air = new ItemStack(AIR);
		meta = air.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Air");
		air.setItemMeta(meta);
		inv.setItem(5, air);

		ItemStack fire = new ItemStack(FIRE);
		meta = fire.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Fire");
		fire.setItemMeta(meta);
		inv.setItem(6, fire);

		ItemStack water = new ItemStack(WATER);
		meta = water.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Water");
		water.setItemMeta(meta);
		inv.setItem(7, water);

		ItemStack cast = new ItemStack(Material.BLAZE_ROD);
		meta = cast.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Cast Weave");
		List<String> castLore = new ArrayList<String>();
		castLore.add(ChatColor.GOLD + "Use your prepared weave");
		meta.setLore(castLore);
		cast.setItemMeta(meta);
		inv.setItem(0, cast);

		ItemStack disband = new ItemStack(Material.SHEARS);
		meta = disband.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Disband Weave");
		List<String> disbandLore = new ArrayList<String>();
		disbandLore.add(ChatColor.GOLD + "Stop weaving and start a new weave");
		meta.setLore(disbandLore);
		disband.setItemMeta(meta);
		inv.setItem(1, disband);

		ItemStack rose = new ItemStack(Material.RED_ROSE);
		meta = rose.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Release Saidar");
		List<String> releaseLore = new ArrayList<String>();
		releaseLore.add(ChatColor.GOLD + "Let go of saidar");
		meta.setLore(releaseLore);
		rose.setItemMeta(meta);
		inv.setItem(2, rose);

		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1000000, 1));

		BukkitRunnable regenerationTask = new PlayerRegenerationTask(player, maxLevel);
		regenerationTask.runTaskTimer(TheOnePower.plugin, 0, taskDuration);
		TheOnePower.taskHolder.put(id, regenerationTask);

		resetElements();

		player.updateInventory();
	}

	/**
	 * Add an element to a weave
	 * 
	 * @param element
	 */
	@SuppressWarnings("deprecation")
	public void addElement(Element element, String elementName)
	{

		Player player = Bukkit.getPlayer(id);

		if (player.getLevel() != 0)
		{
			weave.add(element);

			Location location = player.getLocation();
			location.setX(location.getX() + 14);
			player.playSound(location, Sound.CLICK, 1, 0);

			if (elementString == null)
			{
				elementString = elementName;
			}

			else
			{
				elementString += ", " + elementName;
			}

			PlayerInventory inv = player.getInventory();

			for (int x = 4; x < 9; x++)
			{
				ItemStack item = inv.getItem(x);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(elementString);
				item.setItemMeta(meta);

				player.updateInventory();

			}
		}

		isCasting = true;
	}

	@SuppressWarnings("deprecation")
	public void resetElements()
	{

		Player player = Bukkit.getPlayer(id);

		PlayerInventory inv = player.getInventory();

		for (int x = 4; x < 9; x++)
		{
			ItemStack item = inv.getItem(x);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("  ");
			item.setItemMeta(meta);

			player.updateInventory();

		}

		elementString = null;
	}

	/**
	 * Execute a weave
	 * 
	 * @param clickedBlock
	 */

	public void cast(Block clickedBlock, BlockFace clickedFace, Entity clickedEntity)
	{

		Player player = Bukkit.getPlayer(id);

		if (Stedding.getStedding(player.getLocation()) != null)
		{
			player.sendMessage(ChatColor.RED +
					"The One Power slipped away, surely I am in a stedding.");
			close();
			return;
		}

		Weave effect = WeaveRegistry.compareWeave(weave);
		UUID id = player.getUniqueId();

		if (effect != null)
		{
			World world = player.getWorld();
			int xpLevel = player.getLevel();
			int levelCost = weave.size() * effect.getLevel().getMultiplier();
			int difference = xpLevel - levelCost;
			boolean casted = false;

			if (difference >= 0)
			{
				casted = effect
						.cast(player, world, clickedBlock, clickedFace, clickedEntity);
				player.setLevel(xpLevel - levelCost);
			}

			if ( ! effect.equals(lastWeave) &&
					! effect.getID().equalsIgnoreCase("Invalid") &&
					casted)
			{
				TheOnePower.power.addWeave(id);
				player.setExp((1F / (float) TheOnePower.power.requiredWeavesMap
						.get(id)) *
						TheOnePower.power.weaveProgressMap.get(id));
			}

			lastWeave = effect;

		}

		weave.clear();
		resetElements();

		isCasting = false;
	}

	public void update()
	{
		BukkitRunnable regenerationTask = new PlayerRegenerationTask(Bukkit
				.getPlayer(id), maxLevel);
		regenerationTask.runTaskTimer(TheOnePower.plugin, 0, taskDuration);
		TheOnePower.taskHolder.put(id, regenerationTask);
	}

	/**
	 * Close and remove the channel
	 */

	public void close()
	{

		Player player = Bukkit.getPlayer(id);
		PlayerInventory inventory = player.getInventory();
		inventory.setContents(normalInventory);

		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.setLevel(normalExpLevel);
		player.setExp(normalExpProgress);

		BukkitRunnable task = TheOnePower.taskHolder.get(id);

		if (task != null)
		{
			task.cancel();
		}

		TheOnePower.taskHolder.remove(id);
		TheOnePower.database.removeChannel(id);

	}

	public void disband()
	{

		Player player = Bukkit.getPlayer(id);
		player.playSound(player.getLocation(), Sound.SHEEP_SHEAR, 1, 0);
		weave.clear();
		resetElements();
		isCasting = false;
	}

	/**
	 * Returns the amount of extra levels an angreal yields a player
	 * 
	 * @param player
	 * @return
	 */

	public int getAngrealLevels(Player player)
	{
		PlayerInventory inventory = player.getInventory();

		int level = 0;

		for (AngrealType type : AngrealType.values())
		{
			if (inventory.contains(type.getItem()))
			{
				int typeLevel = type.getLevel();
				if (typeLevel > level)
				{
					level = typeLevel;
				}
			}
		}

		return level;
	}

	/**
	 * Get if the player is casting a weave
	 */

	public boolean isCasting()
	{
		return isCasting;
	}
	
	//Workaround for non-serializable field lastWeave
	//TODO permanent solution
	public void fix()
	{
		lastWeave = null;
	}

}
