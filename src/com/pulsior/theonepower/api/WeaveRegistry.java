package com.pulsior.theonepower.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Invalid;
import com.pulsior.theonepower.weaves.Weave;

public class WeaveRegistry
{
	private static Invalid i = new Invalid();
	
	private static List<Weave> weaves = new ArrayList<Weave>();
	
	private WeaveRegistry()
	{
		
	}
	
	public static Weave compareWeave(List<Element> weavePattern)
	{
		for (Weave weave : weaves)
		{
			List<Element> elements = weave.getElements();
			if ( elements.equals(weavePattern) )
			{
				return weave;
			}
		}
		return i;
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
