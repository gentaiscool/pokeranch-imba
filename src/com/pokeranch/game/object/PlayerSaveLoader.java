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
import java.util.Random;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class PlayerSaveLoader {
	
	private String name;
	private int money;
	private int nbattle, nwin, nlose;
	//private string ampas;
	private Time playingTime;
	private HashMap<String, Monster> monsters;
	private HashMap<String, Integer> items;
	private String currentMonster;
	private String filename;
	private String filepath = "SavedGames";
	private boolean isAbleToSave=true;
	private Context context;
	private String useless;
	private static PlayerSaveLoader instance = null;
	File fileSave;	
	
	private PlayerSaveLoader(Context context){
		this.context=context;
	}
	
	public void initialize(Context context){
		instance=new PlayerSaveLoader(context);
	}
	
	public PlayerSaveLoader getInstance() {
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

	public void setAbleToSave(boolean isAbleToSave) {
		this.isAbleToSave = isAbleToSave;
	}


	public void loadPlayer(String playername){
		 String myData="";
		 if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {  
			   setAbleToSave(false);
		 } else { 
			fileSave = new File(context.getExternalFilesDir(filepath), playername+".sav");
			  }

		try{
			FileInputStream fis = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)  {
				myData = myData + strLine;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		 		 
		
		Scanner scan = new Scanner(myData);
		useless = scan.next();
		name = scan.next();
	    Log.d("pandu", "nama: " + name);
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
		setCurrentMonster(scan.next());
		//items =  DBLoader.getInstance.loadItem();
		//monsters = DBLoader		
	}
	
	public void savePlayer(Player player){
		try {
			FileOutputStream fos = new FileOutputStream(fileSave);
			StringBuilder str=new StringBuilder();	
			str.append(	"Nama: "+name+"\n"+
						"JumlahUang: "+money+"\n"+
						"JumlahBattle: "+nbattle+"\n"+
						"JumlahMenang: "+nwin+"\n"+
						"JumlahKalah: "+nlose+"\n"+
						"WaktuBermain(Tahun,bulan,hari,jam,menit): "+playingTime.toString()+"\n"+
						"MonsterSekarang: "+currentMonster);
			fos.write(str.toString().getBytes());
			fos.close();
	   } catch (IOException e) {
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
