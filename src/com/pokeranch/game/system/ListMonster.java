package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Item;
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


public class ListMonster implements IScreen{
	private ScreenManager manager;

	private Bitmap monsterImage;
	private Paint paint;
	int curScreenWidth, curScreenHeight;
	private ScrollComponent scroll;
	private Rect rect1;
	private RectF rect2;
	private String[] listMonster;
	private TextComponent text, textMoney;
	Collection<Monster> monster;
	StringBuilder sbInfo = new StringBuilder();
	
	HashMap <String, Monster> playerMonsters = new HashMap<String, Monster>();
	
	private boolean show = false;
	
	private BitmapButton backpack;
	
	private Player player;

	public ListMonster(Player _player, int screenWidth, int screenHeight){
		// TODO Auto-generated constructor stub
		Log.d("LM", "chek 1");
		rect1 = new Rect(0,0,0,0);
		rect2 = new RectF(0,0,0,0);
		monsterImage =null;
		paint = new Paint();
		manager = ScreenManager.getInstance();
		//ss = new ScrollComponent(context, 100,0);
		player = _player;
		playerMonsters = player.getAllMonster();
		
		sbInfo.append("Hello! I'm your backpack\n");
		
		curScreenWidth = screenWidth;
		curScreenHeight = screenHeight;
		
		//trans = BitmapManager.getInstance().get("trans");
		//panel = trans;
		
		backpack = new BitmapButton(BitmapManager.getInstance().get("backpack"),20,50);
		
		Log.d("LM", ""+playerMonsters.size());
		
		monster = playerMonsters.values();
		listMonster = new String[playerMonsters.size()+1];
		
		int i = 0;
		Iterator<Monster> it = monster.iterator();
		Log.d("LM", "Habis Iterate");
		while(it.hasNext()){			
			listMonster[i]=it.next().getName();
			i++;
		}
		Log.d("LM", "Habis naruh ke list");
		listMonster[i] = "Back";
		
		scroll = new ScrollComponent(listMonster,220,100,screenHeight,new SelectionListener(){
			@Override
			public void selectAction(int selection) {
				Log.d("LM", Integer.valueOf(selection).toString());
				showMonster(selection);
			}
		});
		
		Log.d("LM", "Habis scrollcomponent");

		text = new TextComponent("", 10, 120);

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
	
	private void showMonster(final int num){
		Log.d("LM", "awalshowMonster");
		
		if(num == listMonster.length-1){
			Log.d("LM", "kalo back");
			ScreenManager.getInstance().pop();
		}
		else{
			monsterImage = BitmapManager.getInstance().get(player.getMonster(listMonster[num]).getSpecies().getName());
			Log.d("LM", (monsterImage==null)+"");
			Log.d("LM",player.getMonster(listMonster[num]).getSpecies().getName());
			rect1 = new Rect(0,0, monsterImage.getWidth(),monsterImage.getHeight());
			Log.d("LM","abisrect1");
			rect2 = new RectF(10,10,100,100);
			Log.d("LM","abisrect2");
			Log.d("LM", "set isi tulisan");
			StringBuilder sb = new StringBuilder();
			sb.append("Selected Monster : " + listMonster[num] +"\n\n");
			sb.append("**** POKEMON INFO ****\n");
			sb.append(player.getMonster(listMonster[num]).toString());
			text.setText(sb.toString());
			show = true;
		}
		Log.d("LM", "mau keluar dari showMonster");
		
	}
	
	@Override
	public void update() {
		//nothing todo here
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(Color.WHITE);
		scroll.draw(canvas);
		//canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(20,90,70,140), null);
		if(!show){
			text.setText(sbInfo.toString());
		}
		Log.d("LM", "sblum gambar poke");
		if(monsterImage!=null){
			canvas.drawBitmap(monsterImage, rect1, rect2, null);
		}
		Log.d("LM", "stlah gambar poke");
		text.draw(canvas);
		//Integer it = player.getMoney();
		//textMoney.setText("Money : " + it.toString());
		//textMoney.draw(canvas);
		if(!show)
			backpack.draw(canvas);
	}
	
	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
		scroll.onTouchEvent(e, magX, magY);
	}

	
}
