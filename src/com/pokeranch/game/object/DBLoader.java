package com.pokeranch.game.object;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import android.content.res.AssetManager;
import android.util.Log;

public class DBLoader {
	
	//modelnya singleton, jadi dapetin objeknya: DBLoader.getInstance()
	
	private static DBLoader instance = null;
	
	private HashMap<String, Element> elements;
	private HashMap<String, Species> species;
	private HashMap<String, Skill> skills;
	private AssetManager assets;
	
	
	private DBLoader(AssetManager assets){
		this.assets = assets;
		elements = new HashMap<String, Element>();
		skills = new HashMap<String, Skill>();
		species = new HashMap<String, Species>();
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
	
	//fungsi2 load
	
	public void loadElement(){
		try{
			String line;
			StringBuilder str=new StringBuilder();
			BufferedReader buf = new BufferedReader(new InputStreamReader(assets.open("elements.dat")));
			while ((line = buf.readLine()) != null){
				str.append(line);
				str.append("\n");
			}
			
			//build semua elementnya dulu
			Scanner scan = new Scanner(str.toString());
			while(scan.hasNext()){
				String s = scan.next();
				if (s.charAt(0)!='#'){
					Element e = new Element(s);
					elements.put(s, e);
				}
				scan.nextLine();
			}
			
			//load data2 nya
			scan = new Scanner(str.toString());
			while(scan.hasNext()){
				String s = scan.next();
				if (s.charAt(0)!='#'){
					Element e = getElement(s);
					e.load(scan);
				}else{
					scan.nextLine();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadSkills(){
		try{
			String line;
			StringBuilder str=new StringBuilder();
			BufferedReader buf = new BufferedReader(new InputStreamReader(assets.open("skills.dat")));
			while ((line = buf.readLine()) != null){
				str.append(line);
				str.append("\n");
			}
			
			//build semua elementnya dulu
			Scanner scan = new Scanner(str.toString());
			while(scan.hasNext()){
				String s = scan.next();
				if (s.charAt(0)!='#'){
					Skill e = new Skill(s);
					skills.put(s, e);
				}
				scan.nextLine();
			}
			
			//load data2 nya
			scan = new Scanner(str.toString());
			while(scan.hasNext()){
				String s = scan.next();
				if (s.charAt(0)!='#'){
					Skill e = getSkill(s);
					e.load(scan);
				}else{
					scan.nextLine();
				}
			}
			
			Log.d("POKE", skills.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadAll(){
		loadElement();
		loadSkills();
	}
	
	//statics
	
	public static void initialize(AssetManager assets){
		instance = new DBLoader(assets);
		
		//TODO fungsi2 loadnya blom semua
		instance.loadAll();
	}
	
	public static DBLoader getInstance(){
		return instance;
	}
}
