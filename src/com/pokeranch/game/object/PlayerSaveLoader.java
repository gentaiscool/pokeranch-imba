package com.pokeranch.game.object;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class PlayerSaveLoader {
	
	private String name;
	private int money;
	private int nbattle, nwin, nlose;
	private Time playingTime;
	private HashMap<String, Monster> monsters;
	private HashMap<String, Integer> items;
	private String currentMonster;
	private String filepath = "SavedGames";
	private boolean isAbleToSave=true;
	private Context context;
	private String useless;
	private static PlayerSaveLoader instance = null;
	File fileSave;	
	
	private PlayerSaveLoader(Context context){
		this.context=context;
	}
	
	public static void initialize(Context context){
		instance=new PlayerSaveLoader(context);
	}
	
	public static PlayerSaveLoader getInstance() {
		return instance;
	}
	
	public int getNwin() {
		return nwin;
	}

	public void setNwin(int nwin) {
		this.nwin = nwin;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getNlose() {
		return nlose;
	}

	public void setNlose(int nlose) {
		this.nlose = nlose;
	}  
	
	public int getNbattle() {
		return nbattle;
	}

	public void setNbattle(int nbattle) {
		this.nbattle = nbattle;
	}

	public String getCurrentMonster() {
		return currentMonster;
	}

	public void setCurrentMonster(String currentMonster) {
		this.currentMonster = currentMonster;
	}

	public boolean isAbleToSave() {
		return isAbleToSave;
	}

	public void setAbleToSave(boolean status) {
		isAbleToSave = status;
	}


	public void loadPlayer(String playername){
		String myData="";
		try{
			FileInputStream fis = new FileInputStream(context.getExternalFilesDir(filepath).toString()+playername+".sav");
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)  {
				myData = myData + strLine + " ";
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		 		 
		
		Scanner scan = new Scanner(myData);
		useless = scan.next();
		name = scan.next();
		useless = scan.next();
		setMoney(scan.nextInt());
		useless = scan.next();
		setNbattle(scan.nextInt());
		useless = scan.next();
		setNwin(scan.nextInt());
		useless = scan.next();
		setNlose(scan.nextInt());
		useless = scan.next();
		playingTime = new Time();
		playingTime.load(scan);
		useless = scan.next();
		int jumlah=scan.nextInt();
		useless = scan.next();
		setCurrentMonster(scan.next());
		useless = scan.next();
		useless = scan.next();
		Monster monster=new Monster();
		int k;
		for(k=0;k<jumlah;k++) {
			monster.load(scan);
			monsters.put(monster.getName(), monster);
		}
		useless = scan.next();
		jumlah=scan.nextInt();
		k = 0;
		String item;
		int jumlahItem;
		for(k=0;k<jumlah;k++){
			item=scan.next();
			jumlahItem=scan.nextInt();
			items.put(item, jumlahItem);
		}
		
		
		/*harusnya jadinya contohnya kayak gini
		Nama: Pandu
		JumlahUang: 10000
		JumlahBattle: 3
		JumlahMenang: 8
		JumlahKalah: 4
		WaktuBermain(Tahun,bulan,hari,jam,menit): 0 0 2 6 7
		MonsterSekarang: Imba
		DaftarMonster:
		NamaMonster: Imba
		Umur: 0 0 0 5 3
		Spesies: Bulba Level: 14
		Exp: 900 EvoExp: 1000
		BonusCash: 20 BonusExp+50
		Status(hp,mp,att,def,eff): 28 36 47 64 POISON / 30 40 50 70 NONE
		Skill:
		Razor Vine_Whip Cut	Tackle
		NamaMonster: Imba2
		Umur: 0 0 0 5 3
		Spesies: Bulba Level: 14
		Exp: 900 EvoExp: 1000
		BonusCash: 20 BonusExp+50
		Status(hp,mp,att,def,eff): 28 36 47 64 POISON / 30 40 50 70
		Skill:
		Razor Vine_Whip Cut Tackle
		JumlahItem: 2
		DaftarItem:
		Potion 2
		MonsterBall 4
		Cut 5
				
		*/
	}
	
	public void savePlayer(Player player){
		Log.d("POKE","gfg0");
		try {
			 if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {  
				   setAbleToSave(false);
					Log.d("POKE","gfg-2");  
			 } else { 
				fileSave = new File(context.getExternalFilesDir(filepath),player.getName()+".sav");
				Log.d("POKE",context.getExternalFilesDir(filepath).toString());
				  }
			Log.d("POKE","gfg1");
			FileOutputStream fos = new FileOutputStream(fileSave);
			Log.d("POKE","gfg2");
			StringBuilder str=new StringBuilder();	
			Log.d("POKE","gfg3");
			str.append(	"Nama: "+player.getName()+"\n"+
						"JumlahUang: "+player.getMoney()+"\n"+
						"JumlahBattle: "+player.getNbattle()+"\n"+
						"JumlahMenang: "+player.getNwin()+"\n"+
						"JumlahKalah: "+player.getNlose()+"\n"+
						"WaktuBermain(Tahun,bulan,hari,jam,menit): "+player.getPlayingTime().toString()+"\n"+
						"JumlahMonster: "+player.getNMonster()+"\n"+
						"MonsterSekarang: "+player.getCurrentMonster().getName()+"\n");
			Log.d("POKE","gfg4");
		      // Get an iterator
			str.append("DaftarMonster:\n");
			Log.d("POKE","gfg5");
			Collection monster = player.getAllMonster().values();
			Log.d("POKE","gfg6");
			Iterator<Monster>i = monster.iterator();
		    //nulis monster(lengkap)--lihat to String di monster
			Log.d("POKE","gfg7");
			while(i.hasNext()) {
		         str.append(i.next().toString()+"\n");
		    }
			Log.d("POKE","gfg8");
			str.append("JumlahItem: "+player.getAllItem().size()+"\n");
			Log.d("POKE","gfg9");
			Set item = player.getAllItem().entrySet();
		      // Get an iterator
		    //nulis item, nama sama jumlahnya
			Log.d("POKE","gfg10");
		    str.append("DaftarItem:\n");
			Log.d("POKE","gfg11");
		    Iterator j = item.iterator();
			Log.d("POKE","gfg12");    
		    while(j.hasNext()){
		    	Map.Entry me= (Map.Entry)j.next();
		    	str.append(me.getKey()+" "+me.getValue()+"\n");
		    }
			Log.d("POKE","gfg13");
			fos.write(str.toString().getBytes());
			fos.close();
			
			/*harusnya jadinya contohnya kayak gini
			Nama: Pandu
			JumlahUang: 10000
			JumlahBattle: 3
			JumlahMenang: 8
			JumlahKalah: 4
			WaktuBermain(Tahun,bulan,hari,jam,menit): 0 0 2 6 7
			JumlahMonster: 2
			MonsterSekarang: Imba
			DaftarMonster:
			NamaMonster: Imba
			Umur: 0 0 0 5 3
			Spesies: Bulba Level: 14
			Exp: 900 EvoExp: 1000
			BonusCash: 20 BonusExp+50
			Status(hp,mp,att,def,eff): 28 36 47 64 POISON / 30 40 50 70
			Skill:
			Razor Vine_Whip Cut	Tackle
			NamaMonster: Imba2
			Umur: 0 0 0 5 3
			Spesies: Bulba Level: 14
			Exp: 900 EvoExp: 1000
			BonusCash: 20 BonusExp+50
			Status(hp,mp,att,def,eff): 28 36 47 64 POISON / 30 40 50 70
			Skill:
			Razor Vine_Whip Cut Tackle
			JumlahItem: 3
			DaftarItem:
			Potion 2
			MonsterBall 4
			Cut 5		
			*/
	   } catch (Exception e) {
		    e.printStackTrace();
	   }
	}
			
	 private static boolean isExternalStorageReadOnly() {  
		  String extStorageState = Environment.getExternalStorageState();  
		  if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {  
		   return true;  
		  }  
		  return false;  
		 }  
		 
		 private static boolean isExternalStorageAvailable() {  
		  String extStorageState = Environment.getExternalStorageState();  
		  if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {  
		   return true;  
		  }  
		  return false;  
		 }	 
	
}
