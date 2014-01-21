package com.pulsior.theonepower.channeling.weave;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

import com.pulsior.theonepower.Direction;
import com.pulsior.theonepower.TheOnePower;
import com.pulsior.theonepower.Utility;

public class Portal {

	/**
	 * Serves as a quick gateway to teleport players
	 * @param location
	 * @param destination
	 * @param northSouth
	 */

	public List<Block> contents = new ArrayList<Block>();

	public Portal(Location location, Location destination, Location playerLocation, Direction spawnDirection){

		/*
		 *Create first portal
		 */

		if(spawnDirection.equals(Direction.EAST) || spawnDirection.equals(Direction.WEST)){
			
			List<Location> list = getNorthSouthPortalLocation(location, true);
			createPortal(list, destination  );
		}

		else{
			
			List<Location> list = getEastWestPortalLocation(location, true);
			createPortal(list, destination);
			
		}

 
		/*
		 * Calculate the correct direction for the second portal
		 */

		Direction dstDirection = Utility.getDirection(destination.getYaw());
		Location portalLocation = destination.clone();

		switch(dstDirection){

		case NORTH:
			portalLocation.add(0, 0, 1);
			break;
		case EAST:
			portalLocation.add(1, 0, 0);
			break;
		case SOUTH:
			portalLocation.add(0, 0, -1);
			break;
		case WEST:
			portalLocation.add(-1, 0, 0);
			break;

		}

		/*
		 * Create second portal
		 */


		if(dstDirection.equals(Direction.EAST) || dstDirection.equals(Direction.WEST)){
			
			List<Location> list = getNorthSouthPortalLocation(portalLocation, false);
			
			if( list.contains(destination) ){
				list = getEastWestPortalLocation(portalLocation, false);
			}

			createPortal(list, playerLocation);
			
		}
		else{
			
			List<Location> list = getEastWestPortalLocation(portalLocation, false);
			
			if( list.contains(destination) ){
				list = getNorthSouthPortalLocation(portalLocation, false);
			}

			createPortal(list, playerLocation);
		}


		TheOnePower.portals.add(this);
	}
	
	
	public void createPortal(List<Location> location, Location destination){
		for(Location l : location){
			Block block = l.getBlock();
			block.setMetadata("isGateway", new FixedMetadataValue(TheOnePower.plugin, new Boolean(true) ) );
			block.setMetadata("getLocation", new FixedMetadataValue(TheOnePower.plugin, destination ) );
			block.setType(Material.PORTAL);				
			contents.add(block);		
		}
	}
	
	

	public List<Location> getNorthSouthPortalLocation(Location spawnLocation, boolean elevate){
		List<Location> location = new ArrayList<Location>();
		
		if(elevate){
			spawnLocation.add(0, 2, 1);
		}
		else{
			spawnLocation.add(0, 1, 1);
		}
		
		for(int y = 0; y < 2; y++){

			for(int x = 0; x < 3; x++){
				location.add( spawnLocation.clone() );
				spawnLocation.add(0, 0, -1);
			}

			spawnLocation.add(0, -1, 3);
		}
		
		return location;
	}
	
	public List<Location> getEastWestPortalLocation(Location spawnLocation, boolean elevate){
		
		List<Location> location = new ArrayList<Location>();
		
		if(elevate){
			spawnLocation.add(1, 2, 0);
		}
		else{
			spawnLocation.add(1, 1, 0);
		}
		
		for(int y = 0; y < 2; y++){

			for(int x = 0; x < 3; x++){
				location.add(spawnLocation.clone());				
				spawnLocation.add(-1, 0, 0);
			}

			spawnLocation.add(3, -1, 0);
		}
		
		return location;
	}

	public void clear(){

		for(Block block : contents){
			block.setType(Material.AIR);
		}

	}


}


