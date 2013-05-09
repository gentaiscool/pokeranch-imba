package com.pokeranch.game.system;
import com.pokeranch.game.object.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Monster;
import com.pokeranch.game.object.MonsterBall;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.object.StatItem;
import com.pokeranch.game.object.TM;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;
import com.pokeranch.game.system.ScrollComponent.SelectionListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class PlayerStatus implements IScreen{
	
	private TextComponent text;
	StringBuilder sbInfo = new StringBuilder();
	private BitmapButton close;
	private Bitmap playerImage;
	private Player player;
	public PlayerStatus(Player _player){
		// TODO Auto-generated constructor stub
		Log.d("LM", "chek 1");
		playerImage=BitmapManager.getInstance().get("playerbig");
		close=new BitmapButton(BitmapManager.getInstance().get("close"), 220, 200);
		//ss = new ScrollComponent(context, 100,0);
		player = _player;
		
		//trans = BitmapManager.getInstance().get("trans");
		//panel = trans;
		
		text = new TextComponent("", 10, 120);
		close.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTouchMove() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTouchDown() {
				// TODO Auto-generated method stub
				ScreenManager.getInstance().pop();
			}
		});
		
		text = new TextComponent("", 20, 20);
		text.setText(player.showStatus());
	}
	/*
	public boolean isInt(String s)  // assuming integer is in decimal number system
	{
		for(int a=0;a<s.length();a++)
		{
			if(a==0 && s.charAt(a) == '-') continue;
			if( !Character.isDigit(s.charAt(a)) ) return false;
		}
		return true;
	}*/
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(Color.WHITE);
		//canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(20,90,70,140), null);
		canvas.drawBitmap(playerImage, new Rect(0,0,playerImage.getWidth(), playerImage.getHeight()), new RectF(230,10,315,180), null);
		text.draw(canvas);
		close.draw(canvas);
		//Integer it = player.getMoney();
		//textMoney.setText("Money : " + it.toString());
		//textMoney.draw(canvas);
	}
	
	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
		close.onTouchEvent(e, magX, magY);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
