package com.pulsior.theonepower.unseenland;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class UnseenLandData implements Serializable{

	private static final long serialVersionUID = 8740103317823618461L;
	
	public List<String> players;
	public HashMap<String, String> sleepingInventoryMap = new HashMap<String, String>();
	public HashMap<String,  List<Memory> > memoryMap = new HashMap<String, List<Memory> >();
	
	public UnseenLandData(UnseenLand land){
		this.players = land.players;
		this.sleepingInventoryMap = land.sleepingInventoryMap;
		this.memoryMap = land.memoryMap;
		land = null;
	}
	
	public List<String> getPlayers(){
		return players;
	}

}
