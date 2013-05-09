package com.pokeranch.game.system;

import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class ScreenManager {
	private CopyOnWriteArrayList<IScreen> screens; 
	//makan memori lebih gede tapi threadsafe katanya
	//alasan diganti: kalo ngepop bakal error concurrent kalo ga pake ini
	private static ScreenManager manager;
	
	private ScreenManager(){
		screens = new CopyOnWriteArrayList<IScreen>();
	}
	
	public static void initialize(){
		manager = new ScreenManager();
	}
	
	public static void release(){
		manager = null;
	}
	
	public static ScreenManager getInstance(){
		return manager;
	}
	
	public void push(IScreen screen){
		screens.add(screen);
	}
	
	public void pop(){
		screens.remove(screens.size()-1);
	}
	
	public void draw(Canvas canvas){
		//semuanya di draw
			
			for(IScreen s : screens){
				s.draw(canvas);
			}
	}
	
	public void update(){
		//yg diupdate cuma yg top
		
		screens.get(screens.size()-1).update();
	}
	
	public void onTouchEvent(MotionEvent e, float magX, float magY){
		//yg diupdate cuma yg top
		screens.get(screens.size()-1).onTouchEvent(e, magX, magY);
	}
	
}
