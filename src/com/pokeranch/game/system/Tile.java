package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

public class Tile {
	//ukuran tile bitmap yg masuk diasumsikan selalu berukuran 16x16 per tile
	private String spriteCodeBG, spriteCodeObj;
	private String teleportTarget; //nama area teleportasi
	private int passable;
	private Point arrivalCord;
	private String actionName;
	private String shores[] = {"1683", "1725", "1727", "1769"};

	Tile(String b, String c, int pass){
		spriteCodeBG = b;
		spriteCodeObj = c;
		passable = pass;
	}
	
	public void setActionName(String _actionName){
		actionName = _actionName;
	}
	
	public String getActionName(){
		return actionName;
	}
	
	public void drawBG(Canvas canvas, int i, int j, int curX, int curY, AreaManager am){
		//Log.d("harits3", "kepanggil");
		canvas.drawBitmap(BitmapManager.getInstance().get(spriteCodeBG), null, new RectF(j*16, i*16, j*16 + 16, i*16 + 16),  am.getPaint());
	}
	
	public void drawObj(Canvas canvas, int i, int j, int curX, int curY, AreaManager am){
		//Log.d("harits3", "kepanggil obj");
		if(spriteCodeObj != null)
			canvas.drawBitmap(BitmapManager.getInstance().get(spriteCodeObj), null, new RectF(j*16, i*16, j*16 + 16, i*16 + 16),  am.getPaint());
	}
	
	public boolean hasTree(){
		return (spriteCodeObj.equals("603"));
	}
	
	public boolean hasBoulder(){
		return (spriteCodeObj.equals("692"));
	}
	
	public boolean isShore(){
		for(String s:shores){
			if(spriteCodeBG.equals(s))
				return true;
		}
		return false;
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
