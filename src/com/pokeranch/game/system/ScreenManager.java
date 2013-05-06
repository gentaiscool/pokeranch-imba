package com.pokeranch.game.system;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class ScreenManager {
	private ArrayList<IScreen> screens;
	private static ScreenManager manager;
	
	private ScreenManager(){
		screens = new ArrayList<IScreen>();
	}
	
	public static void initialize(){
		manager = new ScreenManager();
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
		for(IScreen s : screens) s.draw(canvas);
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
