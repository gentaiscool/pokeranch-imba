package com.pokeranch.game.object;
import java.util.Scanner;



public class Skill {
	private String name;
	private Status damage, cost;
	private Element element;
	private Skill nextSkill;
	private int nextSkillLevel;
	
	public void load(Scanner scan) {
		// element harus diisi manual
		// nextSkill dan levelnya harus diisi manual
		try{
			name = scan.next();
			damage.load(scan);
			cost.load(scan);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//getter setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getDamage() {
		return damage;
	}

	public void setDamage(Status damage) {
		this.damage = damage;
	}

	public Status getCost() {
		return cost;
	}

	public void setCost(Status cost) {
		this.cost = cost;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Skill getNextSkill() {
		return nextSkill;
	}

	public void setNextSkill(Skill nextSkill) {
		this.nextSkill = nextSkill;
	}

	public int getNextSkillLevel() {
		return nextSkillLevel;
	}

	public void setNextSkillLevel(int nextSkillLevel) {
		this.nextSkillLevel = nextSkillLevel;
	}

}
