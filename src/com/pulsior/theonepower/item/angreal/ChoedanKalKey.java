package com.pulsior.theonepower.item.angreal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.item.terangreal.TerAngreal;

public class ChoedanKalKey extends TerAngreal{

	public ChoedanKalKey() {
		super(Material.REDSTONE_TORCH_ON);
		setDisplayName(ChatColor.RESET+"Choedan Kal Key");
	}	


	@Override
	public void use(Player player, Block block, BlockFace face, Entity entity) {
		
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}

}
