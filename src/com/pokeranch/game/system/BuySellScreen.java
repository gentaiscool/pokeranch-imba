package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;
import com.pokeranch.game.object.MonsterBall;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.StatItem;
import com.pokeranch.game.object.TM;
import com.pokeranch.game.system.BitmapButton.TouchListener;

import android.graphics.Canvas;
import android.view.MotionEvent;


public class BuySellScreen implements IScreen{

	int curScreenWidth, curScreenHeight;
	Collection<MonsterBall> monsterBalls;
	Collection<StatItem> statItems;
	Collection<TM> tms;
	StringBuilder sbInfo = new StringBuilder();
	
	ArrayList<MonsterBall> arrMonsterBall = new ArrayList<MonsterBall>();
	ArrayList<StatItem> arrStatItem = new ArrayList<StatItem>();
	ArrayList<TM> arrTM = new ArrayList<TM>();
	
	private BitmapButton buy, sell, close;
	
	private Player player;
	
		public BuySellScreen(Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			
			player = _player;
			
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			close = new BitmapButton(BitmapManager.getInstance().get("close"),200,140);
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
			
			buy = new BitmapButton(BitmapManager.getInstance().get("buybutton"),200,60);
			buy.addTouchListener(new TouchListener() {

				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					BuyScreen buyScreen = new BuyScreen(player, curScreenHeight, curScreenHeight);
					ScreenManager.getInstance().push(buyScreen);
				}

				@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}
			});
			sell = new BitmapButton(BitmapManager.getInstance().get("sellbutton"),200,100);
			sell.addTouchListener(new TouchListener() {

				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					SellScreen sellScreen = new SellScreen(player, curScreenHeight, curScreenHeight);
					ScreenManager.getInstance().push(sellScreen);
				}

				@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		@Override
		public void update() {
			//nothing todo here
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			//canvas.drawColor(Color.WHITE);
			//transparent.draw(canvas);
			buy.draw(canvas);
			sell.draw(canvas);
			close.draw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			buy.onTouchEvent(e, magX, magY);
			sell.onTouchEvent(e, magX, magY);
			close.onTouchEvent(e, magX, magY);
		}
}
