package com.pulsior.theonepower.item.angreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.ItemGenerator;
import com.pulsior.theonepower.item.CustomItem;

public class SaAngreal extends CustomItem{

	public SaAngreal(){
		
		super(Material.EMERALD);
		addLore(ChatColor.GOLD+"Click to embrace saidar");
		setDisplayName(ChatColor.RESET+"Sa'angreal");
		setSpawnChance(15);
		ItemGenerator.registerItem(this);
	}
	
}
