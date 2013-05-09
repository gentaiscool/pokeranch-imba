package com.pokeranch.game.system;

import java.util.Random;

import com.pokeranch.game.object.Monster;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.system.BattleScreen.BattleMode;
import com.pokeranch.game.system.Sprite.SpriteCounter;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

public class WalkingMonster {
	class Tuple{
		public Point first, second;
		Tuple(Point a, Point b){
			first = a;
			second = b;
		}
	}
	private AreaManager am;
	private Random rand;
	private Sprite sprite;
	private boolean startMoving, outOfBounds;
	private int direction, newDirection;
	private int curX, curY;
	private String mode;
	private int monsterID;
	public WalkingMonster(Sprite _sprite, int x, int y, AreaManager _am, String _mode){
		sprite = _sprite;
		curX = x;
		curY = y;
		rand = new Random();
		newDirection = rand.nextInt(4);
		am = _am;
		startMoving = false;
		mode = _mode;
		monsterID = rand.nextInt(10);
	}
	
	public static WalkingMonster createNewWalkingMonster(Point p, String mode, AreaManager _am){
		int i = p.x;
		int j = p.y;
		return new WalkingMonster(new Sprite(i*16, j*16, BitmapManager.getInstance().get("monster4"), 4, 2, 2, new SpriteCounter(){
			public Point getImgPos(int direction, int frame, int width, int height){	
				int x = 0, y = 0;
				//Log.d("spritemonster", width + " " + height);
				switch(direction){
					case 0: //up
						x = 0; y = frame*height;
					break;
					case 1: //right
						x = 32; y = 64 + frame*height;
					break;
					case 2: //down
						x = 0; y = 64 + frame*height;
					break;
					case 3: //left
						x = 32; y = frame*height;
					break;
				}
				return new Point(x,y);
			}
		}), i, j, _am, mode);
	}
	
	public Sprite getBody(){
		return sprite;
	}
	
	public int getDirectionToPlayer(){
		if(Math.abs(curX - am.getCurArea().getCurX()) > Math.abs(curY - am.getCurArea().getCurY())){
			if(curX > am.getCurArea().getCurX()){
				return 0;
			} else
				return 2;
		} else {
			if(curY > am.getCurArea().getCurY()){
				return 3;
			} else
				return 1;
		}
	}
	
	public int getDirectionAwayFromPlayer(){
		if(Math.abs(curX - am.getCurArea().getCurX()) > Math.abs(curY - am.getCurArea().getCurY())){
			if(curX > am.getCurArea().getCurX()){
				return 2;
			} else
				return 0;
		} else {
			if(curY > am.getCurArea().getCurY()){
				return 1;
			} else
				return 3;
		}
	}
	
	public void update(){
		if(!startMoving){
			if(mode.equals("HOMING")){
				direction = getDirectionToPlayer();
			} else if(mode.equals("FLEE")){
				direction = getDirectionAwayFromPlayer();
			} else
				direction = newDirection;
			
			Tuple t = getMeetingPoints();
			//validasi bound border disini
			outOfBounds = false;
			
			if(t.first.x < 0 || t.second.x < 0|| t.first.y < 0 || t.second.y < 0 || t.first.x >= am.getCurArea().getRow() || t.second.x >= am.getCurArea().getRow() || t.first.y >= am.getCurArea().getColumn() || t.second.y >= am.getCurArea().getColumn())
				outOfBounds = true;
			if(!outOfBounds){
				if(am.getCurArea().getCurX() == t.first.x && am.getCurArea().getCurY() == t.first.y){
					Player player2 = new Player();
					Monster m = Monster.getRandomMonster(5, 5);
					player2.addMonster(m);
					player2.setCurrentMonster(m);
					ScreenManager.getInstance().push(new BattleScreen(am.getCurPlayer(), player2, BattleMode.WILD));
					am.resetWalkingMonsters();
					am.getMonsters().remove(this);
				} else if(am.getCurArea().getTile(t.first.x, t.first.y).isPassable() && am.getCurArea().getTile(t.second.x, t.second.y).isPassable()){
					//Log.d("monster", "start to move! :D");
					startMoving = true;
					am.getCurArea().getTile(curX, curY).setPassable(0);
					
					if(t.first.x > curX)
						curX++;
					else if(t.first.x < curX)
						curX--;
					
					if(t.first.y > curY)
						curY++;
					else if(t.first.y < curY)
						curY--;
					
					am.getCurArea().getTile(curX, curY).setPassable(1);
					sprite.move(direction, 1);
				} else {
					sprite.setDirection(direction);
					newDirection = rand.nextInt(4);
				}
			} else {
				sprite.setDirection(direction);
				newDirection = rand.nextInt(4);
			}
		} else {
			sprite.move(direction, 1);
			if(getBody().getX() % 16 == 0 && getBody().getY() % 16 == 0){
					Log.d("monster", "udah sampe tujuan, berhenti dulu");
					newDirection = rand.nextInt(4);
					startMoving = false;
			}
		}
	}
	// MM 
	//MOXM
	//MXXM
	// MM
	//O adalah koordinat acuan, X merupakan badan monster
	//M adalah kemungkinan bertemu, tergantung arah monster ini
	
	//yg dipake kordinat logika, yg sudah merupakan kelipatan 16
	public Tuple getMeetingPoints(){
		if(direction == 0){//UP
			return new Tuple(new Point(curX-1, curY), new Point(curX-1, curY+1));
		} else if(direction == 1){//RIGHT
			return new Tuple(new Point(curX, curY+2), new Point(curX+1, curY+2));
		} else if(direction ==2){//DOWN
			return new Tuple(new Point(curX+2, curY), new Point(curX+2, curY+1));
		} else {//LEFT
			return new Tuple(new Point(curX, curY-1), new Point(curX+1, curY-1));
		}
	}
	
	public void draw(Canvas canvas){
		getBody().draw(canvas);
	}

	public AreaManager getAm() {
		return am;
	}

	public void setAm(AreaManager am) {
		this.am = am;
	}
}
