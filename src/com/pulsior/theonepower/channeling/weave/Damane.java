package com.pulsior.theonepower.channeling.weave;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Damane implements Serializable{

	private static final long serialVersionUID = -2456688996940473532L;
	
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
	
	public Player getPlayer(){
		return Bukkit.getPlayer(name);
	}
}
