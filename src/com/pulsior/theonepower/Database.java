package com.pulsior.theonepower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.pulsior.theonepower.channeling.Channel;
import com.pulsior.theonepower.channeling.Memory;
import com.pulsior.theonepower.channeling.Stedding;
import com.pulsior.theonepower.channeling.weave.Shield;

public class Database implements Serializable
{

	private static final long serialVersionUID = -1169318961339172944L;

	private HashMap<UUID, Channel> channelMap = new HashMap<UUID, Channel>();

	private HashMap<UUID, List<Memory>> memoryMap = new HashMap<UUID, List<Memory>>();

	private List<Stedding> steddingList = new ArrayList<Stedding>();

	private HashMap<UUID, Shield> shieldMap = new HashMap<UUID, Shield>();

	public boolean hasShield(UUID id)
	{
		return shieldMap.containsKey(id);
	}

	public Shield getShield(UUID id)
	{
		return shieldMap.get(id);
	}

	public void addShield(UUID id, Shield shield)
	{
		shieldMap.put(id, shield);
	}

	public void removeShield(UUID id)
	{
		shieldMap.remove(id);
	}

	public void addStedding(Stedding stedding)
	{
		steddingList.add(stedding);
	}

	public List<Stedding> getSteddings()
	{
		return steddingList;
	}

	public void removeStedding(Stedding stedding)
	{
		steddingList.remove(stedding);
	}

	public void addChannel(UUID id, Channel channel)
	{
		channelMap.put(id, channel);
	}

	public Channel getChannel(UUID id)
	{
		return channelMap.get(id);
	}

	public Channel getChannel(Player player)
	{
		return channelMap.get(player.getUniqueId());
	}

	public void removeChannel(UUID id)
	{
		channelMap.remove(id);
	}

	public void removeChannel(Player player)
	{
		channelMap.remove(player.getName());
	}

	public boolean addMemory(UUID id, Memory mem)
	{
		List<Memory> list = memoryMap.get(id);

		if (list != null)
		{
			if (list.size() >= 7)
			{
				return false;
			}

			else
			{
				list.add(mem);
				return true;
			}
		}

		else
		{
			list = new ArrayList<Memory>();
			list.add(mem);
			memoryMap.put(id, list);
			return true;
		}
	}

	public boolean forgetMemory(UUID id, String name)
	{
		List<Memory> list = memoryMap.get(id);
		if (list != null)
		{
			return list.remove(name);			
		}
		return false;
	}
	
	public List<Memory> getMemories(UUID id)
	{
		return memoryMap.get(id);
	}

}
