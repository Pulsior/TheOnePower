package com.pulsior.theonepower.weaves;

import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;

public interface Weave {
	
	
	
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity);
	public List<Element> getElements();
	
}
