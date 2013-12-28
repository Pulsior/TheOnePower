package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class FoldedLight implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public FoldedLight(){
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);
	}
	
	@Override
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity) {	
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1) );
		player.playSound(player.getLocation(), Sound.FIZZ, 1, 0);

	}
	
	@Override
	public List<Element> getElements() {
		return elements;
	}

}
