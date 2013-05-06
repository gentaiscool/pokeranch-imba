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
	private Bitmap background, bar;
	private SkillAnimation animation;
	private boolean animating = false;
	private int turn;
	private Bitmap poke1, poke2;
	private int geserTop = 58; //buat geser layout background ke atas
	private TextComponent message;
	private BattleStatusBar stat1, stat2;
	
	private enum BattleState {START, WAIT_INPUT, NO_INPUT, AI_MOVE, ANIMATING_SKILL, ANIMATING_HEALTH};
	
	private BattleState state;
	
	public BattleScreen(Player player1, Player player2){
		this.player1 = player1;
		this.player2 = player2;
		current = player1;
		enemy = player2;
		
		StringBuilder ss = new StringBuilder("A Wild ");
		ss.append(player2.getCurrentMonster().getName() + " Appear!");
		message = new TextComponent(ss.toString(), 10, (int) MainGameView.standardHeight - 45);
		state = BattleState.START;
		
		background = BitmapManager.getInstance().get("battle_day_land");
		bar = BitmapManager.getInstance().get("battle_bar");
		
		//load gambar
		poke1 = BitmapManager.getInstance().get(player1.getCurrentMonster().getSpecies().getName()+"_back");
		poke2 = BitmapManager.getInstance().get(player2.getCurrentMonster().getSpecies().getName()+"_front");
		
		//stat1 = new BattleStatusBar(player1.getCurrentMonster(),5,115 - geserTop);
		
		turn = 1;
		
		touch = new ArrayList<TouchListener>();
		animation = null;
		
		int buttonTop = 28;
		int buttonLeft = 60;
		int marginTop = (int) MainGameView.standardHeight - 56;
		int marginLeft = 202;
		
		attack = new BitmapButton(BitmapManager.getInstance().get("attackbutton"), marginLeft, marginTop);
		useItem = new BitmapButton(BitmapManager.getInstance().get("itembutton"), marginLeft + buttonLeft, marginTop);
		change = new BitmapButton(BitmapManager.getInstance().get("changebutton"), marginLeft, marginTop + buttonTop);
		escape = new BitmapButton(BitmapManager.getInstance().get("escapebutton"), marginLeft + buttonLeft, marginTop + buttonTop);
		
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
		if(state==BattleState.ANIMATING_SKILL){
			animation.update();
			if(animation.finished()) {
				enemy.getCurrentMonster().inflictDamage(animation.getSkill(), current.getCurrentMonster().getStatus());
				nextTurn();
			}
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		drawBackground(canvas);
		
		attack.draw(canvas);
		useItem.draw(canvas);
		change.draw(canvas);
		escape.draw(canvas);
		int x1 = 5;
		int y1 = 115 - geserTop;
		
		int x2 = 175;
		int y2 = 0;
		
		if(poke1!=null) canvas.drawBitmap(poke1,new Rect(0,0,poke1.getWidth(), poke1.getHeight()), new RectF(x1,y1,x1+poke1.getWidth()*2,y1+poke1.getHeight()*2),null);
		if(poke2!=null) canvas.drawBitmap(poke2,new Rect(0,0,poke2.getWidth(), poke2.getHeight()), new RectF(x2,y2,x2+poke2.getWidth()*2,y2+poke2.getHeight()*2),null);
		
		//stat1.draw(canvas);
		
		canvas.drawBitmap(bar, 0, (int) MainGameView.standardHeight - 58, null);
		
		message.draw(canvas);
		if(state==BattleState.ANIMATING_SKILL) animation.draw(canvas);
	}
	
	private void drawBackground(Canvas canvas){
		//draw
		Rect src = new Rect(0,geserTop - 15,background.getWidth(),background.getHeight());
		Rect dst = new Rect(0,0,(int) MainGameView.standardWidth, (int) MainGameView.standardHeight-geserTop);
		canvas.drawBitmap(background, src, dst, null);
	}
	
	
	
	private void attack(int choice){
		Skill s = current.getCurrentMonster().getSkill(choice);

		message.setText(current.getCurrentMonster().getName() + " use " + s.getName() + "!");
		animation = new SkillAnimation(s, 3, 175, 0, 4);
		state = BattleState.ANIMATING_SKILL;
	}
	
	
	
	private void nextTurn(){
		//turn = turn==1 ? 2 : 1;
		turn = 1;
		state = turn==1 ? BattleState.WAIT_INPUT : BattleState.AI_MOVE;
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
		switch(state){
		case START:
			state = BattleState.WAIT_INPUT;
		break;
		
		case WAIT_INPUT:
			for(TouchListener t : touch) t.onTouchEvent(e, magX, magY);
		break;
		
		default:
		}
	}
}
