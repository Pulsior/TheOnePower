package com.pulsior.theonepower.channeling;

import java.util.List;

public class SerializableChannel {

	String name;
	int maxLevel;
	long taskDuration;
	List<Element> weave;
	
	public SerializableChannel(String name, int maxLevel, long taskDuration, List<Element> weave){
		this.name = name;
		this.maxLevel = maxLevel;
		this.taskDuration = taskDuration;
		this.weave = weave;
	}
	
}
