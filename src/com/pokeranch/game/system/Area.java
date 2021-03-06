package com.pokeranch.game.system;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.system.MainGameView.ButtonClick;

import android.graphics.Canvas;
import android.util.Log;

public class Area {
	private String place;
	private AreaManager am;
	private String name;
	private Tile field[][];
	private int row, column;
	private int curX, curY, nextX, nextY;
	private boolean outOfBounds;
	private boolean move = false;
	private boolean startMoving = false;
	private boolean isUp = true;
	private int direction = 0;
	private int newDirection;
	private boolean isActionA, isActionB;
	private DelayedAction time;
	
	public Area(String n, int r, int c){
		place = null;
		name = n;
		field = new Tile[r][c];
		row = r;
		column= c;
		time = new DelayedAction(){
			@Override
			public void doAction() {
				if(am.getCurPlayer().addTime(1))
					am.notifyDie();
			}

			@Override
			public int getDelay() {
				return 5;
			}
		};
	}
	
	public Tile getTile(int i, int j){
		return field[i][j];
	}
	
	public void createTile(int i, int j, String code1, String code2, int pass){
		field[i][j] = new Tile(code1, code2, pass);
	}
	
	public void setValBG(int i, int j, String val){
		field[i][j].setSpriteCodeBG(val);
	}
	
	public void setValObj(int i, int j, String val){
		field[i][j].setSpriteCodeObj(val);
	}
	
	public void drawBG(Canvas canvas){
		//gambar belakang
		//Log.d("harits3", "drawBG curArea diluar for kepanggil, r c: " + row + " " + column);
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//gambar bitmap ke layar
				//terapkan paint pada koordinat yg bukan user
				//Log.d("harits3", "drawBG curArea kepanggil");
				field[i][j].drawBG(canvas, i, j, curX, curY, am);
			}
		}
	}
	
	public void drawObj(Canvas canvas){
		//gambar belakang
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//gambar bitmap ke layar
				//terapkan paint pada koordinat yg bukan user
				//Log.d("harits3", "drawObj curArea kepanggil");
				field[i][j].drawObj(canvas, i, j, curX, curY, am);
			}
		}
	}
	
	public void getButtonInput(ButtonClick click){
		//Log.d("harits", lastClick + " " + click);
		if(click == ButtonClick.NONE)
			isUp = true;
		else
			isUp = false;
			
		switch(click){
			case ACTION_A:
				isActionA = true;
			break;	
			case ACTION_B:
				isActionB = true;
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
		

		
		if(!move && (!isActionA && !isActionB)){
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
	
	public void update(){
		nextX = curX;
		nextY = curY;
		
		time.update();
		if(time.finished()) 
			time.reset();
		Log.d("harits99", "move " + move);
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
				if(nextX < 0 || nextY < 0 || nextX >= row || nextY >= column)
					outOfBounds = true;
				
				//Log.d("harits", "bakal ke: "+ nextX + " " + nextY);
				if(!outOfBounds){
					if(getTile(nextX, nextY).getTeleportTarget() != null){
						//Log.d("harits", "di sini bisa teleport: " + nextX +" "+ nextY);
						//Log.d("harits", "di teleport ke: " + field[nextX][nextY].getTeleportTarget());
						am.setCurArea(DBLoader.getInstance().getArea(getTile(nextX, nextY).getTeleportTarget()));
						//Log.d("harits", "berhasil memindahkan");
						//ngeset koordinat sprite player
						am.setPlayerCord(getTile(nextX, nextY).getArrivalCord());				
						//ngeset koordinat player pada array field
						//am.setCurCord(field[nextX][nextY].getArrivalCord());
						move = startMoving = false;
						//Log.d("harits", "berhasil menentukan koord baru");
					} else if(am.getRoamingMode().equals("swim")){
						if(getTile(nextX, nextY).isSwimmable()){
							startMoving = true;
							curX = nextX;
							curY = nextY;
							am.movePlayer(direction, 2);
						} else {
							am.setPlayerDirection(direction);
							move = false;
						}
					} else if(getTile(nextX, nextY).isPassable()){
						startMoving = true;
						curX = nextX;
						curY = nextY;
						am.movePlayer(direction, 2);
						
						//Log.d("harits", "posisi sekarang: " + curX + " " + curY);
					} else {
						move = false;
					}
				} else
					move = false;
			} else {
				am.movePlayer(direction, 2);
				if(am.getCurBody().getX() % 16 == 0 && am.getCurBody().getY() % 16 == 0){
					if(isUp)
						move = startMoving = false;
					else {//masih bergerak
						startMoving = false;
					}
				}
			}
		} else {
			am.setPlayerDirection(direction);
			//aksi2 gajelas kayak swim, dorong batu, dkk disini
			if(isActionA){
				Log.d("harits99", "button A pushed");
				am.pushBoulder(curX + am.dirX[direction], curY + am.dirY[direction], direction);
				am.cutTree(curX + am.dirX[direction], curY + am.dirY[direction], direction);
				am.trySwim(curX + am.dirX[direction], curY + am.dirY[direction], direction);
				am.tryAction(curX + am.dirX[direction], curY + am.dirY[direction], direction);
				isActionA = false;
			} else if(isActionB){
				ScreenManager.getInstance().push(PlayerMenu.getInstance());
				isActionB = false;
			}
		}
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setAreaManager(AreaManager am) {
		this.am = am;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public int getCurX() {
		return curX;
	}

	public void setCurX(int curX) {
		this.curX = curX;
	}

	public int getCurY() {
		return curY;
	}

	public void setCurY(int curY) {
		this.curY = curY;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
}
