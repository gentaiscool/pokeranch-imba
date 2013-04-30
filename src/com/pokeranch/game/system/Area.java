package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.pokeranch.game.system.MainGameView.ButtonClick;

public class Area implements IScene{
	private Sprite monster, bg;
	//private ArrayList<Area>
	private boolean move = false;
	private int direction = 0;
	public Area(){
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
	
	public void draw(Canvas canvas){
		bg.draw(canvas);
		monster.draw(canvas);
	}
	
	public void update(){
		if(move) 
			monster.move(direction,1);
	}
	
}
