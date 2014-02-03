package com.pulsior.theonepower.item.utilitem;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.item.CustomItem;

public class ReturnToken extends CustomItem{

	public ReturnToken(){
		super(Material.NETHER_STAR);
		
		addLore(ChatColor.GOLD+"Click to wake up");
		setDisplayName(ChatColor.RESET+"Wake up");
	}
	
}
