package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class ProjectileShield implements Weave {

	//WIP
	List<Element> elements = new ArrayList<Element>();
	String id = "ProjectileShield";
	private final String name = "Projectile Shield";
	private final ChatColor color = ChatColor.LIGHT_PURPLE;
	
	public ProjectileShield()
	{
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		player.setMetadata("hasShield", new FixedMetadataValue(TheOnePower.plugin, true) );
		return true;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Level getLevel() {
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
