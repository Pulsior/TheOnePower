package com.pulsior.theonepower.channeling;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * Represents a memory, which can be used to teleport through the Unseen Land
 * @author Pulsior
 *
 */
public class Memory implements Serializable{

	private static final long serialVersionUID = -6360739145491482100L;
	public String name;
	public String rawName;
	public double[] location = new double[3];
	public float yaw;
	public float pitch;

	public Memory(String name, Location location){
		String tempName = Character.toUpperCase(name.charAt(0)) + name.substring(1);;
		this.rawName = name;
		this.name = ChatColor.RESET + tempName;
		this.location[0] = location.getX();
		this.location[1] = location.getY();
		this.location[2] = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}


	public Location getLocation(boolean inUnseenLand){
		Location memoryLocation;
		
		if(inUnseenLand){
			memoryLocation = new Location(Bukkit.getWorld("tel'aran'rhiod"), location[0], location[1], location[2]);
		}
		
		else{
			memoryLocation = new Location(Bukkit.getWorld("world"), location[0], location[1], location[2]);
		}
		
		memoryLocation.setPitch(pitch);
		memoryLocation.setYaw(yaw);

		return memoryLocation;
	}

}