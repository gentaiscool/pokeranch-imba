package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Species;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

public class ScrollComponent {
	private TouchAction action;
	private Bitmap scrollTab, panel, pokedextablet, pokedexlogo;
	private BitmapButton orangebutton;
	private float x, y, curX, curY, savedY, deltaY;
	private boolean touched = false;
	private Paint paint = new Paint();
	final private Object[] species;
	
	ArrayList<BitmapButton> buttons;
	
	public ScrollComponent(Context context, int _X, int _Y) {
		// TODO Auto-generated constructor stub
		x = _X;
		y = 60;
		curX = 280;
		curY = 60;
		savedY = 0;
		
		final Typeface face = Typeface.createFromAsset(context.getAssets(),
	            "fonts/Pokemon GB.ttf");

		
		BitmapManager.getInstance().put("ic", R.drawable.ic_launcher);
		BitmapManager.getInstance().put("panel", R.drawable.transparent);
		BitmapManager.getInstance().put("orangebutton", R.drawable.orangebutton);
		BitmapManager.getInstance().put("bluebutton", R.drawable.bluebutton);
		scrollTab = BitmapManager.getInstance().get("ic");
		panel = BitmapManager.getInstance().get("panel");
		
		//scrollTab = new scrollTabButton(BitmapManager.getInstance().get("ic"),0,0);
		
		species = DBLoader.getInstance().getAllSpecies().toArray();
		
		buttons = new ArrayList<BitmapButton>();
		
		paint.setTextSize(9);
		paint.setTypeface(face);
		paint.setColor(Color.BLACK);
	}
	
	public void update(int X, int Y){
		
	}
	
	public void draw(Canvas canvas){
		curX = x+80;
		curY = y;
		canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(curX,curY,curX+170,curY+200), null);
		
		//canvas.drawBitmap(scrollTab, new Rect(0,0,scrollTab.getWidth(), scrollTab.getHeight()), new RectF(curX,curY,curX+scrollTab.getWidth(),curY+scrollTab.getHeight()), null);
		for(int i=1; i<=species.length;i++){
			Bitmap orange = BitmapManager.getInstance().get("orangebutton");
			Bitmap blue = BitmapManager.getInstance().get("bluebutton");
			orangebutton = new BitmapButton(orange,curX,curY+30*i-10);
			
			canvas.drawBitmap(blue, curX, curY+30*i-20, paint);
			buttons.add(orangebutton);
			orangebutton.draw(canvas);
			
			BitmapButton bluebutton = new BitmapButton(blue,curX,curY+30*i-20);
			bluebutton.addTouchAction(new TouchAction() {
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
			
			Species sp = (Species)species[i-1];
			if(i<10)
				canvas.drawText("00"+i+"  "+sp.getName(), curX+10, curY+30*i, paint);
			else
				canvas.drawText("0"+i+"  "+sp.getName(), curX+10, curY+30*i, paint);
		}
	}
		
	public void onTouchEvent(MotionEvent event, float magX, float magY) {
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;	
		
		RectF r = new RectF(curX*magX, curY*magY, (curX + panel.getWidth())*magX,  (curY + panel.getHeight())*magY);
		
		Log.d("test", event.getX() + " " + event.getY());
		
		if (r.contains(event.getX(), event.getY())){
			switch (actioncode) {
				case MotionEvent.ACTION_DOWN:
					//action.onTouchDown();
					//Log.d("action", "onTouchDown");
					//curX=(float) (event.getX()/magX-scrollTab.getWidth()*0.5);
					//y=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					//curY=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					savedY = event.getY();
					touched = true;
					deltaY = 0;
					break;
				case MotionEvent.ACTION_MOVE:
					//action.onTouchMove();
					//curX=(float) (event.getX()/magX-scrollTab.getWidth()*0.5);
					deltaY = (float) (event.getY()/magY-scrollTab.getWidth()*0.5)-savedY;
					y= savedY + deltaY;
					curY= savedY + deltaY;
					Log.d("action", "onTouchMove");
					break;
				case MotionEvent.ACTION_UP:
					//action.onTouchUp();
					//curX=(float) (event.getX()/magX-scrollTab.getWidth()*0.5);
					//y=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					//curY=(float) (event.getY()/magY-scrollTab.getHeight()*0.5);
					Log.d("action", "onTouchUp");
					touched = false;
					break;		
			}
		}else{
			if(touched){
				switch (actioncode) {
				case MotionEvent.ACTION_MOVE:
					//action.onTouchUp();
					Log.d("action2", "onTouchMove");
					touched = false;
					break;
				case MotionEvent.ACTION_UP:
					//action.onTouchUp();
					Log.d("action2", "onTouchUp");
					touched = false;
					break;		
				}
			}
		}
	}
}