package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class ClearSky implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	String id = "ClearSky";
	private final String name = "Clear Skies";
	private final ChatColor color = ChatColor.DARK_AQUA;
	
	public ClearSky(){
		elements.add(Element.AIR);
		elements.add(Element.FIRE);
		elements.add(Element.WATER);
		elements.add(Element.FIRE);
		elements.add(Element.AIR);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {

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
