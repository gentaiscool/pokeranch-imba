package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Monster;
import com.pokeranch.game.object.MonsterBall;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.object.StatItem;
import com.pokeranch.game.object.TM;
import com.pokeranch.game.system.BattleScreen.BattleListener;
import com.pokeranch.game.system.BattleScreen.BattleMode;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;
import com.pokeranch.game.system.ScrollComponent.SelectionListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;


public class Stadium implements IScreen{
	
	private ScreenManager manager;

	private Bitmap panel, trans;

	int curScreenWidth, curScreenHeight;
	private TextComponent text, textMoney;
	StringBuilder sbInfo = new StringBuilder();
	
	ArrayList<MonsterBall> arrMonsterBall = new ArrayList<MonsterBall>();
	ArrayList<StatItem> arrStatItem = new ArrayList<StatItem>();
	ArrayList<TM> arrTM = new ArrayList<TM>();
	
	private Monster newMonster = new Monster();
	
	private boolean show = false;
	
	private BitmapButton bet, close;
	
	private Player player;
	
		public Stadium(final AreaManager am, Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			player = _player;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			close = new BitmapButton(BitmapManager.getInstance().get("close"),100,140);
			close.addTouchListener(new TouchListener() {
				
				@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					ScreenManager.getInstance().pop();
				}
			});
			
			bet = new BitmapButton(BitmapManager.getInstance().get("bet"),100,100);
			bet.addTouchListener(new TouchListener() {
				
				@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					MessageManager.prompt("Give your bet", new Action() {
						
						@Override
						public void proceed(Object o) {
							// TODO Auto-generated method stub
							
							try{
								Integer.parseInt(o.toString());
							}catch(Exception e){
								return;
							}
							
							if(isInt(o.toString())){
								//check money
								
								if(player.getMoney() < Integer.parseInt(o.toString())){
									//uang ga cukup
									MessageManager.alert("Your bet is higher than your money");
								}
								else if(Integer.parseInt(o.toString())==0){
									MessageManager.alert("You bet nothing");
								}
								else{
									//uang cukup
									player.setMoney(player.getMoney()-Integer.parseInt(o.toString())); //kurangin uang
									
									//new opponent
									Player opponent = new Player();
									opponent.setMoney(Integer.parseInt(o.toString())*2);
									Random random = new Random();
									
									//new monster
									Integer number = Integer.parseInt(o.toString())/100;
									if(number == 0) number = 1;
									
									Monster newMonsterOpponent = new Monster("",DBLoader.getInstance().getRandomSpecies(random.nextInt(10)+1),random.nextInt(number)+1);
									newMonsterOpponent.setName(newMonsterOpponent.getSpecies().getName()+1);
									
									opponent.addMonster(newMonsterOpponent);  
									opponent.setCurrentMonster(newMonsterOpponent);
									
									BattleScreen bs = new BattleScreen(player, opponent, BattleMode.STADIUM, new BattleListener(){
										@Override 
										public void action(int result) {
											//result = 1 -> win, result = -1 -> lose
											if(result == 1){
												
											}
											else if(result == -1){
												am.setCurArea(DBLoader.getInstance().getArea("HOME"));
												am.setPlayerCord(new Point(8,5));
												MessageManager.alert("You lost in battle");
												show = true;
												player.restoreAllMonster();
											}
											ScreenManager.getInstance().pop();
										}
										
									});
									ScreenManager.getInstance().push(bs);
								}
									
							}
							else{
								MessageManager.alert("The input is not a valid number");
							}
						}
						
						@Override
						public void cancel() {
							// TODO Auto-generated method stub
							
						}
					});
				}
			});
			
			
		}
				
		public boolean isInt(String s)  // assuming integer is in decimal number system
		{
			for(int a=0;a<s.length();a++)
			{
				if(a==0 && s.charAt(a) == '-') continue;
				if( !Character.isDigit(s.charAt(a)) ) return false;
			}
			return true;
		}
		
		@Override
		public void update() {
			//nothing todo here
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			bet.draw(canvas);
			close.draw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			bet.onTouchEvent(e, magX, magY);
			close.onTouchEvent(e, magX, magY);
		}
}
