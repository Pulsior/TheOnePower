package com.pulsior.theonepower;

import java.util.ArrayList;
import java.util.List;

import com.pulsior.theonepower.channeling.weave.Damane;
import com.pulsior.theonepower.channeling.weave.Suldam;

public class Database {
	
	public List<Damane> damaneList = new ArrayList<Damane>();
	public List<Suldam> suldamList = new ArrayList<Suldam>();
	
	public void addDamane(Damane damane){
		damaneList.add(damane);
	}
	
	public boolean isDamane(String name){
		
		for (Damane d : damaneList){
			if(d.getName().equalsIgnoreCase(name) ){
				return true;
			}
		}
		
		return false;
	}
	
	public Damane getDamane(String name){
		
		for (Damane d : damaneList){
			if(d.getName().equalsIgnoreCase(name) ){
				return d;
			}
		}
		
		return null;
	}
	
	public void addSuldam(Suldam suldam){
		suldamList.add(suldam);
	}
	
	public boolean isSuldam(String name){
		
		for (Suldam s : suldamList){
			if(s.getName().equalsIgnoreCase(name) ){
				return true;
			}
		}
		
		return false;
	}
	
	public Suldam getSuldam(String name){
		
		for (Suldam s : suldamList){
			if(s.getName().equalsIgnoreCase(name) ){
				return s;
			}		
		}
		
		return null;
	}
	
	
}
