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
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.weave.Portal;
import com.pulsior.theonepower.channeling.weave.Shield;
import com.pulsior.theonepower.channeling.weave.Warder;
import com.pulsior.theonepower.item.PowerItem;
import com.pulsior.theonepower.listener.ChannelManager;
import com.pulsior.theonepower.listener.EventListener;
import com.pulsior.theonepower.listener.WeaveHandler;
import com.pulsior.theonepower.task.UnseenGenTask;
import com.pulsior.theonepower.unseenland.Memory;
import com.pulsior.theonepower.unseenland.UnseenLand;
import com.pulsior.theonepower.unseenland.UnseenLandData;

/**
 * Plugin main class
 * @author Pulsior
 *
 */
public final class TheOnePower extends JavaPlugin{

	public static HashMap<String, Channel> channelMap = new HashMap<String, Channel>();
	public static HashMap<String, ItemStack[]> embraceInventoryMap = new HashMap<String, ItemStack[]>();
	public static HashMap<String, Integer> currentLevelMap = new HashMap<String, Integer>();
	public static HashMap<String, Float> expLevelProgressMap = new HashMap<String, Float>();
	public static HashMap<String, Boolean> castingPlayersMap = new HashMap<String, Boolean>();
	public static HashMap<String, Shield> shieldedPlayersMap = new HashMap<String, Shield>();
	
	public static List<Portal> portals = new ArrayList<Portal>();
	public static List<Warder> warders = new ArrayList<Warder>();

	public static PowerMap power;
	public static UnseenLand unseenLand;
	public static TheOnePower plugin;
	
	Logger log = Bukkit.getLogger();
	Server server = Bukkit.getServer();
	BukkitScheduler scheduler = Bukkit.getScheduler();

	/**
	 * Registers listeners and creates tel'aran'rhiod when the plugin is enabled
	 */
	@Override
	public void onEnable(){
		plugin = this;
		
		makeDir();
		
		getServer().getPluginManager().registerEvents(new WeaveHandler(), this);
		getServer().getPluginManager().registerEvents(new ChannelManager(), this);
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		

		if (power == null){
			power = new PowerMap();
		}
		
		loadExp();
		loadData();
		
		if(Bukkit.getWorld("world") != null){
			UnseenLandData data = loadUnseenLand();
			unseenLand = new UnseenLand(data);
		}


	}
	/**
	 * Closes all channels and saves data when the plugin is disabled
	 */
	@Override
	public void onDisable(){
		Player[] players = server.getOnlinePlayers();
		for(Player p : players){
			String name = p.getName();
			Channel channel = channelMap.get(name);
			if (channel != null){
				channel.close();
				channelMap.remove(name);
			}
		}
		save();
		saveUnseenLand();
	}

