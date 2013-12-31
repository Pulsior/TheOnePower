package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

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
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Shield;
import com.pulsior.theonepower.weaves.Weave;

public class RemoveShield implements Weave{

	List<Element> elements = new ArrayList<Element>();

	public RemoveShield(){
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		if(clickedEntity instanceof Player){
			Player targetPlayer = (Player) clickedEntity;
			String name = targetPlayer.getName();
			Shield shield = TheOnePower.shieldedPlayersMap.get(name);
			if( shield != null ){
				String casterName = player.getName();
				int level = TheOnePower.channelMap.get(name).maxLevel;
				boolean removed = shield.remove(level, casterName);

				if(removed){

					Firework firework = (Firework) world.spawnEntity(targetPlayer.getLocation(), EntityType.FIREWORK);
					FireworkMeta meta = firework.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.BLUE).withFade(Color.GREEN).with(Type.BALL).trail(false).build();
					meta.addEffect(effect);
					meta.setPower(1);
					firework.setFireworkMeta(meta);

					( (CraftFirework) firework).getHandle().expectedLifespan = 4;

				}
				else{
					player.sendMessage(ChatColor.RED+"You were not strong enough to break the shield!");
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

}
