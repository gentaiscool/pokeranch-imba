package com.pokeranch.game.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.pokeranch.game.system.MainGameView.ButtonClick;

public class Area implements IScene{
	private Sprite monster,bg;
	private int field[][], row, column;
	//private ArrayList<Area>
	private boolean move = false;
	private boolean isUp = true;
	private int direction = 0;
	public Area(int r, int c){
		field = new int[r][c];
		row = r;
		column = c;
		
		monster = new Sprite(BitmapManager.getInstance().get("landmonster"),4,2,2, new SpriteCounter(){
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
		bg = new Sprite(BitmapManager.getInstance().get("images"), 1,1,1, new SpriteCounter(){
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
	
	public void setVal(int i, int j, int val){
		field[i][j] = val;
	}
	
	public void draw(Canvas canvas){
		//gambar belakang
		float mag = 2; //magnifikasi tile, bisa 1.5f, 2, dkk
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//Log.d("harits", "tile yg kepake: " + field[i][j]);
				canvas.drawBitmap(BitmapManager.getInstance().get(String.valueOf(field[i][j])), null, new RectF(j*16*mag, i*16*mag, j*16*mag + 16*mag, i*16*mag + 16*mag), null);
				
				//canvas.drawBitmap(BitmapManager.getInstance().get("43"), j*16, i*16, null);
			}
		}
		monster.draw(canvas);
	}
	
	public void update(){
		if(move){ 
			monster.move(direction,1);
			if(monster.getX() % 32 == 0 && monster.getY() % 32 == 0 && isUp){
				move = false;
			}
		}
	}
}