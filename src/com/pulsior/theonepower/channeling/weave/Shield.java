package com.pulsior.theonepower.channeling.weave;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;

public class Shield implements Serializable
{

	private static final long serialVersionUID = -100093208885579522L;
	public int casterLevel;
	public int targetLevel;

	public UUID casterId;

	public Shield(int casterLevel, int targetLevel, UUID casterId)
	{
		this.casterLevel = casterLevel;
		this.targetLevel = targetLevel;
		this.casterId = casterId;

	}

	public boolean remove(int level, UUID releaseId){
		
		if( casterId.equals(releaseId) )
		{
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
