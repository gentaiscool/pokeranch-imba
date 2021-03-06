package com.pokeranch.game.object;
import java.util.Scanner;



public class Skill implements ILoadable{
	private String name;
	private Status damage, cost;
	private Element element;
	private int nextSkillLevel;
	private Skill nextSkill;
	
	public Skill(){
		damage = new Status();
		cost = new Status();
	}
	
	
	public void load(Scanner scan) {
		// skill dan element harus sudah di built sebelumnya
		try{
			damage.load(scan);
			cost.load(scan);
			element = DBLoader.getInstance().getElement(scan.next());
			nextSkillLevel = scan.nextInt();
			nextSkill = nextSkillLevel > -1 ? DBLoader.getInstance().getSkill(scan.next()) : null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String toString(){
		String s = name + " " + damage.toString() + " " + cost.toString() + " " + element.getName();
		if(nextSkillLevel!=-1){
			s+= " " + nextSkill.getName();
		}
		return s;
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
