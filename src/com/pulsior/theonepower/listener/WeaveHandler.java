package com.pulsior.theonepower.listener;

import java.util.List;
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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.Channel;
import com.pulsior.theonepower.Element;
import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.unseenland.Memory;

/**
 * Most important event listener that registers weave clicks and makes weaves. Also contains events for some items
 * @author Pulsior
 *
 */
public class WeaveHandler implements Listener{

	Logger log = Bukkit.getLogger();

	String earth = ChatColor.DARK_GREEN + "Earth";
	String air = ChatColor.BLUE + "Air";
	String fire = ChatColor.RED + "Fire";
	String water = ChatColor.AQUA + "Water";
	String spirit = ChatColor.GRAY + "Spirit";


	@SuppressWarnings("deprecation")
	/**
	 * Registers many types of interact events
	 * @param event
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		String name = player.getName();
		if (item != null){	//IF clause for items

			/*
			 * Logs memory names to the console (DEBUG)
			 */
			if(item.getType().equals(Material.STICK)){
				for(Memory mem : TheOnePower.unseenLand.memoryMap.get(name) ){
					log.info(mem.name);
				}
			}
			/*
			 * Logs level progress to the console (DEBUG)
			 */
			if(item.getType().equals(Material.STONE_SPADE) && event.getAction().equals(Action.RIGHT_CLICK_AIR)){
				log.info("The current progress is "+Integer.toString(TheOnePower.power.weaveProgressMap.get(name) ) );
				log.info(Integer.toString(TheOnePower.power.requiredWeavesMap.get(name) ) +" more weaves are required.");
				log.info("The amount of levels is now "+Integer.toString(TheOnePower.power.levelMap.get(name ) ) );
			}

			/*
			 * Allows the player to wake up from and leave the Unseen Land, when the name is correct
			 */
			if(item.getType().equals(Material.NETHER_STAR)){
				if(TheOnePower.unseenLand.players.contains(name)){
					ItemMeta meta = item.getItemMeta();
					if(meta != null){
						if(meta.getDisplayName() . equalsIgnoreCase(ChatColor.RESET+"Wake Up")){
							TheOnePower.unseenLand.removePlayer(name);

						}
					}
				}
			}

			List<String> lore = item.getItemMeta().getLore();
			if(lore != null){
				if(lore.get(0).equalsIgnoreCase(ChatColor.GOLD+"Click to embrace saidar") ){
					String playerName = player.getName();
					if(TheOnePower.channelMap.containsKey(playerName) == false){
						new Channel(playerName, TheOnePower.plugin);
						player.updateInventory();
					}
				}
			}

			/*
			 * Tells the player whether he is in the normal world or in the Unseen Land (DEBUG)
			 */
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

			/*
			 * Teleports the player to a memory location when in the Unseen Land
			 */
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
			/*
			 * Channeling and creating weaves starts here
			 */
			Channel channel;
			String itemName = item.getItemMeta().getDisplayName();
			channel = TheOnePower.channelMap.get( name );
			if (itemName != null && channel != null){			//If the player clicked an element, adds the element to the channel
				if (itemName.equalsIgnoreCase(earth) || itemName.equalsIgnoreCase(air) ||
						itemName.equalsIgnoreCase(water) || itemName.equalsIgnoreCase(fire) || 
						itemName.equalsIgnoreCase(spirit) ){

					Element element = getElementByString(itemName);

					if(element != null){
						channel.addElement(element);
					}

				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Cast Weave")){ //Casts and executes the weave
					channel.cast( event.getClickedBlock(), null );

				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Disband Weave")){ //Clears the weave
					player.playSound(player.getLocation(), Sound.SHEEP_SHEAR, 1, 0);
					channel.disband();
				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Release Saidar")){ //Removes the channel
					channel.close();
					player.updateInventory();
				}
			}
		}
	}

	/*
	 * Some more listeners
	 */

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event){
		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();
		Channel channel = TheOnePower.channelMap.get(player.getName());
		if (channel != null){
			channel.cast(null, entity);
		}
	}


	/**
	 * Method to convert a string to an element
	 * @param element
	 * @return
	 */
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