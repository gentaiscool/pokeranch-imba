package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class ScrollComponent {
	
	private SelectionListener listener = null;
	private boolean touched;
	private boolean scroll = false;
	
	private TextComponent[] text;
	private float touchY;
	private int x;
	private int selection;
	private int nitem;
	private int width, screenHeight, selHeight;
	private float top;
	
	//layouts
	private Paint paint;
	private int defaultColor, defaultColor2, selectionColor;
	private int leftMargin;
	
	public interface SelectionListener{
		public void selectAction(int selection);
	}
	
	public ScrollComponent(String[] items, int x, int width, int screenHeight, SelectionListener listener){
		this.screenHeight = screenHeight;
		setSelectionHeight(30);
		top = 0;
		this.listener = listener;
		this.width = width;
		paint = new Paint();
		paint.setColor(Color.BLACK);
		selection = -1;
		text = new TextComponent[items.length];
		leftMargin = 10;
		for(int i = 0; i < items.length; i++){
			text[i] = new TextComponent(items[i], x + leftMargin, selHeight*i + selHeight/2);
		}
		nitem = text.length;
		
		defaultColor = Color.RED;
		defaultColor2 = Color.YELLOW;
		selectionColor = Color.GREEN;
		
	}
	
	public void draw(Canvas canvas){
		canvas.drawRect(x, 0, x+width, screenHeight, paint);
		int y;
		for(int i = 0; i < nitem; i++){
			y = selHeight*i + (int) top;
			paint.setColor(selection==i ? selectionColor : i%2==0 ? defaultColor : defaultColor2);
			canvas.drawRect(x, y, x+width, y+selHeight, paint);
			text[i].setY(y + selHeight/2);
			text[i].draw(canvas);
		}
	}
	
	public void onTouchEvent(MotionEvent event, float magX, float magY){
		final int actioncode = event.getAction() & MotionEvent.ACTION_MASK;	
		RectF r = new RectF(x*magX, 0, (x + width)*magX, screenHeight);
		
		float nowX = event.getX(), nowY = event.getY();
		
		if (r.contains(nowX, nowY)){
			switch (actioncode) {
				case MotionEvent.ACTION_DOWN:
					touched = true;
					scroll = false;
					touchY = nowY;
				break;
				case MotionEvent.ACTION_MOVE:
					//kalo dipencet dan digeser cukup jauh (setengah ukuran sel)
					if (touched && Math.abs(nowY - touchY) > selHeight / 2){
						scroll = true;
						
						//kalo jumlah item lebih dari yang ditampikan
						if(nitem > screenHeight/(magY * selHeight)){
								//update posisi top
								top += (nowY < touchY ? -1 : 1) * selHeight / 2.5;
								//mentok diatas dan dibawah
								if(top > 0) top = 0;
								else if(top < screenHeight/magY - nitem * selHeight) top = screenHeight/magY - nitem * selHeight;
								//biar alus
								touchY = nowY;
						}
					}

				break;
				case MotionEvent.ACTION_UP:
					if(touched){
						touched = false;
						
						//kalo scrollnya mati, artinya tekan
						if(!scroll && listener!=null){
							int posY = (int) (nowY/magY - top);							
							selection = posY / (int)(selHeight);
							listener.selectAction(selection);
						}
					}
				break;		
			}
		}else if(touched){
			touched = false;
		}
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int height) {
		this.screenHeight = height;
	}

	public int getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(int defaultColor) {
		this.defaultColor = defaultColor;
	}

	public int getSelectionColor() {
		return selectionColor;
	}

	public void setSelectionColor(int selectionColor) {
		this.selectionColor = selectionColor;
	}

	public int getSelectionHeight() {
		return selHeight;
	}

	public void setSelectionHeight(int selHeight) {
		this.selHeight = selHeight;
	}

	public int getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
		for(int i = 0; i < nitem; i++){
			text[i].setX(x + leftMargin);
		}
	}
}