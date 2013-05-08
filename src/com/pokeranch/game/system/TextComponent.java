package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Scanner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TextComponent {
	private ArrayList<String> text;
	private static final float defaultSize = 6.3f; 
	private float fontsize;
	private Paint paint;
	private int x, y;
	private boolean drawing = false;
	
	public TextComponent(String text, int x, int y){
		this.text = new ArrayList<String>();
		setText(text);
		this.x = x;
		this.y = y;
		fontsize = defaultSize; //* (float) MainGameView.screenDensity / (float) MainGameView.standardDensity; 
		paint = new Paint();
		paint.setTextSize(fontsize);
		paint.setTypeface(BitmapManager.getInstance().getTypeface());
		paint.setColor(Color.BLACK);	
	}
	
	public synchronized void draw(Canvas canvas){
		drawing = true;
		
		int y1 = y;
		for(String t : text){
			canvas.drawText(t, x, y1, paint);
			y1+=8;
		}
		
		drawing = false;
		notifyAll();
	}
	
	public void setColor(int color){
		paint.setColor(color);
	}
	
	//getter setter

	public synchronized void setText(String text) {
		while(drawing){
			try{
				wait();
			}catch(Exception e){e.printStackTrace();}
		}
		
		this.text.clear();
		appendText(text);
	}
	
	public synchronized void appendText(String text){
		while(drawing){
			try{
				wait();
			}catch(Exception e){e.printStackTrace();}
		}
		
		Scanner scan = new Scanner(text);
		String line;
		while(scan.hasNextLine()){
			line=scan.nextLine();
			this.text.add(line);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
