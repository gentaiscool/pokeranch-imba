package com.pokeranch.game.system;

import android.graphics.Canvas;
import android.view.MotionEvent;
import com.pokeranch.game.object.*;

public class BattleScreen implements IScreen {
	private Player player1, player2;
	private BitmapButton attack, useItem, change, escape;
	
	public BattleScreen(Player player1, Player player2){
		this.player1 = player1;
		this.player2 = player2;
		
		attack = new BitmapButton(BitmapManager.getInstance().get("attackbutton"), 0, 0);
		useItem = new BitmapButton(BitmapManager.getInstance().get("itembutton"), 0, 0);
		change = new BitmapButton(BitmapManager.getInstance().get("changebutton"), 0, 0);
		escape = new BitmapButton(BitmapManager.getInstance().get("escapebutton"), 0, 0);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouchEvent(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Canvas canvas, int mag) {
		// TODO Auto-generated method stub
		
	}

}