package com.pulsior.theonepower;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.Inventory;

import com.pulsior.theonepower.item.CustomItem;
import com.pulsior.theonepower.util.Utility;

public class ItemGenerator implements Listener{

	static List<CustomItem> items = new ArrayList<CustomItem>();

	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent event){

		BlockState[] tileEntities = event.getChunk().getTileEntities();

		for(BlockState state : tileEntities){

			if(state instanceof Chest){
				Chest chest = (Chest) state;
				Inventory inventory = chest.getBlockInventory();

				System.out.println("Generated a chest!");

				for(CustomItem i : items){

					if ( Utility.chance(i.getSpawnChance() ) ){
						inventory.addItem( i.asItem() );
						Location l = chest.getLocation();
						Bukkit.getLogger().info("Added an item @"+l.getBlockX() +", "+ l.getBlockY() +", "+ l.getBlockZ() );
					}
				}


			}	
		}
	}

	public static void registerItem(CustomItem item){
		items.add(item);
	}

}
