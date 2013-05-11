package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.pokeranch.game.object.Monster;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MainGameView.ButtonClick;
import com.pokeranch.game.system.Sprite.SpriteCounter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

public class AreaManager implements IScreen{
	private Area curArea;
	private Sprite curHead, curBody, headGround, bodyGround, headSwim, bodySwim, leftFinSwim, rightFinSwim;
	private ArrayList<BitmapButton> buttons;
	private BitmapButton buttonLeft, buttonUp, buttonDown, buttonRight, buttonA, buttonB;
	private Player curPlayer;
	private Context context;
	private TextComponent jam;
	private String roamingMode;
	private String AMPM="";
	private Paint paint;
	private Paint paintkotak;
	private boolean die = false;
	public final int dirX[] = {-1, 0, 1, 0};
	public final int dirY[] = {0, 1, 0, -1};
	private int screenWidth, screenHeight;
	private String dayLightTime;
	private CopyOnWriteArrayList<WalkingMonster> monsters;
	private Bitmap frame;
	private TextComponent info;
	
	AreaManager(Context con, int scw, int sch, Player p){
		PlayerMenu.initialize(p);
		ListItem.initialize(p, scw, sch);
		screenWidth = scw;
		screenHeight = sch;
		context = con;
		paint = new Paint();
		paintkotak = new Paint();
		paintkotak.setColor(Color.WHITE);
		paint.setColorFilter(null);
		curPlayer = p;
		
		frame = BitmapManager.getInstance().get("dbox");
		info = new TextComponent("One of your monster has died \nand reborn into a level 1 monster", 20, 200);

		dayLightTime = "DAY";
		
		monsters = new CopyOnWriteArrayList<WalkingMonster>();
		
		jam= new TextComponent(" "+curPlayer.getPlayingTime().getHour()+":"+curPlayer.getPlayingTime().getMinute()+" "+AMPM, 5, 15);
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
		buttonLeft= new BitmapButton(BitmapManager.getInstance().get("left"), 10, 203);
		buttonDown = new BitmapButton(BitmapManager.getInstance().get("down"), 47, 203);
		buttonUp = new BitmapButton(BitmapManager.getInstance().get("up"), 47, 166);
		buttonRight = new BitmapButton(BitmapManager.getInstance().get("right"), 84, 203);
		buttonA = new BitmapButton(BitmapManager.getInstance().get("a_button"), 270, 167);
		buttonB = new BitmapButton(BitmapManager.getInstance().get("b_button"), 270, 203);
		//Log.d("harits", "ukuran A: " + buttonA.getX() + " " + buttonA.getY());
		buttonA.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getCurArea().getButtonInput(ButtonClick.ACTION_A);
			}
			@Override
			public void onTouchMove() {}
			
