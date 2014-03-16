package com.pulsior.theonepower.channeling;

import java.util.List;

import com.pulsior.theonepower.weaves.Weave;

public class SerializableChannel {

	String name;
	int maxLevel;
	long taskDuration;
	List<Weave> weave;
	
	public SerializableChannel(String name, int maxLevel, long taskDuration, List<Weave> weave){
		this.name = name;
		this.maxLevel = maxLevel;
		this.taskDuration = taskDuration;
		this.weave = weave;
	}
	
}
