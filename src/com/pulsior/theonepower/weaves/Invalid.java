package com.pulsior.theonepower.weaves;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;

public class Invalid implements Weave {

	List<Element> elements = new ArrayList<Element>();
	
	@Override
	@SuppressWarnings("deprecation")
	public void cast(Player player, World world, Block clickedBlock, Entity clickedEntity) {
		
		double weaveLength = (double) TheOnePower.channelMap.get(player.getName()).weave.size();
		double playerHealth = player.getHealth() - weaveLength;
		if(playerHealth < 0){
			playerHealth = 0;
		}
		if(weaveLength != 0){
			Location location = player.getLocation();
			player.playSound(location, Sound.FIZZ, 1, 0);
			world.createExplosion(player.getTargetBlock(null, 1).getLocation(), 0);
			player.setHealth(playerHealth);
		}

	}
	
	@Override
	public List<Element> getElements() {
		return null;
	}
}