			@Override
			public void onTouchUp() {
				getCurArea().getButtonInput(ButtonClick.NONE);	
			}
		});
		
		buttonB.addTouchListener(new TouchListener(){
			@Override
			public void onTouchDown() {
				getCurArea().getButtonInput(ButtonClick.ACTION_B);
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
		buttons.add(buttonB);
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
		curArea.update();
		for(WalkingMonster m:monsters)
				m.update();
		
		if((getCurPlayer().getPlayingTime().getHour() >= 18 || getCurPlayer().getPlayingTime().getHour() < 6) && getCurArea().getPlace().equals("OUTDOOR")){ //udah malam ikan bobo
			dayLightTime = "NIGHT";
			if(getCurPlayer().haveTorch())
				paint.setColorFilter(new LightingColorFilter(0x004C4C4C, 0));
			else
				paint.setColorFilter(new LightingColorFilter(0x00000000, 0));
		} else {
			paint.setColorFilter(null);
			dayLightTime = "DAY";
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		curArea.drawBG(canvas);
		//asumsi udah malem
//		Path p = new Path();
//		p.addCircle(getCurArea().getCurX()*16 + 8, getCurArea().getCurY()*16 + 8, 12, Path.Direction.CCW);
//		canvas.clipPath(p);
//		canvas.drawBitmap(shade, null, new Rect(0,0,240,320), null);
		curBody.draw(canvas, paint);
		if(roamingMode.equals("swim")){
			leftFinSwim.draw(canvas, paint);
			leftFinSwim.draw(canvas, paint);
			rightFinSwim.draw(canvas, paint);
			rightFinSwim.draw(canvas, paint);
		}
		for(WalkingMonster m:monsters)
			m.draw(canvas, paint);
		curArea.drawObj(canvas);
		
		
		curHead.draw(canvas, paint);
		
		if(die){
			canvas.drawBitmap(frame, new Rect(0,0, frame.getWidth(),frame.getHeight()),new RectF(0,160,320,240), null);
			info.draw(canvas);
		}
		
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
		for(BitmapButton b : buttons){
			b.onTouchEvent(e, magX, magY);
		}

		if(die){
			die = false;
		}
	}
	
	public Area getCurArea(){
		return curArea;
	}
	
	public void setCurArea(Area a) {
		curArea = a;
		curArea.setAreaManager(this);
		resetWalkingMonsters();
		
	}
	
	public void notifyDie(){
		die = true;
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
				Monster m = getCurPlayer().getMonsterWithSkill("Cut");
				if(m != null){
					DialogueBox.getInstance().setMessage(m.getName() + " used push!");
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
	}
	
	public void cutTree(int x, int y, int dir){
		//koordinat tree tujuan ga valid, gagal
		if(!checkBounds(x, y))
			return;
		if(getCurArea().getTile(x, y).getSpriteCodeObj() == null)
			return;
		if(getCurArea().getTile(x, y).hasTree()){
			Monster m = getCurPlayer().getMonsterWithSkill("Cut");
			if(m != null){
				DialogueBox.getInstance().setMessage(m.getName() + " used cut!");
				ScreenManager.getInstance().push(DialogueBox.getInstance());
				
				//hilangin tree
				getCurArea().getTile(x, y).setSpriteCodeObj(null);
				//set passability di koordinat tree
				getCurArea().getTile(x, y).setPassable(0);
			}
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
		//Log.d("harits99", "trySwim invoked");
		if(!checkBounds(x, y) || !checkBounds(newX, newY))
			return;
		//Log.d("harits99", "tile in your direction: " + getCurArea().getTile(x, y).isSwimmable() + ". currently in " + getRoamingMode() + " mode");
		if(getCurArea().getTile(x, y).isSwimmable() && getRoamingMode().equals("ground")){
			Monster m = getCurPlayer().getMonsterWithSkill("Swim");
			if(m != null){
				DialogueBox.getInstance().setMessage(m.getName() + " used swim!");
				//karena x dan y adalah shore, newX dan newY masih dalam boundary,
				//maka newX dan newY udah dapat dipastikan merupakan sea
				ScreenManager.getInstance().push(DialogueBox.getInstance());
				
				//ganti mode, biar fin nya keliatan + sprite keganti
				changeRoamingMode("swim");
				
				//pindahin player ke koordinat baru
				setPlayerCord(new Point(x, y));
			}
		} else if(getCurArea().getTile(x, y).isPassable() && getRoamingMode().equals("swim")){
			Monster m = getCurPlayer().getMonsterWithSkill("Swim");
			if(m != null){
				//ganti mode, biar fin nya keliatan + sprite keganti
				changeRoamingMode("ground");
				
				
				//pindahin player ke koordinat baru
				setPlayerCord(new Point(x, y));
			}
		}
	}
	
	public void tryAction(int x, int y, int dir){
		if(!checkBounds(x, y))
			return;
		String action = getCurArea().getTile(x, y).getActionName();
		//Log.d("tipe action", action.toString());
		if(action == null)
			return;
		if(action.equals("COMBINATORIUM")){
			//masukin kode combinatorium disini
			Combinatorium combinatorium = new Combinatorium(curPlayer,screenWidth,screenHeight);
			ScreenManager.getInstance().push(combinatorium);
		} else if(action.equals("STORE")){
			//masukin kode store disini
			BuySellScreen bss = new BuySellScreen(curPlayer, screenWidth, screenHeight);
			ScreenManager.getInstance().push(bss);
		} else if(action.equals("STADIUM")){
			//masukin kode stadium disini
			Stadium stadium = new Stadium(this, curPlayer,screenWidth,screenHeight);
			ScreenManager.getInstance().push(stadium);
		} else if(action.equals("SAVELOAD")){
			SaveLoadScreen sls = new SaveLoadScreen(context, curPlayer, screenWidth, screenHeight);
			ScreenManager.getInstance().push(sls);
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
	
	public Point getRandomPassableSeaTile(){
		Random rand = new Random();
		int tries = 0;
		int x = rand.nextInt(getCurArea().getRow());
		int y = rand.nextInt(getCurArea().getColumn());
		while(!isSeaMonsterPlacable(x, y)){
			x = rand.nextInt(getCurArea().getRow());
			y = rand.nextInt(getCurArea().getColumn());
			tries++;
			if(tries == 100)
				break;
		}
		if(tries == 100)
			return null;
		else
			return new Point(x,y);
	}
	
	public boolean contains(int blow, int bhigh, int tested){
		return (tested >= blow && tested <= bhigh);
	}
	
	public boolean isSeaMonsterPlacable(int x, int y){
		//ngecek bujur sangkar yg sisinya 2 tile yg ujung kiri atasnya adalah x, y apakah tilenya passable semua
		if(!checkBounds(x, y) || !checkBounds(x+1, y) || !checkBounds(x, y+1) || !checkBounds(x+1, y+1))
			return false;
			
		if(getCurArea().getTile(x, y).isSwimmable() && getCurArea().getTile(x+1, y).isSwimmable() && getCurArea().getTile(x, y+1).isSwimmable() && getCurArea().getTile(x+1, y+1).isSwimmable()){
			return true;
		} else
			return false;
	}
	
	public boolean isGroundMonsterPlacable(int x, int y){
		//ngecek bujur sangkar yg sisinya 2 tile yg ujung kiri atasnya adalah x, y apakah tilenya passable semua
		if(!checkBounds(x+1, y) || !checkBounds(x, y+1) || !checkBounds(x+1, y+1))
			return false;
			
		if(getCurArea().getTile(x, y).isPassable() && getCurArea().getTile(x+1, y).isPassable() && getCurArea().getTile(x, y+1).isPassable() && getCurArea().getTile(x+1, y+1).isPassable()){
			return true;
		} else
			return false;
	}
	public Point getRandomPassableGroundTile(){
		Random rand = new Random();
		int tries = 0;
		int x = rand.nextInt(getCurArea().getRow());
		int y = rand.nextInt(getCurArea().getColumn());
		while(!isGroundMonsterPlacable(x, y) || (contains(getCurArea().getCurX() - 1, getCurArea().getCurX() + 1, x+1) && contains(getCurArea().getCurY(), getCurArea().getCurY() + 1, y + 1))){
			x = rand.nextInt(getCurArea().getRow());
			y = rand.nextInt(getCurArea().getColumn());
			tries++;
			if(tries == 100)
				break;
		}
		if(tries == 100)
			return null;
		else
			return new Point(x,y);
	}

	public void resetWalkingMonsters() {
		monsters.clear();
		Random rand = new Random();
		if(getCurArea().getPlace().equals("OUTDOOR") && !getCurArea().getName().equals("CITY") ){
			Point tmp1,tmp2,tmp3;
			tmp1 = getRandomPassableGroundTile();
			tmp2 = getRandomPassableGroundTile();
			tmp3 = getRandomPassableGroundTile();
			if(dayLightTime.equals("DAY")){
				if(tmp1 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp1, "HOMING", this, "GROUND", rand.nextInt(7)));
				if(tmp2 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp2, "FLEE", this, "GROUND", rand.nextInt(7)));
				if(tmp3 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp3, "RANDOM", this, "GROUND", rand.nextInt(7)));
			} else {
				if(tmp1 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp1, "HOMING", this, "GROUND", 10 + rand.nextInt(9)));
				if(tmp2 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp2, "FLEE", this, "GROUND", 10 + rand.nextInt(9)));
				if(tmp3 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp3, "RANDOM", this, "GROUND", 10 + rand.nextInt(9)));
			}
			Point tmp4,tmp5,tmp6;
			tmp4 = getRandomPassableSeaTile();
			tmp5 = getRandomPassableSeaTile();
			tmp6 = getRandomPassableSeaTile();
			if(dayLightTime.equals("DAY")){
				if(tmp4 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp4, "HOMING", this, "SEA", 7 + rand.nextInt(2)));
				if(tmp5 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp5, "FLEE", this, "SEA", 7 + rand.nextInt(2)));
				if(tmp6 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp6, "RANDOM", this, "SEA", 7 + rand.nextInt(2)));
			} else {
				if(tmp4 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp4, "HOMING", this, "SEA", 9));
				if(tmp5 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp5, "FLEE", this, "SEA", 9));
				if(tmp6 != null)
					monsters.add(WalkingMonster.createNewWalkingMonster(tmp6, "RANDOM", this, "SEA", 9));
			}
		}
	}

	public CopyOnWriteArrayList<WalkingMonster> getMonsters() {
		return monsters;
	}
	
}
