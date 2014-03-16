package com.pulsior.theonepower.item.angreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.ItemGenerator;
import com.pulsior.theonepower.item.CustomItem;

public class Angreal extends CustomItem{
	
	public Angreal(){
		
		super(Material.FLINT);
		addLore(ChatColor.GOLD+"Click to embrace saidar");
		setDisplayName(ChatColor.RESET+"Angreal");
		setSpawnChance(40);
		ItemGenerator.registerItem(this);
	}
	
}
