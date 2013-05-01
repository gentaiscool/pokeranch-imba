package com.pokeranch.game.system;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class ScreenManager {
	private ArrayList<IScene> screens;
	
	public ScreenManager(){
		screens = new ArrayList<IScene>();
	}
	
	public void push(IScene screen){
		screens.add(screen);
	}
	
	public void pop(){
		screens.remove(screens.size()-1);
	}
	
	public void draw(Canvas canvas){
		for(IScene s : screens) s.draw(canvas);
	}
	
	public void update(){
		for(IScene s : screens) s.update();
	}
	
	public void onTouchEvent(MotionEvent e){
		//screens.get(screens.size()-1).onTouchEvent(e);
	}
	
}
