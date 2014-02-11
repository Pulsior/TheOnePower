package com.pulsior.theonepower.channeling.weave;

import java.io.Serializable;
import java.util.Random;

import org.bukkit.Bukkit;

public class Shield implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -100093208885579522L;
	public int casterLevel; 
	public int targetLevel;
	
	public String casterName;
	
	public Shield(int casterLevel, int targetLevel, String casterName){
		this.casterLevel = casterLevel;
		this.targetLevel = targetLevel;
		this.casterName = casterName;
		
	}
	
	public boolean remove(int level, String releaseName){
		
		if(casterName.equalsIgnoreCase(releaseName)){
			return true;
		}
		
		int difference = level - targetLevel;
		int baseChance = 10;
		baseChance = baseChance + (5 * difference);
		double chance = baseChance / 100;
		Bukkit.getLogger().info(Double.toString(chance));
		double d = new Random().nextDouble();
		
		if ( d <= chance){
			return true;
		}
		
		return false;
		
		
	}
	
}