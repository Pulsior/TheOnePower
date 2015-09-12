package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.util.Utility;
import com.pulsior.theonepower.weaves.Weave;

public class SpotPlayers implements Weave{

	List<Element> elements = new ArrayList<Element>();
	String id = "SpotPlayers";
	
	public SpotPlayers(){
		elements.add(Element.SPIRIT);
		elements.add(Element.FIRE);
		elements.add(Element.EARTH);
		elements.add(Element.SPIRIT);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		List<Entity> entities = player.getNearbyEntities(50, 50, 50);

		for(Entity e : entities){
			if(e instanceof Player){
				Utility.spawnFireworkEffect(e.getLocation(), Color.YELLOW, Color.RED, Type.BALL, true, false, 15);
			}
		}
		return true;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Level getLevel()
	{
		return Level.AES_SEDAI;
	}
	
	@Override
	public String getID() {
		return id;
	}

}