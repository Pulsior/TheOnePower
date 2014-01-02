package com.pulsior.theonepower.channeling;

import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.weaves.Invalid;
import com.pulsior.theonepower.weaves.Weave;
import com.pulsior.theonepower.weaves.accepted.BindingAir;
import com.pulsior.theonepower.weaves.accepted.QuickGrowth;
import com.pulsior.theonepower.weaves.aessedai.BindWolfGaidin;
import com.pulsior.theonepower.weaves.aessedai.ClearSky;
import com.pulsior.theonepower.weaves.aessedai.FoldedLight;
import com.pulsior.theonepower.weaves.aessedai.Healing;
import com.pulsior.theonepower.weaves.aessedai.Rain;
import com.pulsior.theonepower.weaves.aessedai.RemoveShield;
import com.pulsior.theonepower.weaves.aessedai.Shielding;
import com.pulsior.theonepower.weaves.aessedai.ShootFireball;
import com.pulsior.theonepower.weaves.forsaken.FireSword;
import com.pulsior.theonepower.weaves.forsaken.HeavenFire;
import com.pulsior.theonepower.weaves.forsaken.Meteor;
import com.pulsior.theonepower.weaves.forsaken.Strike;
import com.pulsior.theonepower.weaves.forsaken.Travel;
import com.pulsior.theonepower.weaves.novice.ExtinguishFire;
import com.pulsior.theonepower.weaves.novice.LightFire;
import com.pulsior.theonepower.weaves.novice.Lightning;
import com.pulsior.theonepower.weaves.novice.OpenDoor;
import com.pulsior.theonepower.weaves.novice.Waterbreathing;

/**
 * Enum for all the possible weaves
 * @author Pulsior
 *
 */
public enum WeaveEffect {
	INVALID(new Invalid(), Level.NOVICE ),
	LIGHTNING(new Lightning(), Level.NOVICE ),
	LIGHT_FIRE(new LightFire(), Level.NOVICE),
	OPEN_DOOR(new OpenDoor(), Level.NOVICE),
	WATERBREATHING(new Waterbreathing(), Level.NOVICE ),
	EXTINGUISH_FIRE(new ExtinguishFire(), Level.NOVICE ),
	BINDING_AIR(new BindingAir(), Level.ACCEPTED),
	QUICK_GROWTH(new QuickGrowth(), Level.ACCEPTED),
	RAIN(new Rain(), Level.AES_SEDAI),
	CLEAR_SKY(new ClearSky(), Level.AES_SEDAI ),
	BIND_WOLF_GAIDIN(new BindWolfGaidin(), Level.AES_SEDAI ),
	FIREBALL(new ShootFireball(), Level.AES_SEDAI),
	HEALING(new Healing(), Level.AES_SEDAI),
	FOLDED_LIGHT(new FoldedLight(), Level.AES_SEDAI),
	SHIELDING(new Shielding(), Level.AES_SEDAI),
	REMOVE_SHIELDING(new RemoveShield(), Level.AES_SEDAI),
	METEOR(new Meteor(), Level.FORSAKEN),
	TRAVEL(new Travel(), Level.FORSAKEN),
	STRIKE(new Strike(), Level.FORSAKEN),
	HEAVEN_FIRE(new HeavenFire(), Level.FORSAKEN),
	FIRE_SWORD(new FireSword(), Level.FORSAKEN);
	
	Weave weave;
	Level level;
	
	WeaveEffect(Weave weave, Level level){
		this.weave = weave;
		this.level = level;
	}
	
	boolean cast(Player player, World world, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity){
		return weave.cast(player, world, clickedBlock, clickedFace, clickedEntity);
	}
	
	int getLevel(){
		return level.getLevel(); 
	}
	
	List<Element> getElements(){
		return weave.getElements();
	}
	
}


