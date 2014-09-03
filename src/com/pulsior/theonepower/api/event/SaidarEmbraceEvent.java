package com.pulsior.theonepower.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SaidarEmbraceEvent extends PlayerEvent implements Cancellable
{

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;

	public SaidarEmbraceEvent(Player player)
	{
		super(player);
	}

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

}
