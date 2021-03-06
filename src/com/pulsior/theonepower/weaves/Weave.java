package com.pulsior.theonepower.weaves;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;

public interface Weave {
	
	
	
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity);
	public List<Element> getElements();
	public Level getLevel();
	public String getID();
	public String getName();
	public ChatColor getColor();
}
