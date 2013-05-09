package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class PlayerMenu implements IScreen{
	
	private static PlayerMenu pmenu;
	private static Paint paint;
	
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
		
	}

	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
		
	}

}
