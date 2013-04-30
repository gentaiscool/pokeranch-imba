package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class BitmapButton implements TouchListener {
	private TouchAction action;
	private Bitmap bitmap;
	private float x, y;
	private boolean touched = false;
	
	public BitmapButton(Bitmap bitmap, float x, float y){
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(bitmap, x, y, null);
	}
	
	public void addTouchAction(TouchAction action){
		this.action = action;
	}
	
	@Override
	public void onTouchEvent(MotionEvent event) {
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;	
		
		Rect r = new Rect((int) x, (int) y, (int) x + bitmap.getWidth(),  (int) y + bitmap.getHeight() );
		
		if (r.contains((int) event.getX(), (int) event.getY())){
			switch (actioncode) {
				case MotionEvent.ACTION_DOWN:
					action.onTouchDown();
					touched = true;
					break;
				case MotionEvent.ACTION_MOVE:
					action.onTouchMove();
					break;
				case MotionEvent.ACTION_UP:
					action.onTouchUp();
					touched = false;
					break;		
			}
		}else{
			if(touched){
				switch (actioncode) {
				case MotionEvent.ACTION_MOVE:
					action.onTouchUp();
					touched = false;
					break;
				case MotionEvent.ACTION_UP:
					action.onTouchUp();
					touched = false;
					break;		
				}
			}
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
