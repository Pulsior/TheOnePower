package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
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
	
	@Override
	@SuppressWarnings("deprecation")
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity) {
		
		player.teleport(player.getTargetBlock(null, 200).getLocation());

	}
	
	@Override
	public List<Element> getElements() {
		return elements;
	}

}
