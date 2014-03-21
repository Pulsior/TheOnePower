package com.pulsior.theonepower.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;

public class PlayerRegenerationTask extends BukkitRunnable{
	
	Player player;
	int maxLevel;
	
	public PlayerRegenerationTask(Player player, int maxLevel){
		this.player = player;
		this.maxLevel = maxLevel;
	}

	@Override
	public void run() {
		if( ! (player.getLevel() >= maxLevel) ) {
			
			Channel channel = TheOnePower.database.getChannel(player);
			if(channel != null){
				if( ! channel.isCasting() ){
					player.setLevel( player.getLevel()+1 );
				}
			}

		}
	};

}
