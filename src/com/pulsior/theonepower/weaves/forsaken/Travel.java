package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class Travel implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public Travel(){
		elements.add(Element.EARTH);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		
		player.teleport(player.getTargetBlock(null, 200).getLocation());
		
		return true;

	}
	
	@Override
	public List<Element> getElements() {
		return elements;
	}

}
