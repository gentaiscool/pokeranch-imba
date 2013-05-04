package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

public class Tile {
	//ukuran tile bitmap yg masuk diasumsikan selalu berukuran 16x16 per tile
	private String spriteCodeBG, spriteCodeObj;
	private String teleportTarget; //nama area teleportasi
	private int passable;
	private Point arrivalCord;

	Tile(String b, String c, int pass){
		spriteCodeBG = b;
		spriteCodeObj = c;
		passable = pass;
	}
	
	public void drawBG(Canvas canvas, int i, int j, int curX, int curY, AreaManager am){
		canvas.drawBitmap(BitmapManager.getInstance().get(spriteCodeBG), null, new RectF(j*16, i*16, j*16 + 16, i*16 + 16),  ( (i == curX && j == curY) ? null : am.getPaint()));
	}
	
	public void drawObj(Canvas canvas, int i, int j, int curX, int curY, AreaManager am){
		if(spriteCodeObj != null)
			canvas.drawBitmap(BitmapManager.getInstance().get(spriteCodeObj), null, new RectF(j*16, i*16, j*16 + 16, i*16 + 16),  ( (i == curX && j == curY) ? null : am.getPaint()));
	}
	
	public void setPassable(int p){
		passable = p;
	}
	
	public boolean isPassable(){
		return passable == 0;
	}
	
	public boolean isBlocked(){
		return passable == 1;
	}
	
	public boolean isSwimmable(){
		return passable == 2;
	}
	
	public String getTeleportTarget() {
		return teleportTarget;
	}
	public void setTeleportTarget(String teleportTarget) {
		this.teleportTarget = teleportTarget;
	}

	public String getSpriteCodeBG() {
		return spriteCodeBG;
	}
	
	public String getSpriteCodeObj() {
		return spriteCodeObj;
	}

	public void setSpriteCodeBG(String spriteCode) {
		this.spriteCodeBG = spriteCode;
	}

	public void setSpriteCodeObj(String spriteCode) {
		this.spriteCodeObj = spriteCode;
	}	
	
	public void setArrivalCord(Point arrivalCord) {
		this.arrivalCord = arrivalCord;
	}

	public Point getArrivalCord() {
		return arrivalCord;
	}
	
}
