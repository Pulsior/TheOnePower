package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Lightning implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public Lightning(){
		elements.add(Element.AIR);
		elements.add(Element.FIRE);
		elements.add(Element.AIR);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		world.strikeLightning(player.getTargetBlock(null, 200).getLocation());
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
}
