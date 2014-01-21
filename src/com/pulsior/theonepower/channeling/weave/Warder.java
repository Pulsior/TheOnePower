package com.pulsior.theonepower.channeling.weave;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Warder {

	String aesSedai;
	String playerName;
	
	public Warder(String playerName, String aesSedai){
		this.playerName = playerName;
		this.aesSedai = aesSedai;
		
		Player player = Bukkit.getPlayer(playerName);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000, 1), true);
	}

	
	public String getAesSedai(){
		return aesSedai;
	}
	
	public void setAesSedai(String aesSedai){
		this.aesSedai = aesSedai;
	}
	
}
