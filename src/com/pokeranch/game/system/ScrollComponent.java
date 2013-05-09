package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
	private int defaultColor1, defaultColor2, selectionColor, emptyColor;
	private int leftMargin;
	
	public interface SelectionListener{
		public void selectAction(int selection);
	}
	
	public ScrollComponent(String[] items, int x, int width, int screenHeight, SelectionListener listener){
		this.screenHeight = screenHeight;
		setSelectionHeight(30);
		top = 0;
		this.listener = listener;
		this.x = x;
		this.width = width;
		paint = new Paint();
		
		selection = -1;
		text = new TextComponent[items.length];
		leftMargin = 5;
		for(int i = 0; i < items.length; i++){
			text[i] = new TextComponent(items[i], x + leftMargin, selHeight*i + selHeight/2);
		}
		nitem = text.length;
		
		defaultColor1 = Color.argb(255,255,190,0);
		defaultColor2 = Color.argb(255,255,230,0);
		selectionColor = Color.argb(255,255,125,0);
		emptyColor = Color.argb(255, 50, 50, 50);
		
	}
	
	public void draw(Canvas canvas){
		paint.setColor(emptyColor);
		canvas.drawRect(x, 0, x+width, screenHeight, paint);
		int y;
		for(int i = 0; i < nitem; i++){
			y = selHeight*i + (int) top;
			paint.setColor(selection==i ? selectionColor : i%2==0 ? defaultColor1 : getDefaultColor2());
			text[i].setY(y + selHeight/2);
			
			canvas.drawRect(x, y, x+width, y+selHeight, paint);
			text[i].draw(canvas);
		}
	}
	
	public int getX(){
		return x;
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
							
							if (selection < 0 || selection >= nitem) selection = -1;
							
							if(selection!=-1)
							listener.selectAction(selection);
						}
					}
				break;		
			}
		}else if(touched){
			touched = false;
		}
	}
	
	public void setItem(String[] items){
		selection = -1;
		text = new TextComponent[items.length];
		leftMargin = 5;
		for(int i = 0; i < items.length; i++){
			text[i] = new TextComponent(items[i], x + leftMargin, selHeight*i + selHeight/2);
		}
		nitem = text.length;	
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int height) {
		this.screenHeight = height;
	}

	public int getDefaultColor1() {
		return defaultColor1;
	}

	public void setDefaultColor1(int defaultColor1) {
		this.defaultColor1 = defaultColor1;
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

	public int getEmptyColor() {
		return emptyColor;
	}

	public void setEmptyColor(int emptyColor) {
		this.emptyColor = emptyColor;
	}

	public int getDefaultColor2() {
		return defaultColor2;
	}

	public void setDefaultColor2(int defaultColor2) {
		this.defaultColor2 = defaultColor2;
	}
}