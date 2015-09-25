package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Jump implements Weave {

	List<Element> elements = new ArrayList<Element>();
	String id = "Jump";
	private final String name = "Air-Powered Boost";
	private final ChatColor color = ChatColor.GRAY;	
	public Jump()
	{
		elements.add(Element.AIR);
		elements.add(Element.AIR);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,
			BlockFace clickedFace, Entity clickedEntity) {
		if (player.getLocation().getY() < 260)
		{
			Vector v = player.getVelocity();
			player.setVelocity( player.getVelocity().setY( v.getY() + 0.5) );
			player.setVelocity( player.getVelocity().setX( v.getX() * 2));
			player.setVelocity( player.getVelocity().setZ( v.getZ() * 2));
			player.setVelocity(player.getVelocity().multiply(5));
			player.setMetadata("hasFeatherFall", new FixedMetadataValue(TheOnePower.plugin, true) );
			return true;
		}

		return false;
	}


	@Override
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Level getLevel() {
		return Level.FORSAKEN;
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
