package com.pulsior.theonepower;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.pulsior.theonepower.listener.ChannelManager;
import com.pulsior.theonepower.listener.EventListener;
import com.pulsior.theonepower.listener.WeaveHandler;
import com.pulsior.theonepower.unseenland.UnseenGenTask;
import com.pulsior.theonepower.unseenland.UnseenLand;

public final class TheOnePower extends JavaPlugin{

	public static HashMap<String, Channel> channelMap = new HashMap<String, Channel>();
	public static HashMap<String, ItemStack[]> inventoryMap = new HashMap<String, ItemStack[]>();
	public static HashMap<String, Integer> currentLevelMap = new HashMap<String, Integer>();


	public static final ItemStack dreamAngreal = getAngrealStack("dream");
	public static final ItemStack saAngreal = getAngrealStack("sa'angreal");
	public static final ItemStack angreal = getAngrealStack("angreal");
	
	public static final ItemStack returnToken = getReturnToken();

	public static PowerMap power;

	public static UnseenLand unseenLand;

	public static WeaveList weaveList = new WeaveList();

	Logger log = Bukkit.getLogger();
	Server server = Bukkit.getServer();
	BukkitScheduler scheduler = Bukkit.getScheduler();

	@Override
	public void onEnable(){
		makeDir();
		getServer().getPluginManager().registerEvents(new WeaveHandler(), this);
		getServer().getPluginManager().registerEvents(new ChannelManager(), this);
		getServer().getPluginManager().registerEvents(new EventListener(this), this);

		load();
		if (power == null){
			power = new PowerMap();
		}
		scheduler.scheduleSyncDelayedTask(this, new UnseenGenTask() );


	}

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

		for(String name : unseenLand.players){
			unseenLand.removePlayer(name);
		}

		server.unloadWorld("tel'aran'riod", true);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("embrace")){
			if(sender instanceof Player){
				String name = sender.getName();
				if(channelMap.get ( name ) == null){
					if(power.levelMap.get(name ) == null){
						power.add( name );
					}



					embraceSaidar((Player) sender);

				}
				else{
					sender.sendMessage("You have already embraced saidar!");
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
					if(arg.equalsIgnoreCase("angreal") ){
						player.getInventory().setItemInHand(angreal);
					}
					else if (arg.equalsIgnoreCase("dream") ){						
						player.getInventory().setItemInHand(dreamAngreal);
					}
					else if(arg.equalsIgnoreCase("sa'angreal")){
						player.getInventory().setItemInHand(saAngreal);
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

		return false; 
	}


	public void embraceSaidar(Player player){
		String name = player.getName();
		PlayerInventory inventory = player.getInventory();
		ItemStack[] inventoryArray = new ItemStack[36];
		for (int x = 0; x < 36; x++){
			inventoryArray[x] = inventory.getItem(x);
		}
		inventoryMap.put(name, inventoryArray);
		Channel channel = new Channel( name , this);
		channelMap.put(name, channel);
	}

	public void save(){
		try{
			FileOutputStream fout = new FileOutputStream("plugins/The One Power/data");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject( TheOnePower.power );
			oos.close();

		}
		catch(IOException ex){
			log.info("[The One Power] Saving problem!");
		}
	}

	public void load(){
		try{
			FileInputStream fileInput = new FileInputStream("plugins/The One Power/data");
			ObjectInputStream objInput = new ObjectInputStream(fileInput);
			Object obj = objInput.readObject();
			if(obj instanceof PowerMap){
				power = (PowerMap) obj;  
			}
			else{
				log.info("[The One Power] Loading problem, no PowerMap instance!");
			}
			objInput.close();
		}
		catch(IOException ex){
			log.info("[The One Power] Loading problem, there's an IOException!");
		} 
		catch (ClassNotFoundException e) {
			log.info("[The One Power] Loading problem, there's a ClassNotFoundException!");
			e.printStackTrace();
		} 
	}

	public void makeDir(){
		File dataFolder = new File("plugins/The One Power");
		if(! (dataFolder.exists() ) ){
			dataFolder.mkdir();
		}
	}

	public static ItemStack getAngrealStack(String type){
		if(type.equals("dream")){
			ItemStack stack = new ItemStack(Material.NETHER_BRICK_ITEM);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.RESET+"Stone of the Unseen Land");
			stack.setItemMeta(meta);
			return stack;
		}
		else if(type.equals("angreal")){
			ItemStack stack = new ItemStack(Material.SKULL);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.RESET+"Angreal");
			stack.setItemMeta(meta);
			return stack;
		}
		else if(type.equals("sa'angreal")){
			ItemStack stack = new ItemStack(Material.SKULL);
			stack.setDurability( (short) 4);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.RESET+"Sa'angreal");
			stack.setItemMeta(meta);
			return stack;
		}
		return null;
	}
	
	public static ItemStack getReturnToken(){
		ItemStack stack = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"Wake up");
		stack.setItemMeta(meta);
		return stack;
	}

}
