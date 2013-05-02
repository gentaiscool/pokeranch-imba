package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.pokeranch.game.system.MainGameView.ButtonClick;

public interface IScreen {
	public void update();
	public void draw(Canvas canvas, int mag);
	public void onTouchEvent(MotionEvent e);
	
	//public void getButtonInput(ButtonClick b);
}
