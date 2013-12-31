package com.pulsior.theonepower.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PowerItem {
	
	public static final ItemStack DREAM_ANGREAL = getAngrealStack("dream");
	public static final ItemStack SA_ANGREAL = getAngrealStack("sa'angreal");
	public static final ItemStack ANGREAL = getAngrealStack("angreal");
	public static final ItemStack CALLANDOR = getAngrealStack("callandor");
	
	public static final ItemStack returnToken = getReturnToken();
	
	
	/**
	 * Returns an angreal
	 * @param type
	 * @return
	 */
	public static ItemStack getAngrealStack(String type){
		
		if(type.equals("dream")){
			ItemStack stack = new ItemStack(Material.NETHER_BRICK_ITEM);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.RESET+"Stone of the Unseen Land");
			List<String> lore = new ArrayList<String>();
			meta.setLore(lore);
			stack.setItemMeta(meta);
			return stack;
		}
		
		else if(type.equals("angreal")){
			ItemStack stack = new ItemStack(Material.FLINT);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.RESET+"Angreal");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD+"Click to embrace saidar");
			meta.setLore(lore);
			stack.setItemMeta(meta);
			return stack;
		}
		
		else if(type.equals("sa'angreal")){
			ItemStack stack = new ItemStack(Material.EMERALD);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.RESET+"Sa'angreal");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD+"Click to embrace saidar");
			meta.setLore(lore);
			stack.setItemMeta(meta);
			return stack;
		}
		
		else if(type.equals("callandor")){
			ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.RESET+"Callandor");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD+"Click to embrace saidar");
			meta.setLore(lore);
			stack.setItemMeta(meta);
			
			stack.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			stack.addEnchantment(Enchantment.DURABILITY, 3);
			
			return stack;
		}
		
		return null;
	}

	/**
	 * Returns an ItemStack to wake up from the Unseen Land
	 * @return
	 */
	public static ItemStack getReturnToken(){
		ItemStack stack = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"Wake Up");
		stack.setItemMeta(meta);
		return stack;
	}
}
