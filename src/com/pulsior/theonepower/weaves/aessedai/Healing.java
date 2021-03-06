package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Healing implements Weave{
	
	List<Element> elements = new ArrayList<Element>();
	String id = "Healing";
	private final String name = "Healing";
	private final ChatColor color = ChatColor.GOLD;
	
	public Healing(){
		elements.add(Element.SPIRIT);
		elements.add(Element.WATER);
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		
		if(clickedEntity instanceof Player){
			Player clickedPlayer = (Player) clickedEntity;
			double playerHealth = clickedPlayer.getHealth();
			double healthDifference = 20-playerHealth;
			clickedPlayer.setFoodLevel(player.getFoodLevel()- (int) healthDifference);
			
			if(clickedPlayer.getFoodLevel() < 2){
				clickedPlayer.setFoodLevel(2);
			}
			
			clickedPlayer.setHealth(20);
			clickedPlayer.playEffect(EntityEffect.WOLF_HEARTS);
			world.playEffect(player.getLocation(), Effect.SMOKE, 0);
			return true;
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
		return Level.AES_SEDAI;
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
