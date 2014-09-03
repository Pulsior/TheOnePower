package com.pulsior.theonepower.weaves.accepted;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Delving implements Weave{

	List<Element> elements = new ArrayList<Element>();

	public Delving(){
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);

	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {

		if(clickedEntity instanceof Player){
			Player clickedPlayer = (Player) clickedEntity;
			double health = clickedPlayer.getHealth();
			player.sendMessage(ChatColor.YELLOW+"The other player has " + ChatColor.RED + Math.floor(health) / 2 + ChatColor.YELLOW + " out of 10 hearts");

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
		return Level.ACCEPTED;
	}

}
