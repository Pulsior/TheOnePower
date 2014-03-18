package com.pulsior.theonepower.channeling;

import java.io.Serializable;

import org.bukkit.Location;

import com.pulsior.theonepower.TheOnePower;

public class Stedding implements Serializable{

	private static final long serialVersionUID = -8848145313185619L;

	String worldName;
	public double[] location1 = new double[2];
	public double[] location2 = new double[2];

	public Stedding(String worldName, Location location1, Location location2){
		this.worldName = worldName;

		this.location1[0] = location1.getX();
		this.location1[1] = location1.getZ();

		this.location2[0] = location2.getX();
		this.location2[1] = location2.getZ();
	}

	public static boolean isInStedding(Location location){

		double x = location.getX();
		double z = location.getZ();
		
		for(Stedding stedding : TheOnePower.database.getSteddings() ){
			
			double[] loc1 = stedding.location1;
			double[] loc2 = stedding.location2;
			
			if(  (x < loc1[0] && x > loc2[0] ) || ( x > loc1[0] && x < loc2[0] ) ) {

				if( (z < loc1[1] && z > loc2[1] ) || ( z > loc1[1] && z < loc2[1] ) ){
					return true;
				}

			}

		}

		return false;
	}
	
	public static void createStedding(String worldName, Location location1, Location location2){
		Stedding s = new Stedding( worldName, location1, location2);
		TheOnePower.database.addStedding(s);
	}

}
