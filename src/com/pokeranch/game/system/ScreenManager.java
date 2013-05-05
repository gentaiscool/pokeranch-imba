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
		for(IScreen s : screens) s.draw(canvas);
	}
	
	public void update(){
		for(IScreen s : screens) s.update();
	}
	
	public void onTouchEvent(MotionEvent e, float magX, float magY){
		screens.get(screens.size()-1).onTouchEvent(e, magX, magY);
	}
	
}
