package com.pulsior.theonepower.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.weave.AesSedai;
import com.pulsior.theonepower.channeling.weave.Portal;
import com.pulsior.theonepower.channeling.weave.Warder;
import com.pulsior.theonepower.unseenland.Memory;

/**
 * Most important event listener that registers weave clicks and makes weaves. Also contains events for some items
 * @author Pulsior
 *
 */
public class WeaveHandler implements Listener{


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

			if(item.getItemMeta().hasDisplayName()){
				if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RESET+"Callandor") ){
					item.setDurability((short) 0);
				}
			}

			if(item.getType().equals(Material.STICK)){
				Channel channel = TheOnePower.channelMap.get(name);
				if(channel != null){
					System.out.println("The max level is "+channel.maxLevel+", the current level is "+player.getLevel());
					System.out.println("Is the task running? "+Bukkit.getScheduler().isCurrentlyRunning(channel.taskId) );
					System.out.println("Is it queued? "+Bukkit.getScheduler().isQueued(channel.taskId) );
					
					try{
						System.out.println("Is the player casting, according to the castingPlayerMap? "+TheOnePower.castingPlayersMap.get(name));
					}
					catch(NullPointerException ex){
						System.out.println("A NullPointerException was thrown when trying to access TheOnePower.castingPlayersMap");
					}
				}
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
			
			/*
			 * Reset compass location for warders and warder-holding Aes Sedai
			 */
			if( item.getType().equals(Material.STICK) ){
				
				for(Warder warder: TheOnePower.warders){

					AesSedai aesSedai = warder.getAesSedai();
					
					if( warder.getPlayer().getName().equals(name)){
						player.setCompassTarget(warder.getAesSedai().getPlayer().getLocation());
						return;
					}
					
					else if ( aesSedai.getPlayer().getName().equals(name)){
						player.setCompassTarget(warder.getPlayer().getLocation());
					}
					
				}
			}

			List<String> lore = item.getItemMeta().getLore();
			if(lore != null){
				if(lore.get(0).equalsIgnoreCase(ChatColor.GOLD+"Click to embrace saidar") && ( event.getAction().equals(Action.RIGHT_CLICK_AIR) ) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
					String playerName = player.getName();
					if(TheOnePower.channelMap.containsKey(playerName) == false){
						if( ! ( TheOnePower.shieldedPlayersMap.containsKey( playerName ) ) ){
							if(player.hasPermission("theonepower.channel")){
								event.setCancelled(true);
								new Channel(playerName);					
							}
							else{
								player.sendMessage(ChatColor.RED+"You don't have permission to embrace saidar");
							}
						}
						else{
							player.sendMessage(ChatColor.RED+"You can feel the True Source, but you can't touch it");
						}
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
							player.teleport(memory.getLocation(true) );
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
			Action action = event.getAction();
			if (itemName != null && channel != null && ( action.equals(Action.RIGHT_CLICK_AIR) ||
					event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ) ){			//If the player clicked an element, adds the element to the channel

				if (itemName.equalsIgnoreCase(earth) || itemName.equalsIgnoreCase(air) ||
						itemName.equalsIgnoreCase(water) || itemName.equalsIgnoreCase(fire) || 
						itemName.equalsIgnoreCase(spirit) ){

					Element element = getElementByString(itemName);

					if(element != null){
						channel.addElement(element);
					}

				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Cast Weave")){ //Casts and executes the weave
					channel.cast( event.getClickedBlock(), event.getBlockFace(), null );


				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Disband Weave")){ //Clears the weave
					player.playSound(player.getLocation(), Sound.SHEEP_SHEAR, 1, 0);
					channel.disband();

					Block block = event.getClickedBlock();

					if(block != null){
						for(Portal portal : TheOnePower.portals){
							if(portal.contents.contains(block) ){
								portal.clear();
							}
						}
					}
				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Release Saidar")){ //Removes the channel
					channel.close();
					player.updateInventory();
					event.setCancelled(true);
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
		ItemStack stack = event.getPlayer().getItemInHand();
		if (channel != null && !(stack.getType().equals(Material.AIR) ) ){ 
			String displayName = stack.getItemMeta().getDisplayName();
			if(displayName != null){
				if(displayName.equalsIgnoreCase(ChatColor.RESET+"Cast Weave")){
					channel.cast(null, null, entity);
				}
			}


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

	/**
	 * If the block is a portal, remove it


	public void clearSurroundingPortalBlocks(Block block){

		List<Block> list = new ArrayList<Block>();
		list.add( block.getLocation().add(0, 1, 0).getBlock() );
		list.add( block.getLocation().add(0, -1, 0).getBlock() );
		list.add( block.getLocation().add(1, 0, 0).getBlock() );
		list.add( block.getLocation().add(-1, 0, 0).getBlock() );
		list.add( block.getLocation().add(0, 0, 1).getBlock() );
		list.add( block.getLocation().add(0, 0, -1).getBlock() );

		for(Block block2: list){
			if(block2.getType().equals(Material.PORTAL)){
				block2.setType(Material.AIR);
				clearSurroundingPortalBlocks(block2);
			}
		}
	}

	 */



}