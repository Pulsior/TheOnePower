package com.pulsior.theonepower.weaves.forsaken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.particle.ParticleEffects;
import com.pulsior.theonepower.weaves.Weave;

public class Teleport implements Weave {

	List<Element> elements = new ArrayList<Element>();
	String id = "Teleport";
	private final String name = "Blink";
	private final ChatColor color = ChatColor.BLACK;

	public Teleport(){
		elements.add(Element.EARTH);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);
		elements.add(Element.SPIRIT);
		elements.add(Element.EARTH);

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
		Location l = player.getLocation();
		Location b = player.getTargetBlock((HashSet<Byte>) null, 200).getLocation();
		player.teleport(b);
		world.playSound(l, Sound.ENDERMAN_TELEPORT, 5, 1);
		world.playSound(b, Sound.ENDERMAN_TELEPORT, 5, 1);
		ParticleEffects.EFFECT_TELEPORT.sendTo(l, 50);
		ParticleEffects.EFFECT_TELEPORT.sendTo(b, 50);
		return true;

	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Level getLevel()
	{
		return Level.FORSAKEN;
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
