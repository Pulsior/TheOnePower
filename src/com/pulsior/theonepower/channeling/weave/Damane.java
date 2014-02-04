package com.pulsior.theonepower.channeling.weave;

import org.bukkit.entity.Player;

public class Damane {

	String name;
	Suldam suldam;
	
	public Damane(Player player, Suldam suldam){
		this.name = player.getName();
		this.suldam = suldam;
		suldam.setDamane(this);
	}
	
	public Suldam getSuldam(){
		return suldam;
	}
	
	public String getName(){
		return name;
	}
}
