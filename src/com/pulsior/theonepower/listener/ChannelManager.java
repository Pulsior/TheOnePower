package com.pulsior.theonepower.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.pulsior.theonepower.Channel;
import com.pulsior.theonepower.TheOnePower;

/**
 * Seperate listener to prevent saidar-embracing players from
 * doing certain things. Soon to be merged with {@link EventListener}
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
}
