package com.pulsior.theonepower.item;

import org.bukkit.inventory.ItemStack;

public enum AngrealType {
	
	ANGREAL(PowerItem.ANGREAL, 15),
	SA_ANGREAL(PowerItem.SA_ANGREAL, 50),
	CALLANDOR(PowerItem.CALLANDOR, 150);
	
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
