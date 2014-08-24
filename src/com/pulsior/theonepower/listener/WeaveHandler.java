package com.pulsior.theonepower.listener;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import com.pulsior.theonepower.channeling.weave.Portal;
import com.pulsior.theonepower.item.terangreal.TerAngreal;
import com.pulsior.theonepower.util.Strings;

/**
 * Most important event listener that registers weave clicks and makes weaves.
 * Also contains events for some items
 * 
 * @author Pulsior
 * 
 */
public class WeaveHandler implements Listener
{

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
	public void onPlayerInteract(PlayerInteractEvent event)
	{

		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		UUID id = player.getUniqueId();
		if (item != null)
		{ // IF clause for items

			ItemMeta meta = item.getItemMeta();

			if (meta.hasDisplayName())
			{
				if (meta.getDisplayName().equalsIgnoreCase(ChatColor.RESET +
						"Callandor"))
				{
					item.setDurability((short) 0);
					player.updateInventory();
				}
			}

			/*
			 * Cancel the event if it's an a'dam
			 */
			if (meta.hasDisplayName())
			{
				if (meta.getDisplayName().equalsIgnoreCase(Strings.A_DAM_NAME))
				{
					event.setCancelled(true);
				}
			}

			if (item.getType().equals(Material.STICK))
			{
				Channel channel = TheOnePower.database.getChannel(player);

				if (channel != null)
				{
					System.out.println("The max level is " + channel.maxLevel +
							", the current level is " + player.getLevel());
					System.out.println("Is the task running? " +
							Bukkit.getScheduler()
									.isCurrentlyRunning(channel.taskId));
					System.out.println("Is it queued? " +
							Bukkit.getScheduler().isQueued(channel.taskId));

					try
					{
						System.out
								.println("Is the player casting, according to the castingPlayerMap? " +
										channel.isCasting());
					} catch (NullPointerException ex)
					{
						System.out
								.println("A NullPointerException was thrown when trying to access TheOnePower.castingPlayersMap");
					}
				}

			}


			/*
			 * Check if the item is bound to saidar embracing
			 */

			List<String> lore = item.getItemMeta().getLore();
			if (lore != null)
			{
				if (lore.get(0).equalsIgnoreCase(ChatColor.GOLD +
						"Saidar-bound item") &&
						(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event
								.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
				{

					SaidarEmbraceEvent saidarEvent = new SaidarEmbraceEvent(player);
					Bukkit.getServer().getPluginManager()
							.callEvent(saidarEvent);
					if (!saidarEvent.isCancelled())
					{
						event.setCancelled(true);
						new Channel(id, 0);
					}
				}
			}

			/*
			 * Check if the item is a ter'angreal
			 */

			if (meta.hasDisplayName())
			{
				TerAngreal terAngreal = TerAngreal.toTerAngreal(item);

				Action action = event.getAction();
				if (terAngreal != null &&
						(action.equals(Action.RIGHT_CLICK_AIR) || action
								.equals(Action.RIGHT_CLICK_BLOCK)))
				{
					terAngreal.use(player, event.getClickedBlock(), event
							.getBlockFace(), null);
					event.setCancelled(true);
				}
			}

			/*
			 * Channeling and creating weaves starts here
			 */
			Channel channel;
			String itemName = item.getItemMeta().getDisplayName();
			channel = TheOnePower.database.getChannel(player);
			Action action = event.getAction();
			if (item != null &&
					channel != null &&
					(action.equals(Action.RIGHT_CLICK_AIR) || event.getAction()
							.equals(Action.RIGHT_CLICK_BLOCK)))
			{ // If the player clicked an element, adds the element to the
				// channel

				if (item.getType().equals(earth) ||
						item.getType().equals(air) ||
						item.getType().equals(water) ||
						item.getType().equals(fire) ||
						item.getType().equals(spirit))
				{

					Element element = getElement(item);

					if (element != null)
					{
						channel.addElement(element, element.toString());
					}

				}

				if (itemName == null)
				{
					return;
				}

				else if (itemName.equalsIgnoreCase(ChatColor.RESET +
						"Cast Weave"))
				{ // Casts and executes the weave
					channel.cast(event.getClickedBlock(), event.getBlockFace(), null);

				}

				else if (itemName.equalsIgnoreCase(ChatColor.RESET +
						"Disband Weave"))
				{ // Clears the weave

					channel.disband();
					Block block = event.getClickedBlock();

					if (block != null)
					{
						for (Portal portal : TheOnePower.portals)
						{
							if (portal.contents.contains(block))
							{
								portal.clear();
							}
						}
					}
				}
				else if (itemName.equalsIgnoreCase(ChatColor.RESET +
						"Release Saidar"))
				{ // Removes the channel
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
	public void onEntityInteract(PlayerInteractEntityEvent event)
	{
		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();
		Channel channel = TheOnePower.database.getChannel(player);
		ItemStack stack = event.getPlayer().getItemInHand();

		if (channel != null && !(stack.getType().equals(Material.AIR)))
		{
			String displayName = stack.getItemMeta().getDisplayName();
			if (displayName != null)
			{
				if (displayName
						.equalsIgnoreCase(ChatColor.RESET + "Cast Weave"))
				{
					channel.cast(null, null, entity);
					return;
				}
			}
		}

		TerAngreal terAngreal = TerAngreal.toTerAngreal(stack);

		if (terAngreal != null)
		{
			terAngreal.use(player, null, null, entity);
		}
	}

	/**
	 * Method to convert a string to an element
	 * 
	 * @param element
	 * @return
	 */
	public Element getElement(ItemStack element)
	{

		if (element.getType().equals(earth))
		{
			return Element.EARTH;
		}
		if (element.getType().equals(air))
		{
			return Element.AIR;
		}
		if (element.getType().equals(fire))
		{
			return Element.FIRE;
		}
		if (element.getType().equals(water))
		{
			return Element.WATER;
		}
		if (element.getType().equals(spirit))
		{
			return Element.SPIRIT;
		}
		return null;
	}

}
