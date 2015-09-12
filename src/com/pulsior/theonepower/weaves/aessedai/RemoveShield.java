package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.channeling.weave.Shield;
import com.pulsior.theonepower.weaves.Weave;

public class RemoveShield implements Weave{

	List<Element> elements = new ArrayList<Element>();
	String id = "RemoveShield";
	
	public RemoveShield(){
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		if(clickedEntity instanceof Player){
			Player targetPlayer = (Player) clickedEntity;
			UUID targetId = targetPlayer.getUniqueId();
			Shield shield = TheOnePower.database.getShield(targetId);
			if( shield != null ){
				UUID casterId = player.getUniqueId();
				int level = TheOnePower.database.getChannel(player).maxLevel;
				boolean removed = shield.remove(level, casterId);

				if(removed){

					Firework firework = (Firework) world.spawnEntity(targetPlayer.getLocation(), EntityType.FIREWORK);
					FireworkMeta meta = firework.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.BLUE).withFade(Color.GREEN).with(Type.BALL).trail(false).build();
					meta.addEffect(effect);
					meta.setPower(1);
					firework.setFireworkMeta(meta);			
					( (CraftFirework) firework).getHandle().expectedLifespan = 4;
					TheOnePower.database.removeShield(targetId);

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
	
	@Override
	public Level getLevel()
	{
		return Level.AES_SEDAI;
	}
	
	@Override
	public String getID() {
		return id;
	}

}
