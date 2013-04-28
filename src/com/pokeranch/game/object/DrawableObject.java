package com.pokeranch.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class DrawableObject {
	private int x, y;
	private Bitmap image;
	
	public DrawableObject(int x,int y, Bitmap img){
		this.x = x;
		this.y = y;
		image = img;
	}
	
	public Point getPosition(){ return new Point(x,y);}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
	}
	
}
