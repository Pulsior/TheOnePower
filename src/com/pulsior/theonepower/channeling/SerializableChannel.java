package com.pulsior.theonepower.channeling;

import java.util.List;
import java.util.UUID;

public class SerializableChannel
{

	UUID id;
	int maxLevel;
	long taskDuration;
	List<Element> weave;

	public SerializableChannel(UUID id, int maxLevel, long taskDuration,
			List<Element> weave)
	{
		this.id = id;
		this.maxLevel = maxLevel;
		this.taskDuration = taskDuration;
		this.weave = weave;
	}

}
