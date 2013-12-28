package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class ClearSky implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	
	public ClearSky(){
		elements.add(Element.AIR);
		elements.add(Element.FIRE);
		elements.add(Element.WATER);
		elements.add(Element.FIRE);
		elements.add(Element.AIR);
	}
	
	@Override
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity) {

		Location location = player.getLocation();
		location.setY(location.getY()+20);
		location.setX(location.getX()+10);
		world.strikeLightning(location);
		location.setX(location.getX()-20);
		world.strikeLightning(location);
		location.setX(location.getX()+10);
		location.setZ(location.getZ()+10);
		world.strikeLightning(location);
		location.setZ(location.getZ()-20);
		world.strikeLightning(location);
		world.setStorm(false);

	}
	
	@Override
	public List<Element> getElements() {
		return elements;
	}

}
