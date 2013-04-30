package com.pokeranch.game.object;

public class SpecialItem extends Item{
	public enum SpecialItem_Type {TORCH, SEPEDA, PANCINGAN}
	SpecialItem_Type specialType;
	boolean itemOn;
	
	//ctor
	SpecialItem(){
		itemOn = false;
	}
	
	//ctor berparameter
	SpecialItem(SpecialItem_Type type){
		specialType = type;
		itemOn = false;
	}
	
	public void setToggleItem(){
		itemOn = !itemOn;
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
	
	public void setSpecialItemType(SpecialItem_Type newType){
		specialType = newType;
	}
	
	public SpecialItem_Type getSpecialItemType(){
		return specialType;
	}
	
	int itemType() {
		return 3;
	}
}
