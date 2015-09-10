package com.pulsior.theonepower;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import com.pulsior.theonepower.api.WeaveRegistry;
import com.pulsior.theonepower.api.event.SaidarEmbraceEvent;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Memory;
import com.pulsior.theonepower.channeling.Stedding;
import com.pulsior.theonepower.channeling.weave.Portal;
import com.pulsior.theonepower.item.angreal.Angreal;
import com.pulsior.theonepower.item.angreal.Callandor;
import com.pulsior.theonepower.item.angreal.SaAngreal;
import com.pulsior.theonepower.item.terangreal.StaffOfFire;
import com.pulsior.theonepower.item.terangreal.StaffOfMeteor;
import com.pulsior.theonepower.item.terangreal.TerAngreal;
import com.pulsior.theonepower.listener.ChannelManager;
import com.pulsior.theonepower.listener.EventListener;
import com.pulsior.theonepower.listener.WeaveHandler;
import com.pulsior.theonepower.util.Strings;
import com.pulsior.theonepower.weaves.accepted.BindingAir;
import com.pulsior.theonepower.weaves.accepted.Delving;
import com.pulsior.theonepower.weaves.accepted.MiningBlast;
import com.pulsior.theonepower.weaves.accepted.QuickGrowth;
import com.pulsior.theonepower.weaves.accepted.ShootFireball;
import com.pulsior.theonepower.weaves.aessedai.BindWolfGaidin;
import com.pulsior.theonepower.weaves.aessedai.ClearSky;
import com.pulsior.theonepower.weaves.aessedai.FoldedLight;
import com.pulsior.theonepower.weaves.aessedai.Healing;
import com.pulsior.theonepower.weaves.aessedai.Lightning;
import com.pulsior.theonepower.weaves.aessedai.Manipulate;
import com.pulsior.theonepower.weaves.aessedai.OpenGround;
import com.pulsior.theonepower.weaves.aessedai.ProjectileShield;
import com.pulsior.theonepower.weaves.aessedai.Rain;
import com.pulsior.theonepower.weaves.aessedai.RemoveShield;
import com.pulsior.theonepower.weaves.aessedai.Shielding;
import com.pulsior.theonepower.weaves.aessedai.Sparks;
import com.pulsior.theonepower.weaves.aessedai.SpotHostileMobs;
import com.pulsior.theonepower.weaves.aessedai.SpotPlayers;
import com.pulsior.theonepower.weaves.forsaken.FireSword;
import com.pulsior.theonepower.weaves.forsaken.Meteor;
import com.pulsior.theonepower.weaves.forsaken.Strike;
import com.pulsior.theonepower.weaves.forsaken.Teleport;
import com.pulsior.theonepower.weaves.forsaken.Travel;
import com.pulsior.theonepower.weaves.novice.ExtinguishFire;
import com.pulsior.theonepower.weaves.novice.LightFire;
import com.pulsior.theonepower.weaves.novice.OpenDoor;
import com.pulsior.theonepower.weaves.novice.Waterbreathing;
import com.pulsior.theonepower.weaves.saangreal.HeavenFire;
import com.pulsior.theonepower.weaves.saangreal.LightningStorm;

/**
 * Plugin main class
 * 
 * @author Pulsior
 * 
 */
public final class TheOnePower extends JavaPlugin
{

	public static HashMap<UUID, BukkitRunnable> taskHolder = new HashMap<UUID, BukkitRunnable>();

	public static List<Portal> portals = new ArrayList<Portal>();

	public static PowerMap power;
	public static TheOnePower plugin;

	public static Database database = new Database();

	Logger log = Bukkit.getLogger();
	Server server = Bukkit.getServer();
	BukkitScheduler scheduler = Bukkit.getScheduler();

