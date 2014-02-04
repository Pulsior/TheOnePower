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
		addLore(ChatColor.BLUE+"Right-click to use this ter'angreal");
	}

	public static TerAngreal toTerAngreal(ItemStack item){

		ItemMeta meta = item.getItemMeta();
		if(meta != null){

			if (meta.hasLore()){

				if( meta.hasDisplayName() ){

					TerAngreal terAngreal = itemMap.get( meta.getDisplayName() );
					if(terAngreal != null){
						return terAngreal;
					}
				}
			}
		}

		return null;
	}

	public static void registerItem(String name, TerAngreal item){
		TerAngreal.itemMap.put(name, item);
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
