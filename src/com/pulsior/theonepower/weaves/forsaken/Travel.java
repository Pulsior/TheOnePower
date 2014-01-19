package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.unseenland.Memory;
import com.pulsior.theonepower.weaves.Weave;

public class Travel implements Weave {

	List<Element> elements = new ArrayList<Element>();

	public Travel(){
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		
		if(TheOnePower.unseenLand.players.contains( player.getName() ) ){
			player.sendMessage(ChatColor.RED+"You cannot create a portal in the Unseen Land!");
			return false;
		}
		
		if(clickedBlock != null){
			openMenu(player);
			return true;
		}
		
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

	public void openMenu(Player player){
		Inventory gui = Bukkit.createInventory(player, 9);
		List<Memory> memories = TheOnePower.unseenLand.memoryMap.get( player.getName() );
		if(memories != null){
			int counter = 0;
			for(Memory mem : memories){
				ItemStack memoryTear = new ItemStack(Material.GHAST_TEAR);
				ItemMeta meta = memoryTear.getItemMeta();
				meta.setDisplayName(mem.name);
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.YELLOW+"Click to select a gateway destination");
				meta.setLore(lore);
				memoryTear.setItemMeta(meta);
				gui.setItem(counter, memoryTear);
				counter++;
			}
			player.openInventory(gui);
		}
	}

}
