package com.pulsior.theonepower.weaves.novice;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class OpenDoor implements Weave {

	List<Element> elements = new ArrayList<Element>();
	String id = "OpenDoor";
	private final String name = "Smash Lock";
	private final ChatColor color = ChatColor.DARK_GRAY;
	

	public OpenDoor(){
		elements.add(Element.EARTH);
		elements.add(Element.EARTH);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {

		if(clickedBlock != null){
			
			BlockState state = clickedBlock.getState();
			
			Location location = clickedBlock.getLocation();
			Material topBlock = clickedBlock.getType();
			location.setY(location.getY()-1);
			
			Block bottomBlock = location.getBlock();
			if(bottomBlock.getType().equals(topBlock)){
				state = bottomBlock.getState();
			}
			
			MaterialData data =  state.getData();

			if(data instanceof Openable){
				Openable openable = (Openable) data;
				boolean isOpen = openable.isOpen();
				openable.setOpen( ! (isOpen) );
				state.setData(data);
				state.update();
				player.playEffect(player.getLocation(), Effect.DOOR_TOGGLE, null);
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
		return Level.NOVICE;
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
