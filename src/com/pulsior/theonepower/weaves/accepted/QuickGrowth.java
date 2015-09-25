package com.pulsior.theonepower.weaves.accepted;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class QuickGrowth implements Weave{

	List<Element> elements = new ArrayList<Element>();
	String id = "QuickGrowth";
	private final String name = "Accelerate Growth";
	private final ChatColor color = ChatColor.DARK_GREEN;
	
	
	public QuickGrowth(){
		elements.add(Element.EARTH);
		elements.add(Element.WATER);
		elements.add(Element.EARTH);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {

		if(clickedBlock != null){

			BlockState state = clickedBlock.getState();
			MaterialData data = state.getData();
			if(data instanceof Crops){
				Crops crops = (Crops) data;
				crops.setState(CropState.RIPE);
				state.setData(crops);
				state.update();
				return true;
			}

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
