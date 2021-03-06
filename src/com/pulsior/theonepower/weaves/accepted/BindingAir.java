package com.pulsior.theonepower.weaves.accepted;

import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class BindingAir implements Weave {
	
	List<Element> elements = new ArrayList<Element>();
	String id = "BindingAir";
	private final String name = "Binding Air";
	private final ChatColor color = ChatColor.WHITE;
	
	public BindingAir(){
		elements.add(Element.AIR);
		elements.add(Element.AIR);
		elements.add(Element.AIR);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {
		if( clickedEntity instanceof LivingEntity){
			LivingEntity entity = (LivingEntity) clickedEntity;
			entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 10) ) ;
			
			Firework firework = (Firework) world.spawnEntity(entity.getLocation(), EntityType.FIREWORK);
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
		return elements;
	}
	
	@Override
	public Level getLevel()
	{
		return Level.ACCEPTED;
	}

	@Override
	public String getID() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public ChatColor getColor()
	{
		return color;
	}

}
