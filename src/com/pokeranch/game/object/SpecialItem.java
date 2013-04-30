package com.pokeranch.game.object;

public class SpecialItem extends Item{
	public enum SpecialItem_Type {TORCH, SEPEDA, PANCINGAN}
	SpecialItem_Type specialType;
	
	//ctor
	SpecialItem(){}
	
	//ctor berparameter
	SpecialItem(SpecialItem_Type type){
		specialType = type;
	}
	
	//method
	public void useItem(){
		if(specialType == SpecialItem_Type.TORCH){
			//Torch
			
		}
		else if(specialType == SpecialItem_Type.SEPEDA){
			//SEPEDA
			
		}
		else if(specialType == SpecialItem_Type.PANCINGAN){
			//PANCINGAN
			
		}
	}
}
