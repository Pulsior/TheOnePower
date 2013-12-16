package com.pulsior.theonepower;

import java.util.ArrayList;
import java.util.List;

/**
 * List of all the available weaves
 * @author Pulsior
 *
 */

public class WeaveList {
	

	public List<Element> lightningWeave = new ArrayList<Element>();
	public List<Element> rainWeave = new ArrayList<Element>();
	public List<Element> clearWeave = new ArrayList<Element>();
	public List<Element> gaidinWolfWeave = new ArrayList<Element>();
	public List<Element> fireballWeave = new ArrayList<Element>();
	public List<Element> healingWeave = new ArrayList<Element>();
	public List<Element> waterbreathingWeave = new ArrayList<Element>();
	public List<Element> extinguishFireWeave = new ArrayList<Element>();
	public List<Element> foldedLightWeave = new ArrayList<Element>();
	public List<Element> travelWeave = new ArrayList<Element>();
	public List<Element> smashLightningWeave = new ArrayList<Element>();
	public List<Element> xpWeave = new ArrayList<Element>();
	
	
	public WeaveList(){
		lightningWeave.add(Element.AIR);
		lightningWeave.add(Element.FIRE);
		lightningWeave.add(Element.AIR);
		
		rainWeave.add(Element.AIR);
		rainWeave.add(Element.WATER);
		rainWeave.add(Element.AIR);
		rainWeave.add(Element.WATER);
		rainWeave.add(Element.AIR);
		
		clearWeave.add(Element.AIR);
		clearWeave.add(Element.FIRE);
		clearWeave.add(Element.WATER);
		clearWeave.add(Element.FIRE);
		clearWeave.add(Element.AIR);
		
		gaidinWolfWeave.add(Element.SPIRIT);
		gaidinWolfWeave.add(Element.AIR);
		gaidinWolfWeave.add(Element.AIR);
		gaidinWolfWeave.add(Element.WATER);
		gaidinWolfWeave.add(Element.SPIRIT);
		gaidinWolfWeave.add(Element.SPIRIT);
		gaidinWolfWeave.add(Element.EARTH);
		gaidinWolfWeave.add(Element.SPIRIT);
		gaidinWolfWeave.add(Element.AIR);
		
		fireballWeave.add(Element.FIRE);
		fireballWeave.add(Element.FIRE);
		
		healingWeave.add(Element.AIR);
		healingWeave.add(Element.WATER);
		healingWeave.add(Element.SPIRIT);
		healingWeave.add(Element.AIR);
		healingWeave.add(Element.WATER);
		healingWeave.add(Element.SPIRIT);
		healingWeave.add(Element.SPIRIT);
		healingWeave.add(Element.EARTH);
		healingWeave.add(Element.FIRE);
		healingWeave.add(Element.SPIRIT);
		healingWeave.add(Element.EARTH);
		healingWeave.add(Element.FIRE);
		
		waterbreathingWeave.add(Element.WATER);
		waterbreathingWeave.add(Element.WATER);
		waterbreathingWeave.add(Element.AIR);
		waterbreathingWeave.add(Element.WATER);
		waterbreathingWeave.add(Element.WATER);
		
		extinguishFireWeave.add(Element.WATER);
		extinguishFireWeave.add(Element.WATER);
		extinguishFireWeave.add(Element.FIRE);
		extinguishFireWeave.add(Element.WATER);
		extinguishFireWeave.add(Element.WATER);
		
		foldedLightWeave.add(Element.SPIRIT);
		foldedLightWeave.add(Element.AIR);
		foldedLightWeave.add(Element.SPIRIT);
		foldedLightWeave.add(Element.SPIRIT);
		foldedLightWeave.add(Element.AIR);
		foldedLightWeave.add(Element.SPIRIT);
		
		travelWeave.add(Element.EARTH);
		travelWeave.add(Element.EARTH);
		travelWeave.add(Element.EARTH);
		travelWeave.add(Element.WATER);
		travelWeave.add(Element.WATER);
		travelWeave.add(Element.SPIRIT);
		travelWeave.add(Element.SPIRIT);
		travelWeave.add(Element.FIRE);
		travelWeave.add(Element.SPIRIT);
		travelWeave.add(Element.FIRE);
		travelWeave.add(Element.SPIRIT);
		travelWeave.add(Element.AIR);
		travelWeave.add(Element.AIR);
		travelWeave.add(Element.AIR);
		travelWeave.add(Element.EARTH);
		travelWeave.add(Element.SPIRIT);
		travelWeave.add(Element.AIR);
		travelWeave.add(Element.EARTH);
		travelWeave.add(Element.SPIRIT);
		travelWeave.add(Element.AIR);
		
		
		smashLightningWeave.add(Element.AIR);
		smashLightningWeave.add(Element.EARTH);
		smashLightningWeave.add(Element.EARTH);
		smashLightningWeave.add(Element.FIRE);
		smashLightningWeave.add(Element.AIR);
		smashLightningWeave.add(Element.EARTH);
		smashLightningWeave.add(Element.EARTH);
		smashLightningWeave.add(Element.FIRE);
		
		
	}
	
	public WeaveEffect compare(List<Element> weave){
		if(weave.equals( lightningWeave ) ){
			return WeaveEffect.LIGHTNING;
		}
		if(weave.equals( rainWeave ) ){
			return WeaveEffect.RAIN;
		}
		if(weave.equals( clearWeave ) ){
			return WeaveEffect.CLEAR_SKY;
		}
		if(weave.equals( gaidinWolfWeave) ){
			return WeaveEffect.BIND_WOLF_GAIDIN;
		}
		if(weave.equals( fireballWeave) ){
			return WeaveEffect.FIREBALL;
		}
		if(weave.equals( healingWeave ) ){
			return WeaveEffect.HEALING;
		}
		if(weave.equals( waterbreathingWeave ) ){
			return WeaveEffect.WATERBREATHING;
		}
		if(weave.equals( extinguishFireWeave ) ){
			return WeaveEffect.EXTINGUISH_FIRE;
		}
		if(weave.equals( foldedLightWeave ) ){
			return WeaveEffect.FOLDED_LIGHT;
		}
		if(weave.equals( travelWeave ) ){
			return WeaveEffect.TRAVEL;
		}
		if(weave.equals( smashLightningWeave ) ){
			return WeaveEffect.STRIKE;
		}
		
		else{
			return WeaveEffect.INVALID;
		}
	}
	
}
