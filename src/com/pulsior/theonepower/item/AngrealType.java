package com.pulsior.theonepower.item;

import org.bukkit.inventory.ItemStack;

import com.pulsior.theonepower.item.angreal.Angreal;
import com.pulsior.theonepower.item.angreal.Callandor;
import com.pulsior.theonepower.item.angreal.SaAngreal;

public enum AngrealType {
	
	ANGREAL(new Angreal().asItem(), 15),
	SA_ANGREAL(new SaAngreal().asItem(), 50),
	CALLANDOR(new Callandor().asItem(), 150);
	
	final ItemStack ITEM;
	final int LEVEL;
	
	AngrealType(ItemStack item, int level){
		this.ITEM = item;
		this.LEVEL = level;
	}
	
	public ItemStack getItem(){
		return ITEM;
	}
	
	public int getLevel(){
		return LEVEL;
	}
}
