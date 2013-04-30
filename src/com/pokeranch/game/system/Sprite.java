package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class Sprite {
	private Bitmap bitmap;
	private int direction = 0;
	private int frame = 0, frameNum;
	private int width, height;
	private int x, y;
	private SpriteCounter scount;
	
	public Sprite(Bitmap bitmap, int row, int col, int frameNum,SpriteCounter scount){
		//Log.d("harits", "konstruktor sprite start");
		this.bitmap = bitmap;
		this.scount = scount;
		this.frameNum = frameNum;
		//Log.d("harits", "konstruktor sprite sebelum ngambil size");
		if(bitmap == null)
			Log.d("harits", "bitmap null lho");
		width = bitmap.getWidth() / col;
		height = bitmap.getHeight() / row;
		x = 0;
		y = 0;
		//Log.d("harits", "konstruktor sprite end");
	}
	
	public void move(int dir, int dist){
		direction = dir;
		nextFrame();
		int dx = direction == 3 ? -dist : (direction == 1 ? dist : 0);
		int dy = direction == 0 ? -dist : (direction == 2 ? dist : 0);
		updatePos(dx, dy);
		
	}
	
	public void nextFrame(){
		frame++;
		frame %= frameNum;
	}
	
	public void stopFrame(){
		frame = 0;
	}
	
	public void updatePos(int dx, int dy){
		x += dx;
		y += dy;
		Log.d("x,y", Integer.valueOf(x).toString() + " " + Integer.valueOf(y).toString());
	}
	
	
	public void draw(Canvas canvas){
		Point p = scount.getImgPos(direction, frame, width, height);
		int sx = p.x;
		int sy = p.y;
		
		canvas.drawBitmap(bitmap, new Rect(sx, sy, sx+width, sy+height), new RectF(x, y, x+width, y+height), null);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("testanim", "adasd");
		return true;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int _x){
		x = _x;
	}
	
	public void setY(int _y){
		y = _y;
	}
	
}