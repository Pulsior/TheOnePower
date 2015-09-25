package com.pulsior.theonepower.weaves.saangreal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class LightningStorm implements Weave{

	List<Element> elements = new ArrayList<Element>();
	String id = "LightningStorm";
	private final String name = "Summon Storm";
	private final ChatColor color = ChatColor.WHITE;
	
	public LightningStorm(){
		elements.add(Element.FIRE);
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.FIRE);
	}

	@Override
	public boolean cast(final Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {


		BukkitRunnable task = new BukkitRunnable(){

			@Override
			public void run() {
				
				List<Entity> entities = player.getNearbyEntities(50, 50, 50);
				for(Entity e : entities){
					if ( ( e instanceof Zombie || e instanceof Creeper || e instanceof Spider || e instanceof Skeleton || e instanceof Enderman ) && ! e.isDead() ){
						e.getWorld().strikeLightning(e.getLocation() );
					}

				}
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
		return Level.SA_ANGREAL;
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
