package com.pulsior.theonepower.weaves.accepted;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.CropState;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class QuickGrowth implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	
	public QuickGrowth(){
		elements.add(Element.EARTH);
		elements.add(Element.WATER);
		elements.add(Element.EARTH);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		BlockState state = clickedBlock.getState();
		MaterialData data = state.getData();
		if(data instanceof Crops){
			Crops crops = (Crops) data;
			crops.setState(CropState.RIPE);
			state.setData(crops);
			state.update();
			return true;
		}
		
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
