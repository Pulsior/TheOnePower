package com.pulsior.theonepower.weaves.aessedai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pulsior.theonepower.channeling.Element;
import com.pulsior.theonepower.channeling.Level;
import com.pulsior.theonepower.weaves.Weave;

public class Manipulate implements Weave{

	List<Element> elements = new ArrayList<Element>();
	String id = "Manipulate";
	
	public Manipulate(){
		elements.add(Element.SPIRIT);
		elements.add(Element.SPIRIT);
	}

	@Override
	public boolean cast(Player player, World world, Block clickedBlock,	BlockFace clickedFace, Entity clickedEntity) {

		if(clickedEntity instanceof Player){

			CraftPlayer p = (CraftPlayer) clickedEntity;
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3, 1) );
			PlayerConnection link = p.getHandle().playerConnection;

			List<Entity> entities = player.getNearbyEntities(100, 100, 100);



			for(Entity e : entities){

				if(e instanceof Zombie || e instanceof Skeleton){
					org.bukkit.inventory.ItemStack chestplateStack = new ItemStack(Material.DIAMOND_CHESTPLATE);
					net.minecraft.server.v1_8_R3.ItemStack chestplateNMSStack = CraftItemStack.asNMSCopy(chestplateStack);
					PacketPlayOutEntityEquipment chestplatePacket = new PacketPlayOutEntityEquipment(e.getEntityId(), 3, chestplateNMSStack);

					org.bukkit.inventory.ItemStack helmetStack = new ItemStack(Material.DIAMOND_HELMET);
					net.minecraft.server.v1_8_R3.ItemStack helmetNMSStack = CraftItemStack.asNMSCopy(helmetStack);
					PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(e.getEntityId(), 4, helmetNMSStack);
					
					org.bukkit.inventory.ItemStack legsStack = new ItemStack(Material.DIAMOND_LEGGINGS);
					net.minecraft.server.v1_8_R3.ItemStack legsNMSStack = CraftItemStack.asNMSCopy(legsStack);
					PacketPlayOutEntityEquipment legsPacket = new PacketPlayOutEntityEquipment(e.getEntityId(), 2, legsNMSStack);
					
					org.bukkit.inventory.ItemStack bootsStack = new ItemStack(Material.DIAMOND_BOOTS);
					net.minecraft.server.v1_8_R3.ItemStack bootsNMSStack = CraftItemStack.asNMSCopy(bootsStack);
					PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(e.getEntityId(), 1, bootsNMSStack);

					link.sendPacket(chestplatePacket);
					link.sendPacket(helmetPacket);
					link.sendPacket(legsPacket);
					link.sendPacket(bootsPacket);
				}
				
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
	public Level getLevel()
	{
		return Level.AES_SEDAI;
	}
	
	@Override
	public String getID() {
		return id;
	}



}
