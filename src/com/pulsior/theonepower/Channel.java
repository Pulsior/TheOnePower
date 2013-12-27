package com.pulsior.theonepower;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Class with one instance for every saidar-embracing player, stored in TheOnePower.channelMap
 * @author Pulsior
 *
 */

public class Channel {

	public Player player;
	public String playerName;
	public List<Element> weave = new ArrayList<Element>();
	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

	Logger log = Bukkit.getLogger();

	boolean crouching;
	boolean gaidinWeaveActive = false;
	boolean healingWeaveActive = false;

	WeaveEffect lastWeave = null;

	TheOnePower plugin;

	PotionEffect nightVisionEffect = new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1);
	PotionEffect absorptionEffect = new PotionEffect(PotionEffectType.ABSORPTION, 1000000, 1);

	PotionEffect waterbreathingEffect = new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1);
	PotionEffect invisiblilityEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1);

	int maxLevel;
	int taskId;
	long taskDuration;

	public Channel(String playerName, TheOnePower plugin){
		player = Bukkit.getPlayer(playerName);
		this.playerName = playerName;
		crouching = false;


		TheOnePower.currentLevelMap.put(playerName, player.getLevel()); //Add the correct levels to the player
		maxLevel = TheOnePower.power.levelMap.get(playerName);
		maxLevel = maxLevel + getAngrealLevels(player);
		player.setLevel(maxLevel);
		player.setExp( ( 1F / (float) TheOnePower.power.requiredWeavesMap.get(playerName)  ) * TheOnePower.power.weaveProgressMap.get(playerName) );

		this.plugin = plugin;
		PlayerInventory inv = player.getInventory();
		inv.clear();

		taskDuration = (long) 60/maxLevel; //Fully regenerating your levels will always take 60 ticks 


		/*
		 * Add items to the inventory 
		 */
		ItemStack spirit = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = spirit.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY+"Spirit");
		spirit.setItemMeta(meta);
		inv.setItem(8, spirit);

		ItemStack earth = new ItemStack(Material.DIRT);
		meta = earth.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GREEN +"Earth");
		earth.setItemMeta(meta);
		inv.setItem(4, earth);

		ItemStack air = new ItemStack(Material.FEATHER);
		meta = air.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE+"Air");
		air.setItemMeta(meta);
		inv.setItem(5, air);

		ItemStack fire = new ItemStack(Material.FIRE);
		meta = fire.getItemMeta();
		meta.setDisplayName(ChatColor.RED+"Fire");
		fire.setItemMeta(meta);
		inv.setItem(6, fire);

		ItemStack water = new ItemStack(Material.WATER);
		meta = water.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA+"Water");
		water.setItemMeta(meta);
		inv.setItem(7, water);

		ItemStack cast = new ItemStack(Material.BLAZE_ROD);
		meta = cast.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Cast Weave");
		List<String> castLore = new ArrayList<String>();
		castLore.add("");
		castLore.add(ChatColor.GOLD+"Use your prepared weave");
		meta.setLore(castLore);
		cast.setItemMeta(meta);
		inv.setItem(0, cast);

		ItemStack disband = new ItemStack(Material.SHEARS);
		meta = disband.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Disband Weave");
		List<String> disbandLore = new ArrayList<String>();
		disbandLore.add("");
		disbandLore.add(ChatColor.GOLD+"Stop weaving and start a new weave");
		meta.setLore(disbandLore);
		disband.setItemMeta(meta);
		inv.setItem(1, disband);

		ItemStack rose = new ItemStack(Material.RED_ROSE);
		meta = rose.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Release Saidar");
		List<String> releaseLore = new ArrayList<String>();
		releaseLore.add("");
		releaseLore.add(ChatColor.GOLD+"Let go of saidar");
		meta.setLore(releaseLore);
		rose.setItemMeta(meta);
		inv.setItem(2, rose);

		player.addPotionEffect(nightVisionEffect);
		player.addPotionEffect(absorptionEffect);

		taskId = scheduler.scheduleSyncRepeatingTask(plugin, regenTask, 0, taskDuration);


	}

	/**
	 * Add an element to a weave
	 * @param element
	 */
	public void addElement(Element element){

		if(player.getLevel() != 0){
			weave.add(element);
			player.setLevel(player.getLevel()-1);
			Location location = player.getLocation();
			location.setX(location.getX()+14);
			player.playSound(location, Sound.CLICK, 1, 0);
		}

		TheOnePower.castingPlayersMap.put(playerName, new Boolean(true) );

	}


	/**
	 * Execute a weave
	 * @param clickedBlock
	 */
	@SuppressWarnings("deprecation")
	public void cast(Block clickedBlock, Entity entity){
		WeaveEffect effect = TheOnePower.weaveList.compare(weave);
		String name = player.getName();

		if( effect.equals(lastWeave) == false &&  effect.equals(WeaveEffect.INVALID) == false ){
			TheOnePower.power.addWeave(player.getName());
			player.setExp( ( 1F / (float) TheOnePower.power.requiredWeavesMap.get(name)  ) * TheOnePower.power.weaveProgressMap.get(name) );
		}

		if(effect != null){
			World world = player.getWorld();
			switch(effect){			
			case LIGHTNING:
				world.strikeLightning(player.getTargetBlock(null, 200).getLocation());
				lastWeave = WeaveEffect.LIGHTNING;
				break;
			case RAIN:			
				world.setStorm(true);
				log.info("sound");
				lastWeave = WeaveEffect.RAIN;
				break;
			case CLEAR_SKY:
				Location location = player.getLocation();
				location.setY(location.getY()+20);
				location.setX(location.getX()+10);
				world.strikeLightning(location);
				location.setX(location.getX()-20);
				world.strikeLightning(location);
				location.setX(location.getX()+10);
				location.setZ(location.getZ()+10);
				world.strikeLightning(location);
				location.setZ(location.getZ()-20);
				world.strikeLightning(location);
				world.setStorm(false);
				lastWeave = WeaveEffect.CLEAR_SKY;
				break;
			case BIND_WOLF_GAIDIN:
				if(entity instanceof Wolf){
					Wolf wolf = (Wolf) entity;
					wolf.setAngry(false);
					wolf.setTamed(true);
					wolf.setOwner(player);
					world.playEffect(wolf.getLocation(), Effect.SMOKE, 0);
				}
				lastWeave = WeaveEffect.BIND_WOLF_GAIDIN;
				break;
			case FIREBALL:
				if(clickedBlock != null){
					createFire(clickedBlock, true);
				}
				else{
					player.launchProjectile(Fireball.class); 
					world.playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 1, 0);
				}
				lastWeave = WeaveEffect.FIREBALL;
				break;
			case HEALING:
				if(entity instanceof Player){
					Player player = (Player) entity;
					double playerHealth = player.getHealth();
					double healthDifference = 20-playerHealth;
					player.setFoodLevel(player.getFoodLevel()- (int) healthDifference);
					if(player.getFoodLevel() < 2){
						player.setFoodLevel(2);
					}
					player.setHealth(20);
					world.playEffect(player.getLocation(), Effect.SMOKE, 0);
				}
				lastWeave = WeaveEffect.HEALING;
				break;
			case WATERBREATHING:
				player.addPotionEffect(waterbreathingEffect);
				player.playSound(player.getLocation(), Sound.DRINK, 1, 0);
				lastWeave = WeaveEffect.WATERBREATHING;
				break;
			case EXTINGUISH_FIRE:
				location = player.getLocation();
				location.setY(location.getY()+2);
				final Block block = world.getBlockAt(location);
				if (block.getType() == Material.AIR){
					block.setType(Material.WATER);

					Runnable task = new Runnable(){
						@Override
						public void run() {
							block.setType(Material.AIR);
						}
					};
					scheduler.scheduleSyncDelayedTask(plugin, task, 10L);
				}

				lastWeave = WeaveEffect.EXTINGUISH_FIRE;
				break;
			case FOLDED_LIGHT:
				player.addPotionEffect(invisiblilityEffect);
				player.playSound(player.getLocation(), Sound.FIZZ, 1, 0);
				lastWeave = WeaveEffect.FOLDED_LIGHT;
				break;
			case TRAVEL:
				player.teleport(player.getTargetBlock(null, 200).getLocation());
				lastWeave = WeaveEffect.TRAVEL;
				break;
			case STRIKE:
				Block block2 = player.getTargetBlock(null, 75);
				location = block2.getLocation();
				world.strikeLightning(location);
				world.createExplosion(location, 6F);
				lastWeave = WeaveEffect.STRIKE;
				break;
			case INVALID:
				double weaveLength = (double) weave.size();
				double playerHealth = player.getHealth() - weaveLength;
				if(playerHealth < 0){
					playerHealth = 0;
				}
				if(weaveLength != 0){
					location = player.getLocation();
					player.playSound(location, Sound.FIZZ, 1, 0);
					world.createExplosion(player.getTargetBlock(null, 1).getLocation(), 0);
					player.setHealth(playerHealth);
				}
				break;
			}
		}
		weave.clear();
		TheOnePower.castingPlayersMap.put(playerName, new Boolean (false) );
	}


	/**
	 * Close and remove the channel
	 */

	public void close(){

		String name = player.getName();
		PlayerInventory inventory = player.getInventory();
		ItemStack[] savedInventory = TheOnePower.embraceInventoryMap.get(name);

		for(int y = 0; y < 36; y++){
			ItemStack item = savedInventory[y];
			if (item != null){
				inventory.setItem(y, item);
			}
			else{
				inventory.setItem(y, new ItemStack(Material.AIR));
			}
		}

		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.setLevel(TheOnePower.currentLevelMap.get( player.getName() ) );
		TheOnePower.currentLevelMap.remove( player.getName() );
		scheduler.cancelAllTasks();
		TheOnePower.channelMap.remove(name);

	}

	public void disband(){
		weave.clear();
		gaidinWeaveActive = false;
		TheOnePower.castingPlayersMap.put(playerName, new Boolean (false) );
	}

	public void toggleItems(){
		crouching = ! (crouching);

		if(crouching){
			
		}
	}

	/**
	 * Returns the amount of extra levels an angreal yields a player
	 * @param player
	 * @return
	 */

	public int getAngrealLevels(Player player){
		PlayerInventory inventory = player.getInventory();
		if(inventory.contains(TheOnePower.angreal)){
			return 10;
		}
		if(inventory.contains(TheOnePower.saAngreal)){
			return 50;
		}
		return 0;
	}

	/**
	 * The task used to regenerate a player's xp level
	 */
	BukkitRunnable regenTask = new BukkitRunnable(){
		@Override
		public void run() {
			if( ! (player.getLevel() >= maxLevel) ) {
				if(TheOnePower.castingPlayersMap.get(playerName).equals(new Boolean(false) ) ){
					player.setLevel( player.getLevel()+1 );;
				}

			}
		};
	};

	/**
	 * Create a fire with a weave
	 * @param block
	 * @param large
	 */

	public void createFire(Block block, boolean large){
		if(large){
			Location location = block.getLocation();
			location.add(-1, 1, -1);

			if(location.getBlock().getType().equals(Material.AIR)){
				location.getBlock().setType(Material.FIRE);
			}

			for(int x = 0; x < 2; x++){
				for(int y = 0; y < 3; y++){
					location.add(0, 0, 1);
					if(location.getBlock().getType().equals(Material.AIR)){
						location.getBlock().setType(Material.FIRE);
					}
				}
				location.add(0, 0, -2);
				location.add(1, 0, 0);
				if(location.getBlock().getType().equals(Material.AIR)){
					location.getBlock().setType(Material.FIRE);
				}
			}
		}
	}


}