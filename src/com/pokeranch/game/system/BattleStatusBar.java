package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.pokeranch.game.object.Monster;

public class BattleStatusBar {
	private Monster monster;
	private int x, y;
	private TextComponent name, hp, mp;
	private boolean animating;
	private int displayHP;
	
	public BattleStatusBar(Monster m, int x, int y){
		setMonster(m);
		this.x = x;
		this.y = y;
		
		name = new TextComponent(m.getName(), x + 10, y + 10);
	}
	
	public void draw(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.argb(200, 50, 50, 50));
		canvas.drawRect(x, y, x+100, y+100, paint);
	}
	
	public void update(){
		
	}
	
	public boolean animationFinished(){
		return true;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
		displayHP = monster.getStatus().getHP();
	}
}
