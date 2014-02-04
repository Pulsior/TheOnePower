package com.pulsior.theonepower.channeling.weave;

import org.bukkit.entity.Player;

public class Suldam {

	String name;
	Damane damane;
	
	public Suldam(Player player){
		name = player.getName();
	}
	
	public String getName(){
		return name;
	}
	
	public void setDamane(Damane damane){
		this.damane = damane;
	}
	
	public Damane getDamane(){
		return damane;
	}
	
}
