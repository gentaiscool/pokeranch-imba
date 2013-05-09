package com.pokeranch.game.system;

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
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;


public class Combinatorium implements IScreen{
	
	private ScreenManager manager;

	private Bitmap panel, trans;

	int curScreenWidth, curScreenHeight;
	private ScrollComponent scroll;
	private String[] monsters, monstersName;
	private TextComponent text, textMoney;
	Collection<MonsterBall> monsterBalls;
	Collection<StatItem> statItems;
	Collection<TM> tms;
	Collection<Monster> colMonster;
	StringBuilder sbInfo = new StringBuilder();
	
	ArrayList<MonsterBall> arrMonsterBall = new ArrayList<MonsterBall>();
	ArrayList<StatItem> arrStatItem = new ArrayList<StatItem>();
	ArrayList<TM> arrTM = new ArrayList<TM>();
	
	private Monster newMonster = new Monster();
	
	private boolean show = false;
	
	private BitmapButton buy, combineButton;
	
	private final Player player;
	
		public Combinatorium(Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			player = _player;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			combineButton = new BitmapButton(BitmapManager.getInstance().get("combine"),50,150);
			combineButton.addTouchListener(new TouchListener() {
				
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
					int i=0;
					
					colMonster = player.getAllMonster().values();
					
					Iterator<Monster> it = colMonster.iterator();
					monsters = new String[colMonster.size()];
					monstersName = new String[colMonster.size()];
					while(it.hasNext()){
						Monster mb = it.next();
						Log.d("name monsterbaru",mb.getName().toString());
						Log.d("Species monsterbaru",mb.getSpecies().getName().toString());
						Log.d("level monsterbaru",""+mb.getLevel());
						monsters[i] = mb.getName().toString()+" ("+mb.getSpecies().getName().toString()+" Lv."+mb.getLevel()+")";
						monstersName[i] = mb.getName().toString();
						//Log.d("Monster",mb.getName().toString());
						//Log.d("Monster", mb.getSpecies().getName().toString());
						i++;
					}
					
					
					MessageManager.multiChoice("Choose two monsters to be combined!", monsters, new Action() {
						@Override
						public void proceed(Object o) {
							// TODO Auto-generated method stub
							boolean[] arrMonster = (boolean[]) o;
							int count = 0;
							int firstMonster = -1, secondMonster = -1;
							
							for(int i=0; i<arrMonster.length;i++){
								if(arrMonster[i]){
									count++;
									if(count == 1){
										firstMonster = i;
									}
									else if(count == 2){
										secondMonster = i;
									}
								}
							}
							if(secondMonster != -1 && count==2){
								//benar
								//MessageManager.alert(monsters[firstMonster]+ " " + monsters[secondMonster]);
								Monster newMonster = null;
								try {
									newMonster = Monster.combineMonster(player,monstersName[firstMonster], monstersName[secondMonster]);
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								//Log.d("test" , newMonster.getSpecies().getName().toString());
								
								final Integer oldMonster1, oldMonster2;
								oldMonster1 = firstMonster; 
								oldMonster2 = secondMonster;
								
								if(newMonster == null){
									Log.d("hehe","null new monster");
								}
								else{
									Log.d("monsterbaru Species jadi", newMonster.getSpecies().getName());
								}
								
								final Monster newMonster1 = newMonster;
								final Integer firstMonster1 = firstMonster;
								final Integer secondMonster1 = secondMonster;
								
								MessageManager.prompt("Congratulation! You get "+ newMonster.getSpecies().getName() +"! Give a name to your new lovely monster!", new Action() {					
									@Override
									public void proceed(Object o) {
										// TODO Auto-generated method stub
										//addNewMonster(o.toString(), oldMonster1, oldMonster2);
										newMonster1.setName(o.toString());
										player.addMonster(newMonster1);
										Log.d("check2", newMonster1.getSpecies().getName().toString());
										
										try {
											player.delMonster(monstersName[firstMonster1]);
											player.delMonster(monstersName[secondMonster1]);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
									@Override
									public void cancel() {
										// TODO Auto-generated method stub
										
									}
								});
							}
							else{
								//salah
								MessageManager.alert("Only 2 Monsters are accepted");
							}
							Log.d("poke", arrMonster[0] +"");
						}
						
						@Override
						public void cancel() {
							// TODO Auto-generated method stub
							
						}
					});
				}
			});
		}
				
		public boolean isInt(String s)  // assuming integer is in decimal number system
		{
			for(int a=0;a<s.length();a++)
			{
				if(a==0 && s.charAt(a) == '-') continue;
				if( !Character.isDigit(s.charAt(a)) ) return false;
			}
			return true;
		}
		
		@Override
		public void update() {
			//nothing todo here
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			combineButton.draw(canvas);
			//canvas.drawColor(Color.WHITE);
			//scroll.draw(canvas);
			//canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(20,90,70,140), null);
			/*if(show)
				buy.draw(canvas);
			else{
				text.setText(sbInfo.toString());
			}
			text.draw(canvas);
			Integer it = player.getMoney();
			textMoney.setText("Money : " + it.toString());
			textMoney.draw(canvas);*/
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			if(show)
				buy.onTouchEvent(e, magX, magY);
			combineButton.onTouchEvent(e, magX, magY);
			//scroll.onTouchEvent(e, magX, magY);
		}
}
