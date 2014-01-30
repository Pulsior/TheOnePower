package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.weave.AesSedai;
import com.pulsior.theonepower.channeling.weave.Warder;
import com.pulsior.theonepower.weaves.Weave;

public class BindWarder implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	
	public BindWarder(){
		elements.add(Element.SPIRIT);
		elements.add(Element.FIRE);
		elements.add(Element.WATER);
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);
		
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
			
		if (clickedEntity instanceof Player){
			
			AesSedai aesSedai = new AesSedai(player);
			
			Warder warder = new Warder((Player) clickedEntity, aesSedai);
			TheOnePower.warders.add(warder);
			
			return true;
		}
		
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
