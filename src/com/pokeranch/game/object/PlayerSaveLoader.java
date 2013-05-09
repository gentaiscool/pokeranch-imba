package com.pokeranch.game.object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class PlayerSaveLoader {
	
	private String filepath = "SavedGames";
	private boolean isAbleToSave=true;
	private Context context;
	private String useless;
	private Player player;
	private static PlayerSaveLoader instance = null;
	File fileSave;	
	
	private PlayerSaveLoader(Context context){
		this.context=context;
	}
	
	public static void initialize(Context context){
		instance=new PlayerSaveLoader(context);
	}
	
	public static void release(){
		instance = null;
	}
	
	public static PlayerSaveLoader getInstance() {
		return instance;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player=player;
	}
	
	public boolean isAbleToSave() {
		return isAbleToSave;
	}

	public void setAbleToSave(boolean status) {
		isAbleToSave = status;
	}

	public boolean isPlayerNotExist(String playername){
		File fis = new File(context.getExternalFilesDir(filepath).toString()+"/"+playername+".sav");
		if(!fis.exists()){
			return true;
		}
		else return false;
	}
	
	public Player loadPlayer(String playername){
		player=new Player();
		String myData="";
		try{
			FileInputStream fis = new FileInputStream(context.getExternalFilesDir(filepath).toString()+"/"+playername+".sav");
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
		setUseless(scan.next());
		player.setName(scan.next());
		setUseless(scan.next());
		player.setMoney(scan.nextInt());
		setUseless(scan.next());
		player.setNbattle(scan.nextInt());
		setUseless(scan.next());
		player.setNwin(scan.nextInt());
		setUseless(scan.next());
		player.setNlose(scan.nextInt());
		setUseless(scan.next());
		player.setPlayingTime(new Time());
		player.getPlayingTime().load(scan);
		setUseless(scan.next());
		int jumlah=scan.nextInt();
		setUseless(scan.next());
		player.setCurrentMonster(scan.next());
		setUseless(scan.next());
		Monster monster;
		int k;
		for(k=0;k<jumlah;k++) {
			monster=new Monster();
			monster.load(scan);
			player.getAllMonster().put(monster.getName(), monster);
		}
		setUseless(scan.next());
		k = 0;
		jumlah=scan.nextInt();
		setUseless(scan.next());
		String item;
		int jumlahItem;
		for(k=0;k<jumlah;k++){
			item=scan.next();
			jumlahItem=scan.nextInt();
			player.getAllItem().put(item, jumlahItem);
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
		
		return player; 
	}
	
	public void savePlayer(Player player){
		try {
			 if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {  
				   setAbleToSave(false);
			 } else { 
				fileSave = new File(context.getExternalFilesDir(filepath),player.getName()+".sav");
				  }
			FileOutputStream fos = new FileOutputStream(fileSave);
			StringBuilder str=new StringBuilder();	
			str.append(	"Nama: "+player.getName()+"\n"+
						"JumlahUang: "+player.getMoney()+"\n"+
						"JumlahBattle: "+player.getNbattle()+"\n"+
						"JumlahMenang: "+player.getNwin()+"\n"+
						"JumlahKalah: "+player.getNlose()+"\n"+
						"WaktuBermain(Tahun,bulan,hari,jam,menit): "+player.getPlayingTime().toString()+"\n"+
						"JumlahMonster: "+player.getNMonster()+"\n"+
						"MonsterSekarang: "+player.getCurrentMonster().getName()+"\n");
		      // Get an iterator
			str.append("DaftarMonster:\n");
			Collection<Monster> monster = player.getAllMonster().values();
			Iterator<Monster>i = monster.iterator();
		    //nulis monster(lengkap)--lihat to String di monster
			while(i.hasNext()) {
		         str.append(i.next().toString()+"\n");
		    }
			str.append("JumlahItem: "+player.getAllItem().size()+"\n");
			Set<Map.Entry<String, Integer>> item = player.getAllItem().entrySet();
		      // Get an iterator
		    //nulis item, nama sama jumlahnya
		    str.append("DaftarItem:\n");
		    Iterator<Map.Entry<String, Integer>> j = item.iterator();
		    while(j.hasNext()){
		    	Map.Entry<String, Integer> me= j.next();
		    	str.append(me.getKey()+" "+me.getValue()+"\n");
		    }
		    
		    Log.d("POKE SAVE", str.toString());
		    
			fos.write(str.toString().getBytes());
			fos.close();
			
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

		public String getUseless() {
			return useless;
		}

		public void setUseless(String useless) {
			this.useless = useless;
		}	 
	
}
