package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class BindWolfGaidin implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public BindWolfGaidin(){
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.AIR);
		elements.add(Element.WATER);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {

		if(clickedEntity instanceof Wolf){
			Wolf wolf = (Wolf) clickedEntity;
			wolf.setAngry(false);
			wolf.setTamed(true);
			wolf.setOwner(player);
			world.playEffect(wolf.getLocation(), Effect.SMOKE, 0);
			player.playSound(player.getLocation(), Sound.WOLF_BARK, 1, 0);
			return true;
		}
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}