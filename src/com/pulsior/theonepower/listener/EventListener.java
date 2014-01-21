package com.pulsior.theonepower.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.pulsior.theonepower.Direction;
import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.Utility;
import com.pulsior.theonepower.channeling.weave.Portal;
import com.pulsior.theonepower.item.PowerItem;
import com.pulsior.theonepower.task.PlayerRegisterTask;
import com.pulsior.theonepower.unseenland.Memory;

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

	HashMap<String, Integer> map = new HashMap<String, Integer>();
	BukkitScheduler scheduler = Bukkit.getScheduler();

	public EventListener(TheOnePower plugin){
		this.plugin = plugin;
	}


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

				if( inventory.contains( PowerItem.DREAM_ANGREAL ) ){
					player.setBedSpawnLocation( player.getLocation() );
					TheOnePower.unseenLand.addPlayer(playerName);

				}
			}
		};

		int taskId = scheduler.scheduleSyncDelayedTask(plugin, task, 100L);
		map.put(player.getName(),taskId);

	}

	@EventHandler
	public void onPlayerBedLeave(PlayerBedLeaveEvent event){
		Integer taskId = map.get( event.getPlayer().getName() );
		if(taskId != null){
			scheduler.cancelTask(taskId);
		}
	}

	/**
	 * Select a memory from the GUI with the traveling weave.
	 * @param event
	 */

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInventorySelect(InventoryClickEvent event){
		
		Inventory inv = event.getInventory();
		InventoryHolder inventoryHolder = inv.getHolder();
		
		if(inventoryHolder instanceof Player && inv.getSize() == 9 ){
			
			ItemStack item = inv.getItem(event.getSlot() );

			Player player = (Player) inv.getHolder();
			float yaw = player.getLocation().getYaw();
			if(item != null){


				ItemMeta meta = item.getItemMeta();
				if( meta.hasLore() ){


					if( meta.getLore().contains (ChatColor.YELLOW+"Click to select a gateway destination") ){

						String name = player.getName();
						List<Memory> list = TheOnePower.unseenLand.memoryMap.get(name);
						if(list != null){

							for(Memory memory : list){

								if(memory.name.equals(meta.getDisplayName()) ){

									Location spawnLocation = player.getTargetBlock(null, 5).getLocation();
									Location destination = memory.getLocation(false);
									Direction direction = Utility.getDirection(yaw);
									new Portal(spawnLocation, destination,  player.getLocation(), direction);
									player.closeInventory();
								}
							}
						}
					}
					event.setCancelled(true);
				}
			}
		}

	}

	/**
	 * Listener method for the Traveling weave
	 * @param event
	 */

	@EventHandler
	public void onPortalEnter(EntityPortalEnterEvent event){

		Block block = event.getLocation().getBlock();
		List<MetadataValue> meta = block.getMetadata("isGateway");
		for(MetadataValue value : meta){
			if(value.asBoolean() == true){
				Location destination = (Location) block.getMetadata("getLocation").get(0).value();
				Entity entity = event.getEntity();
				if(entity != null){
					entity.teleport(destination);
				}
			}
		}
	}

	/**
	 * Add player to the Unseen Land if necessary.
	 * @param event
	 */

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		String name = player.getName();

		if( TheOnePower.unseenLand.offlinePlayers.contains(name)  ){
			TheOnePower.unseenLand.offlinePlayers.remove(name);
			TheOnePower.unseenLand.players.add(name);
			scheduler.scheduleSyncDelayedTask(plugin, new PlayerRegisterTask(name), 20L );
		}

		if(TheOnePower.unseenLand.memoryMap.get(name) == null){
			TheOnePower.unseenLand.memoryMap.put(name, new ArrayList<Memory>() );
		}
	}

	
	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent event){
		
		BlockState[] tileEntities = event.getChunk().getTileEntities();
		
		for(BlockState state : tileEntities){
			
			if(state instanceof Chest){
				Chest chest = (Chest) state;
				Inventory inventory = chest.getBlockInventory();
				
				System.out.println("Generated a chest!");
				
				if( Utility.chance(40) ){
					inventory.addItem(PowerItem.ANGREAL);
					System.out.println("Added an angreal");
				}
				
				if( Utility.chance(15)){
					inventory.addItem(PowerItem.SA_ANGREAL);
					System.out.println("Added a sa'angreal");
				}
				
				if( Utility.chance(5) ){
					inventory.addItem(PowerItem.CALLANDOR);
					System.out.println("Added Callandor");
				}
								
				
			}	
		}
	}
	
	/**
	 * Generate the Unseen Land when the overworld has loaded.
	 * @param event
	 */

	@EventHandler
	public void onWorldLoad(WorldLoadEvent event){
		World world = event.getWorld();
		if(world.getName().equalsIgnoreCase("world") ){

			BukkitRunnable task = new BukkitRunnable(){
				@Override
				public void run() {
					plugin.createUnseenLand();
				}
			};
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task);

		}
	}

	/**
	 * Add mob drops
	 * @param event
	 */

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		LivingEntity entity = event.getEntity();
		EntityType type = entity.getType();

		if(type.equals(EntityType.ZOMBIE) || type.equals(EntityType.SKELETON)){

			if(new Random().nextInt(10) == 0){
				event.getDrops().add(PowerItem.DREAM_ANGREAL);
			}

		}

	}

	/**
	 * Makes sure a gateway does not disappear immediately.
	 * @param event
	 */

	@EventHandler
	public void onPortalChange(BlockPhysicsEvent event){
		Block block = event.getBlock();
		List<MetadataValue> list = block.getMetadata("isGateway");		
		for(MetadataValue value : list){
			if(value.asBoolean() == true){
				event.setCancelled(true);
			}
		}


	}

}