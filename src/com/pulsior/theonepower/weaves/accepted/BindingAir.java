package com.pulsior.theonepower.weaves.accepted;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftFirework;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.weaves.Weave;

public class BindingAir implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	
	public BindingAir(){
		elements.add(Element.AIR);
		elements.add(Element.AIR);
		elements.add(Element.AIR);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		if( clickedEntity instanceof Player){
			Player targetPlayer = (Player) clickedEntity;
			targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 10) ) ;
			
			Firework firework = (Firework) world.spawnEntity(targetPlayer.getLocation(), EntityType.FIREWORK);
			FireworkMeta meta = firework.getFireworkMeta();
			FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.BLACK).withFade(Color.GRAY).with(Type.BALL).trail(false).build();
			meta.addEffect(effect);
			meta.setPower(1);
			firework.setFireworkMeta(meta);
			
			( (CraftFirework) firework).getHandle().expectedLifespan = 4;
			
			return true;
			
		}
		return false;
	}

	@Override
	public List<Element> getElements() {
		// TODO Auto-generated method stub
		return elements;
	}

}
