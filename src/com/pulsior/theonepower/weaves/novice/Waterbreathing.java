package com.pulsior.theonepower.weaves.novice;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Waterbreathing implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public Waterbreathing(){
		elements.add(Element.WATER);
		elements.add(Element.AIR);
		elements.add(Element.WATER);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 1 ) );
		player.playSound(player.getLocation(), Sound.DRINK, 1, 0);
		
		return true;

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

}
