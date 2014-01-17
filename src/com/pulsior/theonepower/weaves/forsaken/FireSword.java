package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class FireSword implements Weave{
	
	public FireSword(){
		elements.add(Element.FIRE);
		elements.add(Element.FIRE);
		elements.add(Element.FIRE);
		elements.add(Element.EARTH);
	}
	
	List<Element> elements = new ArrayList<Element>();

	@Override
	@SuppressWarnings("deprecation")
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
		sword.addEnchantment(Enchantment.DURABILITY, 3);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD+"A sword made out of the One Power");
		ItemMeta meta = sword.getItemMeta();
		meta.setDisplayName(ChatColor.RED+"Fire-Wrought Sword");
		meta.setLore(lore);
		sword.setItemMeta(meta);
		Channel channel = TheOnePower.channelMap.get(player.getName() );
		channel.pickUpItems = true;
		player.getInventory().setItem(3, sword);
		player.updateInventory();
		channel.pickUpItems = false;
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
