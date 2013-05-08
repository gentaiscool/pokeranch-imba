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
	private int curX, curY, nextX, nextY;
	private boolean outOfBounds;
	private boolean move = false;
	private boolean startMoving = false;
	private boolean isUp = true;
	private int direction = 0;
	private int newDirection;
	private boolean isAction;
	private DelayedAction time;
	
	AreaManager(Context con, int scw, int sch, Player p){
		DialogueBox.initialize();
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
		buttonLeft= new BitmapButton(BitmapManager.getInstance().get("left"), 10, 190);
		buttonDown = new BitmapButton(BitmapManager.getInstance().get("down"), 35, 215);
		buttonUp = new BitmapButton(BitmapManager.getInstance().get("up"), 35, 165);
		buttonRight = new BitmapButton(BitmapManager.getInstance().get("right"), 60, 190);
		buttonA = new BitmapButton(BitmapManager.getInstance().get("a_button"), 280, 190);
		//Log.d("harits", "ukuran A: " + buttonA.getX() + " " + buttonA.getY());
		buttonA.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getButtonInput(ButtonClick.ACTION);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getButtonInput(ButtonClick.NONE);	
			}
		});
		
		buttonDown.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getButtonInput(ButtonClick.DOWN);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonUp.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getButtonInput(ButtonClick.UP);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonLeft.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getButtonInput(ButtonClick.LEFT);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getButtonInput(ButtonClick.NONE);	
			}		
		});
		
		buttonRight.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getButtonInput(ButtonClick.RIGHT);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getButtonInput(ButtonClick.NONE);
			}
		});
		
		buttons.add(buttonDown);
		buttons.add(buttonUp);
		buttons.add(buttonLeft);
		buttons.add(buttonRight);
		buttons.add(buttonA);
		
		time = new DelayedAction(){
			@Override
			public void doAction() {
				getCurPlayer().addTime(1);
			}

			@Override
			public int getDelay() {
				return 5;
			}
		};
	}
	
	public void getButtonInput(ButtonClick click){
		//Log.d("harits", lastClick + " " + click);
		if(click == ButtonClick.NONE)
			isUp = true;
		else
			isUp = false;
			
		switch(click){
			case ACTION:
				isAction = true;
			break;	
			case LEFT:
				newDirection = 3;
			break;
			case RIGHT:
				newDirection = 1;
			break;
			case DOWN:
				newDirection = 2;
			break;
			case UP:
				newDirection = 0;
			break;
			default:
				break;
		}
		

		
		if(!move && !isAction){
			move = true;
			switch(click){
			case LEFT:
				move = true;
				direction = 3;
			break;
			case RIGHT:
				move = true;
				direction = 1;
			break;
			case DOWN:
				move = true;
				direction = 2;
			break;
			case UP:
				move = true;
				direction = 0;
			break;
			case NONE:
				move = false;
			break;
			default:
				break;
			}
		}
	}
	
	public void setPlayerCord(Point p){
		//16 ~ ukuran tilenya (16x16)
		//lokasi fisik
		
		curBody.setX(p.x*16);
		curBody.setY(p.y*16); 
		curHead.setX(p.x*16-16);
		curHead.setY(p.y*16);
		if(roamingMode.equals("swim")){
			leftFinSwim.setX(p.y*16-16);
			rightFinSwim.setX(p.y*16+16);
		}
		//lokasi logika
		curX = p.x;
		curY = p.y;
	}
	
	public void movePlayer(int direction, int distance){
		curHead.move(direction,1);
		curBody.move(direction,1);
		if(roamingMode.equals("swim")){
			leftFinSwim.move(direction, 1);
			rightFinSwim.move(direction, 1);
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
		nextX = curX;
		nextY = curY;
		
		time.update();
		if(time.finished()) {time.reset();}
		
		if(move){
			if(!startMoving){
				direction = newDirection;
				switch(direction){
					case 0:{ //up
						nextX = curX - 1;
					}break;
					case 1:{ //right 
						nextY = curY + 1;
					}break;
					case 2:{ //down
						nextX = curX + 1;
					}break;
					case 3:{ //left
						nextY = curY - 1;
					}break;
				}
				
				//validasi bound border disini
				outOfBounds = false;
				if(nextX < 0 || nextY < 0 || nextX >= getCurArea().getRow() || nextY >= getCurArea().getColumn())
					outOfBounds = true;
				
				//Log.d("harits", "bakal ke: "+ nextX + " " + nextY);
				if(!outOfBounds){
					if(getCurArea().getTile(nextX, nextY).getTeleportTarget() != null){
						//Log.d("harits", "di sini bisa teleport: " + nextX +" "+ nextY);
						//Log.d("harits", "di teleport ke: " + field[nextX][nextY].getTeleportTarget());
						setCurArea(DBLoader.getInstance().getArea(getCurArea().getTile(nextX, nextY).getTeleportTarget()));
						//Log.d("harits", "berhasil memindahkan");
						//ngeset koordinat sprite player
						setPlayerCord(getCurArea().getTile(nextX, nextY).getArrivalCord());				
						//ngeset koordinat player pada array field
						//am.getCurArea().setCurCord(field[nextX][nextY].getArrivalCord());
						move = startMoving = false;
						//Log.d("harits", "berhasil menentukan koord baru");
					} else if(getCurArea().getTile(nextX, nextY).isPassable()){
						startMoving = true;
						curX = nextX;
						curY = nextY;
						movePlayer(direction, 1);
						
						//Log.d("harits", "posisi sekarang: " + curX + " " + curY);
					} else {
						move = false;
					}
				} else
					move = false;
			} else {
				movePlayer(direction, 1);
				if(curBody.getX() % 16 == 0 && curBody.getY() % 16 == 0){
					if(isUp)
						move = startMoving = false;
					else {//masih bergerak
						startMoving = false;
					}
				}
			}
		} else {
			setPlayerDirection(direction);
			//aksi2 gajelas kayak swim, dorong batu, dkk disini
			if(isAction){
				pushBoulder(curX + dirX[direction], curY + dirY[direction], direction);
				cutTree(curX + dirX[direction], curY + dirY[direction], direction);
				isAction = false;
			}
		}
		
		if(getCurPlayer().getPlayingTime().getHour() > 18){ //udah malam ikan bobo
			if(getCurPlayer().haveTorch())
				paint.setColorFilter(new LightingColorFilter(0x004C4C4C, 0));
			else
				paint.setColorFilter(new LightingColorFilter(0x00000000, 0));
		} else
			paint.setColorFilter(null);
	}
	
	public int getCurX(){
		return curX;
	}
	
	public int getCurY(){
		return curY;
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
			
			leftFinSwim.setX(curBody.getX());
			leftFinSwim.setY(curBody.getY() - 1);
			rightFinSwim.setX(curBody.getX());
			rightFinSwim.setY(curBody.getY() + 1);
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
}
