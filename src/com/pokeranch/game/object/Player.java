package com.pokeranch.game.object;

import java.util.HashMap;

public class Player {
	private String name;
	private int money;
	private int nbattle, nwin, nlose;
	
	Time playingTime;
	HashMap<Item, Integer> item;
	//HashMap<String, Monster> monster;
	
	Player(){
		playingTime = new Time();
		item = new HashMap<Item, Integer>();
	}
	
	public void addItem(Item item, int n) {
		//menambah item pada map item, bisa baru atau menambah jumlah yang lama
	} 	
	public void delItem(String item, int n){ //menghapus atau mengurangi jumlah sebuah item dari map Item
		
	}
	void addTime(int minute){
		//menambah jumlah playingTime, currentTime dan umur monster player
		playingTime.addMinute(minute);
		//umur monster?
	}
	//void addMonster(Monster m); //menambah sebuah Monster pada vector Monsters
	//void delMonster(string); //menghapus sebuah Monster dari vector Monster
	
	public void restoreAllMonster(){
		//mengembalikan semua atribut setiap Monster yang dimiliki player
	}
	public int getNbattle() {
		return nbattle;
	}
	public void setNbattle(int nbattle) {
		this.nbattle = nbattle;
	}
	public int getNwin() {
		return nwin;
	}
	public void setNwin(int nwin) {
		this.nwin = nwin;
	}
	public int getNlose() {
		return nlose;
	}
	public void setNlose(int nlose) {
		this.nlose = nlose;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
