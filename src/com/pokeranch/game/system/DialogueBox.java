package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class DialogueBox implements IScreen{
	
	private String message;
	private static DialogueBox dbox = null;
	private static Paint paint;
	private static boolean isShown;
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void setMessage(String msg){
		message = msg;
	}
	
	public static void initialize(){
		if(dbox == null){
			paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTypeface(BitmapManager.getInstance().getTypeface());
			paint.setTextSize(8);
			dbox = new DialogueBox();
		}
	}
	
	public static DialogueBox getInstance(){
		return dbox;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(BitmapManager.getInstance().get("dbox"), null, new RectF(0,180,320,240),  null);
		int curStart = 0;
		int spacing = 10;
		int nCharactersPerLine = 37;
		while(curStart < message.length()){
			int ending = (curStart/nCharactersPerLine + 1)*nCharactersPerLine;
			Log.d("harits1", curStart + " " + ending);
			canvas.drawText(message, curStart, (ending < message.length() ? ending : message.length()), 10, 200 + (curStart/nCharactersPerLine)*spacing, paint);
			curStart += nCharactersPerLine;
		}
	}

	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
		final int code = e.getAction() & MotionEvent.ACTION_MASK;	
		if(code == MotionEvent.ACTION_DOWN){
			isShown = false;
			ScreenManager.getInstance().pop();
			//kalo dia bisa kena onTouchEvent, berarti dia ada di top stack
			//pop diri sendiri
			
		}
	}

	public static boolean isShown() {
		return isShown;
	}

	public static void setShown(boolean isShown) {
		DialogueBox.isShown = isShown;
	}

}
