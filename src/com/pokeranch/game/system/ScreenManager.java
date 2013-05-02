package com.pokeranch.game.system;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class ScreenManager {
	private ArrayList<IScreen> screens;
	
	public ScreenManager(){
		screens = new ArrayList<IScreen>();
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
	
	public void onTouchEvent(MotionEvent e, float mag){
		screens.get(screens.size()-1).onTouchEvent(e, mag);
	}
	
}
