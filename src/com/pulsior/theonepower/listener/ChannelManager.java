package com.pulsior.theonepower.listener;

import java.util.HashSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.util.Utility;

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
		if (TheOnePower.database.getChannel(player) != null){
			Material material = event.getBlock().getType();			
			switch(material){
			case DIRT:
				event.setCancelled(true);
				break;
			case WATER_LILY:
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
		if( TheOnePower.database.getChannel(event.getPlayer() ) != null){
			event.setAmount(0);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Channel channel = TheOnePower.database.getChannel(event.getPlayer() );
		if (channel != null){
			channel.close();
		}
	}



	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = (Player) event.getEntity();
		Channel channel = TheOnePower.database.getChannel( player ) ;
		if (channel != null){
			channel.close();
			event.getDrops().clear();
		}

		int randomInt = new Random().nextInt(100);
		if (randomInt == 0){
			player.sendMessage("<Ba'alzamon> I have won again, Lews Therin."); //Wheel of Time fans will understand

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		Channel channel = TheOnePower.database.getChannel(player);
		if(channel != null ){

			Item item = event.getItemDrop();
			ItemStack stack = item.getItemStack();

			if(stack.getAmount() > 1){
				event.setCancelled(true);
			}

			else{
				player.setItemInHand(stack);
				item.remove();
			}

			Block block = player.getTargetBlock((HashSet<Byte>) null, 5);
			Entity entity = Utility.getTargetEntity(player);
			if(entity != null){
				if( player.getLocation().distance(entity.getLocation()  ) > 5 ) {
					entity = null;
				}
			}

			if(block.getType() == Material.AIR){
				channel.cast(null, null, entity);

			}
			else{
				channel.cast(block, null, entity);
			}
		}
	}




	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		Channel c = TheOnePower.database.getChannel( event.getPlayer() );
		if( c != null) {
			/*
			Inventory inv = Bukkit.createInventory(event.getPlayer(), 36);
			inv.setContents(c.normalInventory);
			boolean isStored = ( inv.addItem( event.getItem().getItemStack() ) == null );
			c.normalInventory = inv.getContents();
			if (isStored ) { event.getItem().remove(); }
			*/
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		ItemStack i = event.getItem();
		if (event.getItem() != null)
		{
			if (i.getType().equals(Material.FIREBALL) ){
				event.setCancelled(true);
			}
		}
	}

}
