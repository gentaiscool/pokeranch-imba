package com.pokeranch.game.system;

import android.graphics.Bitmap;

public class Tile {
	//ukuran tile bitmap yg masuk diasumsikan selalu berukuran 16x16 per tile
	private int spriteCode;
	private Area teleportTarget;
	private boolean passable;

	Tile(int b, boolean pass){
		spriteCode = b;
		passable = pass;
	}
	
	public boolean isPassable(){
		return passable;
	}
	
	public Area getTeleportTarget() {
		return teleportTarget;
	}
	public void setTeleportTarget(Area teleportTarget) {
		this.teleportTarget = teleportTarget;
	}

	public int getSpriteCode() {
		return spriteCode;
	}

	public void setSpriteCode(int spriteCode) {
		this.spriteCode = spriteCode;
	}
	
}
