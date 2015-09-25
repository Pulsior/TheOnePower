package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Stun implements Weave {

	List<Element> elements = new ArrayList<Element>();
	private final String id = "Stun";
	private final String name = "Stun Player";
	private final ChatColor color = ChatColor.RED;
	
	public Stun()
	{
		elements.add(Element.SPIRIT);
		elements.add(Element.FIRE);
		elements.add(Element.SPIRIT);
	}
	
	@Override
	public boolean cast(Player player, World world, Block clickedBlock,
			BlockFace clickedFace, Entity clickedEntity) {
		if (clickedEntity instanceof Player)
		{
			Player target = (Player) clickedEntity;
			target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 150, 5) );
			target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10) );
			Channel c = TheOnePower.database.getChannel(target);
			if (c != null)
			{
				c.close();
			}
			return true;
		}
		return false;
		
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Level getLevel() {
		return Level.AES_SEDAI;
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
