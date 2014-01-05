package com.pulsior.theonepower.weaves.forsaken;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class Travel implements Weave {

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		Location loc = player.getLocation();
		loc.add(0, -300, 0);
		return false;
	}

	@Override
	public List<Element> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
