package com.pulsior.theonepower.item.terangreal;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.ItemGenerator;
import com.pulsior.theonepower.util.Strings;

public class StaffOfFire extends TerAngreal{
	
	public StaffOfFire() {
		super(Material.BLAZE_ROD);
		setDisplayName(Strings.FIRE_STAFF_NAME);
		setSpawnChance(25);
		ItemGenerator.registerItem(this);

	}
	
	@Override
	public void use(Player player, Block block, BlockFace face, Entity entity){
		player.launchProjectile(Fireball.class);
		player.getWorld().playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 1, 0);
		
	}

	@Override
	public boolean needsChanneling() {
		return false;
	}
	

}
