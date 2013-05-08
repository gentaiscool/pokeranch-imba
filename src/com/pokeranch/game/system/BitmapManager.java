package com.pokeranch.game.system;

import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;

public class BitmapManager {
	private Resources res;
	private HashMap<String, Bitmap> bitmaps;
	private Typeface typeface;
	private boolean locked = false;
	private static BitmapManager instance; 
	
	private BitmapManager(Resources res){
		this.res = res;  
		bitmaps = new HashMap<String, Bitmap>();
		typeface = Typeface.createFromAsset(res.getAssets(), "fonts/Pokemon GB.ttf");
	}
	
	public void putMap(String name, int id, int r, int c, int border, int pixelSize){
		//untuk tile peta, asumsinya langsung dirotate 90 derajat searah jarum jam
		//int border = 1;
		//int pixelSize = 16;
		Bitmap src = BitmapFactory.decodeResource(res, id);
		//Log.d("harits", src.getHeight() + " " + src.getWidth());
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				//Log.d("harits1", "motong " + i + " dan " + j);
				Integer key = i*c+j;	
				bitmaps.put(name+key.toString(), Bitmap.createBitmap(src, border+((border+pixelSize)*j), border+((border+pixelSize)*i), pixelSize, pixelSize));
			}
		}
		//Log.d("harits", "berhasil motong2 bitmap");
	}
	
	public void putImage(String key, int id, int width, int height){
		Bitmap src = BitmapFactory.decodeResource(res, id);	
		bitmaps.put(key.toString(), Bitmap.createScaledBitmap(src, width, height, true));

	}
	
	public static void initialize(Resources res){
		instance = new BitmapManager(res);
	}
	
	public static BitmapManager getInstance(){
		return instance;
	}
	
	public void put(String key, int id) throws Exception{
		if(!locked)
			bitmaps.put(key, BitmapFactory.decodeResource(res, id));
		else{
			throw new Exception("BitmapManager already locked!");
		}
	}
	
	public void lockPut(){
		locked = true;
	}
	
	public Typeface getTypeface(){
		return typeface;
	}
	
	public Bitmap get(String key){
		return bitmaps.get(key);
	}
}