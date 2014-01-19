package com.pulsior.theonepower.listener;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;

/**
 * Seperate listener to prevent saidar-embracing players from
 * doing certain things.
 * @author Pulsior
 *
 */
public class ChannelManager implements Listener {

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
			player.sendMessage("<Ba'alzamon> I have won again, Lews Therin."); //Wheel of Time fans will understand

		}

		if( TheOnePower.unseenLand.players.contains(name ) ){
			//TheOnePower.unseenLand.removePlayer(name);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event){
		String name = event.getPlayer().getName();
		if(TheOnePower.channelMap.containsKey(name) ){
			event.setCancelled(true);
		}
		else if(TheOnePower.unseenLand.players.contains(name) ){
			event.setCancelled(true);
		}
	}



	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		String name = event.getPlayer().getName();
		
		if(TheOnePower.channelMap.containsKey(name ) ){
			event.setCancelled(true);
		}
		else if(TheOnePower.unseenLand.players.contains(name) ){
			event.setCancelled(true);
		}
	}

}
