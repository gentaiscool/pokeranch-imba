package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;
import com.pokeranch.game.object.MonsterBall;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.PlayerSaveLoader;
import com.pokeranch.game.object.StatItem;
import com.pokeranch.game.object.TM;
import com.pokeranch.game.system.BitmapButton.TouchListener;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;


public class SaveLoadScreen implements IScreen{

	int curScreenWidth, curScreenHeight;
	StringBuilder sbInfo = new StringBuilder();
	
	private BitmapButton save,load, close,sleep;
	
	private Player player;
	private Context curContext;
	
		public SaveLoadScreen(Context context, Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			
			player = _player;
			
			curContext = context;
			
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			sleep = new BitmapButton(BitmapManager.getInstance().get("sleep"),50,50);
			sleep.addTouchListener(new TouchListener() {
				
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
					player.restoreAllMonster();
				}
			});
			
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
			
			save = new BitmapButton(BitmapManager.getInstance().get("savebutton"),200,60);
			save.addTouchListener(new TouchListener() {

				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					PlayerSaveLoader.getInstance().savePlayer(player);
					MessageManager.alert("Game is saved");
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
			load = new BitmapButton(BitmapManager.getInstance().get("loadbutton"),200,100);
			load.addTouchListener(new TouchListener() {

				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					MainLoadScreen mls = new MainLoadScreen(curContext, curScreenHeight, curScreenHeight);
					ScreenManager.getInstance().push(mls);
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
			save.draw(canvas);
			load.draw(canvas);
			close.draw(canvas);
			sleep.draw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			save.onTouchEvent(e, magX, magY);
			load.onTouchEvent(e, magX, magY);
			close.onTouchEvent(e, magX, magY);
			sleep.onTouchEvent(e, magX, magY);
		}
}
