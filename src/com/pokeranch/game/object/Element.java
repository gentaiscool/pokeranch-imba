package com.pokeranch.game.object;

import java.util.ArrayList;
import java.util.Scanner;

public class Element implements ILoadable{
	private String name;
	private ArrayList<Element> strong, weak, immune;
	
	public Element(){
		strong = new ArrayList<Element>();
		weak = new ArrayList<Element>();
		immune = new ArrayList<Element>();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
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
		//element harus sudah dibuilt sebelumnya
		try{
			
			int num = scan.nextInt();
			for (int i = 0; i < num; i++){
				addStrong(DBLoader.getInstance().getElement(scan.next()));
			}
			
			num = scan.nextInt();
			for (int i = 0; i < num; i++){
				addWeak(DBLoader.getInstance().getElement(scan.next()));
			}
			
			num = scan.nextInt();
			for (int i = 0; i < num; i++){
				addImmune(DBLoader.getInstance().getElement(scan.next()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return name;
	}
}
