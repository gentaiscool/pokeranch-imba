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
import android.graphics.Point;
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
	
	public Collection<MonsterBall> getAllBalls(){
		return balls.values();
	}
	
	public Collection<StatItem> getAllStatItems(){
		return statitems.values();
	}
	
	public Collection<TM> getAllTms(){
		return tms.values();
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
		
		if(name.equals("Torch")){
			item = new Item("Torch");
			item.setPrice(100);
			return item;
		}
		else{
			item = getMonsterBall(name);
			if (item!=null) return item;
			
			item = getStatItem(name);
			if (item!=null) return item;
			
			item = getTM(name);
			if (item!=null) return item;
		}
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
			String nm = (String) names[rand.nextInt(names.length)];
			s = getSpecies(nm);
		}while(s.getCombineRating()>maxRating);
		
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
			while(scan.hasNext()){
				String place = scan.next();
				String nama = scan.next();
				//Log.d("harits", "nama: " + nama);
				int r = scan.nextInt();
				int c = scan.nextInt();
				//Log.d("harits3","di DBLoader, r c: " +  r + " " + c);
				Area a = new Area(nama, r, c); //belum ada sprite player
				a.setPlace(place);
				//ambil id gambar1, id gambar2, passability tile
				for(int i=0;i<r;i++){
					for(int j=0;j<c;j++){
						String tmp = scan.next();
						String[] tmp2 = tmp.split("/");
						//buat tile di i,j, defaultnya bisa dilewati
						if(tmp2.length == 3)
							a.createTile(i, j, tmp2[0], tmp2[1], Integer.valueOf(tmp2[2]));
						else
							a.createTile(i, j, tmp2[0], null, Integer.valueOf(tmp2[1]));
					}
				}
								
				//ambil titik2 action
				int k = scan.nextInt();
				for(int i=0;i<k;i++){
					String actionType = scan.next();
					if(actionType.equals("TELEPORT")){
						int x = scan.nextInt();
						int y = scan.nextInt();
						String z = scan.next();
						int v = scan.nextInt();
						int w = scan.nextInt();
						a.getTile(x, y).setActionName(actionType);
						a.getTile(x, y).setTeleportTarget(z);
						a.getTile(x, y).setArrivalCord(new Point(v, w));
					} else {
						int x = scan.nextInt();
						int y = scan.nextInt();
						a.getTile(x, y).setActionName(actionType);
					}
				}
				
				areas.put(nama, a);
			}
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
	
	public static void release(){
		instance.elements.clear();
		instance.species.clear();
		instance.skills.clear();
		instance.areas.clear();
		instance.balls.clear();
		instance.statitems.clear();
		instance.tms.clear();
		instance = null;
	}
}
