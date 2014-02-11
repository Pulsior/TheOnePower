package com.pulsior.theonepower.channeling.weave;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Suldam implements Serializable{

	private static final long serialVersionUID = -5029938244184739585L;
	
	String name;
	Damane damane;
	
	public Suldam(Player player){
		name = player.getName();
	}
	
	public String getName(){
		return name;
	}
	
	public Player getPlayer(){
		return Bukkit.getPlayer(name);
	}
	
	public boolean hasDamane(){
		return getDamane() != null;
	}
	
	public void setDamane(Damane damane){
		this.damane = damane;
	}
	
	public Damane getDamane(){
		return damane;
	}
	
}
