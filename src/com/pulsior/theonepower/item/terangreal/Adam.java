package com.pulsior.theonepower.item.terangreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.weave.Damane;
import com.pulsior.theonepower.channeling.weave.Suldam;
import com.pulsior.theonepower.util.Strings;

public class Adam extends TerAngreal{

	public Adam() {
		
		super(Material.INK_SACK);
		setDurability( (short) 15);
		setDisplayName(Strings.A_DAM_NAME);
		
	}

	@Override
	public void use(Player player, Block block, BlockFace face, Entity entity) {
		
		if(entity instanceof Player){
			
			Suldam suldam = new Suldam(player);
			Player targetPlayer = (Player) entity;
			Damane damane = new Damane(targetPlayer, suldam);
			
			System.out.println(suldam.getName()+" has leashed "+damane.getName() );
			targetPlayer.sendMessage(ChatColor.GRAY+"You were leashed by "+ player.getName() );
			player.sendMessage(ChatColor.GREEN+"You leashed "+targetPlayer.getName() );
			
			
			TheOnePower.database.addDamane(damane);
			TheOnePower.database.addSuldam(suldam);
		}
		
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}

}
