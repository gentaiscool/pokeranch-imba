package com.pokeranch.game.object;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

public class Player {
	private String name;
	private int money;
	private int nbattle, nwin, nlose;
	
	private Time playingTime;
	private HashMap<String, Monster> monsters;
	private HashMap<String, Integer> items;
	private String currentMonster;
	
	public Player(){
		name ="";
		money = 1000;
		playingTime = new Time();
		nbattle = 0;
		nwin = 0;
		nlose = 0;
		items = new HashMap<String, Integer>();
		monsters = new HashMap<String, Monster>();
	}
	
	public String showStatus(){
		StringBuilder str = new StringBuilder();
		str.append(	"***********PLAYER STATS*************\n\n\n"+
					getName()+"\n\n"+
					"$"+getMoney()+"\n\n"+
					"year "+getPlayingTime().getYear()+" month "+getPlayingTime().getMonth()+"\nday "+getPlayingTime().getDay()+" hour "+getPlayingTime().getHour()+" minute "+getPlayingTime().getMinute()+"\n\n"+
					getNbattle()+" Battle "+getNwin()+" Win "+getNlose()+" Lose"
				);
		return str.toString();
	}
	
	public Monster getMonsterWithSkill(String skillname){
		Collection<Monster> mvalues = monsters.values();
		// Get an iterator
		HashMap<String, Skill> skills;
		Monster m;
		Iterator<Monster> i = mvalues.iterator();
	    while(i.hasNext()) {
	    	m = i.next();
	    	skills = m.getAllSkill();
			Collection<Skill> svalues = skills.values();	    	
			Iterator<Skill> it = svalues.iterator();
			while (it.hasNext()) {
				if(it.next().getName().equals(skillname)) {
					return m;
				}
			}
	    }
	    return null;
	}
	
	public void addItem(Item item, int n) {
		String nitem = item.getName();
		//mencari key yang sesuai, jika tidak ditemukan melakukan throw
		Integer jmlitem =items.get(nitem); 
		if (jmlitem != null) {
			jmlitem+=n;
			items.remove(item);
			items.put(item.getName(), jmlitem);
		} else {
			items.put(item.getName(), n);
		}
		//menambah item pada map item, bisa baru atau menambah jumlah yang lama
	} 	
	public void delItem(String item, int n) throws Exception{ //menghapus atau mengurangi jumlah sebuah item dari map Item
		//mencari key yang sesuai, jika tidak ditemukan melakukan throw
		Integer jmlitem =items.get(item); 
		if (jmlitem != null){
			items.remove(item);
			if ((jmlitem-n)!=0){
				jmlitem-=n;
				items.put(item, jmlitem);
			}
		} else {
			throw new Exception();
		}
	}
	
	public int getItemStock(String name){
		Integer i = items.get(name);
		return i==null? 0 : i.intValue(); 
	}
	
	public HashMap<String, Integer> getAllItem(){
		return items;
	}
	
	public HashMap<String, Integer> getBattleItem(){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(Map.Entry<String, Integer> s : items.entrySet()){
			String name = s.getKey();
			Integer sum = s.getValue();
			Item item = DBLoader.getInstance().getItem(name);
			
			//cuma stat item dan monsterball aja
			if((item instanceof StatItem) || (item instanceof MonsterBall)){
				map.put(name,sum);
			}
		}
		return map;
	}
	
	public void addTime(int minute){
		//menambah jumlah playingTime, currentTime dan umur monster player
		playingTime.addMinute(minute);
	    Collection<Monster> monster = monsters.values();
	      // Get an iterator
	    Iterator<Monster>i = monster.iterator();
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
			} else {
				if (nmonster.equals(getCurrentMonster().getName())) {
					getCurrentMonster().getStatus().setHP(0);
					Log.d("cekdel","CurMons sebelum del"+getCurrentMonster().getName());
					setCurrentMonster(getNextMonster());
					Log.d("cekdel","CurMons sebelum del"+getCurrentMonster().getName());
					}
				monsters.remove(nmonster);
				Log.d("cekdel","lewat remove");
			}	
		} else {
				throw new Exception();
		}
	}
	
	public HashMap<String, Monster> getAllMonster(){
		return monsters;
	}
	
	public Monster getMonster(String name){
		return monsters.get(name);
	}
	
	public Monster getCurrentMonster() {
		return monsters.get(currentMonster);
	}	
	
	public void setCurrentMonster(String currMons) {
		this.currentMonster = currMons;
	}
	
	public void setCurrentMonster(Monster currMons) {
		this.currentMonster = currMons.getName();
	}
	
	public Monster getNextMonster() throws Exception {
	    Collection<Monster> values = monsters.values();
	      // Get an iterator
	    Iterator<Monster> i = values.iterator();
	    while(i.hasNext()) {
	    	Monster m = i.next();
	    	if ((m.getStatus().getHP())>0){
	    		return m;
	    	}
	    }
	    throw new Exception();
	}

	public int getNMonster() {
		return monsters.size();
	}
		
	public void restoreAllMonster(){
		//mengembalikan semua atribut setiap Monster yang dimiliki player
	    Collection<Monster> monster = monsters.values();
	      // Get an iterator
	    Iterator<Monster>i = monster.iterator();
	    while(i.hasNext()) {
	         i.next().restoreStatus();
	    }
	}
	
	//getter setter
	
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
	
	public boolean haveTorch(){
		return true;
	}
	
	public String[] buildArrayMonster() {
		String[] listMonster = new String[monsters.size()]; 
		Collection monster = monsters.values();
		listMonster = new String[monsters.size()+1];
		
		listMonster[0]=getCurrentMonster().getName();
		int i = 1;
		Iterator<Monster> it = monster.iterator();
		Log.d("LM", "Habis Iterate");
		while(it.hasNext()){	
			Monster m = it.next();
			if (!m.getName().equals(getCurrentMonster().getName())) {
				listMonster[i]=m.getName();
				i++;
			}
		}
		Log.d("LM", i+"");

		Log.d("LM", "Habis Iterate2");
		return listMonster;
	}

}
