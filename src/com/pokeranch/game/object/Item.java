package com.pokeranch.game.object;

public class Item {
	protected String name;
	protected int price;
	
	//konstruktor
	public Item() {
		// TODO Auto-generated constructor stub
		name = "";
	}
	
	//ctor berparameter
	public Item(String _namaItem){
		name = _namaItem;
	}
	
	//equals
	boolean equals(Item comparedItem){
		if(this.name.equals(comparedItem.name) && this.price == comparedItem.price){
			return true;
		}
		else return false;
	}
	
	//getter dan setter
	String getName(){
		return this.name;
	}
	void setName(String _name){
		this.name = _name;
	}
	int getPrice(){
		return price;
	}
	
	Status getItemEffect()
	{
		Status s = null;
		return s;
	}
	float getCatchRate()
	{
		float f = 0f;
		return f;
	}
	int itemType(){
		return 0;
	}
	
}
