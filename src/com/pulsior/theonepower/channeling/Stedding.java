package com.pulsior.theonepower.channeling;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.pulsior.theonepower.TheOnePower;

public class Stedding implements Serializable{

	private static final long serialVersionUID = -8848145313185619L;

	String worldName;
	double radius;
	public double[] location = new double[3];

	public Stedding(String worldName, Location location, double radius){
		this.worldName = worldName;
		this.radius = radius;
		
		this.location[0] = location.getX();
		this.location[1] = location.getY();
		this.location[2] = location.getZ();
	}

	public static boolean isInStedding(Location location){
		
		for(Stedding stedding : TheOnePower.database.getSteddings() ){
			double distance = location.distance( stedding.asLocation() );
			if(distance < stedding.getRadius() ){
				return true;
			}
		}

		return false;
	}
	
	public static void createStedding(String worldName, Location location1, int radius){
		Stedding s = new Stedding( worldName, location1, radius);
		TheOnePower.database.addStedding(s);
	}
	
	public double getRadius(){
		return radius;
	}
	
	public Location asLocation(){
		World world = Bukkit.getWorld(worldName);
		return new Location(world, location[0], location[1], location[2]);
	}

}
