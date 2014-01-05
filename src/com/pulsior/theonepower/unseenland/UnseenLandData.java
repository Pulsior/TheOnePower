package com.pulsior.theonepower.unseenland;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.pulsior.theonepower.channeling.Shield;
	
/**
 * A serializable data class to preserve data about the Unseen Land
 * @author Pulsior
 *
 */

public class UnseenLandData implements Serializable{

	private static final long serialVersionUID = 8740103317823618461L;
	
	public List<String> players;
	public HashMap<String, ItemStack[]> unseenLandInventoryMap = new HashMap<String, ItemStack[]>();
	public HashMap<String, ItemStack[]> unseenLandArmorMap = new HashMap<String, ItemStack[]>();
	public HashMap<String,  List<Memory> > memoryMap = new HashMap<String, List<Memory> >();
	public HashMap<String, Shield> shieldedPlayersMap = new HashMap<String, Shield>();
	
	public UnseenLandData(UnseenLand land){
		this.players = land.players;
		this.memoryMap = land.memoryMap;
		this.unseenLandInventoryMap = land.unseenLandInventoryMap;
		this.unseenLandArmorMap = land.unseenLandArmorMap;
		land = null;
	}
	
	public List<String> getPlayers(){
		return players;
	}

}
