package com.pokeranch.game.system;

import android.view.MotionEvent;

public interface TouchListener {
	public void onTouchEvent(MotionEvent event, float magX,float magY);
}
