package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class ScrollComponent {
	//system
	private String[] items;
	private SelectionListener listener;
	private boolean touched;
	
	//gui
	private int x, y;
	private int width, height;
	private int defaultColor, selectionColor;
	private int showItem;
	
	public interface SelectionListener{
		public void selectAction(int selection);
	}
	
	public ScrollComponent(String[] items, int x, int y, SelectionListener listener){
		
	}
	
	public void draw(Canvas canvas){
		
	}
	
	public void onTouchEvent(MotionEvent event, float magX, float magY){
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;	
		RectF r = new RectF(x*magX, y*magY, (x + width)*magX,  (y + height)*magY);
		
		if (r.contains(event.getX(), event.getY())){
			switch (actioncode) {
				case MotionEvent.ACTION_DOWN:
					touched = true;
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					touched = false;
					break;		
			}
		}else{
			if(touched){
				switch (actioncode) {
				case MotionEvent.ACTION_MOVE:
					touched = false;
					break;
				case MotionEvent.ACTION_UP:
					touched = false;
					break;		
				}
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(int defaultColor) {
		this.defaultColor = defaultColor;
	}

	public int getSelectionColor() {
		return selectionColor;
	}

	public void setSelectionColor(int selectionColor) {
		this.selectionColor = selectionColor;
	}
}