	/**
	 * Open Saidar to a player with /embrace and closes it with /release. Get an angreal with /angreal, 
	 * cheat yourself into tel'aran'rhiod with /dream and store a memory with /remember
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("embrace")){
			if(sender instanceof Player){
				String name = sender.getName();
				
				if(args.length == 1){
					
					if(args[0].equalsIgnoreCase("bind")){					
						Player player = (Player) sender;
						ItemStack stack = player.getItemInHand();
						
						if(stack != null){
							ItemMeta meta = stack.getItemMeta();
							List<String> lore = new ArrayList<String>();
							lore.add(ChatColor.GOLD+"Click to embrace saidar");
							meta.setLore(lore);
							stack.setItemMeta(meta);
						}
						
						else{
							sender.sendMessage(ChatColor.RED+"You don't have an item in your hand!");
						}
					}
				}
				
				else{
					
					if(channelMap.get ( name ) == null ){
						
						if( ! ( shieldedPlayersMap.containsKey( name ) ) ){
							new Channel ( sender.getName() );
						}
						else{
							sender.sendMessage(ChatColor.RED+"You can feel the True Source, but you can't touch it");
						}
						
						
					}
					
					else{
						sender.sendMessage("You have already embraced saidar!");
					}
				}
			}
			
			else{
				sender.sendMessage("This command cannot be excecuted from the console!");
			}
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("release")){
			if(sender instanceof Player){
				Channel channel = channelMap.get(sender.getName()); 
				
				if (channel != null){
					channel.close();
				}
				
				else{
					sender.sendMessage("You have not embraced saidar!");
				}
			}
			
			else{
				sender.sendMessage("This command cannot be excecuted from the console!");
			}
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("angreal")){
			
			if (args.length == 1){
				
				if(sender instanceof Player){
					String arg = args[0];
					Player player = (Player) sender;
					PlayerInventory inventory = player.getInventory();
					
					if(arg.equalsIgnoreCase("angreal") ){
						inventory.addItem(PowerItem.ANGREAL);
					}
					
					else if (arg.equalsIgnoreCase("dream") ){						
						inventory.addItem(PowerItem.DREAM_ANGREAL);
					}
					
					else if(arg.equalsIgnoreCase("sa'angreal")){
						inventory.addItem(PowerItem.SA_ANGREAL);
					}
					
					else if(arg.equalsIgnoreCase("callandor")){
						inventory.addItem(PowerItem.CALLANDOR);
					}
					
					else{
						player.sendMessage("angreal, dream, sa'angreal");
					}	

					return true;
				}
			}
			else{
				sender.sendMessage(ChatColor.RED+"You can choose between angreal, dream and sa'angreal");
				return true;
			}
		}

		if(cmd.getName().equalsIgnoreCase("dream")){
			
			if(sender instanceof Player){
				String name = sender.getName();
				if( unseenLand.players.contains( name ) ){
					unseenLand.removePlayer(name);
				}
				
				else{
					unseenLand.addPlayer(name);
				}
				
				return true;
			}
		}

		if(cmd.getName().equalsIgnoreCase("remember")){
			
			if(sender instanceof Player && args.length == 1){
				String playerName = sender.getName();
				String memoryName = args[0];
				
				if(! (unseenLand.memoryMap.containsKey(playerName) ) ) {
					unseenLand.memoryMap.put(playerName, new ArrayList<Memory>() );
				}
				
				Player player = (Player) sender;
				if(unseenLand.addMemory(playerName, new Memory(memoryName, player.getLocation() ) ) ){
					player.sendMessage(ChatColor.GREEN+"Remembered this place as '"+memoryName+"'");
					return true;
				}
				
				else{
					player.sendMessage(ChatColor.RED+"You cannot remember any more places, forget a location first");
					return true;
				}
			}
		}

		if(cmd.getName().equalsIgnoreCase("forget")){
			
			if(sender instanceof Player && args.length == 1){
				String playerName = sender.getName();
				String memoryName = ChatColor.RESET + args[0];
				List<Memory> list = unseenLand.memoryMap.get(playerName);
				
				if(list != null){
					Memory removedMemory = null;
					
					for(Memory mem : list){
						
						if(mem.name.equalsIgnoreCase(memoryName) ){
							removedMemory = mem;
						}
					}
					
					if(removedMemory != null){
						list.remove(removedMemory);
						sender.sendMessage(ChatColor.GREEN+"You forgot the memory '"+args[0]+"'");
					}
					
					else{
						sender.sendMessage(ChatColor.RED+"You didn't know the memory '"+args[0]+"'");
					}
					
					return true;
				}
			}
		}

		if(cmd.getName().equalsIgnoreCase("stats")){
			String name = sender.getName();
			if(power.levelMap.containsKey(name)){
				sender.sendMessage("Your current saidar level is "+ChatColor.GREEN+Integer.toString( power.levelMap.get(name) ) );
				sender.sendMessage("You have to do "+ChatColor.GREEN+Integer.toString( power.requiredWeavesMap.get(name) - power.weaveProgressMap.get (name) )+ChatColor.RESET+" weaves to reach the next level" );
				List<Memory> memories = unseenLand.memoryMap.get(name);
				if(memories != null){
					int size = memories.size();
					if(size == 1){
						sender.sendMessage("You have "+ChatColor.GREEN+Integer.toString(size) + ChatColor.RESET+ " memory:" );
					}

					else{
						sender.sendMessage("You have "+ChatColor.GREEN+Integer.toString(size) + ChatColor.RESET+ " memories:" );
					}
					if(size > 0){
						String message = "";
						for(int x = 0; x < size; x++){
							message += memories.get(x).rawName;
							if(size == x+2){
								message += ChatColor.RESET+" and "+ChatColor.GREEN;
							}
							else if(size != x+1){
								message += ChatColor.RESET+", "+ChatColor.GREEN;
							}
						}
						sender.sendMessage(ChatColor.GREEN+ (Character.toUpperCase(message.charAt(0)) + message.substring(1) ) );
					}
				}
				return true;
			}
			else{
				if(sender instanceof Player){
					sender.sendMessage("You don't have stats yet, embrace saidar first!");
				}
				else{
					sender.sendMessage("A console can't have stats...");
				}
				return true;
			}
		}
		


		return false; 
	}

	/**
	 * Saves all level data
	 */
	public void save(){
		try{
			FileOutputStream fileOutput = new FileOutputStream("plugins/The One Power/data_levels");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			BukkitObjectOutputStream bukkitOutput = new BukkitObjectOutputStream(output);
			bukkitOutput.writeObject( TheOnePower.power );
			bukkitOutput.close();
			log.info("[The One Power] Saving level data");

		}
		catch(IOException ex){
			log.info("[The One Power] Saving problem!");
		}

		
		
		try{
			Data data = new Data();
			File file = new File("plugins/The One Power/data_global");
			if(file.exists()){
				file.delete();
			}
			FileOutputStream fileOutput = new FileOutputStream("plugins/The One Power/data_global");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			BukkitObjectOutputStream bukkitOutput = new BukkitObjectOutputStream(output);
			bukkitOutput.writeObject( data );
			bukkitOutput.close();
			log.info("[The One Power] Saving Unseen Land data");

		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	
	public static void saveUnseenLand(){
		Logger log = Bukkit.getLogger();
		try{
			UnseenLandData data = new UnseenLandData(unseenLand);
			File file = new File("plugins/The One Power/data_unseenLand");
			if(file.exists()){
				file.delete();
			}
			FileOutputStream fileOutput = new FileOutputStream("plugins/The One Power/data_unseenLand");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			BukkitObjectOutputStream bukkitOutput = new BukkitObjectOutputStream(output);
			bukkitOutput.writeObject( data );
			bukkitOutput.close();
			log.info("[The One Power] Saving Unseen Land data");

		}
		catch(IOException ex){
			log.info("[The One Power] [WARNING] The Unseen Land data could not be saved!");
			ex.printStackTrace();
		}
	}
	
	public boolean loadExp(){
		try{
			FileInputStream fileInput = new FileInputStream("plugins/The One Power/data_levels");
			ObjectInputStream input = new ObjectInputStream(fileInput);
			BukkitObjectInputStream bukkitInput = new BukkitObjectInputStream(input); 
			Object obj = bukkitInput.readObject();
			if(obj instanceof PowerMap){
				power = (PowerMap) obj;
				bukkitInput.close();
				log.info("[The One Power] Loaded level data");
				return true;
			}
			else{
				log.info("[The One Power] Loading problem, the level progress save is corrupt!");
				log.info("[The One Power] (The save is not an instance of PowerMap.java)");
			}
			bukkitInput.close();
		}
		catch(IOException ex){
			log.info("[The One Power] No save found for the level progress data, creating a new one");
		} 
		catch (ClassNotFoundException e) {
			log.info("[The One Power] Loading problem, a ClassNotFoundException occurred while loading the level progress data");
		}
		return false;
	}

	public UnseenLandData loadUnseenLand(){
		try{
			FileInputStream fileInput = new FileInputStream("plugins/The One Power/data_unseenLand");
			ObjectInputStream objInput = new ObjectInputStream(fileInput);
			BukkitObjectInputStream bukkitInput = new BukkitObjectInputStream(objInput);
			Object obj = bukkitInput.readObject();
			if(obj instanceof UnseenLandData){
				UnseenLandData dat = (UnseenLandData) obj;
				bukkitInput.close();
				log.info("[The One Power] Loaded Unseen Land data");
				return dat;
			}
			else{
				log.info("[The One Power] Loading problem, the Unseen Land save is corrupt!");
				log.info("[The One Power] (The save is not an instance of UnseenLandData.java)");
			}
			bukkitInput.close();

		}
		catch(IOException ex){
			log.info("[The One Power] No save found for the Unseen Land, creating a new one");
			log.info("IOException!");
		} 
		catch (ClassNotFoundException e) {
			log.info("[The One Power] Loading problem, a ClassNotFoundException occured while loading the Unseen Land");
		}
		return null;
	}
	
	public void loadData(){
		try{
			FileInputStream fileInput = new FileInputStream("plugins/The One Power/data_global");
			ObjectInputStream objInput = new ObjectInputStream(fileInput);
			BukkitObjectInputStream bukkitInput = new BukkitObjectInputStream(objInput);
			Object obj = bukkitInput.readObject();
			if(obj instanceof Data){
				Data dat = (Data) obj;
				bukkitInput.close();
				TheOnePower.shieldedPlayersMap = dat.shieldedPlayersMap;
				log.info("[The One Power] Loaded global data");
				return;
			}
			else{
				log.info("[The One Power] Loading problem, the global save is corrupt!");
				log.info("[The One Power] (The save is not an instance of Data.java)");
			}
			bukkitInput.close();

		}
		catch(IOException ex){
			log.info("[The One Power] No save found for the global data, creating a new one");
			log.info("IOException!");
		} 
		catch (ClassNotFoundException e) {
			log.info("[The One Power] Loading problem, a ClassNotFoundException occured while loading global data");
		}
	}

	/**
	 * Makes a new /The One Power folder, to store data files
	 */
	public void makeDir(){
		File dataFolder = new File("plugins/The One Power");
		if(! (dataFolder.exists() ) ){
			dataFolder.mkdir();
		}
	}
	
	public void createUnseenLand(){
		if(Bukkit.getWorld("tel'aran'rhiod") == null){
			log.info("[The One Power] Initializing Tel'aran'rhiod");
			UnseenGenTask task = new UnseenGenTask();
			task.run();
		}

		UnseenLandData data = loadUnseenLand();
		if (data != null){
			log.info("[The One Power] Loading Unseen Land data");
			unseenLand = new UnseenLand(data);

		}
		else{
			unseenLand = new UnseenLand();
			log.info("[The One Power] Creating new Unseen Land");
		}
	}

}
