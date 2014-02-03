package com.pulsior.theonepower.item.terangreal;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.item.CustomItem;


public abstract class TerAngreal extends CustomItem{

	public static HashMap<String, TerAngreal> itemMap = new HashMap<String, TerAngreal>();

	public TerAngreal(Material type) {
		super(type);
		addLore(ChatColor.GOLD+"Right-click to use this ter'angreal");
	}

	public static TerAngreal toTerAngreal(ItemStack item){

		ItemMeta meta = item.getItemMeta();
		if (meta.hasLore()){

			if( meta.hasDisplayName() ){
				
				TerAngreal terAngreal = itemMap.get( meta.getDisplayName() );
				if(terAngreal != null){
					return terAngreal;
				}
			}
		}

		return null;
	}
	
	public static void registerName(String name){
		TerAngreal.itemMap.put(name, new StaffOfFire());
	}

	/**
	 * Contains code to be executed on a right-click
	 */

	public abstract void use(Player player, Block block, BlockFace face, Entity entity);

	/**
	 *  Get if it requires the user to channel
	 */
	public abstract boolean needsChanneling();

}
