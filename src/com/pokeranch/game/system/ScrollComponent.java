package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class ScrollComponent {
	private TouchAction action;
	private Bitmap scrollTab, panel, pokedextablet;
	private float x, y, curX, curY;
	private boolean touched = false;
	
	public ScrollComponent(int _X, int _Y) {
		// TODO Auto-generated constructor stub
		x = _X;
		y = _Y;
		curX = 280;
		curY = 0;
		BitmapManager.getInstance().put("ic", R.drawable.ic_launcher);
		BitmapManager.getInstance().put("panel", R.drawable.panelground);
		BitmapManager.getInstance().put("pokedextablet", R.drawable.pokedex);
		scrollTab = BitmapManager.getInstance().get("ic");
		panel = BitmapManager.getInstance().get("panel");
		pokedextablet = BitmapManager.getInstance().get("pokedextablet");
		//scrollTab = new scrollTabButton(BitmapManager.getInstance().get("ic"),0,0);
	}
	
	public void update(int X, int Y){
		
	}
	
	public void draw(Canvas canvas){
		curX = x+80;
		curY = y;
		canvas.drawBitmap(pokedextablet, new Rect(0,0,pokedextablet.getWidth(), pokedextablet.getHeight()), new RectF(2,8,175,180), null);
		canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(curX,curY,curX+170,curY+200), null);
		//canvas.drawBitmap(scrollTab, new Rect(0,0,scrollTab.getWidth(), scrollTab.getHeight()), new RectF(curX,curY,curX+scrollTab.getWidth(),curY+scrollTab.getHeight()), null);
	}
		
	public void onTouchEvent(MotionEvent event, float magX, float magY) {
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;	
		
		RectF r = new RectF(curX*magX, curY*magY, (curX + panel.getWidth())*magX,  (curY + panel.getHeight())*magY);
		
		Log.d("test", event.getX() + " " + event.getY());
		
		if (r.contains(event.getX(), event.getY())){
			switch (actioncode) {
				case MotionEvent.ACTION_DOWN:
					//action.onTouchDown();
					//Log.d("action", "onTouchDown");
					//curX=(float) (event.getX()/magX-scrollTab.getWidth()*0.5);
					//y=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					//curY=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					
					touched = true;
					break;
				case MotionEvent.ACTION_MOVE:
					//action.onTouchMove();
					//curX=(float) (event.getX()/magX-scrollTab.getWidth()*0.5);
					y=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					curY=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					Log.d("action", "onTouchMove");
					break;
				case MotionEvent.ACTION_UP:
					//action.onTouchUp();
					//curX=(float) (event.getX()/magX-scrollTab.getWidth()*0.5);
					y=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					curY=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					Log.d("action", "onTouchUp");
					touched = false;
					break;		
			}
		}else{
			if(touched){
				switch (actioncode) {
				case MotionEvent.ACTION_MOVE:
					//action.onTouchUp();
					Log.d("action2", "onTouchMove");
					touched = false;
					break;
				case MotionEvent.ACTION_UP:
					//action.onTouchUp();
					Log.d("action2", "onTouchUp");
					touched = false;
					break;		
				}
			}
		}
	}
}
