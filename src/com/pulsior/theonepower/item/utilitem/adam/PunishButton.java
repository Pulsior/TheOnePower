package com.pulsior.theonepower.item.utilitem.adam;

import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.weave.Damane;
import com.pulsior.theonepower.channeling.weave.Suldam;
import com.pulsior.theonepower.util.Strings;

public class PunishButton extends AdamButton{

	public PunishButton() {
		super(Material.WOOD_SWORD);
		setDisplayName(Strings.A_DAM_PUNISH_BUTTON);
		addLore(ChatColor.GOLD+"Punish your damane");
		
	}

	@Override
	public void use(Suldam suldam, Damane damane) {
		Player player = damane.getPlayer();
		double health = player.getHealth();
		player.setHealth( health-1 );
		player.playEffect(EntityEffect.HURT);
	}

}
