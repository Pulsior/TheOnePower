package com.pulsior.theonepower.channeling.weave;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Warder{

	AesSedai aesSedai;
	String name;
	Player player;
	
	public Warder(Player player, AesSedai aesSedai){
		System.out.println(player.getName()+" became the warder of " + aesSedai.getPlayer().getName() );
		
		this.player = player;
		this.name = player.getName();
		this.aesSedai = aesSedai;
		aesSedai.setWarder(this);
		
		player.setCompassTarget(aesSedai.getPlayer().getLocation() );
		player.getInventory().addItem(new ItemStack(Material.COMPASS) );
		player.addPotionEffect( new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10000, 1), true);
		
	}

	
	public AesSedai getAesSedai(){
		return aesSedai;
	}
	
	public void setAesSedai(AesSedai aesSedai){
		this.aesSedai = aesSedai;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public String getName(){
		return name;
	}
	
}
