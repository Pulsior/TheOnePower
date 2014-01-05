package com.pulsior.theonepower.weaves.novice;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class ExtinguishFire implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public ExtinguishFire(){
		elements.add(Element.WATER);
		elements.add(Element.WATER);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {

		Location location = player.getLocation();
		location.setY(location.getY()+2);
		final Block block = world.getBlockAt(location);
		if (block.getType() == Material.AIR){
			block.setType(Material.WATER);

			BukkitRunnable task = new BukkitRunnable(){
				@Override
				public void run() {
					block.setType(Material.AIR);
				}
			};
			Bukkit.getScheduler().scheduleSyncDelayedTask(TheOnePower.plugin, task, 10L);

		}
		return true;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}
}