package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
	private Matrix mtx;
	private SpriteCounter scount;
	
	public interface SpriteCounter {
		public Point getImgPos(int direction, int frame, int width, int height);
		
	}
	
	//sprite X dan Y nya merupakan row dan colums saat landscape
	public Sprite(int _x, int _y, Bitmap bitmap, int row, int col, int frameNum,SpriteCounter scount){
		//Log.d("harits", "konstruktor sprite start");
		this.bitmap = bitmap;
		this.scount = scount;
		this.frameNum = frameNum;
		mtx = new Matrix();
		mtx.setRotate(90);
		//Log.d("harits", "konstruktor sprite sebelum ngambil size");
		if(bitmap == null)
			Log.d("harits", "bitmap null lho");
		width = bitmap.getWidth() / col;
		height = bitmap.getHeight() / row;
		x = _y;
		y = _x;
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
		//Log.d("x,y", Integer.valueOf(x).toString() + " " + Integer.valueOf(y).toString());
	}
	
	
	public void draw(Canvas canvas){
		//mag= 1,2,3,4
		Point p = scount.getImgPos(direction, frame, width, height);
		int sx = p.x;
		int sy = p.y;
		//
		
		//Log.d("harits", "bitmap asli: 0,0," + bitmap.getWidth() + ", " + bitmap.getHeight());
		//Log.d("harits", "yg dipotong: "+ sx + ", "+sy+", "+(sx+width)+", "+(sy+height));
		
		//Bitmap newB = Bitmap.createBitmap(bitmap, sx, sy, width, height, mtx, false);
		//Log.d("harits", "berhasil motong");
		//Bitmap newB = bitmap;
		canvas.drawBitmap(bitmap, new Rect(sx, sy, sx+width, sy+height), new RectF(x, y, x+width, y+height), null);
	}
	
	public boolean onTouchEvent(MotionEvent event, float mag) {
		Log.d("testanim", "adasd");
		return true;
	}
	
	public int getX(){
		return y;
	}
	
	public int getY(){
		return x;
	}
	
	public void setX(int _x){
		y = _x;
	}
	
	public void setY(int _y){
		x = _y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}