package com.pokeranch.game.system;

import java.util.Random;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Monster;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.system.BattleScreen.BattleMode;
import com.pokeranch.game.system.BattleScreen.BattleListener;
import com.pokeranch.game.system.Sprite.SpriteCounter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

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
	private String place;
	private Sprite sprite;
	private boolean startMoving, outOfBounds;
	private int direction, newDirection;
	private int curX, curY;
	private String mode;
	private int monsterID;
	public boolean fighting;
	public WalkingMonster(Sprite _sprite, int x, int y, AreaManager _am, String _mode, String _place, int spriteID){
		monsterID = spriteID;
		place = _place;
		sprite = _sprite;
		fighting = false;
		curX = x;
		curY = y;
		rand = new Random();
		newDirection = rand.nextInt(4);
		am = _am;
		startMoving = false;
		mode = _mode;
	}
	
	public static WalkingMonster createNewWalkingMonster(Point p, String mode, AreaManager _am, String _place, int spriteID){
		int i = p.x;
		int j = p.y;
		return new WalkingMonster(new Sprite(i*16, j*16, BitmapManager.getInstance().get("monster" + String.valueOf(spriteID)), 4, 2, 2, new SpriteCounter(){
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
		}), i, j, _am, mode, _place, spriteID);
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
				//Log.d("harits11", "X: " + (am.getCurArea().getCurX() - 16) + " <= " + (curX+16) + " <= " + (am.getCurArea().getCurX() + 16));
				//Log.d("harits11", "X: " + (am.getCurArea().getCurY()) + " <= " + (curY+16) + " <= " + (am.getCurArea().getCurY() + 16));
				Rect playerBoundary = new Rect(am.getCurBody().getY(), am.getCurBody().getX()-8,am.getCurBody().getY()+16, am.getCurBody().getX()+16);
				Rect monsterBoundary = new Rect(sprite.getY()+8, sprite.getX()+8, sprite.getY()+24, sprite.getX()+24);
				
			
				
				if(playerBoundary.intersect(monsterBoundary) && !fighting){
//					Log.d("harits99", "monster with ferocious type of " + mode + " is fighting!");
//					Log.d("harits99", "playerbound: " + playerBoundary.left + " " + playerBoundary.top + " " + playerBoundary.right + " " + playerBoundary.bottom);
//					Log.d("harits99", "monsterbound: " + monsterBoundary.left + " " + monsterBoundary.top + " " + monsterBoundary.right + " " + monsterBoundary.bottom);
//					Log.d("harits99", "lokasi logika: " + curX + " " + curY);
//					Log.d("harits99", "lokasi sprite: " + sprite.getX() + " " + sprite.getY());
//					fighting = true;
					Player player2 = new Player();
					Species s = Monster.getSpeciesById(monsterID);
					s.setCombineRating(1);
					Monster m = new Monster(s.getName(), s, am.getCurPlayer().getCurrentMonster().getLevel());
					player2.addMonster(m);
					player2.setCurrentMonster(m);
					am.getMonsters().remove(this);
					am.resetWalkingMonsters();


					ScreenManager.getInstance().push(new BattleScreen(am.getCurPlayer(), player2, BattleMode.WILD, new BattleListener() {
						@Override
						public void action(int result) {
							if(result==-1){
								am.getCurPlayer().restoreAllMonster();
								am.setCurArea(DBLoader.getInstance().getArea("HOME"));
								am.setPlayerCord(new Point(8,5));
							}
						}
					}));

				} else if(place.equals("SEA")){ 
					if(am.getCurArea().getTile(t.first.x, t.first.y).isSwimmable() && am.getCurArea().getTile(t.second.x, t.second.y).isSwimmable()){
						//Log.d("monster", "start to move! :D");
						startMoving = true;
						//am.getCurArea().getTile(curX, curY).setPassable(0);
						
						if(t.first.x > curX)
							curX++;
						else if(t.first.x < curX)
							curX--;
						
						if(t.first.y > curY)
							curY++;
						else if(t.first.y < curY)
							curY--;
						
						//am.getCurArea().getTile(curX, curY).setPassable(1);
						sprite.move(direction, 1);
					} else {
						sprite.setDirection(direction);
						newDirection = rand.nextInt(4);
					}
				} else if(place.equals("GROUND")){
					if(am.getCurArea().getTile(t.first.x, t.first.y).isPassable() && am.getCurArea().getTile(t.second.x, t.second.y).isPassable()){
						//Log.d("monster", "start to move! :D");
						startMoving = true;
						//am.getCurArea().getTile(curX, curY).setPassable(0);
						
						if(t.first.x > curX)
							curX++;
						else if(t.first.x < curX)
							curX--;
						
						if(t.first.y > curY)
							curY++;
						else if(t.first.y < curY)
							curY--;
						
						//am.getCurArea().getTile(curX, curY).setPassable(1);
						sprite.move(direction, 1);
					} else {
						sprite.setDirection(direction);
						newDirection = rand.nextInt(4);
					}
				}
			} else {
				sprite.setDirection(direction);
				newDirection = rand.nextInt(4);
			}
		} else {
			sprite.move(direction, 1);
			if(getBody().getX() % 16 == 0 && getBody().getY() % 16 == 0){
					//Log.d("monster", "udah sampe tujuan, berhenti dulu");
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
	
	public void draw(Canvas canvas, Paint paint){
		getBody().draw(canvas, paint);
	}

	public AreaManager getAm() {
		return am;
	}

	public void setAm(AreaManager am) {
		this.am = am;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
}
