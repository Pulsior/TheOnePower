package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class Healing implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	
	public Healing(){
		elements.add(Element.AIR);
		elements.add(Element.WATER);
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.WATER);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);
		elements.add(Element.FIRE);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);
		elements.add(Element.FIRE);
	}
	
	@Override
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity) {
		
		if(clickedEntity instanceof Player){
			Player clickedPlayer = (Player) clickedEntity;
			double playerHealth = clickedPlayer.getHealth();
			double healthDifference = 20-playerHealth;
			clickedPlayer.setFoodLevel(player.getFoodLevel()- (int) healthDifference);
			if(clickedPlayer.getFoodLevel() < 2){
				clickedPlayer.setFoodLevel(2);
			}
			clickedPlayer.setHealth(20);
			world.playEffect(player.getLocation(), Effect.SMOKE, 0);
		}
		
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}


}
