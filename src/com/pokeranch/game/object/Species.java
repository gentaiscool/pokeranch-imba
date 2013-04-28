package com.pokeranch.game.object;

import java.util.ArrayList;
import java.util.Scanner;

public class Species {
	private String name;
	private Element element;
	private int evoLevel;
	private Species evoSpecies;
	private int combineRating;
	private Status baseStat;
	private ArrayList<Skill> baseSkill;
	
	public void load(Scanner scan) {
		// TODO Auto-generated method stub
	}
	
	public void addBaseSkill(Skill sk){
		baseSkill.add(sk);
	}
	
	//getter setter
	
	public Skill getBaseSkill(int n){
		return baseSkill.get(n);
	}
	
	public int getBaseSkillNum(){
		return baseSkill.size();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Element getElement() {
		return element;
	}
	public void setElement(Element element) {
		this.element = element;
	}

	public int getEvoLevel() {
		return evoLevel;
	}

	public void setEvoLevel(int evoLevel) {
		this.evoLevel = evoLevel;
	}

	public Species getEvoSpecies() {
		return evoSpecies;
	}

	public void setEvoSpecies(Species evoSpecies) {
		this.evoSpecies = evoSpecies;
	}

	public int getCombineRating() {
		return combineRating;
	}

	public void setCombineRating(int combineRating) {
		this.combineRating = combineRating;
	}

	public Status getBaseStat() {
		return baseStat;
	}

	public void setBaseStat(Status baseStat) {
		this.baseStat = baseStat;
	}

}
