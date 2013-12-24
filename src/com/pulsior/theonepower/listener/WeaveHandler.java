package com.pulsior.theonepower.listener;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.Channel;
import com.pulsior.theonepower.Element;
import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.unseenland.Memory;

public class WeaveHandler implements Listener{

	Logger log = Bukkit.getLogger();

	String earth = ChatColor.DARK_GREEN + "Earth";
	String air = ChatColor.BLUE + "Air";
	String fire = ChatColor.RED + "Fire";
	String water = ChatColor.AQUA + "Water";
	String spirit = ChatColor.GRAY + "Spirit";


	@SuppressWarnings("deprecation")
	@EventHandler
	public void onWeave(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		String name = player.getName();
		if (item != null){
			if(item.getType().equals(Material.STICK)){
				for(Memory mem : TheOnePower.unseenLand.memoryMap.get(name) ){
					log.info(mem.name);
				}
				return; 
			}
			if(item.getType().equals(Material.STONE_SPADE) && event.getAction().equals(Action.RIGHT_CLICK_AIR)){
				log.info("The current progress is "+Integer.toString(TheOnePower.power.weaveProgressMap.get(name) ) );
				log.info(Integer.toString(TheOnePower.power.requiredWeavesMap.get(name) ) +" more weaves are required.");
				log.info("The amount of levels is now "+Integer.toString(TheOnePower.power.levelMap.get(name ) ) );
			}
			if(item.getType().equals(Material.NETHER_STAR)){
				if(TheOnePower.unseenLand.players.contains(name)){
					ItemMeta meta = item.getItemMeta();
					if(meta != null){
						if(meta.getDisplayName() . equalsIgnoreCase(ChatColor.RESET+"Wake Up")){
							log.info("Waking up!");
							TheOnePower.unseenLand.removePlayer(name);

						}
					}
				}
			}

			if(item.getType().equals(Material.BLAZE_POWDER)){
				if(player.getWorld().getName().equals("tel'aran'rhiod") ) {
					player.sendMessage("In the Unseen Land, according to your world name");
				}
				else{
					player.sendMessage("In the real world, according to your world name");
				}
				if(TheOnePower.unseenLand.players.contains(name) ){
					player.sendMessage("In the Unseen Land, according to the UnseenLand ArrayList");
				}
				else{
					player.sendMessage("In the real world, according to the UnseenLand ArrayList");
				}

			}

			if(item.getType().equals(Material.GHAST_TEAR) && TheOnePower.unseenLand.players.contains(name)){
				String memoryName = item.getItemMeta().getDisplayName();
				if(memoryName != null){
					List<Memory> list = TheOnePower.unseenLand.memoryMap.get(name);
					for(Memory memory : list){
						if(memory.name.equals(memoryName) ){
							player.teleport(memory.getLocation() );
							player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
						}
					}

				}
			}
			Channel channel;
			String itemName = item.getItemMeta().getDisplayName();
			channel = TheOnePower.channelMap.get( name );
			if (itemName != null && channel != null){
				if (itemName.equalsIgnoreCase(earth) || itemName.equalsIgnoreCase(air) ||
						itemName.equalsIgnoreCase(water) || itemName.equalsIgnoreCase(fire) || 
						itemName.equalsIgnoreCase(spirit) ){

					Element element = getElementByString(itemName);				
					if(element != null){
						channel.addElement(element);
					}
				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Cast Weave")){

					channel.cast( event.getClickedBlock() );

				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Disband Weave")){
					player.playSound(player.getLocation(), Sound.SHEEP_SHEAR, 1, 0);
					channel.disband();
				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Release Saidar")){
					channel.close();
					player.updateInventory();
				}
			}
		}
	}




	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event){
		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();
		Channel channel = TheOnePower.channelMap.get(player.getName());
		if (channel != null){
			channel.cast(entity);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = (Player) event.getEntity();
		String name = player.getName();
		Channel channel = TheOnePower.channelMap.get(player.getName());
		if (channel != null){
			channel.close();
			event.getDrops().clear();
		}

		int randomInt = new Random().nextInt(100);
		if (randomInt == 0){
			player.sendMessage("<Ba'alzamon> I have won again, Lews Therin.");

		}

		if( TheOnePower.unseenLand.players.contains(name ) ){
			TheOnePower.unseenLand.removePlayer(name);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if (TheOnePower.channelMap.get(player.getName()) != null){
			Material material = event.getBlock().getType();			
			switch(material){
			case DIRT:
				event.setCancelled(true);
				break;
			case FIRE:
				event.setCancelled(true);
				break;
			case WATER:
				event.setCancelled(true);
				break;
			case RED_ROSE:
				event.setCancelled(true);
				break;
			default:
				break;
			}
			player.updateInventory();

		}

	}

	@EventHandler
	public void onExpGet(PlayerExpChangeEvent event){
		if(TheOnePower.channelMap.get(event.getPlayer().getName()) != null){
			event.setAmount(0);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Channel channel = TheOnePower.channelMap.get(event.getPlayer().getName());
		if (channel != null){
			channel.close();
		}
	}



	public Element getElementByString(String element){
		if(element.equalsIgnoreCase(earth)){
			return Element.EARTH;
		}
		if(element.equalsIgnoreCase(air)){
			return Element.AIR;
		}
		if(element.equalsIgnoreCase(fire)){
			return Element.FIRE;
		}
		if(element.equalsIgnoreCase(water)){
			return Element.WATER;
		}
		if(element.equalsIgnoreCase(spirit)){
			return Element.SPIRIT;
		}
		return null;
	}



}