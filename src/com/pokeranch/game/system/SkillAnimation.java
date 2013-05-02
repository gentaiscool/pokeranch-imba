package com.pokeranch.game.system;

import com.pokeranch.game.object.Skill;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class SkillAnimation {
	private Bitmap source;	
	private Skill skill;
	private int updatePerImage;
	private int currentUpdate, currentFrame;
	private int x,y, totalFrame;
	private int width, height;
	private boolean finish = false;
	
	public SkillAnimation(Skill skill, int updatePerImage, int x, int y, int totalFrame){
		this.skill = skill;
		this.source = BitmapManager.getInstance().get(skill.getName());
		this.updatePerImage = updatePerImage;
		this.x=x;
		this.y=y;
		this.totalFrame=totalFrame;
		currentUpdate = 0;
		currentFrame = 0;
		
		width = source.getWidth() / totalFrame;
		height = source.getHeight(); //source image harus memanjang
		
	}
	
	public void update(){
		if(!finish){
			currentUpdate++;
			if(currentUpdate % updatePerImage == 0){
				currentUpdate = 0;
				currentFrame++;
				finish = currentFrame == totalFrame - 1;
			}
		}
	}
	
	public boolean finished(){
		return finish;
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(source, new Rect(currentFrame*width,0,(currentFrame+1)*width,height), new RectF(x,y,x+width,y+height), null);
	}

	public Skill getSkill() {
		return skill;
	}
	
}
