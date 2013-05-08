package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.util.Log;

public class Area {
	private AreaManager am;
	private String name;
	private Tile field[][];
	private int row, column;
	public Area(String n, int r, int c){
		name = n;
		field = new Tile[r][c];
		row = r;
		column= c;
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
				field[i][j].drawBG(canvas, i, j, am.getCurX(), am.getCurY(), am);
			}
		}
	}
	
	public void drawObj(Canvas canvas){
		//gambar belakang
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//gambar bitmap ke layar
				//terapkan paint pada koordinat yg bukan user
				Log.d("harits3", "drawObj curArea kepanggil");
				field[i][j].drawObj(canvas, i, j, am.getCurX(), am.getCurY(), am);
			}
		}
	}
	
	public void update(){
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
}
