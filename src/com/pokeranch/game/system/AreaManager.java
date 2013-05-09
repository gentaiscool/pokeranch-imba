package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MainGameView.ButtonClick;
import com.pokeranch.game.system.Sprite.SpriteCounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class AreaManager implements IScreen{
	private Area curArea;
	private Sprite curHead, curBody, headGround, bodyGround, headSwim, bodySwim, leftFinSwim, rightFinSwim;
	private ArrayList<BitmapButton> buttons;
	private BitmapButton buttonLeft, buttonUp, buttonDown, buttonRight, buttonA;
	private Player curPlayer;
	private Context context;
	private TextComponent jam;
	private String roamingMode;
	private String AMPM="";
	private Paint paint;
	private Paint paintkotak;
	public final int dirX[] = {-1, 0, 1, 0};
	public final int dirY[] = {0, 1, 0, -1};
	
	AreaManager(Context con, int scw, int sch, Player p){
		DialogueBox.initialize();
		PlayerMenu.initialize();
		context = con;
		paint = new Paint();
		paintkotak = new Paint();
		paintkotak.setColor(Color.WHITE);
		paint.setColorFilter(null);
		curPlayer = p;
		jam= new TextComponent("Day "+curPlayer.getPlayingTime().getDay()+" "+curPlayer.getPlayingTime().getHour()+":"+curPlayer.getPlayingTime().getMinute(), 5, 15);
		headGround = new Sprite(32,0, BitmapManager.getInstance().get("chara"), 2,12,3, new SpriteCounter(){
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
		
		bodyGround = new Sprite(0,0,BitmapManager.getInstance().get("chara"), 2,12,3, new SpriteCounter(){
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
		
		headSwim = new Sprite(32,0, BitmapManager.getInstance().get("chara_swim"), 2,24,2, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 7*width + 3*width*frame; y = 0;
					break;
					case 1: //right
						x = 19*width + 3*width*frame; y = 0;
					break;
					case 2: //down
						x = width + 3*width*frame; y = 0;
					break;
					case 3: //left
						x = 13*width + 3*width*frame; y = 0;
					break;
				}
				//Log.d("harits", "w: " + width);
				//Log.d("harits", "x: " + x + ", y: " + y);
				return new Point(x,y);
			}
		});
		
		bodySwim = new Sprite(0,0,BitmapManager.getInstance().get("chara_swim"), 2,24,2, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 7*width + 3*width*frame; y = height;
					break;
					case 1: //right
						x = 19*width + 3*width*frame; y = height;
					break;
					case 2: //down
						x = width + 3*width*frame; y = height;
					break;
					case 3: //left
						x = 13*width + 3*width*frame; y = height;
					break;
				}
				//Log.d("harits", "w: " + width);
				//Log.d("harits", "x: " + x + ", y: " + y);
				return new Point(x,y);
			}
		});
		
		leftFinSwim = new Sprite(0,0,BitmapManager.getInstance().get("chara_swim"), 2,24,2, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 6*width + 3*width*frame; y = height;
					break;
					case 1: //right
						x = 18*width + 3*width*frame; y = height;
					break;
					case 2: //down
						x = 3*width*frame; y = height;
					break;
					case 3: //left
						x = 12*width + 3*width*frame; y = height;
					break;
				}
				//Log.d("harits", "w: " + width);
				//Log.d("harits", "x: " + x + ", y: " + y);
				return new Point(x,y);
			}
		});
		
		rightFinSwim = new Sprite(0,0,BitmapManager.getInstance().get("chara_swim"), 2,24,2, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 8*width + 3*width*frame; y = height;
					break;
					case 1: //right
						x = 20*width + 3*width*frame; y = height;
					break;
					case 2: //down
						x = 2*width + 3*width*frame; y = height;
					break;
					case 3: //left
						x = 14*width + 3*width*frame; y = height;
					break;
				}
				//Log.d("harits", "w: " + width);
				//Log.d("harits", "x: " + x + ", y: " + y);
				return new Point(x,y);
			}
		});
		
		curHead = headGround;
		curBody = bodyGround;
		roamingMode = "ground";
		
		buttons = new ArrayList<BitmapButton>();
		buttonLeft= new BitmapButton(BitmapManager.getInstance().get("left"), 10, 166);
		buttonDown = new BitmapButton(BitmapManager.getInstance().get("down"), 47, 203);
		buttonUp = new BitmapButton(BitmapManager.getInstance().get("up"), 47, 129);
		buttonRight = new BitmapButton(BitmapManager.getInstance().get("right"), 84, 166);
		buttonA = new BitmapButton(BitmapManager.getInstance().get("a_button"), 270, 167);
		//Log.d("harits", "ukuran A: " + buttonA.getX() + " " + buttonA.getY());
		buttonA.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getCurArea().getButtonInput(ButtonClick.ACTION);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getCurArea().getButtonInput(ButtonClick.NONE);	
			}
		});
		
		buttonDown.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getCurArea().getButtonInput(ButtonClick.DOWN);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getCurArea().getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonUp.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getCurArea().getButtonInput(ButtonClick.UP);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getCurArea().getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonLeft.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getCurArea().getButtonInput(ButtonClick.LEFT);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getCurArea().getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonRight.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getCurArea().getButtonInput(ButtonClick.RIGHT);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getCurArea().getButtonInput(ButtonClick.NONE);
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
		//lokasi sprite
		
		curBody.setX(p.x*16);
		curBody.setY(p.y*16); 
		curHead.setX(p.x*16-16);
		curHead.setY(p.y*16);
		if(roamingMode.equals("swim")){
			curHead.setX(p.x*16-15);
			leftFinSwim.setX(p.x*16);
			leftFinSwim.setY(p.y*16-16);
			rightFinSwim.setX(p.x*16);
			rightFinSwim.setY(p.y*16+16);
		}
		//lokasi logika di area
		
		getCurArea().setCurX(p.x);
		getCurArea().setCurY(p.y);
	}
	
	public void movePlayer(int direction, int distance){
		curHead.move(direction,distance);
		curBody.move(direction,distance);
		if(roamingMode.equals("swim")){
			leftFinSwim.move(direction, distance);
			rightFinSwim.move(direction, distance);
		}
	}
	
	public void setPlayerDirection(int direction){
		curHead.setDirection(direction);
		curBody.setDirection(direction);
		if(roamingMode.equals("swim")){
			leftFinSwim.setDirection(direction);
			rightFinSwim.setDirection(direction);
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		curArea.update();
		
		if(getCurPlayer().getPlayingTime().getHour() > 18 && getCurArea().getPlace().equals("OUTDOOR")){ //udah malam ikan bobo
			if(getCurPlayer().haveTorch())
				paint.setColorFilter(new LightingColorFilter(0x004C4C4C, 0));
			else
				paint.setColorFilter(new LightingColorFilter(0x00000000, 0));
		} else
			paint.setColorFilter(null);
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
		if(paint.getColorFilter() == null || (paint.getColorFilter() != null && getCurPlayer().haveTorch())){
			curBody.draw(canvas);
			if(roamingMode.equals("swim")){
				leftFinSwim.draw(canvas);
				leftFinSwim.draw(canvas);
				rightFinSwim.draw(canvas);
				rightFinSwim.draw(canvas);
			}
		}
		curArea.drawObj(canvas);
		if(paint.getColorFilter() == null || (paint.getColorFilter() != null && getCurPlayer().haveTorch()))
			curHead.draw(canvas);
		for(BitmapButton b : buttons){
			b.draw(canvas);
		}
		if (curPlayer.getPlayingTime().getHour() > 11) {
			AMPM="PM";
		} else {
			AMPM="AM";
		}
		canvas.drawRect(5, 5, 65, 20, paintkotak);
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
		
		if(getCurArea().getTile(x, y).hasBoulder()){
			if(getCurArea().getTile(newX, newY).isPassable()){
				DialogueBox.getInstance().setMessage("[put monster name that used push here] used push!");
				ScreenManager.getInstance().push(DialogueBox.getInstance());
				
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
	
	public void cutTree(int x, int y, int dir){
		//koordinat tree tujuan ga valid, gagal
		if(!checkBounds(x, y))
			return;
		if(getCurArea().getTile(x, y).getSpriteCodeObj() == null)
			return;
		if(getCurArea().getTile(x, y).hasTree()){
			DialogueBox.getInstance().setMessage("[put monster name that used cut here] used cut!");
			ScreenManager.getInstance().push(DialogueBox.getInstance());
			
			//hilangin tree
			getCurArea().getTile(x, y).setSpriteCodeObj(null);
			//set passability di koordinat tree
			getCurArea().getTile(x, y).setPassable(0);
		}
	}
	
	public void changeRoamingMode(String newMode){
		if(newMode.equals("swim")){
			roamingMode = "swim";
			headSwim.setX(curHead.getX());
			headSwim.setY(curHead.getY());
			bodySwim.setX(curBody.getX());
			bodySwim.setY(curBody.getY());
			
			curHead = headSwim;
			curBody = bodySwim;
		} else {
			roamingMode = "ground";
			headGround.setX(curHead.getX());
			headGround.setY(curHead.getY());
			bodyGround.setX(curBody.getX());
			bodyGround.setY(curBody.getY());
			
			curHead = headGround;
			curBody = bodyGround;
		}
	}
	
	public void trySwim(int x, int y, int dir){
		int newX = x + dirX[dir];
		int newY = y + dirY[dir];
		
		//salah satu dari koord shore/koordinat laut ga valid,
		//gagal
		if(!checkBounds(x, y) || !checkBounds(newX, newY))
			return;
		
		if(getCurArea().getTile(x, y).isShore()){
				//karena x dan y adalah shore, newX dan newY masih dalam boundary,
				//maka newX dan newY udah dapat dipastikan merupakan sea
				DialogueBox.getInstance().setMessage("[put monster name that used swim here] used swim!");
				ScreenManager.getInstance().push(DialogueBox.getInstance());
				
				//ganti mode, biar fin nya keliatan + sprite keganti
				if(roamingMode.equals("swim")){
					changeRoamingMode("ground");
				} else
					changeRoamingMode("swim");
				
				//pindahin player ke koordinat baru
				setPlayerCord(new Point(newX, newY));
		}
	}
	
	public void tryAction(int x, int y, int dir){
		if(!checkBounds(x, y))
			return;
		String action = getCurArea().getTile(x, y).getActionName();
		if(action == null)
			return;
		if(action.equals("COMBINATORIUM")){
			//masukin kode combinatorium disini
		} else if(action.equals("STORE")){
			//masukin kode store disini
		} else if(action.equals("STADIUM")){
			//masukin kode stadium disini
		}
	}



	public Sprite getCurHead() {
		return curHead;
	}



	public void setCurHead(Sprite curHead) {
		this.curHead = curHead;
	}



	public Sprite getCurBody() {
		return curBody;
	}



	public void setCurBody(Sprite curBody) {
		this.curBody = curBody;
	}

	public String getRoamingMode() {
		return roamingMode;
	}

	public void setRoamingMode(String roamingMode) {
		this.roamingMode = roamingMode;
	}
}
