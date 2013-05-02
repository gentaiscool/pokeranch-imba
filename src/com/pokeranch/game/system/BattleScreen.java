package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import com.pokeranch.game.object.*;

public class BattleScreen implements IScreen {
	private Player player1, player2, current, enemy;
	private BitmapButton attack, useItem, change, escape;
	private ArrayList<TouchListener> touch;
	private ArrayList<SkillAnimation> animation;
	
	public BattleScreen(Player player1, Player player2){
		this.player1 = player1;
		this.player2 = player2;
		current = player1;
		enemy = player2;
		
		touch = new ArrayList<TouchListener>();
		animation = new ArrayList<SkillAnimation>();
		
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
		// TODO Auto-generated method stub
		Iterator<SkillAnimation> it = animation.iterator();
		while(it.hasNext()){
			SkillAnimation a = it.next();
			a.update();
			if(a.finished()) animation.remove(a);
		}
	}

	
	
	@Override
	public void draw(Canvas canvas, int mag) {
		canvas.drawColor(Color.WHITE);
		attack.draw(canvas);
		useItem.draw(canvas);
		change.draw(canvas);
		escape.draw(canvas);
		for(SkillAnimation a : animation) a.draw(canvas, mag);
	}
	
	private void selectAttack(){
		//Log.d("POKE", "attack");
		animation.add(new SkillAnimation(BitmapManager.getInstance().get("Swim"), 3, 200, 100, 4));
	}
	
	private void selectItem(){
		Log.d("POKE", "item");
	}
	
	private void changeMonster(){
		Log.d("POKE", "change");
	}
	
	private void tryEscape(){
		Log.d("POKE", "escape");
	}
	
	@Override
	public void onTouchEvent(MotionEvent e) {
		for(TouchListener t : touch) t.onTouchEvent(e);
	}
}
