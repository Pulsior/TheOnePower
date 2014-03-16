package com.pulsior.theonepower.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {
	
	ItemStack item;
	int spawnChance;
	
	public CustomItem(Material type){
		item = new ItemStack(type);
	}
	
	public void setType(Material type){
		item.setType(type);
	}
	
	public void setDurability(short data){
		item.setDurability(data);
	}
	
	public void addLore(String lore){
		ItemMeta meta = item.getItemMeta();
		List<String> list;
		
		if(meta.hasLore() ){
			list = meta.getLore();
		}
		
		else{
			list = new ArrayList<String>();
		}
		
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
	
	public void setSpawnChance(int chance){
		this.spawnChance = chance;
	}
	
	public int getSpawnChance(){
		return spawnChance;
	}
	
	public ItemStack asItem(){
		return item;
	}
	
	
}
