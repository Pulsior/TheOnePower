package com.pulsior.theonepower.channeling;

public enum Level {

	NOVICE(1),
	AES_SEDAI(3),
	FORSAKEN(7),
	SA_ANGREAL(20);
	
	private final int level;
	
	Level(int level){
		this.level = level;
	}
		
	int getLevel(){
		return level;
	}

}
