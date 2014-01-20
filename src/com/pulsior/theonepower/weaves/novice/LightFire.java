package com.pulsior.theonepower.weaves.novice;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class LightFire implements Weave {

	List<Element> elements = new ArrayList<Element>();

	public LightFire(){
		elements.add(Element.FIRE);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {

		if(clickedFace != null && clickedBlock != null){
			
			if(clickedBlock.getState() instanceof Furnace){
				Furnace furnace = (Furnace) clickedBlock.getState();
				furnace.setBurnTime( (short) 200);
				return true;
			}
			
			Location blockLocation = clickedBlock.getLocation();

			switch(clickedFace){

			case UP:
				blockLocation.setY(blockLocation.getY()+1);
				break;
			case DOWN:
				break;
			case NORTH:
				blockLocation.setZ(blockLocation.getZ()-1);
				break;
			case EAST:
				blockLocation.setX(blockLocation.getX()+1);
				break;
			case SOUTH:
				blockLocation.setZ(blockLocation.getZ()+1);
				break;
			case WEST:
				blockLocation.setX(blockLocation.getX()-1);
				break;
			default:

				break;

			}

			Block block = blockLocation.getBlock();

			if(block.getType().equals(Material.AIR)){
				block.setType(Material.FIRE);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
