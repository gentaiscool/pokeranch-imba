package com.pokeranch.game.system;

import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.PlayerSaveLoader;
import com.pokeranch.game.system.ScrollComponent.SelectionListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class PlayerMenu implements IScreen{
	
	private String menus[] = {"Items", "Monsters", "Status", "Back"};
	private ScrollComponent scroll;
	private Paint paint;
	private static int defaultColor1, defaultColor2, selectionColor, emptyColor;
	private Player player;
	private static PlayerMenu instance = null;
	
	private PlayerMenu(Player _player){
		
		scroll = new ScrollComponent(menus, 220, 100, (int)MainGameView.realScreenHeight, new SelectionListener(){
			@Override
			public void selectAction(int selection) {
				showCategory(selection);
			}
		});
		defaultColor1 = Color.argb(255,233,233,233);
		defaultColor2 = Color.argb(255,247,247,247);
		selectionColor = Color.argb(255,214,214,214);
		emptyColor = Color.argb(0, 50, 50, 50);
		
		scroll.setDefaultColor1(defaultColor1);
		scroll.setDefaultColor2(defaultColor2);
		scroll.setEmptyColor(emptyColor);
		scroll.setSelectionColor(selectionColor);
		
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTypeface(BitmapManager.getInstance().getTypeface());
		paint.setTextSize(8);
		player = _player;
	}
	
	public static void initialize(Player _player){
		instance=new PlayerMenu(_player);
	}
	public static void release(){
		instance = null;
	}
	
	public static PlayerMenu getInstance() {
		return instance;
	}
	protected void showCategory(int selection) {
		// TODO Auto-generated method stub
		if(selection == 0){
			//keluarin list item
			ListItem.getInstance().refresh();
			ScreenManager.getInstance().push(ListItem.getInstance());
		} else if(selection == 1){
			//keluarin list monster
			ListMonster lm= new ListMonster(player, (int) MainGameView.realScreenWidth, (int)MainGameView.realScreenHeight);
			ScreenManager.getInstance().push(lm);
		} else if(selection == 2){
			//keliarin status
			PlayerStatus ps= new PlayerStatus(player);
			ScreenManager.getInstance().push(ps);
		} else if(selection == 3){
			//kembali ke areamanager
			ScreenManager.getInstance().pop();
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		scroll.draw(canvas);
	}

	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
			scroll.onTouchEvent(e, magX, magY);
	}

}
