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

import com.pulsior.theonepower.SaidarEmbraceEvent;
import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.weave.AesSedai;
import com.pulsior.theonepower.channeling.weave.Portal;
import com.pulsior.theonepower.channeling.weave.Warder;
import com.pulsior.theonepower.item.terangreal.TerAngreal;
import com.pulsior.theonepower.unseenland.Memory;
import com.pulsior.theonepower.util.Strings;

/**
 * Most important event listener that registers weave clicks and makes weaves. Also contains events for some items
 * @author Pulsior
 *
 */
public class WeaveHandler implements Listener{


	Material earth = Material.DIRT;
	Material air = Material.FEATHER;
	Material fire = Material.FIRE;
	Material water = Material.WATER;
	Material spirit = (Material.NETHER_STAR);


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
			
			ItemMeta meta = item.getItemMeta();
			
			if(meta.hasDisplayName()){
				if(meta.getDisplayName().equalsIgnoreCase(ChatColor.RESET+"Callandor") ){
					item.setDurability((short) 0);
					player.updateInventory();
				}
			}
			
			
			/*
			 * Cancel the event if it's an a'dam
			 */
			if(meta.hasDisplayName()){
				if(meta.getDisplayName().equalsIgnoreCase( Strings.A_DAM_NAME) ){
					event.setCancelled(true);
				}
			}

			if(item.getType().equals(Material.STICK)){
				Channel channel = TheOnePower.database.getChannel(player);
				
				if(channel != null){
					System.out.println("The max level is "+channel.maxLevel+", the current level is "+player.getLevel());
					System.out.println("Is the task running? "+Bukkit.getScheduler().isCurrentlyRunning(channel.taskId) );
					System.out.println("Is it queued? "+Bukkit.getScheduler().isQueued(channel.taskId) );

					try{
						System.out.println("Is the player casting, according to the castingPlayerMap? "+channel.isCasting());
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
			if( item.getType().equals(Material.COMPASS) ){
				
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
			
						
			/*
			 * Check if the item is bound to saidar embracing 
			 */
			
			List<String> lore = item.getItemMeta().getLore();
			if(lore != null){
				if(lore.get(0).equalsIgnoreCase(ChatColor.GOLD+"Saidar-bound item") && ( event.getAction().equals(Action.RIGHT_CLICK_AIR)  || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ) ){
					
					SaidarEmbraceEvent saidarEvent = new SaidarEmbraceEvent( player );
					Bukkit.getServer().getPluginManager().callEvent(saidarEvent);
					if(! saidarEvent.isCancelled() ){
						event.setCancelled(true);
						new Channel ( name, 0);
					}
				}
			}
			
			/*
			 * Check if the item is a ter'angreal
			 */
			
			if( meta.hasDisplayName() ){
				TerAngreal terAngreal = TerAngreal.toTerAngreal(item);
				
				Action action = event.getAction();
				if(terAngreal != null && (action.equals(Action.RIGHT_CLICK_AIR ) || action.equals(Action.RIGHT_CLICK_BLOCK) ) ){
					terAngreal.use(player, event.getClickedBlock(), event.getBlockFace(), null);
					event.setCancelled(true);
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
			channel = TheOnePower.database.getChannel(player);
			Action action = event.getAction();
			if (item != null && channel != null && ( action.equals(Action.RIGHT_CLICK_AIR) ||
					event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ) ){			//If the player clicked an element, adds the element to the channel

				if (item.getType().equals(earth) || item.getType().equals(air) ||
						item.getType().equals(water) || item.getType().equals(fire) || 
						item.getType().equals(spirit) ){

					Element element = getElement(item);

					if(element != null){
						channel.addElement(element, element.toString() );
					}
					
				}
				
				if(itemName == null){
					return;
				}
				
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Cast Weave")){ //Casts and executes the weave
					channel.cast( event.getClickedBlock(), event.getBlockFace(), null );


				}
				
				else if (itemName.equalsIgnoreCase(ChatColor.RESET + "Disband Weave")){ //Clears the weave
			
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
		Channel channel = TheOnePower.database.getChannel(player);
		ItemStack stack = event.getPlayer().getItemInHand();
		
		if (channel != null && !(stack.getType().equals(Material.AIR) ) ){ 
			String displayName = stack.getItemMeta().getDisplayName();
			if(displayName != null){
				if(displayName.equalsIgnoreCase(ChatColor.RESET+"Cast Weave")){
					channel.cast(null, null, entity);
					return;
				}
			}
		}
		
		TerAngreal terAngreal = TerAngreal.toTerAngreal(stack);
		
		if (terAngreal != null){
			terAngreal.use(player, null, null, entity);
		}
	}


	/**
	 * Method to convert a string to an element
	 * @param element
	 * @return
	 */
	public Element getElement(ItemStack element){

		if(element.getType().equals(earth)){
			return Element.EARTH;
		}
		if(element.getType().equals(air)){
			return Element.AIR;
		}
		if(element.getType().equals(fire)){
			return Element.FIRE;
		}
		if(element.getType().equals(water)){
			return Element.WATER;
		}
		if(element.getType().equals(spirit)){
			return Element.SPIRIT;
		}
		return null;
	}




}