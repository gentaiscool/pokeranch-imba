package com.pokeranch.game.object;

import java.io.BufferedWriter;
import java.util.Scanner;

public class Status {

	public enum Effect {POISON, PARLYZ, SLEEP, BURN, NONE, HEALING}
	private int hp, mp, attack, defense;
	private Effect effect;
	
	public Status() {
		// TODO Auto-generated constructor stub
		
	}
	
	//ctor berparameter
	public Status(int _hp, int _mp, int _attack, int _defense, Effect _effect) {
		hp = _hp;
		mp = _mp;
		attack = _attack;
		defense = _defense;
		effect = _effect;
	}
	
	public Status(Status source){
		hp = source.hp;
		mp = source.mp;
		attack = source.attack;
		defense = source.defense;
		effect = source.effect;
	}
	
	
	public void save(BufferedWriter buf) {
		try{
			buf.write(hp + " ");
			buf.write(mp + " ");
			buf.write(attack + " ");
			buf.write(defense + " ");
			buf.write(effect.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void load(Scanner scan) {
		try{
			hp = scan.nextInt();
			mp = scan.nextInt();
			attack = scan.nextInt();
			defense = scan.nextInt();
			effect = Effect.valueOf(scan.next());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(hp + " "+mp + " "+attack + " "+defense + " "+effect);
		return s.toString();
	}
	
	//getter setter
	
	public void set(int hp, int mp, int attack, int defense, Effect effect){
		this.hp = hp;
		this.mp = mp;
		this.attack = attack;
		this.defense = defense;
		this.effect = effect;
	}
	
	public void updateBy(int hp, int mp, int attack, int defense, Effect effect){
		this.hp += hp;
		this.mp += mp;
		this.attack += attack;
		this.defense += defense;
		if (effect == Effect.HEALING){
			this.effect = Effect.NONE;
		}else if(effect != Effect.NONE){
			this.effect = effect;
		}
	}
	
	public Status substractStatus(Status s1, Status s2){
		Status s = new Status();
		s.setAttack(s1.getAttack() - s2.getAttack());
		s.setDefense(s1.getDefense() - s2.getDefense());
		s.setHP(s1.getHP() - s2.getHP());
		s.setMP(s1.getMP() - s2.getMP());
		s.setEffect(Effect.NONE);
		return s;
	}
	
	public int getHP() {
		return hp;
	}
	public void setHP(int hp) {
		this.hp = hp;
	}
	public int getMP() {
		return mp;
	}
	public void setMP(int mp) {
		this.mp = mp;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public Effect getEffect() {
		return effect;
	}
	public void setEffect(Effect effect) {
		this.effect = effect;
	}
}
