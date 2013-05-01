package com.pokeranch.game.system;

import android.graphics.Canvas;

import com.pokeranch.game.system.MainGameView.ButtonClick;

public interface IScene {
	public void update();
	public void draw(Canvas canvas);
	//public void OnTouchEvent(MotionEvent e);
	
	public void getButtonInput(ButtonClick b);
}
