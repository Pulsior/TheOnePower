package com.pulsior.theonepower.channeling;

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
	
	public Location firstPortalLocation;
	public Location secondPortalLocation;

	public Portal(Location location, Location destination, Location playerLocation, Direction spawnDirection){

		/*
		 *Create first portal
		 */

		if(spawnDirection.equals(Direction.EAST) || spawnDirection.equals(Direction.WEST)){
			createNorthSouthPortal(location, new Location(destination.getWorld(), destination.getX(), destination.getY(), destination.getZ() ), true );
		}

		else{
			createEastWestPortal(location, new Location(destination.getWorld(), destination.getX(), destination.getY(), destination.getZ() ), true );
		}


		Direction dstDirection = Utility.getDirection(destination.getYaw());
		Location portalLocation = destination;

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
			createNorthSouthPortal(portalLocation, playerLocation, false);
		}
		else{
			createEastWestPortal(portalLocation, playerLocation, false);
		}

	}

	public void createNorthSouthPortal(Location spawnLocation, Location destination, boolean elevate){

		if(elevate){
			spawnLocation.add(0, 2, 1);
		}
		else{
			spawnLocation.add(0, 1, 1);
		}


		/*
		 * Draw first portal
		 */

		for(int y = 0; y < 2; y++){

			for(int x = 0; x < 3; x++){
				Block block = spawnLocation.getBlock();
				block.setMetadata("isGateway", new FixedMetadataValue(TheOnePower.plugin, new Boolean(true) ) );
				block.setMetadata("getLocation", new FixedMetadataValue(TheOnePower.plugin, destination ) );
				block.setType(Material.PORTAL);
				spawnLocation.add(0, 0, -1);
			}

			spawnLocation.add(0, -1, 3);
		}
	}

	public void createEastWestPortal(Location spawnLocation, Location destination, boolean elevate){
		
		if(elevate){
			spawnLocation.add(1, 2, 0);
		}
		else{
			spawnLocation.add(1, 1, 0);
		}
		
		
		for(int y = 0; y < 2; y++){

			for(int x = 0; x < 3; x++){
				Block block = spawnLocation.getBlock();
				block.setMetadata("isGateway", new FixedMetadataValue(TheOnePower.plugin, new Boolean(true) ) );
				block.setMetadata("getLocation", new FixedMetadataValue(TheOnePower.plugin, destination ) );
				block.setType(Material.PORTAL);
				spawnLocation.add(-1, 0, 0);

			}

			spawnLocation.add(3, -1, 0);
		}
	}
}


