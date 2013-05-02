package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.system.MainGameView.ButtonClick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

public class AreaManager implements IScreen{
	Area curArea;
	private Sprite head, body;
	private ArrayList<BitmapButton> buttons;
	private BitmapButton buttonLeft, buttonUp, buttonDown, buttonRight;
	private int butLeftestX, butDist, butY;
	private int screenHeight, screenWidth;
	Context context;
	AreaManager(Context con, int scw, int sch){		
		screenHeight = sch;
		screenWidth = scw;
		head = new Sprite(32,0, BitmapManager.getInstance().get("chara"), 2,12,3, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 6*width + frame*width; y = 0;
					break;
					case 1: //right
						x = 9*width + frame*width; y = 0;
					break;
					case 2: //down
						x = 0*width + frame*width; y = 0;
					break;
					case 3: //left
						x = 3*width + frame*width; y = 0;
					break;
				}
				//Log.d("harits", "w: " + width);
				//Log.d("harits", "x: " + x + ", y: " + y);
				return new Point(x,y);
			}
		});
		
		body = new Sprite(0,0,BitmapManager.getInstance().get("chara"), 2,12,3, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				
				int x = 0, y = 0;
				switch(direction){
					case 0: //up --dirotasi 90 derajat searah jarum jam jadi kanan
						x = 6*width + frame*width; y = height;
					break;
					case 1: //right --dst
						x = 9*width + frame*width; y = height;
					break;
					case 2: //down
						x = 0*width + frame*width; y = height;
					break;
					case 3: //left
						x = 3*width + frame*width; y = height;
					break;
				}
				//Log.d("harits", "w: " + width);
				//Log.d("harits", "x: " + x + ", y: " + y);
				return new Point(x,y);
			}
		});
		
		
		
		buttons = new ArrayList<BitmapButton>();
		butLeftestX = 25;
		butDist = BitmapManager.getInstance().get("left").getWidth()*2;
		butY = screenHeight - 60;
		buttonLeft= new BitmapButton(BitmapManager.getInstance().get("left"), butLeftestX, butY);
		buttonDown = new BitmapButton(BitmapManager.getInstance().get("down"), butLeftestX + butDist, butY);
		buttonUp = new BitmapButton(BitmapManager.getInstance().get("up"), butLeftestX + 2*butDist, butY);
		buttonRight= new BitmapButton(BitmapManager.getInstance().get("right"), butLeftestX + 3*butDist, butY);
		
		buttonDown.addTouchAction(new TouchAction(){
			@Override
			public void onTouchDown() {
				curArea.getButtonInput(ButtonClick.DOWN);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				curArea.getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonUp.addTouchAction(new TouchAction(){
			@Override
			public void onTouchDown() {
				curArea.getButtonInput(ButtonClick.UP);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				curArea.getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonLeft.addTouchAction(new TouchAction(){
			@Override
			public void onTouchDown() {
				curArea.getButtonInput(ButtonClick.LEFT);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				curArea.getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonRight.addTouchAction(new TouchAction(){
			@Override
			public void onTouchDown() {
				curArea.getButtonInput(ButtonClick.RIGHT);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				curArea.getButtonInput(ButtonClick.NONE);
			}
		});
		
		buttons.add(buttonDown);
		buttons.add(buttonUp);
		buttons.add(buttonLeft);
		buttons.add(buttonRight);
	}
	
	public void setPlayerCord(Point p){
		//32 ~ ukuran tilenya (32x32)
		body.setX(p.x*32);
		body.setY(p.y*32); 
		head.setX(p.x*32-32);
		head.setY(p.y*32);
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		curArea.update();
		//update head & body diurusi area
	}

	@Override
	public void draw(Canvas canvas, int mag) {
		// TODO Auto-generated method stub
		curArea.draw(canvas, 2);
		head.draw(canvas, 2);
		body.draw(canvas, 2);
		for(BitmapButton b : buttons){
			b.draw(canvas);
		}
	}

	@Override
	public void onTouchEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		for(BitmapButton b : buttons){
			b.onTouchEvent(e);
		}
	}
	
	public Area getCurArea(){
		return curArea;
	}
	
	public void setCurArea(Area a) {
		curArea = a;
		curArea.setAreaManager(this);
		curArea.setBody(body);
		curArea.setHead(head);
	}
}
