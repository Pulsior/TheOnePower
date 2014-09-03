package com.pulsior.theonepower;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.pulsior.theonepower.item.angreal.Angreal;
import com.pulsior.theonepower.item.angreal.Callandor;
import com.pulsior.theonepower.item.angreal.SaAngreal;
import com.pulsior.theonepower.item.terangreal.StaffOfFire;
import com.pulsior.theonepower.item.terangreal.StaffOfMeteor;
import com.pulsior.theonepower.item.utilitem.SteddingCreator;

public class AngrealMenu
{
	
	public static Inventory getInventory()
	{
		Inventory inv = Bukkit.createInventory(null, 9);
		inv.addItem(new Angreal().asItem() );
		inv.addItem(new Callandor().asItem() );
		inv.addItem(new SaAngreal().asItem() );
		inv.addItem(new StaffOfFire().asItem() );
		inv.addItem(new StaffOfMeteor().asItem() );
		inv.addItem(new SteddingCreator().asItem() );
		
		return inv;
	}
	
}
