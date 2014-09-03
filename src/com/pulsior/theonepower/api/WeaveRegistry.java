package com.pulsior.theonepower.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.pulsior.theonepower.weaves.Weave;

public class WeaveRegistry
{

	private static List<Weave> weaves = new ArrayList<Weave>();
	
	private WeaveRegistry()
	{
		
	}
	
	
	
	public static boolean registerWeave(Weave newWeave)
	{
		for(Weave w : weaves)
		{
			if( w.getElements().equals( newWeave.getElements() ) )
			{
				Bukkit.getLogger().info("TheOnePower: Tried to register weave with duplicate elements");
				return false;
			}
		}
		
		weaves.add(newWeave);
		return true;
	}
	
}
