package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class ScrollComponent {
	BitmapButton scrollTab;
	public ScrollComponent() {
		// TODO Auto-generated constructor stub
		BitmapManager.getInstance().put("ic", R.drawable.ic_launcher);
		scrollTab = new BitmapButton(BitmapManager.getInstance().get("ic"),0,0);
	}
	
	public void update(int X, int Y){
		
	}
	
	public void draw(Canvas canvas){
		scrollTab.draw(canvas);
	}
/*
	public void onTouchEvent(MotionEvent event, float mag) {
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;	
		
		RectF r = new RectF(x*mag, y*mag, (x + bitmap.getWidth())*mag,  (y + bitmap.getHeight())*mag);
		
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
	}*/
}
