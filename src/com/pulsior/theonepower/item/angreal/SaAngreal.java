package com.pulsior.theonepower.item.angreal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.ItemGenerator;
import com.pulsior.theonepower.SaidarEmbraceEvent;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.item.terangreal.TerAngreal;

public class SaAngreal extends TerAngreal{

	public SaAngreal(){
		
		super(Material.EMERALD);
		addLore(ChatColor.GOLD+"Click to embrace saidar");
		setDisplayName(ChatColor.RESET+"Sa'angreal");
		setSpawnChance(15);
		ItemGenerator.registerItem(this);
	}

	@Override
	public void use(Player player, Block block, BlockFace face, Entity entity) {
		SaidarEmbraceEvent event = new SaidarEmbraceEvent( player);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if(! event.isCancelled() ){
			new Channel ( player.getName(), 50 );
		}
		
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}
	
}
