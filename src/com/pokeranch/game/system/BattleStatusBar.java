package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.pokeranch.game.object.Monster;

public class BattleStatusBar {
	private Monster monster;
	private boolean visible = true;
	private int x, y;
	private TextComponent name, hp, mp;
	private boolean animating;
	private int displayHP, maxHP;
	private int displayMP, maxMP;
	private int fetchHP, fetchMP, dHP, dMP;
	private int updateCount, totalUpdate = 40, step;
	private Paint paint;
	
	public BattleStatusBar(Monster m, int x, int y){
		name = new TextComponent("", x + 10, y + 10);
		hp = new TextComponent("", x + 10, y + 29);
		mp = new TextComponent("", x + 10, y + 36);
		setMonster(m);
		this.x = x;
		this.y = y;
		paint = new Paint();
		
		name.setColor(Color.WHITE);
		hp.setColor(Color.WHITE);
		mp.setColor(Color.WHITE);
	}
	
	public void draw(Canvas canvas){
		paint.setColor(Color.argb(225, 50, 50, 50));
		canvas.drawRect(x, y, x+120, y+55, paint);
		name.draw(canvas);
		
		int percent = (int) (((float) displayHP /(float) maxHP) * 100);
		if (percent > 50) paint.setColor(Color.GREEN);
		else if(percent > 20) paint.setColor(Color.YELLOW);
		else paint.setColor(Color.RED);
		

		//Log.d("POKE HPBAR", Integer.valueOf(percent).toString());
		
		float per = (float) displayHP/(float) maxHP;
		if (per < 0) per = 0;
		
		//Log.d("POKE HP", Float.valueOf(per).toString());
		
				
		canvas.drawRect(x+10, y+15, x+10+((int) (per * 100.f)), y+21, paint);
		hp.draw(canvas);
		mp.draw(canvas);
		//paint.setColor(Color.argb(255, red, green, blue))
	}
	
	public void update(){
		step++;
		if(step==2){
			updateCount++;
			step=0;
			if (updateCount < totalUpdate){
				displayHP-=dHP;
				displayMP-=dMP;
				
				if(displayHP < fetchHP){
					if(dHP > 0) displayHP = fetchHP;
				}else{
					if (dHP < 0) displayHP = fetchHP;
				}
				
				if(displayMP < fetchMP){
					if(dMP > 0) displayMP = fetchMP;
				}else{
					if (dMP < 0) displayMP = fetchMP;
				}
				
			}else{
				displayHP = fetchHP;
				displayMP = fetchMP;
			}
			
			refreshDisplay();
		}
	}
	
	public boolean animationFinished(){
		return updateCount>=totalUpdate;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
		refreshData();
	}
	
	public void refreshData(){
		displayHP = monster.getStatus().getHP();
		maxHP = monster.getFullStatus().getHP();
		name.setText(monster.getName() + " (Lv. " + monster.getLevel() + ")");
		displayMP = monster.getStatus().getMP();
		maxMP = monster.getFullStatus().getMP();
		refreshDisplay();
	}
	
	public void fetchData(){
		updateCount = 0;
		step = 0;
		fetchHP = monster.getStatus().getHP();
		fetchMP = monster.getStatus().getMP();
		Log.d("POKE fetHP", Integer.valueOf(fetchHP).toString());
		Log.d("POKE fetMP", Integer.valueOf(fetchMP).toString());
		dHP = (displayHP - fetchHP) / totalUpdate + 1;
		dMP = (displayMP - fetchMP) / totalUpdate + 1;
	}
	
	public void refreshDisplay(){
		StringBuilder ss = new StringBuilder();
		ss.append("HP: " + displayHP + "/" + maxHP);
		hp.setText(ss.toString());
		ss = new StringBuilder();
		ss.append("MP: ");
		ss.append(displayMP +"/" + maxMP);
		mp.setText(ss.toString());
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
