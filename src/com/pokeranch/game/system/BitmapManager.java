package com.pokeranch.game.system;

import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapManager {
	private Resources res;
	private HashMap<String, Bitmap> bitmaps;
	private static BitmapManager instance; 
	
	private BitmapManager(Resources res){
		this.res = res;  
		bitmaps = new HashMap<String, Bitmap>();
	}
	
	public static void initialize(Resources res){
		instance = new BitmapManager(res);
	}
	
	public static BitmapManager getInstance(){
		return instance;
	}
	
	public void put(String key, int id){
		bitmaps.put(key, BitmapFactory.decodeResource(res, id));
	}
	
	public Bitmap get(String key){
		return bitmaps.get(key);
	}
}