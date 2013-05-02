package com.pokeranch.game.object;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import com.pokeranch.game.system.Area;

import android.content.res.AssetManager;
import android.util.Log;

public class DBLoader {
	
	//modelnya singleton, jadi dapetin objeknya: DBLoader.getInstance()
	
	private static DBLoader instance = null;
	
	private HashMap<String, Element> elements;
	private HashMap<String, Species> species;
	private HashMap<String, Skill> skills;
	private HashMap<String, Area> areas;
	private HashMap<String, MonsterBall> balls;
	private HashMap<String, StatItem> statitems;
	private HashMap<String, TM> tms;
	//private HashMap<String, Egg> eggs;
	private AssetManager assets;
	
	
	private DBLoader(AssetManager assets){
		this.assets = assets;
		elements = new HashMap<String, Element>();
		skills = new HashMap<String, Skill>();
		species = new HashMap<String, Species>();
		areas = new HashMap<String, Area>();
		balls = new HashMap<String, MonsterBall>();
		statitems = new HashMap<String, StatItem>();
		tms = new HashMap<String, TM>();
	}
	
	//getter
	
	public Collection<Species> getAllSpecies(){
		//WARNING! Ini reference, jadi perubahan di collection bakal ngubah data spesies juga!
		return species.values();
	}
	
	public Element getElement(String name){
		return elements.get(name);
	}
	
	public Species getSpecies(String name){
		return species.get(name);
	}
	
	public Skill getSkill(String name){
		return skills.get(name);
	}
	
	public Item getItem(String name){
		Item item;
		item = getMonsterBall(name);
		if (item!=null) return item;
		
		item = getStatItem(name);
		if (item!=null) return item;
		
		item = getTM(name);
		if (item!=null) return item;
		
		return null;
	}
	
	public MonsterBall getMonsterBall(String name){
		return balls.get(name);
	}
	
	public StatItem getStatItem(String name){
		return statitems.get(name);
	}
	
	public TM getTM(String name){
		return tms.get(name);
	}
	
	public Area getArea(String name){
		if(areas.get(name) == null)
			Log.d("harits", "areanya kosong cuy");
		else
			Log.d("harits", "areanya gak kosong");
		return areas.get(name);
	}
	
	public Species getCombinedSpecies(Element e, int rating){
		Collection<Species> sp = species.values();
		
		Iterator<Species> it = sp.iterator();
		while(it.hasNext()){
			Species s = it.next();
			if(s.getElement().equals(e) && s.getCombineRating()==rating){
				return s;
			}
		}
		return null;
	}
	
	public Species getRandomSpecies(int maxRating){
		Object[] names = species.keySet().toArray();
		Random rand = new Random();
		Species s;
		do{
			s = getSpecies((String) names[rand.nextInt(names.length)]);
		}while(s.getCombineRating()<=maxRating);
		return s;
	}
	
	public Element getRandomElement(){
		Object[] names = elements.keySet().toArray();
		Random rand = new Random();
		return getElement((String) names[rand.nextInt(names.length)]);
	}
	
	//fungsi2 load
	
	public void loadAll(){

		load(Element.class, elements, "elements.dat");
		load(Skill.class, skills, "skills.dat");
		load(Species.class, species, "species.dat");
		load(MonsterBall.class, balls, "balls.dat");
		load(StatItem.class, statitems, "statitems.dat");
		load(TM.class, tms, "tm.dat");
		loadMap("map.dat");
		
	}
	
	public void loadMap(String assetFile){
		//Log.d("harits", "mulai ngeload");
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
			String nama = scan.next();
			//Log.d("harits", "nama: " + nama);
			int r = scan.nextInt();
			int c = scan.nextInt();
			//Log.d("harits", r + " " + c);
			Area a = new Area(nama, r, c, 1, null, null); //belum ada sprite player
			for(int i=0;i<r;i++){
				for(int j=0;j<c;j++){
					int tmp = scan.nextInt();
					//buat ngetes boundary
					//kalo pagardia ga bisa dilewati
					a.createTile(i, j, tmp, (tmp == 272?false:true));
				}
			}
			
			areas.put(nama, a);
		} catch(Exception e){
			e.printStackTrace();
		}
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
