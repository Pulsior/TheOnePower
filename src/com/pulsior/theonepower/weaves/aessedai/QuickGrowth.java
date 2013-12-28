package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.World;
import org.bukkit.block.Block;
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
	@SuppressWarnings("deprecation")
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity) {
		BlockState state = clickedBlock.getState();
		MaterialData data = state.getData();
		if(data instanceof Crops){
			Crops crops = (Crops) data;
			crops.setState(CropState.RIPE);
			clickedBlock.setData(CropState.RIPE.getData());
			Bukkit.getLogger().info("Doesn't work!");
		}
		else{
			Bukkit.getLogger().info("No instance");
		}
		
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
