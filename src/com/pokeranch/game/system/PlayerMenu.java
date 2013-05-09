package com.pokeranch.game.system;

import com.pokeranch.game.system.ScrollComponent.SelectionListener;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class PlayerMenu implements IScreen{
	
	private static String menus[] = {"Items", "Monsters", "Status", "Back"};
	private static ScrollComponent scroll;
	private static PlayerMenu pmenu;
	private static Paint paint;
	private static int defaultColor1, defaultColor2, selectionColor, emptyColor;
	public static void initialize(){
		
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
		pmenu = new PlayerMenu();
	}
	
	protected static void showCategory(int selection) {
		// TODO Auto-generated method stub
		if(selection == 0){
			//keluarin list item
			ScreenManager.getInstance().push(ListItem.getInstance());
		} else if(selection == 1){
			//keluarin list monster
		} else if(selection == 2){
			//keliarin status
		} else if(selection == 3){
			//kembali ke areamanager
			ScreenManager.getInstance().pop();
		}
	}

	public static PlayerMenu getInstance(){
		return pmenu;
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
