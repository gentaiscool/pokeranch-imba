package com.pokeranch.game.object;

import java.util.Scanner;

public class Item implements ILoadable{
	protected String name;
	protected int price;
	
	@Override
	public void load(Scanner scan) {
		// TODO Auto-generated method stub
		try{
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
	
	public void setPrice(int price) {
		// TODO Auto-generated method stub
			this.price = price;
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
	public String getName(){
		return this.name;
	}
	
	public int getPrice(){
		return price;
	}
	
	int itemType(){
		return 0;
	}	
	
}
