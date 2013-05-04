package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.system.MainGameView.ButtonClick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class AreaManager implements IScreen{
	Area curArea;
	private Sprite head, body;
	private ArrayList<BitmapButton> buttons;
	private BitmapButton buttonLeft, buttonUp, buttonDown, buttonRight;
	private int butLeftestX, butDist, butY;
	private int screenHeight, screenWidth;
	private Player curPlayer;
	private Context context;
	private Paint paint;
	AreaManager(Context con, int scw, int sch, Player p){  
		paint = new Paint();
		curPlayer = p;
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
					case 0: //up 
						x = 6*width + frame*width; y = height;
					break;
					case 1: //right
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
		butDist = (int) (BitmapManager.getInstance().get("left").getWidth()*2);
		butY = 180;
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
		//16 ~ ukuran tilenya (16x16)
		//lokasi fisik
		body.setX(p.x*16);
		body.setY(p.y*16); 
		head.setX(p.x*16-16);
		head.setY(p.y*16);
		//lokasi logika
		getCurArea().setCurCord(p);
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		curArea.update();
		//malam cupu, sementara pake ini dulu
		Log.d("harits", "" + getCurPlayer().getPlayingTime().getHour());
		if(getCurPlayer().getPlayingTime().getHour() > 18){
			paint.setColorFilter(new LightingColorFilter(0x00000000, 0));
		} else
			paint.setColorFilter(null);
		//update head & body diurusin area
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		//Log.d("harits", "drawing area...");
		curArea.drawBG(canvas);
		//asumsi udah malem
//		Path p = new Path();
//		p.addCircle(getCurArea().getCurX()*16 + 8, getCurArea().getCurY()*16 + 8, 12, Path.Direction.CCW);
//		canvas.clipPath(p);
//		canvas.drawBitmap(shade, null, new Rect(0,0,240,320), null);
		head.draw(canvas);
		body.draw(canvas);
		curArea.drawObj(canvas);
		for(BitmapButton b : buttons){
			b.draw(canvas);
		}
		//Log.d("harits", "done drawing area...");
	}

	@Override
	public void onTouchEvent(MotionEvent e, float mag) {
		// TODO Auto-generated method stub
		for(BitmapButton b : buttons){
			b.onTouchEvent(e, mag);
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

	public Player getCurPlayer() {
		return curPlayer;
	}

	public void setCurPlayer(Player curPlayer) {
		this.curPlayer = curPlayer;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
}
