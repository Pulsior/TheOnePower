package com.pulsior.theonepower.unseenland;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;


public class Memory implements Serializable{
	
	private static final long serialVersionUID = -6360739145491482100L;
	public String name;
	public double[] location = new double[3];
	
	public Memory(String name, Location location){
		String tempName = Character.toUpperCase(name.charAt(0)) + name.substring(1);;
		this.name = ChatColor.RESET + tempName;
		this.location[0] = location.getX();
		this.location[1] = location.getY();
		this.location[2] = location.getZ();
	}
	
	
	public Location getLocation(){
		return new Location(Bukkit.getWorld("tel'aran'rhiod"), location[0], location[1], location[2]);
	}

}