	/**
	 * Registers listeners when the plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		plugin = this;
		makeDir();

		getServer().getPluginManager().registerEvents(new WeaveHandler(), this);
		getServer().getPluginManager()
				.registerEvents(new ChannelManager(), this);
		getServer().getPluginManager()
				.registerEvents(new EventListener(), this);
		getServer().getPluginManager()
				.registerEvents(new ItemGenerator(), this);

		registerItems();
		registerWeaves();

		if (power == null)
		{
			power = new PowerMap();
		}

		loadExp();
		loadData();

		for (Player player : Bukkit.getServer().getOnlinePlayers())
		{
			Channel channel = database.getChannel(player);
			if (channel != null)
			{
				channel.update();
			}
		}

	}

	/**
	 * Saves data when the plugin is disabled
	 */
	@Override
	public void onDisable()
	{
		save();

		for (Portal p : portals)
		{
			p.clear();
		}
	}

	/**
	 * Open Saidar to a player with /embrace and closes it with /release. Get an
	 * angreal with /angreal and store a memory with /remember
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("embrace"))
		{

			if (sender instanceof Player)
			{
				UUID id = ((Player) sender).getUniqueId();

				if (args.length == 1)
				{

					if (args[0].equalsIgnoreCase("bind"))
					{
						Player player = (Player) sender;
						ItemStack stack = player.getItemInHand();

						if (stack != null)
						{
							ItemMeta meta = stack.getItemMeta();
							List<String> lore = new ArrayList<String>();
							lore.add(ChatColor.GOLD + "Saidar-bound item");
							meta.setLore(lore);
							stack.setItemMeta(meta);
						}

						else
						{
							sender.sendMessage(ChatColor.RED +
									"You don't have an item in your hand!");
						}
					}
				}

				else
				{

					SaidarEmbraceEvent event = new SaidarEmbraceEvent((Player) sender);
					Bukkit.getServer().getPluginManager().callEvent(event);
					if (!event.isCancelled())
					{
						new Channel(id, 0);
					}

				}
			}

			else
			{
				sender.sendMessage("This command cannot be executed from the console!");
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("release"))
		{
			if (sender instanceof Player)
			{
				Channel channel = TheOnePower.database
						.getChannel(((Player) sender).getUniqueId());

				if (channel != null)
				{
					channel.close();
				}

				else
				{
					sender.sendMessage("You have not embraced saidar!");
				}
			}

			else
			{
				sender.sendMessage("This command cannot be excecuted from the console!");
			}
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("angreal"))
		{

			if (sender instanceof Player)
			{
				Player p = (Player) sender;
				p.openInventory(AngrealMenu.getInventory());
			}

			else
			{
				sender.sendMessage(ChatColor.RED +
						"This command can only be used by a player");
			}
			return true;

		}

		if (cmd.getName().equalsIgnoreCase("remember"))
		{

			if (sender instanceof Player && args.length == 1)
			{
				String memoryName = args[0];

				Player player = (Player) sender;
				if (database
						.addMemory(player.getUniqueId(), new Memory(memoryName, player
								.getLocation())))
				{
					player.sendMessage(ChatColor.GREEN +
							"Remembered this place as '" + memoryName + "'");
					return true;
				}

				else
				{
					player.sendMessage(ChatColor.RED +
							"You cannot remember any more places, forget a location first");
					return true;
				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("forget"))
		{

			if (sender instanceof Player && args.length == 1)
			{
				UUID id = ((Player) sender).getUniqueId();
				String memoryName = ChatColor.RESET + args[0];
				List<Memory> list = database.getMemories(id);

				if (list != null)
				{
					Memory removedMemory = null;

					for (Memory mem : list)
					{

						if (mem.name.equalsIgnoreCase(memoryName))
						{
							removedMemory = mem;
						}
					}

					if (removedMemory != null)
					{
						list.remove(removedMemory);
						sender.sendMessage(ChatColor.GREEN +
								"You forgot the memory '" + args[0] + "'");
					}

					else
					{
						sender.sendMessage(ChatColor.RED +
								"You didn't know the memory '" + args[0] + "'");
					}

					return true;
				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("stats"))
		{
			UUID id = ((Player) sender).getUniqueId();

			if (power.levelMap.containsKey(id))
			{
				sender.sendMessage("Your current saidar level is " +
						ChatColor.GREEN +
						Integer.toString(power.levelMap.get(id)));
				sender.sendMessage("You have to do " +
						ChatColor.GREEN +
						Integer.toString(power.requiredWeavesMap.get(id) -
								power.weaveProgressMap.get(id)) +
						ChatColor.RESET + " weaves to reach the next level");
				List<Memory> memories = database.getMemories(id);
				if (memories != null)
				{
					int size = memories.size();
					if (size == 1)
					{
						sender.sendMessage("You have " + ChatColor.GREEN +
								Integer.toString(size) + ChatColor.RESET +
								" memory:");
					}

					else
					{
						sender.sendMessage("You have " + ChatColor.GREEN +
								Integer.toString(size) + ChatColor.RESET +
								" memories:");
					}
					if (size > 0)
					{
						String message = "";
						for (int x = 0; x < size; x++)
						{
							message += memories.get(x).rawName;
							if (size == x + 2)
							{
								message += ChatColor.RESET + " and " +
										ChatColor.GREEN;
							}
							else if (size != x + 1)
							{
								message += ChatColor.RESET + ", " +
										ChatColor.GREEN;
							}
						}
						sender.sendMessage(ChatColor.GREEN +
								(Character.toUpperCase(message.charAt(0)) + message
										.substring(1)));
					}
				}
				return true;
			}
			else
			{
				if (sender instanceof Player)
				{
					sender.sendMessage("You don't have stats yet, embrace saidar first!");
				}
				else
				{
					sender.sendMessage("A console can't have stats...");
				}
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("stedding"))
		{

			if (sender instanceof Player)
			{

				if (args.length == 1)
				{

					int radius;

					try
					{
						radius = Integer.parseInt(args[0]);
					} catch (NumberFormatException exception)
					{
						if (args[0].equalsIgnoreCase("remove"))
						{
							Stedding stedding = Stedding
									.getStedding(((Player) sender)
											.getLocation());

							if (stedding != null)
							{
								sender.sendMessage(ChatColor.GREEN +
										"Stedding removed");
								TheOnePower.database.removeStedding(stedding);
								return true;
							}

							else
							{
								sender.sendMessage(ChatColor.RED +
										"You are not in a stedding");
							}

						}
						return false;
					}

					Player player = (Player) sender;
					Location loc1 = player.getLocation();

					Stedding.createStedding(player.getWorld().getName(), loc1, radius);
					sender.sendMessage(ChatColor.GREEN +
							"Stedding created with a radius of " + radius);

					return true;

				}

			}

			else
			{
				sender.sendMessage("This command cannot be executed by the console");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("unshield"))
		{

			if (args.length > 0 ){
				@SuppressWarnings("deprecation")
				Player tgt = Bukkit.getPlayer( args[0] );
				if (tgt != null)
				{
					UUID id = tgt.getUniqueId();
					TheOnePower.database.removeShield(id);
					sender.sendMessage(Color.GREEN + "Unshielded player " + args[0]);
				}
				else
					sender.sendMessage(Color.RED + "Player " + args[0] + " is not online");
			}
			
			return true;

		}

		return false;
	}

	/**
	 * Saves all level data
	 */
	public void save()
	{
		try
		{
			FileOutputStream fileOutput = new FileOutputStream("plugins/TheOnePower/data_levels");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			BukkitObjectOutputStream bukkitOutput = new BukkitObjectOutputStream(output);
			bukkitOutput.writeObject(TheOnePower.power);
			bukkitOutput.close();
			log.info("[The One Power] Saving level data");

		} catch (IOException ex)
		{
			log.info("[The One Power] Saving problem!");
		}

		try
		{
			File file = new File("plugins/TheOnePower/data_global");
			if (file.exists())
			{
				file.delete();
			}
			FileOutputStream fileOutput = new FileOutputStream("plugins/TheOnePower/data_global");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			BukkitObjectOutputStream bukkitOutput = new BukkitObjectOutputStream(output);
			bukkitOutput.writeObject(database);
			bukkitOutput.close();

		} catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	public boolean loadExp()
	{
		try
		{
			FileInputStream fileInput = new FileInputStream("plugins/TheOnePower/data_levels");
			ObjectInputStream input = new ObjectInputStream(fileInput);
			BukkitObjectInputStream bukkitInput = new BukkitObjectInputStream(input);
			Object obj = bukkitInput.readObject();
			if (obj instanceof PowerMap)
			{
				power = (PowerMap) obj;
				bukkitInput.close();
				log.info("[The One Power] Loaded level data");
				return true;
			}
			else
			{
				log.info("[The One Power] Loading problem, the level progress save is corrupt!");
				log.info("[The One Power] (The save is not an instance of PowerMap.java)");
			}
			bukkitInput.close();
		} catch (IOException ex)
		{
			log.info("[The One Power] No save found for the level progress data, creating a new one");
		} catch (ClassNotFoundException e)
		{
			log.info("[The One Power] Loading problem, a ClassNotFoundException occurred while loading the level progress data");
		}
		return false;
	}

	public void loadData()
	{
		try
		{
			FileInputStream fileInput = new FileInputStream("plugins/TheOnePower/data_global");
			ObjectInputStream objInput = new ObjectInputStream(fileInput);
			BukkitObjectInputStream bukkitInput = new BukkitObjectInputStream(objInput);
			Object obj = bukkitInput.readObject();
			if (obj instanceof Database)
			{
				Database db = (Database) obj;
				bukkitInput.close();
				database = db;
				log.info("[The One Power] Loaded global data");
				return;
			}
			else
			{
				log.info("[The One Power] Loading problem, the global save is corrupt!");
				log.info("[The One Power] (The save is not an instance of Data.java)");
			}
			bukkitInput.close();

		} catch (IOException ex)
		{
			log.info("[The One Power] No save found for the global data, creating a new one");
		} catch (ClassNotFoundException e)
		{
			log.info("[The One Power] Loading problem, a ClassNotFoundException occured while loading global data");
		}
	}

	private void registerItems()
	{
		TerAngreal.registerItem(Strings.FIRE_STAFF_NAME, new StaffOfFire());
		TerAngreal.registerItem(Strings.METEOR_STAFF_NAME, new StaffOfMeteor());
		TerAngreal.registerItem(Strings.ANGREAL_NAME, new Angreal());
		TerAngreal.registerItem(Strings.SA_ANGREAL_NAME, new SaAngreal());
		TerAngreal.registerItem(Strings.CALLANDOR_NAME, new Callandor());
	}

	private void registerWeaves()
	{
		WeaveRegistry.registerWeave(new ExtinguishFire());
		WeaveRegistry.registerWeave(new LightFire());
		WeaveRegistry.registerWeave(new OpenDoor());
		WeaveRegistry.registerWeave(new Waterbreathing());

		WeaveRegistry.registerWeave(new BindingAir());
		WeaveRegistry.registerWeave(new Delving());
		WeaveRegistry.registerWeave(new MiningBlast());
		WeaveRegistry.registerWeave(new QuickGrowth());
		WeaveRegistry.registerWeave(new ShootFireball());

		WeaveRegistry.registerWeave(new BindWolfGaidin());
		WeaveRegistry.registerWeave(new ClearSky());
		WeaveRegistry.registerWeave(new FoldedLight());
		WeaveRegistry.registerWeave(new Healing());
		WeaveRegistry.registerWeave(new Lightning());
		WeaveRegistry.registerWeave(new Manipulate());
		WeaveRegistry.registerWeave(new OpenGround());
		WeaveRegistry.registerWeave(new Rain());
		WeaveRegistry.registerWeave(new RemoveShield());
		WeaveRegistry.registerWeave(new Shielding());
		WeaveRegistry.registerWeave(new Sparks());
		WeaveRegistry.registerWeave(new SpotHostileMobs());
		WeaveRegistry.registerWeave(new SpotPlayers());
		WeaveRegistry.registerWeave(new ProjectileShield());
		
		WeaveRegistry.registerWeave(new FireSword());
		WeaveRegistry.registerWeave(new Meteor());
		WeaveRegistry.registerWeave(new Strike());
		WeaveRegistry.registerWeave(new Teleport());
		WeaveRegistry.registerWeave(new Travel());
		
		WeaveRegistry.registerWeave(new HeavenFire());
		WeaveRegistry.registerWeave(new LightningStorm());

	}

	/**
	 * Makes a new /TheOnePower folder, to store data files
	 */
	public void makeDir()
	{
		File dataFolder = new File("plugins/TheOnePower");
		if (!(dataFolder.exists()))
		{
			dataFolder.mkdir();
		}
	}
}
