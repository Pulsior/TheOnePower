package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Rain implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	String id = "Rain";
	
	private final String name = "Rain";
	private final ChatColor color = ChatColor.DARK_BLUE;
	
	public Rain(){
		elements.add(Element.AIR);
		elements.add(Element.WATER);
		elements.add(Element.AIR);
		elements.add(Element.WATER);
		elements.add(Element.AIR);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		world.setStorm(true);
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
