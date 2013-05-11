package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

public class BitmapButton implements Touchables {
	private TouchListener action;
	private Bitmap bitmap;
	private float x, y;
	private boolean touched = false;
	private boolean enable = true;
	private boolean visible = true;
	public interface TouchListener {
		public void onTouchDown();
		public void onTouchUp();
		public void onTouchMove();
	}
	
	public BitmapButton(Bitmap bitmap, float x, float y){
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
	}
	
	public Bitmap getBitmap () {
		return bitmap;
	}
	
	public void setBitmap (Bitmap _bitmap) {
		bitmap = _bitmap;
	}
	public void draw(Canvas canvas){
		if(visible)
			canvas.drawBitmap(bitmap, new Rect(0,0,bitmap.getWidth(), bitmap.getHeight()), new RectF(x,y,x+bitmap.getWidth(),y+bitmap.getHeight()), null);
	}
	
	public void addTouchListener(TouchListener action){
		this.action = action;
	}
	
	@Override
	public void onTouchEvent(MotionEvent event, float magX, float magY) {
		if(!enable) return;
		
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;	
		
		RectF r = new RectF(x*magX, y*magY, (x + bitmap.getWidth())*magX,  (y + bitmap.getHeight())*magY);
		
		if (r.contains(event.getX(), event.getY())){
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

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		//setEnable(visible);
	}
}
