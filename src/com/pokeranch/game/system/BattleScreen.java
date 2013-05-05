package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.pokeranch.game.object.*;
import com.pokeranch.game.system.MessageManager.Action;

public class BattleScreen implements IScreen {
	private Player player1, player2, current, enemy;
	private BitmapButton attack, useItem, change, escape;
	private ArrayList<TouchListener> touch;
	private SkillAnimation animation;
	private boolean animating = false;
	private int turn;
	private Bitmap poke1, poke2;
	
	public BattleScreen(Player player1, Player player2){
		this.player1 = player1;
		this.player2 = player2;
		current = player1;
		enemy = player2;
		
		//load gambar
		poke1 = BitmapManager.getInstance().get(player1.getCurrentMonster().getSpecies().getName()+"_back");
		poke2 = BitmapManager.getInstance().get(player2.getCurrentMonster().getSpecies().getName()+"_front");
		
		//Log.d("POKE", player1.getCurrentMonster().getSpecies().getName()+"_back");
		
		turn = 1;
		
		touch = new ArrayList<TouchListener>();
		animation = null;
		
		attack = new BitmapButton(BitmapManager.getInstance().get("attackbutton"), 0, 0);
		useItem = new BitmapButton(BitmapManager.getInstance().get("itembutton"), 0, 70);
		change = new BitmapButton(BitmapManager.getInstance().get("changebutton"), 0, 140);
		escape = new BitmapButton(BitmapManager.getInstance().get("escapebutton"), 0, 210);
		
		attack.addTouchAction(new TouchAction() {
			@Override
			public void onTouchUp() {selectAttack();}
			@Override
			public void onTouchMove() {}
			@Override
			public void onTouchDown() {}
		});
		
		useItem.addTouchAction(new TouchAction() {
			@Override
			public void onTouchUp() {selectItem();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		change.addTouchAction(new TouchAction() {
			@Override
			public void onTouchUp() {changeMonster();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		escape.addTouchAction(new TouchAction() {
			@Override
			public void onTouchUp() {tryEscape();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		touch.add(attack);
		touch.add(useItem);
		touch.add(change);
		touch.add(escape);
	}
	
	@Override
	public void update() {
		if(animation!=null){
			animation.update();
			if(animation.finished()) {
				//StringBuilder s = new StringBuilder();
				//s.append((enemy==null) + " ");
				//s.append(b)
				//Log.d("POKE", s.toString());
				enemy.getCurrentMonster().inflictDamage(animation.getSkill(), current.getCurrentMonster().getStatus());
				animation=null;
				nextTurn();
			}
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		attack.draw(canvas);
		useItem.draw(canvas);
		change.draw(canvas);
		escape.draw(canvas);
		int x1 = 200;
		int y1 = 100;
		
		int x2 = 400;
		int y2 = 100;
		
		if(poke1!=null) canvas.drawBitmap(poke1,new Rect(0,0,poke1.getWidth(), poke1.getHeight()), new RectF(x1,y1,x1+poke1.getWidth()*2,y1+poke1.getHeight()*2),null);
		if(poke2!=null) canvas.drawBitmap(poke2,new Rect(0,0,poke2.getWidth(), poke2.getHeight()), new RectF(x2,y2,x2+poke2.getWidth()*2,y2+poke2.getHeight()*2),null);
		if(animation!=null) animation.draw(canvas);
	}
	
	
	
	private void attack(int choice){
		Skill s = current.getCurrentMonster().getSkill(choice);
		animation = new SkillAnimation(s, 3, 200, 100, 4);
		animating = true;
	}
	
	
	
	private void nextTurn(){
		//turn = turn==1 ? 2 : 1;
		//Player temp = current;
		//current = enemy;
		//enemy = temp;
		animating = false;
		Log.d("POKE",player2.getCurrentMonster().getStatus() + "/" + player2.getCurrentMonster().getFullStatus());
	}
	
	
	//terkait human player
	private void selectAttack(){
		if(animating) return;
		
		String[] selects = new String[4];  
				
		current.getCurrentMonster().getAllSkill().keySet().toArray(selects);// .toArray();// new String[4];
				
		MessageManager.singleChoice("Select a skill to attack", selects, new Action(){
			@Override
			public void proceed(Object o) {
				attack(((Integer) o).intValue());
			}
			@Override
			public void cancel() {}
			
		});
	}
	
	private void selectItem(){
		if(animating) return;
		Log.d("POKE", "item");
	}
	
	private void changeMonster(){
		if(animating) return;
		Log.d("POKE", "change");
	}
	
	private void tryEscape(){
		if(animating) return;
		Log.d("POKE", "escape");
	}
	
	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		for(TouchListener t : touch) t.onTouchEvent(e, magX, magY);
	}
}
