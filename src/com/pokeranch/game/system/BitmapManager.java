package com.pokeranch.game.system;

import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapManager {
	private Resources res;
	private HashMap<String, Bitmap> bitmaps;
	private static BitmapManager instance; 
	
	private BitmapManager(Resources res){
		this.res = res;  
		bitmaps = new HashMap<String, Bitmap>();
	}
	
	public void putMap(int id){
		
		int border = 1;
		int pixelSize = 16;
		
		Bitmap src = BitmapFactory.decodeResource(res, id);
		//Log.d("harits", src.getHeight() + " " + src.getWidth());
		for(int i=0;i<13;i++){
			for(int j=0;j<43;j++){
			//	Log.d("harits", "motong " + i + " dan " + j);
				Integer key = i*43+j;
				bitmaps.put(key.toString(), Bitmap.createBitmap(src, border+((border+pixelSize)*j), border+((border+pixelSize)*i), pixelSize, pixelSize));
			}
		}
		//Log.d("harits", "berhasil motong2 bitmap");
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