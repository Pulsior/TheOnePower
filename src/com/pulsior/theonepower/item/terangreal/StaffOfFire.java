package com.pulsior.theonepower.item.terangreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class StaffOfFire extends TerAngreal{

	final String NAME = ChatColor.RESET+"Staff of Fire";
	
	public StaffOfFire() {
		super(Material.BLAZE_ROD);
		
		setDisplayName(NAME);

	}
	
	@Override
	public void use(Player player, Block block, BlockFace face, Entity entity){
		player.launchProjectile(Fireball.class);
		
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}
	

}
