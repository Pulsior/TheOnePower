package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Strike implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public Strike(){
		elements.add(Element.AIR);
		elements.add(Element.EARTH);
		elements.add(Element.EARTH);
		elements.add(Element.FIRE);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		
		Block block2 = player.getTargetBlock((HashSet<Byte>) null, 75);
		Location location = block2.getLocation();
		world.strikeLightning(location);
		world.createExplosion(location, 6F);
		
		return true;
	}
	
	@Override
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Level getLevel()
	{
		return Level.FORSAKEN;
	}

}
