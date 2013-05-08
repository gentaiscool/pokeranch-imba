package com.pokeranch.game.system;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.pokeranch.game.object.*;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;

public class BattleScreen implements IScreen {
	//gui
	private BitmapButton attack, useItem, change, escape;
	private ArrayList<Touchables> touch;
	private Bitmap background, bar;
	private SkillAnimation animation;
	private Bitmap poke1, poke2;
	private int x1, y1, x2, y2; //posisi gambar avatar pokemon
	private int _x1, _y1, _x2, _y2; //posisi asli gambar
	private int geserTop = 58; //buat geser layout background ke atas
	private TextComponent message;
	private BattleStatusBar stat1, stat2;
	
	//system
	public enum BattleMode {WILD, STADIUM, PVP};
	private enum BattleState {START, WAIT_INPUT, NO_INPUT, AI_MOVE, ANIMATING_SKILL, ANIMATING_HEALTH, ANIMATING_MATI, MATI, END};
	
	private Player player1, player2, current, enemy;
	private int turn; //player no berapa yg sedang jalan
	private BattleMode mode;
	private BattleState state;

	private DelayedAction delayAction = null, dieAnim = null;
	
	public BattleScreen(Player player1, Player player2, BattleMode mode){
		this.player1 = player1;
		this.player2 = player2;
		this.mode = mode;
		current = this.player1;
		enemy = this.player2;
		
		if (mode == BattleMode.WILD){
			StringBuilder ss = new StringBuilder("A Wild ");
			ss.append(player2.getCurrentMonster().getName() + " Appear!");
			message = new TextComponent(ss.toString(), 10, (int) MainGameView.standardHeight - 45);
		}else{
			StringBuilder ss = new StringBuilder("Trainer ");
			ss.append(player2.getName() + " Challenge You!");
			message = new TextComponent(ss.toString(), 10, (int) MainGameView.standardHeight - 45);
		}
		
		state = BattleState.START;
		turn = 1;
		
		//init gui
		
		_x1 = 5;
		_y1 = 115 - geserTop;
		
		_x2 = 175;
		_y2 = 0;
		
		x1 = _x1;
		y1 = _y1;
		
		x2 = _x2;
		y2 = _y2;
		
		refreshImage();
		
		background = BitmapManager.getInstance().get("battle_day_land");
		bar = BitmapManager.getInstance().get("battle_bar");
		
		stat1 = new BattleStatusBar(player1.getCurrentMonster(),(int) MainGameView.standardWidth - 125,(int) MainGameView.standardHeight - 60 - geserTop);
		stat2 = new BattleStatusBar(player2.getCurrentMonster(),10,10);
		
		touch = new ArrayList<Touchables>();
		animation = null;
		
		int buttonTop = 28;
		int buttonLeft = 60;
		int marginTop = (int) MainGameView.standardHeight - 56;
		int marginLeft = 202;
		
		attack = new BitmapButton(BitmapManager.getInstance().get("attackbutton"), marginLeft, marginTop);
		useItem = new BitmapButton(BitmapManager.getInstance().get("itembutton"), marginLeft + buttonLeft, marginTop);
		change = new BitmapButton(BitmapManager.getInstance().get("changebutton"), marginLeft, marginTop + buttonTop);
		escape = new BitmapButton(BitmapManager.getInstance().get("escapebutton"), marginLeft + buttonLeft, marginTop + buttonTop);
		
		initListener();
	}
	
	@Override
	public void update() {
		if(delayAction!=null){
			delayAction.update();
			if (delayAction.finished()){
				delayAction=null;
			}
		}
		
		if(dieAnim!=null){
			dieAnim.updateFrequently(3);
			if (dieAnim.finished()){
				dieAnim=null;
				x1=_x1;
				x2=_x2;
				stat1.setVisible(true);
				stat2.setVisible(true);				
				state = BattleState.MATI;
			}
		}
		
		switch(state){
		case ANIMATING_SKILL:
			animation.update();
			if(animation.finished()) {
				enemy.getCurrentMonster().inflictDamage(animation.getSkill(), current.getCurrentMonster());
				current.getCurrentMonster().updateStatusBy(animation.getSkill().getCost());
				stat1.fetchData();
				stat2.fetchData();
				state = BattleState.ANIMATING_HEALTH;
			}
		break;
		case ANIMATING_HEALTH:
			stat1.update();
			stat2.update();
			if(stat1.animationFinished() && stat2.animationFinished())
				if(!cekmati()) nextTurn();
		break;
		case MATI:
			try{
				Monster m = enemy.getCurrentMonster();
				message.setText(m.getName() + " fainted.");
				enemy.setCurrentMonster(enemy.getNextMonster());
				refreshImage();
				if(turn==1){
					stat2.setMonster(enemy.getCurrentMonster());
					message.appendText("Enemy use " + enemy.getCurrentMonster().getName());
				}else{
					stat1.setMonster(enemy.getCurrentMonster());
					message.appendText("You use " + enemy.getCurrentMonster().getName());
				}
				
				nextTurn();
			}catch (Exception e){
				state = BattleState.END;
				endBattle();
			}
		break;
		default:
		}
	}
	
