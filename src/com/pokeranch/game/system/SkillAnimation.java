package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SkillAnimation {
	private Bitmap source;
	private int updatePerImage;
	private int currentUpdate;
	private int x,y;
	
	public SkillAnimation(Bitmap source, int updatePerImage, int x, int y){
		this.source = source;
		this.updatePerImage = updatePerImage;
		currentUpdate = 0;
	}
	
	public void update(){
		
	}
	
	public void draw(Canvas canvas, int magnification){
		//canvas.draw
	}
	
	
}
