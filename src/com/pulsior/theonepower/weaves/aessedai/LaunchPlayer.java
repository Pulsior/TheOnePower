package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class LaunchPlayer implements Weave {

	List<Element> elements = new ArrayList<Element>();
	String id = "LaunchPlayer";
	public LaunchPlayer()
	{
		elements.add(Element.AIR);
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
		elements.add(Element.AIR);
	}

	@Override
	public boolean cast(Player pdayer, World world, Block clickedBlock,
			BlockFace clickedFace, Entity clickedEntity) {
		if (clickedEntity != null)
		{
			Random r = new Random();

			Vector v = clickedEntity.getVelocity();
			clickedEntity.getLocation().setYaw( - (clickedEntity.getLocation().getYaw() ) );
			clickedEntity.setVelocity( v.setY( v.getY() + 1).setX(r.nextInt(6) * negativeChance() ).setZ( r.nextInt(6) * negativeChance() ) );
			clickedEntity.setVelocity(clickedEntity.getVelocity().multiply(10));			
		}
		return true;
	}

	private int negativeChance()
	{
		Random r = new Random();
		int i = r.nextInt(2);

		if (i == 0)
		{
			return -1;
		}

		else{
			return 1;
		}
	}


	@Override
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Level getLevel() {
		return Level.FORSAKEN;
	}

	@Override
	public String getID() {
		return id;
	}

}
