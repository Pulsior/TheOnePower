package com.pulsior.theonepower.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pulsior.theonepower.TheOnePower;

public class PlayerRegenerationTask extends BukkitRunnable{
	
	Player player;
	int maxLevel;
	
	public PlayerRegenerationTask(Player player, int maxLevel){
		this.player = player;
		this.maxLevel = maxLevel;
	}

	@Override
	public void run() {
		String playerName = player.getName();
		if( ! (player.getLevel() >= maxLevel) ) {

			if(TheOnePower.castingPlayersMap.get( playerName ).equals (new Boolean( false) ) ){
				player.setLevel( player.getLevel()+1 );;
			}

		}
	};

}
