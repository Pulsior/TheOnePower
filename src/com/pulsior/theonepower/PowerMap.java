package com.pulsior.theonepower;

import java.io.Serializable;
import java.util.HashMap;

public class PowerMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1455786389387906551L;
	
	public HashMap<String, Integer> levelMap;
	public HashMap<String, Integer> weaveProgressMap;
	public HashMap<String, Integer> requiredWeavesMap;
	
	public PowerMap(){
		levelMap = new HashMap<String, Integer>();
		weaveProgressMap  = new HashMap<String, Integer>();
		requiredWeavesMap = new HashMap<String, Integer>();
	}
	
	public void addWeave(String name){
		int amtOfWeaves = weaveProgressMap.get(name);
		amtOfWeaves = amtOfWeaves+1;
		weaveProgressMap.put(name, amtOfWeaves);
		checkLevel(name);
	}
	
	public void increaseLevel(String name){
		int amtOfLevels = levelMap.get(name);
		amtOfLevels = amtOfLevels + 1;
		levelMap.put(name, amtOfLevels);
	}
	
	public void add(String name){
		levelMap.put(name, 3);
		weaveProgressMap.put(name, 0);
		requiredWeavesMap.put(name, 3);
		
	}
	
	public boolean checkLevel(String name){
		int amountOfWeaves = weaveProgressMap.get( name );
		int requiredWeaves = requiredWeavesMap.get( name );
		
		if(amountOfWeaves == requiredWeaves){
			weaveProgressMap.put(name, 0);
			requiredWeavesMap.put(name, requiredWeaves+2);
			increaseLevel(name);
		}
	
		
		return false;
	}
	
	
}
