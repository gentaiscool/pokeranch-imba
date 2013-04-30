package com.pokeranch.game.object;

import java.util.Scanner;

public class Item implements ILoadable{
	protected String name;
	protected int price;
	
	@Override
	public void load(Scanner scan) {
		// TODO Auto-generated method stub
		try{
			name = scan.next();
			price = scan.nextInt();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
			this.name = name;
	}
	
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
