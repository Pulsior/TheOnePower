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
 * Class with one instance for every saidar-embracing player, stored in TheOnePower.channelMap.
 * @author Pulsior
 *
 */

public class Channel {

	public Player player;
	public List<Element> weave = new ArrayList<Element>();
	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	static boolean nowCasting = false;

	Logger log = Bukkit.getLogger();

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


		TheOnePower.currentLevelMap.put(playerName, player.getLevel());
		maxLevel = TheOnePower.power.levelMap.get(playerName);

		maxLevel = maxLevel + getAngrealLevels(player);

		this.plugin = plugin;
		PlayerInventory inv = player.getInventory();
		inv.clear();

		taskDuration = (long) 60/maxLevel;

		player.setLevel(maxLevel);

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
		cast.setItemMeta(meta);
		inv.setItem(0, cast);

		ItemStack disband = new ItemStack(Material.SHEARS);
		meta = disband.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Disband Weave");
		disband.setItemMeta(meta);
		inv.setItem(1, disband);

		ItemStack rose = new ItemStack(Material.RED_ROSE);
		meta = rose.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Release Saidar");
		rose.setItemMeta(meta);
		inv.setItem(2, rose);

		player.addPotionEffect(nightVisionEffect);
		player.addPotionEffect(absorptionEffect);

		taskId = scheduler.scheduleSyncRepeatingTask(plugin, regenTask, 0, taskDuration);


	}

	public void addElement(Element element){

		if(player.getLevel() != 0){
			weave.add(element);
			player.setLevel(player.getLevel()-1);
			Location location = player.getLocation();
			location.setX(location.getX()+14);
			player.playSound(location, Sound.CLICK, 1, 0);
		}

		nowCasting = true;

	}

	@SuppressWarnings("deprecation")
	public void cast(Block clickedBlock){
		WeaveEffect effect = TheOnePower.weaveList.compare(weave);

		if( effect.equals(lastWeave) == false &&  effect.equals(WeaveEffect.INVALID) == false ){
			TheOnePower.power.addWeave(player.getName());
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
				gaidinWeaveActive = true;
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
				healingWeaveActive = true;
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
		nowCasting = false;
	}

	public void cast(Entity entity){
		World world = player.getWorld();
		if(gaidinWeaveActive == true && entity instanceof Wolf){
			Wolf wolf = (Wolf) entity;
			wolf.setAngry(false);
			wolf.setTamed(true);
			wolf.setOwner(player);
			world.playEffect(wolf.getLocation(), Effect.SMOKE, 0);
			gaidinWeaveActive = false;
		}
		else if (healingWeaveActive == true && entity instanceof Player){
			Player player = (Player) entity;
			double playerHealth = player.getHealth();
			double healthDifference = 20-playerHealth;
			player.setFoodLevel(player.getFoodLevel()- (int) healthDifference);
			if(player.getFoodLevel() < 2){
				player.setFoodLevel(2);
			}
			player.setHealth(20);
			world.playEffect(player.getLocation(), Effect.SMOKE, 0);
			healingWeaveActive = false;
		}

	}


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
		nowCasting = false;
	}

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

	BukkitRunnable regenTask = new BukkitRunnable(){
		@Override
		public void run() {
			if( ! (player.getLevel() >= maxLevel) && Channel.nowCasting == false){
				player.setLevel( player.getLevel()+1 );;
			}
		};
	};

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