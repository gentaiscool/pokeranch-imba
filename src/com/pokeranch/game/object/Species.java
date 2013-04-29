package com.pokeranch.game.object;

import java.util.ArrayList;
import java.util.Scanner;

public class Species implements ILoadable{
	private String name;
	private Element element;
	private int evoLevel;
	private Species evoSpecies;
	private int combineRating;
	private Status baseStat;
	private ArrayList<Skill> baseSkill;
	
	public Species(){
		baseStat = new Status();
		baseSkill = new ArrayList<Skill>();
	}
	
	public void load(Scanner scan) {
		//skill, species dan element harus diload dulu sebelumnya
		try{
			combineRating = scan.nextInt();
			baseStat.load(scan);
			String s = scan.next();
			evoSpecies = s.equals("NULL") ? null : DBLoader.getInstance().getSpecies(s);
			evoLevel = scan.nextInt();
			element = DBLoader.getInstance().getElement(scan.next());
			
			//nunggu database
			
			for (int i = 0; i < 4; i++){
				addBaseSkill(DBLoader.getInstance().getSkill(scan.next()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addBaseSkill(Skill sk){
		baseSkill.add(sk);
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(name + " ");
		s.append(combineRating+ " ");
		s.append(baseStat.toString()+ " ");
		s.append(evoSpecies==null ? "NULL" : evoSpecies.getName());
		s.append(" " + evoLevel + " ");
		s.append(element.getName() + " ");
		for(Skill sk : baseSkill) s.append(sk.getName()+ " ");
		return s.toString();
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
