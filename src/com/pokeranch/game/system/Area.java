package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.system.MainGameView.ButtonClick;

public class Area {
	private AreaManager am;
	private String name;
	private Sprite body,head;
	private Tile field[][];
	private int row, column, curX, curY, nextX, nextY;;
	private boolean outOfBounds;
	private boolean move = false;
	private boolean startMoving = false;
	private boolean isUp = true;
	private int direction = 0;
	private int newDirection;
	private boolean isAction;
	private DelayedAction time;
	
	public Area(String n, int r, int c, Sprite _head, Sprite _body){
		name = n;
		field = new Tile[r][c];
		curX = curY = nextX = nextY = 0;
		row = r; //jumlah baris
		column = c; //jumlah kolom
		isAction = false;
		time = new DelayedAction(){
			@Override
			public void doAction() {
				am.getCurPlayer().addTime(1);
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
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//gambar bitmap ke layar
				//terapkan paint pada koordinat yg bukan user
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
				field[i][j].drawObj(canvas, i, j, curX, curY, am);
			}
		}
	}
	
	public void update(){
		//Log.d("harits", "time player: " + am.getCurPlayer().getPlayingTime() + " m");
		//Log.d("harits", "sekarang ada di: " + curX + " " + curY);
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
				if(nextX < 0 || nextY < 0 || nextX >= row || nextY >= column)
					outOfBounds = true;
				
				//Log.d("harits", "bakal ke: "+ nextX + " " + nextY);
				if(!outOfBounds){
					if(field[nextX][nextY].getTeleportTarget() != null){
						//Log.d("harits", "di sini bisa teleport: " + nextX +" "+ nextY);
						//Log.d("harits", "di teleport ke: " + field[nextX][nextY].getTeleportTarget());
						am.setCurArea(DBLoader.getInstance().getArea(field[nextX][nextY].getTeleportTarget()));
						//Log.d("harits", "berhasil memindahkan");
						//ngeset koordinat sprite player
						am.setPlayerCord(field[nextX][nextY].getArrivalCord());				
						//ngeset koordinat player pada array field
						//am.getCurArea().setCurCord(field[nextX][nextY].getArrivalCord());
						move = startMoving = false;
						//Log.d("harits", "berhasil menentukan koord baru");
					} else if(field[nextX][nextY].isPassable()){
						startMoving = true;
						curX = nextX;
						curY = nextY;
						head.move(direction,1);
						body.move(direction,1);
						//Log.d("harits", "posisi sekarang: " + curX + " " + curY);
					} else {
						move = false;
					}
				} else
					move = false;
			} else {
				head.move(direction,1);
				body.move(direction,1);
				if(body.getX() % 16 == 0 && body.getY() % 16 == 0){
					if(isUp)
						move = startMoving = false;
					else {//masih bergerak
						startMoving = false;
					}
				}
			}
		} else {
			body.setDirection(direction);
			head.setDirection(direction);
			//aksi2 gajelas kayak swim, dorong batu, dkkdisini
			if(isAction){
				//Log.d("boulder", "action performed");
				am.pushBoulder(curX + am.dirX[direction], curY + am.dirY[direction], direction);
				am.cutTree(curX + am.dirX[direction], curY + am.dirY[direction], direction);
				isAction = false;
			}
		}
	}

	public Sprite getHead() {
		return head;
	}

	public void setHead(Sprite head) {
		this.head = head;
	}

	public Sprite getBody() {
		return body;
	}

	public void setBody(Sprite body) {
		this.body = body;
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
	
	public void setCurCord(Point p){
		curX = p.x;
		curY = p.y;
	}
	
	public Point getCurCord(){
		return new Point(curX, curY);
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
}
