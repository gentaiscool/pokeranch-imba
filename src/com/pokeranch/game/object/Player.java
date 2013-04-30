package com.pokeranch.game.object;

import java.util.*;

public class Player {
	private String name;
	private int money;
	private int nbattle, nwin, nlose;
	
	private Time playingTime;
	private HashMap<String, Monster> monsters;
	private HashMap<String, Integer> items;
	private String currentMonster;
	
	Player(){
		money = 1000;
		playingTime = new Time();
		currentMonster = "";
		nbattle = 0;
		nwin = 0;
		nlose = 0;
		items = new HashMap<String, Integer>();
		monsters = new HashMap<String, Monster>();
	}
	
	public void addItem(Item item, int n) {
		String nitem = item.getName();
		//mencari key yang sesuai, jika tidak ditemukan melakukan throw
		Integer jmlitem =items.get(nitem); 
		if (jmlitem != null) {
			jmlitem+=n;
		} else {
			items.put(item.getName(), n);
		}
		//menambah item pada map item, bisa baru atau menambah jumlah yang lama
	} 	
	public void delItem(String item, int n) throws Exception{ //menghapus atau mengurangi jumlah sebuah item dari map Item
		//mencari key yang sesuai, jika tidak ditemukan melakukan throw
		Integer jmlitem =items.get(item); 
		if (jmlitem != null){
			if ((jmlitem-n)==0){
				items.remove(item);
			} else {
				jmlitem-=n;
			}
		} else {
			throw new Exception();
		}
	}
	
	public HashMap<String, Integer> getAllItem(){
		return items;
	}
	
	public void addTime(int minute){
		//menambah jumlah playingTime, currentTime dan umur monster player
		playingTime.addMinute(minute);
	    Collection values = monsters.entrySet();
	      // Get an iterator
	    Iterator<Monster>i = values.iterator();
	    while(i.hasNext()) {
	         i.next().addAgeByMinute(minute);
	    }
	}
	
	public void addMonster(Monster mon) {
		//menambah sebuah Monster pada map Monsters
		monsters.put(mon.getName(), mon);
	}
		
	public void delMonster(String nmonster) throws Exception{
		 //menghapus sebuah Monster dari vector Monster
		Integer jmlmonsters =monsters.size();
		Monster mons = monsters.get(nmonster);
		if (mons!=null){
			if (jmlmonsters == 1){
				throw new Exception();
			}
			else {
				monsters.remove(nmonster);
			}	
		} else {
				throw new Exception();
		}
	}
	
	public HashMap<String, Monster> getAllMonster(){
		return monsters;
	}
	
	public String getCurrentMonster() {
		return currentMonster;
	}	
	
	public void setCurrentMonster(String currMons) {
		this.currentMonster = currMons;
	}
	
	public Monster getNextMonster() throws Exception {
	    Collection values = monsters.entrySet();
	      // Get an iterator
	    Iterator<Monster> i = values.iterator();
	    while(i.hasNext()) {
	    	if ((i.next().getStatus().getHP())==0){
	    		return i.next();
	    	}
	    }
	    throw new Exception();
	}

	public int getNMonster() {
		return monsters.size();
	}
	
	public void restoreAllMonster(){
		//mengembalikan semua atribut setiap Monster yang dimiliki player
	    Collection values = monsters.entrySet();
	      // Get an iterator
	    Iterator<Monster>i = values.iterator();
	    while(i.hasNext()) {
	         i.next().restoreStatus();
	    }
	}
	
	public Time getPlayingTime() {
		return playingTime;
	}
	
	public void setPlayingTime(Time t) {
		playingTime=t;
	}
	public int getNbattle() {
		return nbattle;
	}
	public void setNbattle(int nbattle) {
		this.nbattle = nbattle;
	}
	
	public void addBattle(int nbattle) {
		this.nbattle+=nbattle;
	}
	
	public int getNwin() {
		return nwin;
	}
	public void setNwin(int nwin) {
		this.nwin = nwin;
	}
	
	public void addWin(int nwin) {
		this.nwin+=nwin;
	}
	public int getNlose() {
		return nlose;
	}
	public void setNlose(int nlose) {
		this.nlose = nlose;
	}

	public void addLose(int nlose) {
		this.nlose+=nlose;
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
