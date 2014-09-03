package com.pulsior.theonepower.weaves.accepted;

import java.util.ArrayList;
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

public class MiningBlast implements Weave{

	List<Element> elements = new ArrayList<Element>();

	public MiningBlast(){
		elements.add(Element.EARTH);
		elements.add(Element.FIRE);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		if(clickedBlock != null){
			Location location = clickedBlock.getLocation();

			for (int z = 0; z < 2; z++){
				for(int x = 0; x < 2; x++){

					for(int y = 0; y < 2; y++){
						location.getBlock().breakNaturally();
						location.add(0, 0, 1);
					}

					location.add(0, 0, -2);


					location.add(1, 0, 0);

				}
				location.add(-2, 0, 0);
				location.add(0, -1, 0);
			}

			return true;
		}
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}
	
	@Override
	public Level getLevel()
	{
		return Level.ACCEPTED;
	}

}
