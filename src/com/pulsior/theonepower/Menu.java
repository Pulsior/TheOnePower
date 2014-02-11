package com.pulsior.theonepower;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.pulsior.theonepower.item.utilitem.adam.ForceEmbraceButton;
import com.pulsior.theonepower.item.utilitem.adam.PunishButton;

public class Menu {
	
	public static Inventory getAdamMenu(Player player){
		
		Inventory inventory = Bukkit.createInventory(player, 9);
		inventory.addItem( new PunishButton().asItem() );
		inventory.addItem( new ForceEmbraceButton().asItem() );
				
		return inventory;
	}
	
}
