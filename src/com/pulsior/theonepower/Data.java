package com.pulsior.theonepower;

import java.io.Serializable;
import java.util.HashMap;

import com.pulsior.theonepower.channeling.Shield;
import com.pulsior.theonepower.unseenland.Memory;

public class Data implements Serializable{

	private static final long serialVersionUID = 6757620084506353691L;
	public HashMap<String, Shield> shieldedPlayersMap = new HashMap<String, Shield>();
	public HashMap<String, Memory> portalMemoriesMap = new HashMap<String, Memory>();
	
	public Data(){
		this.shieldedPlayersMap = TheOnePower.shieldedPlayersMap;
		this.portalMemoriesMap = TheOnePower.portalMemoriesMap;
	}
	
}
