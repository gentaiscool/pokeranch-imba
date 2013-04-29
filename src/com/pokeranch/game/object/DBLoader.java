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
	
	public void loadAll(){
		load(Element.class, elements, "elements.dat");
		load(Skill.class, skills, "skills.dat");
		load(Species.class, species, "species.dat");
	}
	
	//load generic
	private <E extends ILoadable> void load(Class<E> c, HashMap<String, E> map, String assetFile){
		try{
			String line;
			StringBuilder str=new StringBuilder();
			BufferedReader buf = new BufferedReader(new InputStreamReader(assets.open(assetFile)));
			while ((line = buf.readLine()) != null){
				str.append(line);
				str.append("\n");
			}
			
			//build semua elementnya dulu
			Scanner scan = new Scanner(str.toString());
			while(scan.hasNext()){
				String s = scan.next();
				if (s.charAt(0)!='#'){
					E e = build(c, s);
					map.put(s, e);
				}
				scan.nextLine();
			}
			
			//load data2 nya
			scan = new Scanner(str.toString());
			while(scan.hasNext()){
				String s = scan.next();
				if (s.charAt(0)!='#'){
					E e = get(map, s);
					e.load(scan);
				}else{
					scan.nextLine();
				}
			}
			//Log.d("POKE 1", map.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private <E extends ILoadable> E build(Class<E> c, String name){
		try {
			E e = c.newInstance();
			e.setName(name);
			return e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private <E> E get(HashMap<String, E> map, String key){
		return map.get(key);
	}
	
	//statics
	
	public static void initialize(AssetManager assets){
		instance = new DBLoader(assets);
		instance.loadAll();
	}
	
	public static DBLoader getInstance(){
		return instance;
	}
}
