package com.pokeranch.game.system;

import java.util.ArrayList;

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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Pokedex implements IScreen{
	
	private Paint paint = new Paint();
	private ScreenManager manager;
	
	private Matrix matrix = new Matrix();	
	float magnification;
	Bitmap pokedextablet, pokedexlogo, panel, bluepanel;
	public enum ButtonClick {LEFT, RIGHT, UP, DOWN, OK, CANCEL, NONE};
	int curScreenWidth, curScreenHeight;
	Context curContext;
	ScrollComponent ss;
	
		@SuppressLint("NewApi")
		public Pokedex(Context context, int screenWidth, int screenHeight) {
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			//ss = new ScrollComponent(context, 100,0);
			final Typeface face = Typeface.createFromAsset(context.getAssets(),
		            "fonts/Pokemon GB.ttf");
			
			curContext = context;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			
			panel = BitmapManager.getInstance().get("panel");
			
			//BitmapManager.getInstance().put("bluepanel", R.drawable.bluepanel);
			//bluepanel = BitmapManager.getInstance().get("bluepanel");
			
			pokedextablet = BitmapManager.getInstance().get("pokedextablet");
			pokedexlogo = BitmapManager.getInstance().get("pokedexlogo");
			/*
			newgame = new BitmapButton(BitmapManager.getInstance().get("newgame"),253,66);
			loadgame = new BitmapButton(BitmapManager.getInstance().get("loadgame"),  265, 105);
			helpgame = new BitmapButton(BitmapManager.getInstance().get("helpgame"), 253, 144);
			exitbutton = new BitmapButton(BitmapManager.getInstance().get("exitbutton"), 20, 150);
			pokeball = new BitmapButton(BitmapManager.getInstance().get("pokeball"),100,32);
			logo = new BitmapButton(BitmapManager.getInstance().get("logo"),20,32);
			 */			
			paint.setTextSize(11);
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
			canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(180,60,450,340), null);
			//ss.draw(canvas);
			canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(180,0,340,60), null);
			canvas.drawBitmap(pokedexlogo, new Rect(0,0,pokedexlogo.getWidth(), pokedexlogo.getHeight()), new RectF(180,8,310,60), null);
			canvas.drawBitmap(pokedextablet, new Rect(0,0,pokedextablet.getWidth(), pokedextablet.getHeight()), new RectF(2,8,175,180), null);

			
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			
			//ss.onTouchEvent(e, magX, magY);
		}
		
		/*@Override
		public void onTouchEvent(MotionEvent e, float mag) {
			// TODO Auto-generated method stub
//			//ss.onTouchEvent(e, mag);
			for(BitmapButton b : buttons){
				b.onTouchEvent(e, mag);
			}
		}*/
}
