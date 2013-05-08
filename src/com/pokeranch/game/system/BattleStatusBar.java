package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.pokeranch.game.object.Monster;

public class BattleStatusBar {
	private Monster monster;
	private boolean visible = true;
	private int x, y;
	private TextComponent name, hp;
	private int displayHP, maxHP;
	private int displayMP, maxMP;
	private int displayEXP, maxEXP; 
	private int fetchHP, fetchMP, dHP, dMP;
	private int totalUpdate = 40, tick = 3;
	private DelayedAction delayAction;
	private Paint paint;
	
	public BattleStatusBar(Monster m, int x, int y){
		name = new TextComponent("", x + 10, y + 10);
		hp = new TextComponent("", x + 10, y + 29);
		//mp = new TextComponent("", x + 10, y + 36);
		setMonster(m);
		this.x = x;
		this.y = y;
		paint = new Paint();
		delayAction = null;
		name.setColor(Color.WHITE);
		hp.setColor(Color.WHITE);
		//mp.setColor(Color.WHITE);
	}
	
	public void draw(Canvas canvas){
		if(!visible) return;
		
		paint.setColor(Color.argb(225, 50, 50, 50));
		canvas.drawRect(x, y, x+120, y+55, paint);
		name.draw(canvas);
		
		int percent = (int) (((float) displayHP /(float) maxHP) * 100);
		if (percent > 50) paint.setColor(Color.GREEN);
		else if(percent > 20) paint.setColor(Color.YELLOW);
		else paint.setColor(Color.RED);
		
		float per = (float) displayHP/(float) maxHP;
		if (per < 0) per = 0;
				
		canvas.drawRect(x+10, y+15, x+10+((int) (per * 100.f)), y+21, paint);
		hp.draw(canvas);
		//mp.draw(canvas);
		//paint.setColor(Color.argb(255, red, green, blue))
	}
	
	public void update(){
		if(delayAction!=null){
				delayAction.updateFrequently(tick);
				
				if(displayHP == fetchHP && displayMP == fetchMP){
					delayAction.forceFinish();
				}
				
				if(delayAction.finished()){
					displayHP = fetchHP;
					displayMP = fetchMP;
					delayAction = null;
				}
				refreshDisplay();
		}
	}
	
	private void animate(){
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
	}
	
	public boolean animationFinished(){
		return delayAction==null;
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
		displayEXP = monster.getExp();
		maxEXP = monster.getLvlExp();
		refreshDisplay();
	}
	
	public void fetchData(){
		fetchHP = monster.getStatus().getHP();
		fetchMP = monster.getStatus().getMP();
		dHP = (displayHP - fetchHP) / totalUpdate + 1;
		dMP = (displayMP - fetchMP) / totalUpdate + 1;
			
		delayAction = new DelayedAction(){
			@Override
			public void doAction() { animate();}
			@Override
			public int getDelay() {	return totalUpdate*tick;}
		};
	}
	
	public void refreshDisplay(){
		
		
		StringBuilder ss = new StringBuilder();
		ss.append("HP: " + displayHP + "/" + maxHP);
		ss.append("\nMP: ");
		ss.append(displayMP +"/" + maxMP);
		ss.append("\nEXP: ");
		ss.append(displayEXP + "/" + maxEXP);
		hp.setText(ss.toString());
		//mp.setText(ss.toString());
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
