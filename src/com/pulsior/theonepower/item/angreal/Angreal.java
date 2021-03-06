package com.pulsior.theonepower.item.angreal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.ItemGenerator;
import com.pulsior.theonepower.api.event.SaidarEmbraceEvent;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.item.terangreal.TerAngreal;

public class Angreal extends TerAngreal{
	
	public Angreal(){
		
		super(Material.FLINT);
		addLore(ChatColor.GOLD+"Click to embrace saidar");
		setDisplayName(ChatColor.RESET+"Angreal");
		setSpawnChance(40);
		ItemGenerator.registerItem(this);
	}

	@Override
	public void use(Player player, Block block, BlockFace face, Entity entity) {
		SaidarEmbraceEvent event = new SaidarEmbraceEvent( player);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if(! event.isCancelled() ){
			new Channel ( player.getUniqueId(), 15 );
		}
		
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}
	
}
