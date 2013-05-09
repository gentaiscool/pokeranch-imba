package com.pokeranch.game.system;

import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MainGameView.ButtonClick;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class PlayerMenu implements IScreen{
	
	private static String message;
	private static PlayerMenu pmenu;
	private static Paint paint;
	private static BitmapButton items, monsters, status, quit;
	
	public static void initialize(){
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTypeface(BitmapManager.getInstance().getTypeface());
		paint.setTextSize(8);
		pmenu = new PlayerMenu();
	}
	
	public static PlayerMenu getInstance(){
		return pmenu;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(BitmapManager.getInstance().get("pmenu"), null, new RectF(230,0,320,120),  null);
		int curStart = 0;
		int spacing = 15;
		int nCharactersPerLine = 10;
		while(curStart < message.length()){
			int ending = (curStart/nCharactersPerLine + 1)*nCharactersPerLine;
			Log.d("harits1", curStart + " " + ending);
			canvas.drawText(message, curStart, (ending < message.length() ? ending : message.length()), 250, 15 + (curStart/nCharactersPerLine)*spacing, paint);
			curStart += nCharactersPerLine;
		}
	}

	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
		
	}

}
