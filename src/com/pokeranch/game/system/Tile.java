package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Tile {
	//ukuran tile bitmap yg masuk diasumsikan selalu berukuran 16x16 per tile
	private String spriteCodeBG, spriteCodeObj;
	private String teleportTarget; //nama area teleportasi
	private boolean passable;
	private Point arrivalCord;

	Tile(String b, String c, boolean pass){
		spriteCodeBG = b;
		spriteCodeObj = c;
		passable = pass;
	}
	
	public void setPassable(boolean p){
		passable = p;
	}
	
	public boolean isPassable(){
		return passable;
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
