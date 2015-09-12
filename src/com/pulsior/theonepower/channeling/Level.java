package com.pulsior.theonepower.channeling;

public enum Level
{

	NOVICE(1), ACCEPTED(2), AES_SEDAI(3), FORSAKEN(7), SA_ANGREAL(20);

	private final int multiplier;

	Level(int multiplier)
	{
		this.multiplier = multiplier;
	}

	int getMultiplier()
	{
		return multiplier;
	}

}
