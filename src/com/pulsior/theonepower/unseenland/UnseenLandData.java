package com.pulsior.theonepower.unseenland;

import java.io.Serializable;
import java.util.List;

public class UnseenLandData implements Serializable{

	private static final long serialVersionUID = 8740103317823618461L;
	
	public List<String> players;
	
	public UnseenLandData(UnseenLand land){
		this.players = land.players;
		land = null;
	}
	
	public List<String> getPlayers(){
		return players;
	}

}
