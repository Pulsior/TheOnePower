package com.pulsior.theonepower.item.utilitem.adam;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.weave.Damane;
import com.pulsior.theonepower.channeling.weave.Suldam;
import com.pulsior.theonepower.util.Strings;

public class ForceEmbraceButton extends AdamButton{

	public ForceEmbraceButton() {
		super(Material.NETHER_STAR);
		setDisplayName(Strings.A_DAM_FORCE_EMBRACE_BUTTON);
		addLore(ChatColor.GOLD+"Force your damane to embrace saidar");
	}

	@Override
	public void use(Suldam suldam, Damane damane) {
		new Channel( damane.getName(), 0 );
	}

}
