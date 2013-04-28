package com.pokeranch.game.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class DBLoader {
	
	//modelnya singleton, jadi dapetin objeknya: DBLoader.getInstance()
	
	private static DBLoader instance = null;
	
	private HashMap<String, Element> elements;
	private HashMap<String, Species> species;
	private HashMap<String, Skill> skills;
	
	
	private DBLoader(){
		elements = new HashMap<String, Element>();
		skills = new HashMap<String, Skill>();
		species = new HashMap<String, Species>();
		//fungsi2 loadnya blom
	}
	
	//getter
	
	public Element getElement(String name){
		return elements.get(name);
	}
	
	public Species getSpecies(String name){
		return species.get(name);
	}
	
	public Skill getSkill(String name){
		return skills.get(name);
	}
	
	public Species getRandomSpecies(){
		Object[] names = species.keySet().toArray();
		Random rand = new Random();
		return getSpecies((String) names[rand.nextInt(names.length)]);
	}
	
	//statics
	
	public static void initialize(){
		instance = new DBLoader();
	}
	
	public static DBLoader getInstance(){
		return instance;
	}
}
