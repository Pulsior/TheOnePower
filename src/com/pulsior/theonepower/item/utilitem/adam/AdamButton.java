package com.pulsior.theonepower.item.utilitem.adam;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.channeling.weave.Damane;
import com.pulsior.theonepower.channeling.weave.Suldam;
import com.pulsior.theonepower.item.CustomItem;

public abstract class AdamButton extends CustomItem{

	private static HashMap<String, AdamButton> buttonMap = new HashMap<String, AdamButton>();
	
	public AdamButton(Material type) {
		super(type);
	}
	
	public static void registerButton(String name, AdamButton button){
		buttonMap.put(name, button);
	}
	
	public static AdamButton getButton(ItemStack stack){
		ItemMeta meta = stack.getItemMeta();
		
		if( meta.hasDisplayName() ){
			return buttonMap.get( meta.getDisplayName() );
		}
		
		else{
			return null;
		}
	}
	
	public abstract void use(Suldam suldam, Damane damane);

}
