package com.pulsior.theonepower.weaves;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;

public class Invalid implements Weave {

	List<Element> elements = new ArrayList<Element>();
	String id = "Invalid";
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		
		double weaveLength = (double) TheOnePower.database.getChannel(player).weave.size();
		double playerHealth = player.getHealth() - weaveLength;
		if(playerHealth < 0){
			playerHealth = 0;
		}
		if(weaveLength != 0){
			Location location = player.getLocation();
			player.playSound(location, Sound.FIZZ, 1, 0);
			world.createExplosion(player.getTargetBlock( (HashSet<Byte>) null, 1).getLocation(), 0);
			player.setHealth(playerHealth);
			player.playEffect(EntityEffect.HURT);
		}
		
		return false;
	}
	
	@Override
	public List<Element> getElements() {
		return null;
	}
	
	@Override
	public Level getLevel()
	{
		return Level.NOVICE;
	}
	
	@Override
	public String getID()
	{
		return id;
	}
}
