package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftFirework;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Shield;
import com.pulsior.theonepower.weaves.Weave;

public class Shielding implements Weave {

	List<Element> elements = new ArrayList<Element>();

	public Shielding(){
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.FIRE);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		Bukkit.getLogger().info("Excetude");
		if(clickedEntity instanceof Player){
			Player targetPlayer = (Player) clickedEntity;
			String targetName = targetPlayer.getName();
			Channel channel = TheOnePower.channelMap.get(targetName);
			Bukkit.getLogger().info("Clicked!");
			if(channel != null){
				channel.close();
			}
			targetPlayer.sendMessage(ChatColor.RED+"You were shielded from saidar");
			
			
			Firework firework = (Firework) world.spawnEntity(targetPlayer.getLocation(), EntityType.FIREWORK);
			FireworkMeta meta = firework.getFireworkMeta();
			FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.RED).with(Type.BALL).trail(false).build();
			meta.addEffect(effect);
			meta.setPower(1);
			firework.setFireworkMeta(meta);
			
			( (CraftFirework) firework).getHandle().expectedLifespan = 4;
			
			int casterLevel;
			String casterName = player.getName();
			Channel casterChannel = TheOnePower.channelMap.get( casterName );
			casterLevel = casterChannel.maxLevel;
			
			
			TheOnePower.shieldedPlayersMap.put(targetName, new Shield(casterLevel, channel.maxLevel, casterName) );
			Bukkit.getLogger().info("Shielded!");

		}
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}