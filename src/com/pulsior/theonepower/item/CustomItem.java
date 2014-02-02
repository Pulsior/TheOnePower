package com.pulsior.theonepower.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {
	
	ItemStack item;
	
	public CustomItem(Material type){
		item = new ItemStack(type);
	}
	
	public void setType(Material type){
		item.setType(type);
	}
	
	public void setLore(String lore){
		ItemMeta meta = item.getItemMeta();
		List<String> list = new ArrayList<String>();
		list.add(lore);
		meta.setLore(list);
		item.setItemMeta(meta);
	}
	
	public void setDisplayName(String name){
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}
	
	public void setEnchantment(Enchantment ench, int level){
		item.addEnchantment(ench, level);
	}
	
	public ItemStack asItem(){
		return item;
	}
	
	
}
