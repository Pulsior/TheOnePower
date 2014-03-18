package com.pulsior.theonepower.task;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class OpenGroundTask extends BukkitRunnable{

	List<Block> blocks;
	List<Material> materials;
	
	public OpenGroundTask(List<Block> blocks, List<Material> materials){
		this.blocks = blocks;
		this.materials = materials;
	}
	
	@Override
	public void run() {
		int size = blocks.size();
		for(int x = 0; x < size; x++){
			Block b = blocks.get(x);
			b.setType(materials.get(x) );
		}
	}

}
