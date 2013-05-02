package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Tile {
	//ukuran tile bitmap yg masuk diasumsikan selalu berukuran 16x16 per tile
	private int spriteCode;
	private String teleportTarget; //nama area teleportasi
	private boolean passable;
	private Point arrivalCord;

	Tile(int b, boolean pass){
		spriteCode = b;
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

	public int getSpriteCode() {
		return spriteCode;
	}

	public void setSpriteCode(int spriteCode) {
		this.spriteCode = spriteCode;
	}

	public void setArrivalCord(Point arrivalCord) {
		this.arrivalCord = arrivalCord;
	}

	public Point getArrivalCord() {
		return arrivalCord;
	}
	
}
