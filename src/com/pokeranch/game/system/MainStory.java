package com.pokeranch.game.system;

import com.pokeranch.game.object.PlayerSaveLoader;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

public class MainStory implements IScreen{
	
	private Paint paint = new Paint();
	private ScreenManager manager;
	private BitmapButton transparentButton, oak, frame;
	
	private int state = 0;
	private TextComponent textcomponent;
	float magnification;
	int curScreenWidth, curScreenHeight;
	Context curContext;
	
		@SuppressLint("NewApi")
		public MainStory(Context context, int screenWidth, int screenHeight) {
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			
			curContext = context;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			textcomponent = new TextComponent("", 77, 172);
			//Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Pokemon GB.ttf");
			
			paint.setTextSize(5);
			paint.setTypeface(BitmapManager.getInstance().getTypeface());
			paint.setColor(Color.BLACK);	
			
			BitmapManager.getInstance().putImage("transparent", R.drawable.transparent, screenWidth, screenHeight);
			
			frame = new BitmapButton(BitmapManager.getInstance().get("frame"),65,150);
			oak = new BitmapButton(BitmapManager.getInstance().get("professoroak"),19,60);
			transparentButton = new BitmapButton(BitmapManager.getInstance().get("transparent"),0,0);
			transparentButton.addTouchListener(new TouchListener() {
				
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
					Log.d("transparent", "tekan"+state);
					state++;
					
					if(state > 1){
						MessageManager.prompt("What is your name?", new Action() {
							@Override
							public void proceed(Object o) {
								// TODO Auto-generated method stub
								if(!o.toString().equals("")){
									//check if the name exists before
									if(PlayerSaveLoader.getInstance().isPlayerNotExist(o.toString())){
										selectFrom();
									}
									else{
										MessageManager.alert("Player name exist");
									}
									//state=-1;
								}
								else{
									
								}
							}
							
							@Override
							public void cancel() {
								// TODO Auto-generated method stub
								
							}
						}, true);
					}
				}
			});
			
		}
		
		public void selectFrom(){
			MainChooseMonster mcm = new MainChooseMonster(curContext, curScreenWidth, curScreenHeight);
			manager.push(mcm);
		}
		
		@Override
		public void update() {
			// TODO Auto-generated method stub
			//update head & body diurusi area
			//if(state==-1){
			//	selectFrom();
				
			//}
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			canvas.drawColor(Color.WHITE);
			/*for(BitmapButton b : buttons){
				b.draw(canvas);
			}
			pokeball.draw(canvas);
			
			logo.draw(canvas);*/
		
			transparentButton.draw(canvas);
			oak.draw(canvas);
			frame.draw(canvas);
			canvas.drawText("tap to continue...", 200, 0, paint);
			if(state == 0){
				/*canvas.drawText("Welcome to PokeRancher World!. ", 77, 172, paint);
				canvas.drawText("An amazing journey is waiting", 77, 180, paint);
				canvas.drawText("you.", 77, 188, paint);*/
				textcomponent.setText("Welcome to PokeRancher World!.\nAn amazing journey is waiting\nyou.");
			}
			else
				//canvas.drawText("Please introduce yourself!", 77, 172, paint);
				textcomponent.setText("Please introduce yourself!");
			textcomponent.draw(canvas);
			
		}

		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			/*for(BitmapButton b : buttons){
				b.onTouchEvent(e, mag);
			}*/
			transparentButton.onTouchEvent(e, magX, magY);
		}
}
