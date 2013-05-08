package com.pokeranch.game.system;

import java.util.ArrayList;

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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.ScrollComponent.SelectionListener;

public class MainMenu implements IScreen{
	
	private Paint paint = new Paint();
	private ScreenManager manager;
	private BitmapButton newgame, loadgame, helpgame,exitbutton,pokeball,logo;
	private ArrayList<BitmapButton> buttons;
	
	private Sprite head;
	
	private Matrix matrix = new Matrix();	
	float magnification;
	public enum ButtonClick {LEFT, RIGHT, UP, DOWN, OK, CANCEL, NONE};
	int curScreenWidth, curScreenHeight;
	Context curContext;
	 
		@SuppressLint("NewApi")
		public MainMenu(Context context, int screenWidth, int screenHeight) {
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();

			curContext = context;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			newgame = new BitmapButton(BitmapManager.getInstance().get("newgame"),253,66);
			loadgame = new BitmapButton(BitmapManager.getInstance().get("loadgame"),  265, 105);
			helpgame = new BitmapButton(BitmapManager.getInstance().get("helpgame"), 253, 144);
			exitbutton = new BitmapButton(BitmapManager.getInstance().get("exitbutton"), 20, 150);
			pokeball = new BitmapButton(BitmapManager.getInstance().get("pokeball"),100,32);
			logo = new BitmapButton(BitmapManager.getInstance().get("logo"),20,32);
			 
			logo.addTouchListener(new TouchListener() {
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
					
				}
			});
			newgame.addTouchListener(new TouchListener() {
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
					MainStory ms = new MainStory(curContext, curScreenHeight, curScreenHeight);
					manager.push(ms);
				}
			});
			
			loadgame.addTouchListener(new TouchListener() {
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
					
				}
			});
			
			helpgame.addTouchListener(new TouchListener() {
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
					
				}
			});
			
			exitbutton.addTouchListener(new TouchListener() {
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
					
				}
			});
			
			//Log.d("hehe",newgame.toString());
			BitmapButton buttonLeft = new BitmapButton(BitmapManager.getInstance().get("left"), 25,180);
			buttons = new ArrayList<BitmapButton>();
			buttons.add(newgame);
			buttons.add(loadgame);
			buttons.add(helpgame);
			buttons.add(exitbutton);
			
			paint.setTextSize(40);
			paint.setTypeface(Typeface.MONOSPACE);
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
			pokeball.draw(canvas);
			
			logo.draw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			for(BitmapButton b : buttons){
				b.onTouchEvent(e, magX, magY);
			}
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
