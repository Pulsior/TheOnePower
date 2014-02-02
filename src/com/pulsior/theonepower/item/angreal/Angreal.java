package com.pulsior.theonepower.item.angreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.item.CustomItem;

public class Angreal extends CustomItem{
	
	public Angreal(){
		
		super(Material.FLINT);
		setLore(ChatColor.GOLD+"Click to embrace saidar");
		setDisplayName(ChatColor.RESET+"Angreal");
	}
	
}
