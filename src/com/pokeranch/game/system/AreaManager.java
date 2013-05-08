package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MainGameView.ButtonClick;
import com.pokeranch.game.system.Sprite.SpriteCounter;

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
	private BitmapButton buttonLeft, buttonUp, buttonDown, buttonRight, buttonA;
	private int butLeftestX, butDist, butY;
	private int screenHeight, screenWidth;
	private Player curPlayer;
	private Context context;
	private TextComponent jam;
	private String AMPM="";
	private Paint paint;
	private Paint paintkotak;
	public final int dirX[] = {-1, 0, 1, 0};
	public final int dirY[] = {0, 1, 0, -1};
	AreaManager(Context con, int scw, int sch, Player p){  
		paint = new Paint();
		paintkotak = new Paint();
		paintkotak.setColor(Color.WHITE);
		paint.setColorFilter(null);
		curPlayer = p;
		screenHeight = sch;
		screenWidth = scw;
		jam= new TextComponent("Day "+curPlayer.getPlayingTime().getDay()+" "+curPlayer.getPlayingTime().getHour()+":"+curPlayer.getPlayingTime().getMinute(), 10, 20);
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
		butLeftestX = 24;
		butDist = 74;
		butY = 180;
		buttonLeft= new BitmapButton(BitmapManager.getInstance().get("left"), 10, 190);
		buttonDown = new BitmapButton(BitmapManager.getInstance().get("down"), 35, 215);
		buttonUp = new BitmapButton(BitmapManager.getInstance().get("up"), 35, 165);
		buttonRight = new BitmapButton(BitmapManager.getInstance().get("right"), 60, 190);
		buttonA = new BitmapButton(BitmapManager.getInstance().get("a_button"), 280, 190);
		Log.d("harits", "ukuran A: " + buttonA.getX() + " " + buttonA.getY());
		buttonA.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				curArea.getButtonInput(ButtonClick.ACTION);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				curArea.getButtonInput(ButtonClick.NONE);	
			}
		});
		
		buttonDown.addTouchListener(new TouchListener(){
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
		
		buttonUp.addTouchListener(new TouchListener(){
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
		
		buttonLeft.addTouchListener(new TouchListener(){
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
		
		buttonRight.addTouchListener(new TouchListener(){
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
		buttons.add(buttonA);
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
		if(paint.getColorFilter() == null)
			body.draw(canvas);
		curArea.drawObj(canvas);
		if(paint.getColorFilter() == null)
			head.draw(canvas);
		for(BitmapButton b : buttons){
			b.draw(canvas);
		}
		if (curPlayer.getPlayingTime().getHour() > 11) {
			AMPM="PM";
		} else {
			AMPM="AM";
		}
		canvas.drawRect(5, 5, 100, 25, paintkotak);
		jam.setText(" "+curPlayer.getPlayingTime().getHour()+":"+curPlayer.getPlayingTime().getMinute()+" "+AMPM);
		jam.draw(canvas);
		//Log.d("harits", "done drawing area...");
	}

	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
		for(BitmapButton b : buttons){
			b.onTouchEvent(e, magX, magY);
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
	
	public boolean checkBounds(int newX, int newY){
		if(newX < 0 || newY < 0 || newX >= getCurArea().getRow() || newY >= getCurArea().getColumn())
			return false;
		return true;
	}
	
	public void pushBoulder(int x, int y, int dir){
		int newX = x + dirX[dir];
		int newY = y + dirY[dir];
		
		//salah satu dari koord boulder asal/koordinat boulder tujuan ga valid,
		//gagal
		if(!checkBounds(x, y) || !checkBounds(newX, newY))
			return;
		if(getCurArea().getTile(x, y).getSpriteCodeObj() == null)
			return;
		
		if(getCurArea().getTile(x, y).getSpriteCodeObj().equals("692")){//sprite code buat boulder
			if(getCurArea().getTile(newX, newY).isPassable()){
				//bisa didorong soalnya gak ada objek
				
				//hilangin boulder di koordinat asal
				getCurArea().getTile(x, y).setSpriteCodeObj(null);
				//set passability di koordinat asal
				getCurArea().getTile(x, y).setPassable(0);
				
				//kasih boulder di koordinat baru
				getCurArea().getTile(newX, newY).setSpriteCodeObj("692");
				getCurArea().getTile(newX, newY).setPassable(1);
			}
		}
	}
}
