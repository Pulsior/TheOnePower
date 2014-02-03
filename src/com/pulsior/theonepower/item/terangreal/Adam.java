package com.pulsior.theonepower.item.terangreal;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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
			
		}
		
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}

}
