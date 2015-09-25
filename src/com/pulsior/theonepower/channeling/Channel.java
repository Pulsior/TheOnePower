package com.pulsior.theonepower.channeling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
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
	public Weave shortcut = null;

	public int maxLevel;
	public int naturalMax;
	public int usedLevels;
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

		if (TheOnePower.power.maxLevelMap.get(id) == null)
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
		maxLevel = TheOnePower.power.maxLevelMap.get(id);
		naturalMax = maxLevel; //The maximum amount of levels a player could hold without the help of items
		usedLevels = TheOnePower.power.usedLevelMap.get(id);
		
		maxLevel = maxLevel + extraLevels;		
		player.setLevel(maxLevel - usedLevels);
		player.setExp((1F / (float) TheOnePower.power.requiredWeavesMap
				.get(id)) *
				TheOnePower.power.weaveProgressMap.get(id));

		PlayerInventory inv = player.getInventory();
		inv.clear();

		taskDuration = (long) 180 / maxLevel; // Fully regenerating your levels
		// will always take 180 ticks

		/*
		 * Add correct items to the player's inventory
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

	public void cast(Block clickedBlock, BlockFace clickedFace, Entity clickedEntity, boolean useShortcut)
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
		
		if (useShortcut && this.shortcut != null)
		{
			effect = this.shortcut;
		}
		
		UUID id = player.getUniqueId();

		if (effect != null && ( effect.getElements() != null || effect.getID().equals("Invalid") ) )
		{			
			
			World world = player.getWorld();
			int xpLevel = player.getLevel();
			int size = weave.size();
			
			if (useShortcut)
			{
				size = shortcut.getElements().size();
			}			
			
			int mult = effect.getLevel().getMultiplier();
			int levelCost = size * mult;
			int difference = xpLevel - levelCost;
			boolean successfullyCast = false;

			if (difference >= 0)
			{
				successfullyCast = effect
						.cast(player, world, clickedBlock, clickedFace, clickedEntity);
				player.setLevel(xpLevel - levelCost);
			}

			if ( ! effect.equals(lastWeave) &&
					! effect.getID().equalsIgnoreCase("Invalid") &&
					successfullyCast)
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

		
		if (maxLevel == naturalMax)
		{
			TheOnePower.power.usedLevelMap.put(id, maxLevel - player.getLevel() );
		}
		else
		{
			TheOnePower.power.usedLevelMap.put(id, naturalMax);
		}
		
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
		shortcut = null;
	}

	@SuppressWarnings("deprecation")
	public void setShortcut()
	{
		Weave weave = WeaveRegistry.compareWeave(this.weave);
		Player player = Bukkit.getPlayer(id);
		
		if (weave.getID().equals("FireSword") )
		{
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD+"A sword made out of the One Power");
			ItemMeta meta = sword.getItemMeta();
			meta.setDisplayName(ChatColor.RED+"Fire-Wrought Sword");
			meta.setLore(lore);
			sword.setItemMeta(meta);
			Channel channel = TheOnePower.database.getChannel(player);
			channel.pickUpItems = true;
			player.getInventory().setItem(3, sword);			
			player.updateInventory();
			channel.pickUpItems = false;
		}
		
		else{
			ChatColor color = weave.getColor();
			String name = weave.getName();
		
			Dye dye = new Dye();
			DyeColor dyeColor;
			
			switch(color)
			{
			case AQUA:
				dyeColor = DyeColor.LIGHT_BLUE;
				break;
			case BLACK:
				dyeColor = DyeColor.BLACK;
				break;
			case BLUE:
				dyeColor = DyeColor.BLUE;
				break;
			case DARK_AQUA:
				dyeColor = DyeColor.BLUE;
				break;
			case DARK_BLUE:
				dyeColor = DyeColor.BLUE;
				break;
			case DARK_GRAY:
				dyeColor = DyeColor.GRAY;
				break;
			case DARK_GREEN:
				dyeColor = DyeColor.GREEN;
				break;
			case DARK_PURPLE:
				dyeColor = DyeColor.PURPLE;
				break;
			case DARK_RED:
				dyeColor = DyeColor.RED;
				break;
			case GOLD:
				dyeColor = DyeColor.YELLOW;
				break;
			case GRAY:
				dyeColor = DyeColor.GRAY;
				break;
			case GREEN:
				dyeColor = DyeColor.GREEN;
				break;
			case LIGHT_PURPLE:
				dyeColor = DyeColor.MAGENTA;
				break;
			case RED:
				dyeColor = DyeColor.RED;
				break;
			case YELLOW:
				dyeColor = DyeColor.YELLOW;
				break;
			default:
				dyeColor = DyeColor.WHITE;
				break;
			
			}
			
			dye.setColor(dyeColor);
			ItemStack shortcut = dye.toItemStack();
			shortcut.setAmount(1);
			
			ItemMeta meta = shortcut.getItemMeta();			
			meta.setDisplayName(color + name);
			
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + "Shortcut to quickly use a weave");
			
			meta.setLore(lore);
			shortcut.setItemMeta(meta);
			player.getInventory().setItem(3, shortcut);
			this.shortcut = weave;
			this.weave.clear();
			resetElements();
		}
	}

}
