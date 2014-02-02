package com.pulsior.theonepower.item.terangreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.item.CustomItem;

public class UnseenLandStone extends CustomItem{
	
	public UnseenLandStone(){
		super(Material.NETHER_BRICK_ITEM);
		setLore(ChatColor.GOLD+"Sleep with this to go to the Unseen Land");
		setDisplayName(ChatColor.RESET+"Stone of the Unseen Land");
	}
	
	
}
