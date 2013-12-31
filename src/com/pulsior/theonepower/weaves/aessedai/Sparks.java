package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class Sparks implements Weave{

	List<Element> elements = new ArrayList<Element>();
	
	public Sparks(){
		elements.add(Element.FIRE);
		elements.add(Element.EARTH);
		elements.add(Element.FIRE);
		elements.add(Element.EARTH);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}
	
	/**
	 * @author Bradicaljh
	 */
	
	public Direction getDirection(Float yaw)
	{
	    yaw = yaw / 90;
	    yaw = (float)Math.round(yaw);
	 
	    if (yaw == -4 || yaw == 0 || yaw == 4) {return Direction.NORTH;}
	    if (yaw == -1 || yaw == 3) {return Direction.EAST;}
	    if (yaw == -2 || yaw == 2) {return Direction.SOUTH;}
	    if (yaw == -3 || yaw == 1) {return Direction.WEST;}
	    return null;
	}
	
	enum Direction{
		NORTH,
		EAST,
		SOUTH,
		WEST
	}

}
