package com.pulsior.theonepower.channeling;

import org.bukkit.ChatColor;

/**
 * Enum representing the five powers
 * @author Pulsior
 *
 */
public enum Element {
	EARTH(ChatColor.DARK_GREEN),
	AIR(ChatColor.BLUE),
	FIRE(ChatColor.RED),
	WATER(ChatColor.AQUA),
	SPIRIT(ChatColor.GRAY);
	
	ChatColor color;
	
	Element(ChatColor color){
		this.color = color;
	}
	
	public String toString(){
		String string = super.toString();
		string = string.toLowerCase();
		String newString = string.substring(0, 1).toUpperCase() + string.substring(1);
		return color + newString;
	}
}



