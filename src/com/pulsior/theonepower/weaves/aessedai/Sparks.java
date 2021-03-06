package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.util.Utility;
import com.pulsior.theonepower.weaves.Weave;

public class Sparks implements Weave{

	List<Element> elements = new ArrayList<Element>();
	String id = "Sparks";
	private final String name = "Sparks";
	private final ChatColor color = ChatColor.DARK_RED;
	
	public Sparks(){
		elements.add(Element.FIRE);
		elements.add(Element.SPIRIT);
		elements.add(Element.FIRE);
		elements.add(Element.SPIRIT);
	}

	@Override
	public boolean cast(final Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {

		BukkitRunnable task = new BukkitRunnable(){

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Block block = player.getTargetBlock((HashSet<Byte>) null, 500);
				Location location = block.getLocation().add(0, 1, 0);
				location.getBlock().setType(Material.FIRE);
				Utility.spawnFireworkEffect(location, Color.RED, Color.BLACK, Type.BURST, false, false, 0);
				
			}

		};

		BukkitScheduler scheduler = Bukkit.getScheduler();

		for(long x = 0; x < 100; x = x+2){
			scheduler.scheduleSyncDelayedTask(TheOnePower.plugin, task, x);
		}


		return true;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}
	
	@Override
	public Level getLevel()
	{
		return Level.AES_SEDAI;
	}
	
	@Override
	public String getID() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public ChatColor getColor()
	{
		return color;
	}

}
