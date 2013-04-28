package com.pokeranch.game.object;

import java.util.ArrayList;
import java.util.Scanner;

public class Element{
	private String name;
	private ArrayList<Element> strong, weak, immune;
	
	public String getName(){
		return name;
	}
	
	public boolean equals(Element e){
		return name.equals(e.name);
	}
	
	public float getDamageFactor(Element e){
		if (strong.contains(e)) return 0.5f;
		if (weak.contains(e)) return 2.0f;
		if (immune.contains(e)) return 0.0f;
		return 1.0f;
	}
	
	public void addStrong(Element e){
		strong.add(e);
	}
	
	public void addWeak(Element e){
		weak.add(e);
	}
	
	public void addImmune(Element e){
		immune.add(e);
	}
	
	
	public void load(Scanner scan){
		//TODO implement
	}
}
