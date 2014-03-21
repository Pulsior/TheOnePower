package com.pulsior.theonepower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Stedding;
import com.pulsior.theonepower.channeling.weave.Damane;
import com.pulsior.theonepower.channeling.weave.Shield;
import com.pulsior.theonepower.channeling.weave.Suldam;
import com.pulsior.theonepower.channeling.weave.Warder;

public class Database implements Serializable{

	private static final long serialVersionUID = -1169318961339172944L;

	private HashMap<String, Channel> channelMap = new HashMap<String, Channel>();
	
	private List<Damane> damaneList = new ArrayList<Damane>();
	private List<Suldam> suldamList = new ArrayList<Suldam>();
	private List<Warder> warderList = new ArrayList<Warder>();
	private List<Stedding> steddingList = new ArrayList<Stedding>();

	private HashMap<String, Shield> shieldMap = new HashMap<String, Shield>();
	
	public void addDamane(Damane damane){
		if(! isDamane(damane.getName() ) ){
			damaneList.add(damane);
		}
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
	
	public void removeDamane(String name){
		damaneList.remove( getDamane(name) );
	}
	
	public void removeDamane(Damane damane){
		damaneList.remove( damane );
		suldamList.remove( damane.getSuldam() );
	}

	public void addSuldam(Suldam suldam){
		if(! isSuldam(suldam.getName() ) ){
			suldamList.add(suldam);
		}
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
	
	public void removeSuldam(String name){
		suldamList.remove( getSuldam(name) );
	}
	
	public void removeDamane(Suldam suldam){
		suldamList.remove( suldam );
	}
	
	public void addWarder(Warder warder){
		if(! isWarder(warder.getName() ) ){
			warderList.add(warder);
		}
	}

	public boolean isWarder(String name){

		for(Warder w : warderList){

			if(w.getName().equalsIgnoreCase( name ) ){
				return true;
			}
		}

		return false;
	}

	public Warder getWarder(String name){

		for(Warder w : warderList){

			if(w.getName().equalsIgnoreCase(name)){
				return w;
			}
		}

		return null;
	}


	public boolean hasShield(String name){
		return shieldMap.containsKey(name);
	}

	public Shield getShield(String name){
		return shieldMap.get(name);
	}

	public void addShield(String name, Shield shield){
		shieldMap.put(name, shield);
	}

	public void removeShield(String name){
		shieldMap.remove(name);
	}
	
	public void addStedding(Stedding stedding){
		steddingList.add(stedding);
	}
	
	public List<Stedding> getSteddings(){
		return steddingList;
	}
	
	public void removeStedding(Stedding stedding){
		steddingList.remove(stedding);
	}
	
	public void addChannel(String name, Channel channel){
		channelMap.put(name, channel);
	}
	
	public Channel getChannel(String name){
		return channelMap.get(name);
	}
	
	public Channel getChannel(Player player){
		return channelMap.get( player.getName() );
	}

	public void removeChannel(String name){
		channelMap.remove(name);
	}
	
	public void removeChannel(Player player){
		channelMap.remove( player.getName() );
	}
	

}
