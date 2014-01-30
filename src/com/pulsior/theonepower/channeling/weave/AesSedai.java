package com.pulsior.theonepower.channeling.weave;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AesSedai {

	
	Player player;
	Warder warder;
	
	public AesSedai(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	
	public Warder getWarder(){
		return warder;
	}
	
	public void setWarder(Warder warder){
		this.warder = warder;
		player.setCompassTarget( warder.getPlayer().getLocation() );
		Inventory inventory = player.getInventory();
		
		if(! inventory.contains( Material.COMPASS ) ){
			inventory.addItem(new ItemStack(Material.COMPASS) );
		}
	}
}
