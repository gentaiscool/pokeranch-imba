package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Monster;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainChooseMonster implements IScreen{
	
	private Paint paint = new Paint();
	private BitmapButton transparentButton, oak;
	private Bitmap dbox;
	private TextComponent message;
	private Sprite head;
	private int state = 0;
	private Matrix matrix = new Matrix();	
	float magnification;
	int curScreenWidth, curScreenHeight;
	Context curContext;
	
	String curName;
	
	private ArrayList<BitmapButton> buttons;
		@SuppressLint("NewApi")
		public MainChooseMonster(String name, Context context, int screenWidth, int screenHeight) {
			// TODO Auto-generated constructor stub
			
			curName = name;

			curContext = context;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			dbox = BitmapManager.getInstance().get("dbox");	
			BitmapButton charchar = new BitmapButton(BitmapManager.getInstance().get("Charchar"), 10,50);
			BitmapButton squir = new BitmapButton(BitmapManager.getInstance().get("Squir"),  110, 50);
			BitmapButton bulba = new BitmapButton(BitmapManager.getInstance().get("Bulba"), 220, 50);
			message = new TextComponent("Which Monster would you choose as your first \npartner?", 15, 185);
			charchar.addTouchListener(new TouchListener() {
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					//AreaManager am = new AreaManager(curContext, curScreenWidth, curScreenHeight, )
					Species s = DBLoader.getInstance().getSpecies("Charchar");
					setMonsterName(s);
				}
				@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}
			});
			squir.addTouchListener(new TouchListener() {
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					Species s = DBLoader.getInstance().getSpecies("Squir");
					setMonsterName(s);
				}@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}
			});
			bulba.addTouchListener(new TouchListener() {
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					Species s = DBLoader.getInstance().getSpecies("Bulba");
					setMonsterName(s);
				}
				@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}
			});
			buttons = new ArrayList<BitmapButton>();
			buttons.add(charchar);
			buttons.add(squir);
			buttons.add(bulba);
			
			Typeface face = Typeface.createFromAsset(context.getAssets(),
		            "fonts/Pokemon GB.ttf");
			
			paint.setTextSize(8);
			paint.setTypeface(face);
			paint.setColor(Color.BLACK);	

		}
		
		public void setMonsterName(final Species s){
			MessageManager.prompt("Give your starter monster a name", new Action() {
				
				@Override
				public void proceed(Object o) {
					// TODO Auto-generated method stub
					if(o.toString().length()==0){
						MessageManager.alert("Input is expected");
					}
					else{
						Monster m = new Monster(o.toString(), s, 5);
						Player newPlayer = new Player();
						newPlayer.setName(curName);
						newPlayer.addMonster(m);
						newPlayer.setCurrentMonster(m);
						
						AreaManager am = new AreaManager(curContext, curScreenWidth, curScreenHeight, newPlayer);
						//Log.d("harits3","di MainGameView, r c: " +  DBLoader.getInstance().getArea("FIELD").getRow() + " " + DBLoader.getInstance().getArea("FIELD").getColumn());
						am.setCurArea(DBLoader.getInstance().getArea("HOME"));
						am.setPlayerCord(new Point(8,5));
						ScreenManager.getInstance().push(am);
					}
				}
				
				@Override
				public void cancel() {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		@Override
		public void update() {
			// TODO Auto-generated method stub
			//update head & body diurusi area
			
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			canvas.drawColor(Color.WHITE);
			for(BitmapButton b : buttons){
				b.draw(canvas);
			}
			canvas.drawText("Charchar", 30, 20, paint);
			canvas.drawText("Squir", 135, 20, paint);
			canvas.drawText("Bulba", 240, 20, paint);
			canvas.drawBitmap(dbox, new Rect(0,0, dbox.getWidth(),dbox.getHeight()),new RectF(0,160,320,240), null);
			message.draw(canvas);
		}

		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			for(BitmapButton b : buttons){
				b.onTouchEvent(e, magX, magY);
			}
		}
}