	private void endBattle(){
		if(turn==1) 
			message.appendText("Enemy Lose!");
		else
			message.appendText("You Lose!");
	}
	
	private void attack(Skill sk){
		int x = turn==1? x2 : x1;
		int y = turn==1? y2 : y1;
		message.appendText(current.getCurrentMonster().getName() + " use " + sk.getName() + "!");
		animation = new SkillAnimation(sk, 4, x, y, 4);
		state = BattleState.ANIMATING_SKILL;
	}
	
	private void attack(int choice){
		Skill sk = current.getCurrentMonster().getSkill(choice);
		attack(sk);
	}
	
	//turn system
	
	private boolean cekmati(){
		//mati : currentmonster hpnya 0
		if( enemy.getCurrentMonster().getStatus().getHP()<=0){
			state = BattleState.ANIMATING_MATI;
			
			if(turn==2){
				stat1.setVisible(false);
			}else{
				stat2.setVisible(false);
			}
			
			dieAnim = new PokeOut();
			
			return true;
		}else{
			return false;
		}
	}
	
	private class PokeOut extends DelayedAction{
		@Override
		public int getDelay() {
			return 60;
		}
		@Override
		public void doAction() {
			if(turn==2){
				x1-=10;
			}else{
				x2+=10;
			}
		}
	}
	
	private void nextTurn(){
		turn = turn==1 ? 2 : 1;
		
		if(mode==BattleMode.PVP)
			message.setText("Player " + turn + " turn");
		else
			message.setText(turn==1? "Your turn" : "Enemy Turn");
		
		Player temp = current;
		current = enemy;
		enemy = temp;
		
		Log.d("POKE",player2.getCurrentMonster().getStatus() + "/" + player2.getCurrentMonster().getFullStatus());
		Log.d("POKE update", "update");
		if(turn==2) {
			if(mode==BattleMode.PVP) {
				state = BattleState.WAIT_INPUT;
			}
			else {
				state = BattleState.AI_MOVE;
				delayAction = new DelayedAction(){
					@Override
					public void doAction() {player2Turn();}
					@Override
					public int getDelay() {return 35;}
				};
			}
		} else {
			state = BattleState.WAIT_INPUT;
		}
	}
	
	private void player2Turn(){
		attack(player2.getCurrentMonster().getRandomSkill());
	}
	
	//draw
	
	private void refreshImage(){
		poke1 = BitmapManager.getInstance().get(player1.getCurrentMonster().getSpecies().getName()+"_back");
		poke2 = BitmapManager.getInstance().get(player2.getCurrentMonster().getSpecies().getName()+"_front");
	}
	
	
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		drawBackground(canvas);
		
		attack.draw(canvas);
		useItem.draw(canvas);
		change.draw(canvas);
		escape.draw(canvas);
		
		if(poke1!=null) canvas.drawBitmap(poke1,new Rect(0,0,poke1.getWidth(), poke1.getHeight()), new RectF(x1,y1,x1+poke1.getWidth()*2,y1+poke1.getHeight()*2),null);
		if(poke2!=null) canvas.drawBitmap(poke2,new Rect(0,0,poke2.getWidth(), poke2.getHeight()), new RectF(x2,y2,x2+poke2.getWidth()*2,y2+poke2.getHeight()*2),null);
		
		stat1.draw(canvas);
		stat2.draw(canvas);
		
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
	
	// aksi-aksi tombol

	private void selectAttack(){	
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
		Log.d("POKE", "item");
	}
	
	private void changeMonster(){
		Log.d("POKE", "change");
	}
	
	private void tryEscape(){
		Log.d("POKE", "escape");
	}
	
	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		switch(state){
		case START:
			state = BattleState.WAIT_INPUT;
		break;
		
		case WAIT_INPUT:
			for(Touchables t : touch) t.onTouchEvent(e, magX, magY);
		break;
		
		default:
		}
	}
	
	public void initListener(){
		attack.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {selectAttack();}
			@Override
			public void onTouchMove() {}
			@Override
			public void onTouchDown() {}
		});
		
		useItem.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {selectItem();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		change.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {changeMonster();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		escape.addTouchListener(new TouchListener() {
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
}
