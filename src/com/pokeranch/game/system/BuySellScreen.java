package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.MonsterBall;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.object.StatItem;
import com.pokeranch.game.object.TM;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;
import com.pokeranch.game.system.ScrollComponent.SelectionListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;


public class BuySellScreen implements IScreen{
	
	private ScreenManager manager;

	private Bitmap panel, trans;

	int curScreenWidth, curScreenHeight;
	private ScrollComponent scroll;
	private String[] monsterBall, statItem, tm, category;
	private TextComponent text, textMoney;
	Collection<MonsterBall> monsterBalls;
	Collection<StatItem> statItems;
	Collection<TM> tms;
	StringBuilder sbInfo = new StringBuilder();
	
	ArrayList<MonsterBall> arrMonsterBall = new ArrayList<MonsterBall>();
	ArrayList<StatItem> arrStatItem = new ArrayList<StatItem>();
	ArrayList<TM> arrTM = new ArrayList<TM>();
	
	private boolean show = false;
	
	private BitmapButton buy, sell, transparent, close;
	
	private Player player;
	
		public BuySellScreen(Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			
			player = _player;
			
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			transparent  = new BitmapButton(BitmapManager.getInstance().get("trans"),0,0);
			
			close = new BitmapButton(BitmapManager.getInstance().get("close"),200,150);
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
			
			buy = new BitmapButton(BitmapManager.getInstance().get("buybutton"),20,150);
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
			sell = new BitmapButton(BitmapManager.getInstance().get("sellbutton"),20,200);
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
