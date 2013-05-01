package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.pokeranch.game.system.MainGameView.ButtonClick;

public class Area {
	private Sprite monster,bg,body,head;
	private Tile field[][];
	private int row, column, mag, curX, curY, nextX, nextY;;
	//private ArrayList<Area>
	private Matrix mtx;
	private boolean outOfBounds;
	private boolean move = false;
	private boolean startMoving = false;
	private boolean isUp = true;
	private int direction = 0;
	public Area(int r, int c, int m){
		field = new Tile[r][c];
		curX = curY = nextX = nextY = 0;
		row = r; //jumlah baris
		column = c; //jumlah kolom
		mag = m; //magnifikasi
		mtx = new Matrix();
		mtx.setRotate(90);
		head = new Sprite(32,0, BitmapManager.getInstance().get("chara"), 2,12,3, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 3*width + frame*width; y = 0;
					break;
					case 1: //right
						x = 6*width + frame*width; y = 0;
					break;
					case 2: //down
						x = 9*width + frame*width; y = 0;
					break;
					case 3: //left
						x = 0*width + frame*width; y = 0;
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
						x = 3*width + frame*width; y = height;
					break;
					case 1: //right --dst
						x = 6*width + frame*width; y = height;
					break;
					case 2: //down
						x = 9*width + frame*width; y = height;
					break;
					case 3: //left
						x = 0*width + frame*width; y = height;
					break;
				}
				//Log.d("harits", "w: " + width);
				//Log.d("harits", "x: " + x + ", y: " + y);
				return new Point(x,y);
			}
		});
		
		monster = new Sprite(0,0,BitmapManager.getInstance().get("landmonster"),4,2,2, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 0; y = 0;
					break;
					case 1: //right
						x = width; y = height * 2;
					break;
					case 2: //down
						x = 0; y = height * 2;
					break;
					case 3: //left
						x = width; y = 0;
					break;
				}
				
				if (frame>0) y+=height;
				
				return new Point(x,y);
			}
		});
		
		//monster.setX(5);
		
		bg = new Sprite(0,0,BitmapManager.getInstance().get("images"), 1,1,1, new SpriteCounter(){
			@Override
			public Point getImgPos(int direction, int frame, int width, int height) {
				int x = 0, y = 0;
				switch(direction){
					case 0: //up
						x = 0; y = 0;
					break;
					case 1: //right
						x = width; y = height * 2;
					break;
					case 2: //down
						x = 0; y = height * 2;
					break;
					case 3: //left
						x = width; y = 0;
					break;
				}
				
				if (frame>0) y+=height;
				
				return new Point(x,y);
			}
		});
	}
	
	public void getButtonInput(ButtonClick click){
		
		if(click == ButtonClick.NONE)
			isUp = true;
		else
			isUp = false;
		
		if(!move){
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
	
	public void createTile(int i, int j, int code, boolean pass){
		field[i][j] = new Tile(code, pass);
	}
	
	public void setVal(int i, int j, int val){
		field[i][j].setSpriteCode(val);
	}
	
	public void draw(Canvas canvas){
		//gambar belakang

		
		float mag = 2; //magnifikasi tile, bisa 1.5f, 2, dkk
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//bitmap dirotasi 90 derajat searah jarum jam dengan matrix mtx
				//Bitmap drawnBitmap = Bitmap.createBitmap(BitmapManager.getInstance().get(String.valueOf(field[i][j].getSpriteCode())), 0, 0, 16, 16, mtx, false);
				//gambar bitmap ke layar
				canvas.drawBitmap(BitmapManager.getInstance().get(String.valueOf(field[i][j].getSpriteCode())), null, new RectF(j*16*mag, i*16*mag, j*16*mag + 16*mag, i*16*mag + 16*mag), null);
				
			}
		}
		
		head.draw(canvas, 2);
		body.draw(canvas, 2);
	}
	
	public void update(){
		nextX = curX;
		nextY = curY;
		if(move){
			if(!startMoving){
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
				
				//Log.d("harits", field[nextX][nextY].getSpriteCode() + " " + field[nextX][nextY].isPassable());
				if(!outOfBounds){
					if(field[nextX][nextY].isPassable()){
						startMoving = true;
						curX = nextX;
						curY = nextY;
						Log.d("harits", "posisi sekarang: " + curX + " " + curY);
					} else {
						move = false;
					}
				} else
					move = false;
			} else {
				head.move(direction,1);
				body.move(direction,1);
				if(body.getX() % 32 == 0 && body.getY() % 32 == 0){
					if(isUp)
						move = startMoving = false;
					else {//masih bergerak
						startMoving = false;
					}
				}
			}
		} 
	}
}