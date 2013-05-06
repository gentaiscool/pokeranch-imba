package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.system.MessageManager.Action;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainChooseMonster implements IScreen{
	
	private Paint paint = new Paint();
	private ScreenManager manager;
	private BitmapButton transparentButton, oak, frame;
	
	private Sprite head;
	private int state = 0;
	private Matrix matrix = new Matrix();	
	float magnification;
	int curScreenWidth, curScreenHeight;
	Context curContext;
	
	private ArrayList<BitmapButton> buttons;
		@SuppressLint("NewApi")
		public MainChooseMonster(Context context, int screenWidth, int screenHeight) {
			// TODO Auto-generated constructor stub
			
			manager = ScreenManager.getInstance();
			
			curContext = context;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
				
			BitmapButton charchar = new BitmapButton(BitmapManager.getInstance().get("charchar"), 0,100);
			BitmapButton squir = new BitmapButton(BitmapManager.getInstance().get("squir"),  150, 100);
			BitmapButton bulba = new BitmapButton(BitmapManager.getInstance().get("bulba"), 300, 100);
			
			charchar.addTouchAction(new TouchAction() {
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					//AreaManager am = new AreaManager(curContext, curScreenWidth, curScreenHeight, )
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
			squir.addTouchAction(new TouchAction() {
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					
				}@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}@Override
				public void onTouchUp() {
					// TODO Auto-generated method stub
					
				}
			});
			bulba.addTouchAction(new TouchAction() {
				@Override
				public void onTouchDown() {
					// TODO Auto-generated method stub
					
				}@Override
				public void onTouchMove() {
					// TODO Auto-generated method stub
					
				}@Override
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
		}

		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			for(BitmapButton b : buttons){
				b.onTouchEvent(e, magX, magY);
			}
		}
}
