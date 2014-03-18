package com.pulsior.theonepower.item.utilitem;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.item.CustomItem;

public class SteddingCreator extends CustomItem{
	
	public SteddingCreator(){
		super(Material.BLAZE_ROD);
		setDisplayName(ChatColor.RESET+"Stedding Creator");
		addLore(ChatColor.GREEN+"Click two blocks to create a stedding");
	}
}
