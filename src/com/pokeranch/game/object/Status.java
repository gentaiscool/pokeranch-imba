package com.pokeranch.game.object;

import java.io.BufferedWriter;
import java.util.Scanner;

public class Status {

	public enum Effect {POISON, PARLYZ, SLEEP, BURN, NONE}
	private int hp, mp, attack, defense;
	private Effect effect;
	
	
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
	
	//getter setter
	
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
