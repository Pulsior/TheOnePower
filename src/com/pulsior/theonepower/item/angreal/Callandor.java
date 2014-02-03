package com.pulsior.theonepower.item.angreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import com.pulsior.theonepower.item.CustomItem;

public class Callandor extends CustomItem{

	public Callandor(){
		
		super(Material.DIAMOND_SWORD);
		addLore(ChatColor.GOLD+"Click to embrace saidar");
		setDisplayName(ChatColor.RESET+"Callandor");
		setEnchantment(Enchantment.DAMAGE_ALL, 3);
		
	}
	
}
