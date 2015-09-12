package com.pulsior.theonepower.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.api.event.SaidarEmbraceEvent;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Memory;
import com.pulsior.theonepower.channeling.Stedding;
import com.pulsior.theonepower.channeling.weave.Portal;
import com.pulsior.theonepower.util.Direction;
import com.pulsior.theonepower.util.Utility;

/**
 * Event listener for uses other then creating-casting weaves and limiting
 * saidar/embracing players
 * 
 * @author Pulsior
 * 
 */
public class EventListener implements Listener
{

	TheOnePower plugin = TheOnePower.plugin;

	String earth = ChatColor.DARK_GREEN + "Earth";
	String air = ChatColor.BLUE + "Air";
	String fire = ChatColor.RED + "Fire";
	String water = ChatColor.AQUA + "Water";
	String spirit = ChatColor.GRAY + "Spirit";

	HashMap<String, Integer> map = new HashMap<String, Integer>();
	BukkitScheduler scheduler = Bukkit.getScheduler();


	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent event)
	{
		Entity entity = event.getDamager();
		Entity e2 = event.getEntity();

		if (entity instanceof Projectile)
		{
			Projectile projectile = (Projectile) entity;
			List<MetadataValue> list = e2.getMetadata("hasShield");
			for (MetadataValue value : list) {
				if (value.getOwningPlugin() == TheOnePower.plugin) {
					boolean hasShield = (boolean) value.value();
					if ( hasShield )
					{
						ProjectileSource shooter = projectile.getShooter();
						if (shooter instanceof Entity)
						{
							Location shooterLocation = ((Entity) shooter).getLocation();
							Vector newPath = shooterLocation.toVector().subtract(e2.getLocation().toVector());
							projectile.setVelocity(newPath);
						}

						event.setCancelled(true);
						e2.setMetadata("hasShield", new FixedMetadataValue(TheOnePower.plugin, false) );
					}
				}
			}
		}

		if (entity instanceof Fireball)
		{
			List<MetadataValue> meta = entity.getMetadata("isLethal");
			if (meta.size() > 0)
			{
				if (meta.get(0).asBoolean())
				{
					Entity hitEntity = event.getEntity();
					if (hitEntity instanceof Creature)
					{
						((Creature) hitEntity).damage(10000);
					}
				}
			}
		}

	}


	/**
	 * Select a memory from the GUI with the traveling weave.
	 * 
	 * @param event
	 */

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInventorySelect(InventoryClickEvent event)
	{

		Inventory inv = event.getInventory();
		InventoryHolder inventoryHolder = inv.getHolder();

		if (inventoryHolder instanceof Player && inv.getSize() == 9)
		{

			ItemStack item = event.getCurrentItem();

			Player player = (Player) inv.getHolder();

			float yaw = player.getLocation().getYaw();

			if (item != null)
			{

				ItemMeta meta = item.getItemMeta();

				if (meta != null)
				{

					if (meta.hasLore())
					{

						if (meta.getLore().contains(ChatColor.YELLOW +
								"Click to select a gateway destination"))
						{

							List<Memory> list = TheOnePower.database.getMemories(player.getUniqueId() );
							if (list != null)
							{

								for (Memory memory : list)
								{

									if (memory.name.equals(meta
											.getDisplayName()))
									{

										Location spawnLocation = player
												.getTargetBlock((HashSet<Byte>) null, 5)
												.getLocation();
										Location destination = memory
												.getLocation();
										Direction direction = Utility
												.getDirection(yaw);
										new Portal(spawnLocation, destination, player
												.getLocation(), direction);
										player.closeInventory();
										event.setCancelled(true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Listener method for the Traveling weave
	 * 
	 * @param event
	 */

	@EventHandler
	public void onPortalEnter(EntityPortalEnterEvent event)
	{

		Block block = event.getLocation().getBlock();
		List<MetadataValue> meta = block.getMetadata("isGateway");
		for (MetadataValue value : meta)
		{
			if (value.asBoolean() == true)
			{
				Location destination = (Location) block
						.getMetadata("getLocation").get(0).value();
				Entity entity = event.getEntity();
				if (entity != null)
				{
					entity.teleport(destination);
				}
			}
		}
	}

	@EventHandler
	public void onSaidarEmbrace(SaidarEmbraceEvent event)
	{

		Player player = event.getPlayer();
		UUID id = player.getUniqueId();

		if (!player.hasPermission("theonepower.channel"))
		{
			player.sendMessage(ChatColor.RED +
					"You don't have permission to embrace saidar");
			event.setCancelled(true);
		}

		if (TheOnePower.database.hasShield(id) )
		{
			player.sendMessage(ChatColor.RED +
					"You can feel the True Source, but you can't touch it");
			event.setCancelled(true);
		}

		if (Stedding.getStedding(player.getLocation()) != null)
		{
			player.sendMessage(ChatColor.RED +
					"You can't feel the True Source, you must be in a stedding");
			event.setCancelled(true);
		}

		if (TheOnePower.database.getChannel(player) != null)
		{
			player.sendMessage(ChatColor.GOLD +
					"You have already embraced saidar!");
			event.setCancelled(true);
		}
	}


	/**
	 * Makes sure a gateway does not disappear immediately.
	 * 
	 * @param event
	 */

	@EventHandler
	public void onPortalChange(BlockPhysicsEvent event)
	{
		Block block = event.getBlock();
		List<MetadataValue> list = block.getMetadata("isGateway");
		for (MetadataValue value : list)
		{
			if (value.asBoolean() == true)
			{
				event.setCancelled(true);
			}
		}

	}

	/**
	 * Release saidar when opening a chest
	 */

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Block b = event.getClickedBlock();
		if ( b != null)
		{
			if (b.getType().equals(Material.CHEST))
			{
				Channel c = TheOnePower.database.getChannel(event.getPlayer());
				if (c != null)
				{
					c.close();
				}
			}
		}
	}

	/**
	 * Register and cancel fall event
	 */
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (event.getCause().equals(DamageCause.FALL) )
		{
			Entity entity = event.getEntity();
			List<MetadataValue> list = entity.getMetadata("hasFeatherFall");
			for (MetadataValue value : list) {
				if (value.getOwningPlugin() == TheOnePower.plugin) {
					boolean hasFeatherFall = (boolean) value.value();
					if ( hasFeatherFall )
					{					
						entity.setMetadata("hasFeatherFall", new FixedMetadataValue(TheOnePower.plugin, false) );
						event.setCancelled(true);
					}
				}
			}
		}
	}

}
