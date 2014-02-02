package com.pulsior.theonepower.item.angreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.item.CustomItem;

public class SaAngreal extends CustomItem{

	public SaAngreal(){
		super(Material.EMERALD);
		setLore(ChatColor.GOLD+"Click to embrace saidar");
		setDisplayName(ChatColor.RESET+"Sa'angreal");
	}
	
}
