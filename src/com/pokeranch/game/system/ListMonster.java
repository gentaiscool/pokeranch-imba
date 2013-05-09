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
	private Bitmap monsterImage, backpack;
	private BitmapButton dismiss, setmain;
	private Paint paint;
	int curScreenWidth, curScreenHeight;
	private ScrollComponent scroll;
	private Rect rect1,rect3;
	private RectF rect2,rect4;
	private String[] listMonster;
	private TextComponent text, textMainMonster;
	Collection<Monster> monster;
	StringBuilder sbInfo = new StringBuilder();
	
	HashMap <String, Monster> playerMonsters = new HashMap<String, Monster>();
	
	private boolean show = false;
	
	private Player player;
	
	private DelayedAction action;
	
	public ListMonster(Player _player, int screenWidth, int screenHeight){
		action = null;
		Log.d("LM", "chek 1");
		backpack = BitmapManager.getInstance().get("backpack");
		rect1 = new Rect(0,0,0,0);
		rect2 = new RectF(0,0,0,0);
		rect3 = new Rect(0,0,backpack.getWidth(), backpack.getHeight());
		rect4 = new RectF(20,20,200,200);
		monsterImage =null;
		dismiss = new BitmapButton(BitmapManager.getInstance().get("dismiss"), 125, 10);
		setmain = new BitmapButton(BitmapManager.getInstance().get("setmain"), 125, 45);
		
		dismiss.setVisible(false);
		setmain.setVisible(false);
		paint = new Paint();
		//ss = new ScrollComponent(context, 100,0);
		player = _player;
		playerMonsters = player.getAllMonster();
		
		sbInfo.append("Hello! I'm your backpack\n");
		
		curScreenWidth = screenWidth;
		curScreenHeight = screenHeight;
		
		//trans = BitmapManager.getInstance().get("trans");
		//panel = trans;
		
		Log.d("LM", ""+playerMonsters.size());
		
		listMonster = player.buildArrayMonster();
		Log.d("LM", "habis assign array string");		
		listMonster[listMonster.length-1] = "Back";
		
		if (listMonster.length!=2) {
			dismiss.setBitmap(BitmapManager.getInstance().get("dismiss"));
			dismiss.setEnable(true);
		} else {
			dismiss.setBitmap(BitmapManager.getInstance().get("di_dismiss"));
			dismiss.setEnable(false);
		}
	
		scroll = new ScrollComponent(listMonster,220,100,screenHeight,new SelectionListener(){
			@Override
			public void selectAction(int selection) {
				Log.d("LM", Integer.valueOf(selection).toString());
				showMonster(selection);
			}
		});
		
		scroll.setDefaultColor1(Color.argb(255, 61, 158, 255));
		scroll.setDefaultColor2(Color.argb(255, 17, 240, 166));		
		Log.d("LM", "Habis scrollcomponent");
		textMainMonster = new TextComponent("",125,100);
		text = new TextComponent("", 10, 200);
	}

	private void showMonster(final int num){
		Log.d("LM", "awalshowMonster");
	
		dismiss.addTouchListener(new TouchListener() {
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
				Monster m = player.getMonster(listMonster[num]);
				MessageManager.confirm("You are about to dismiss "+listMonster[num]+". Are you sure?", new Action() {

					@Override
					public void proceed(Object o) {
						// TODO Auto-generated method stub
						action = new DelayedAction() {
							@Override
							public int getDelay() {
								return 1;
							}
							@Override
							public void doAction() {
								try {
									Log.d("LM","size monsters sebelum delete "+player.getAllMonster().size());
									Log.d("LM","size listmonster sebelum delete "+player.getAllMonster().size());
									Log.d("LM", "CurMonster sebelum del "+ player.getCurrentMonster().getName());
									player.delMonster(listMonster[num]);
									Log.d("LM", "CurMonster setelah del "+ player.getCurrentMonster().getName());
									Log.d("LM","size monsters setelah delete "+player.getAllMonster().size());
									listMonster = player.buildArrayMonster();

									Log.d("LM", "abis build");
									listMonster[listMonster.length-1] = "Back";
									Log.d("LM", "abis nambah back");
									Log.d("LM","size listmonster setelah delete "+player.getAllMonster().size());
									
									scroll.setItem(listMonster);
									
									if (listMonster.length==2) {//monster tinggal 1
										dismiss.setBitmap(BitmapManager.getInstance().get("disableddismiss"));
										dismiss.setEnable(false);
									} //monster lebih dari 1
									
						
									Log.d("LM", "abis set Item");
								} catch (Exception e) {
								}
							}
						};
					}
		
					@Override
					public void cancel() {
						// TODO Auto-generated method stub
					}			
				});
			}
		});

		setmain.addTouchListener(new TouchListener() {
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
				Monster m = player.getMonster(listMonster[num]);
				MessageManager.confirm("Do you want to set "+listMonster[num]+" as your main Monster?", new Action() {

					@Override
					public void proceed(Object o) {
						// TODO Auto-generated method stub
						player.setCurrentMonster(listMonster[num]);
						listMonster = player.buildArrayMonster();
						Log.d("LM", "abis build");
						listMonster[listMonster.length-1] = "Back";
						Log.d("LM", "abis nambah back");
						Log.d("LM","size listmonster setelah delete "+player.getAllMonster().size());
						
						scroll.setItem(listMonster);
					}
		
					@Override
					public void cancel() {
						// TODO Auto-generated method stub
					}			
				});
			}
		});

		if(num == listMonster.length-1){
			Log.d("LM", "kalo back");
			ScreenManager.getInstance().pop();
		}
		else{
			monsterImage = BitmapManager.getInstance().get(player.getMonster(listMonster[num]).getSpecies().getName());
			rect1 = new Rect(0,0, monsterImage.getWidth(),monsterImage.getHeight());
			rect2 = new RectF(10,10,100,100);
			StringBuilder sb = new StringBuilder();
			sb.append("Selected Monster : " + listMonster[num] +"\n\n");
			sb.append("**** POKEMON INFO ****\n");
			sb.append(player.getMonster(listMonster[num]).toString());
			text.setText(sb.toString());
			show = true;
			if(!dismiss.isVisible()) dismiss.setVisible(true);
			if(!setmain.isVisible()) setmain.setVisible(true);
		}
		Log.d("LM", "mau keluar dari showMonster");
		if(listMonster[num].equals(player.getCurrentMonster().getName())) {//kalo yang dipilih sama dengan text
			textMainMonster.setText("MAIN MONSTER");
		} else {
			textMainMonster.setText("");
		}
		Log.d("LM", "mau keluar banget dari showMonster");
	}
	
	@Override
	public void update() {
		if(action!=null){
			action.update();
			if(action.finished()) action=null;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(Color.WHITE);
		scroll.draw(canvas);
		//canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(20,90,70,140), null);
		if(!show){
			text.setText(sbInfo.toString());
			canvas.drawBitmap(backpack, rect3,rect4, null);
		}
		if(monsterImage!=null){
			canvas.drawBitmap(monsterImage, rect1, rect2, null);
		}
		text.draw(canvas);
		textMainMonster.draw(canvas);
		dismiss.draw(canvas);
		setmain.draw(canvas);
		//Integer it = player.getMoney();
		//textMoney.setText("Money : " + it.toString());
		//textMoney.draw(canvas);

	}
	
	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		// TODO Auto-generated method stub
		scroll.onTouchEvent(e, magX, magY);
		dismiss.onTouchEvent(e, magX, magY);
		setmain.onTouchEvent(e, magX, magY);
	}

	
}
