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


	public Player loadPlayer(String playername){
		player=new Player();
		Log.d("POKE","load1");
		String myData="";
		Log.d("POKE","load2");
		try{
			FileInputStream fis = new FileInputStream(context.getExternalFilesDir(filepath).toString()+"/"+playername+".sav");
			Log.d("POKE","load3");
			DataInputStream in = new DataInputStream(fis);
			Log.d("POKE","load4");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			Log.d("POKE","load5");
			String strLine;
			Log.d("POKE","load6");
			while ((strLine = br.readLine()) != null)  {
				myData = myData + strLine + " ";
				Log.d("POKE","load7");
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		 		 
		Log.d("POKE","load8");
		Scanner scan = new Scanner(myData);
		Log.d("POKE","load9");
		setUseless(scan.next());
		player.setName(scan.next());
		Log.d("POKE","load10");
		setUseless(scan.next());
		player.setMoney(scan.nextInt());
		Log.d("POKE","load11");
		setUseless(scan.next());
		player.setNbattle(scan.nextInt());
		Log.d("POKE","load12");
		setUseless(scan.next());
		player.setNwin(scan.nextInt());
		Log.d("POKE","load13");
		setUseless(scan.next());
		player.setNlose(scan.nextInt());
		Log.d("POKE","load14");
		setUseless(scan.next());
		player.setPlayingTime(new Time());
		Log.d("POKE","load15");
		player.getPlayingTime().load(scan);
		Log.d("POKE","load16");
		setUseless(scan.next());
		int jumlah=scan.nextInt();
		setUseless(scan.next());
		player.setCurrentMonster(scan.next());
		Log.d("POKE","load17");
		setUseless(scan.next());
		Monster monster;
		Log.d("POKE","load18");
		int k;
		for(k=0;k<jumlah;k++) {
			monster=new Monster();
			monster.load(scan);
			Log.d("POKE","load19");
			player.getAllMonster().put(monster.getName(), monster);
			Log.d("POKE","load20");
		}
		setUseless(scan.next());
		Log.d("POKE","habis monter"+getUseless());
		k = 0;
		jumlah=scan.nextInt();
		Log.d("POKE",Integer.valueOf(jumlah).toString());
		setUseless(scan.next());
		String item;
		int jumlahItem;
		for(k=0;k<jumlah;k++){
			item=scan.next();
			Log.d("POKE","load22");
			jumlahItem=scan.nextInt();
			Log.d("POKE","load23");
			player.getAllItem().put(item, jumlahItem);
			Log.d("POKE","load24");
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
			Collection monster = player.getAllMonster().values();
			Iterator<Monster>i = monster.iterator();
		    //nulis monster(lengkap)--lihat to String di monster
			while(i.hasNext()) {
		         str.append(i.next().toString()+"\n");
		    }
			str.append("JumlahItem: "+player.getAllItem().size()+"\n");
			Set item = player.getAllItem().entrySet();
		      // Get an iterator
		    //nulis item, nama sama jumlahnya
		    str.append("DaftarItem:\n");
		    Iterator j = item.iterator();
		    while(j.hasNext()){
		    	Map.Entry me= (Map.Entry)j.next();
		    	str.append(me.getKey()+" "+me.getValue()+"\n");
		    }
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

		public String getUseless() {
			return useless;
		}

		public void setUseless(String useless) {
			this.useless = useless;
		}	 
	
}
