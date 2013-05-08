package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IScreen {
	public void update();
	public void draw(Canvas canvas);
	public void onTouchEvent(MotionEvent e, float magX, float magY);
}
