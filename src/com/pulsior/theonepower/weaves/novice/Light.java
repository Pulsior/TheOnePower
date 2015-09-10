package com.pulsior.theonepower.weaves.novice;

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

public class Light implements Weave {

	List<Element> elements = new ArrayList<Element>();
	
	public Light()
	{
		elements.add(Element.SPIRIT);
		elements.add(Element.FIRE);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		return false;
	}

	@Override
	public List<Element> getElements() {
		
		return null;
	}

	@Override
	public Level getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

}
