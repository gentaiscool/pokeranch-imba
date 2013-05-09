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
	
	private BitmapButton buy;
	
	private Player player;
	
		public Combinatorium(Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			player = _player;
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			colMonster = _player.getAllMonster().values();
			
			int i=0;
			Iterator<Monster> it = colMonster.iterator();
			monsters = new String[colMonster.size()];
			monstersName = new String[colMonster.size()];
			while(it.hasNext()){
				Monster mb = it.next();
				monsters[i] = mb.getName().toString()+" ("+mb.getSpecies().getName().toString()+" Lv."+mb.getLevel()+")";
				monstersName[i] = mb.getName().toString();
				//Log.d("Monster",mb.getName().toString());
				//Log.d("Monster", mb.getSpecies().getName().toString());
				i++;
			}
			
			
			MessageManager.multiChoice("Choose your first Pokemon", monsters, new Action() {
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
						
						MessageManager.prompt("Congratulation! You get "+ newMonster.getSpecies().getName() +"! Give a name to your new lovely monster!", new Action() {					
							@Override
							public void proceed(Object o) {
								// TODO Auto-generated method stub
								addNewMonster(o.toString(), oldMonster1, oldMonster2);
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
			
			
			
			/*sbInfo.append("Welcome to our shop\n");
			sbInfo.append("Take your time and buy the items\n\n\n");
			
			
			
			
			trans = BitmapManager.getInstance().get("trans");
			panel = trans;
			
			buy = new BitmapButton(BitmapManager.getInstance().get("buybutton"),20,200);
			
			monsterBalls = DBLoader.getInstance().getAllBalls();
			statItems = DBLoader.getInstance().getAllStatItems();
			tms = DBLoader.getInstance().getAllTms();
			
			monsterBall = new String[monsterBalls.size()+1];
			
			statItem = new String[statItems.size()+1];
			Log.d("Size", ""+statItems.size());
			tm = new String[tms.size()+1];
			
			int i = 0;
			Iterator<MonsterBall> it = monsterBalls.iterator();
			while(it.hasNext()){
				MonsterBall mb = it.next();
				monsterBall[i] = mb.getName();
				arrMonsterBall.add(mb);
				i++;
			}
			monsterBall[i] = "Back";
			
			i = 0;
			Iterator<StatItem> it2 = statItems.iterator();
			while(it2.hasNext()){
				StatItem si = it2.next();
				statItem[i] = si.getName();
				arrStatItem.add(si);
				i++;
			}
			statItem[i] = "Back";
			
			i = 0;
			Iterator<TM> it3 = tms.iterator();
			while(it3.hasNext()){
				TM teem = it3.next();
				tm[i] = teem.getName();
				arrTM.add(teem);
				i++;
			}
			tm[i] = "Back";
			
			category = new String[4];
			category[0] = "Monster Ball";
			category[1] = "Stat Item";
			category[2] = "TM";
			category[3] = "Back";
			
			scroll = new ScrollComponent(category,220,100,screenHeight,new SelectionListener(){
				@Override
				public void selectAction(int selection) {
					showCategory(selection);
				}
			});
			
			/*
			scroll = new ScrollComponent(monsterBall,220,100,screenHeight,new SelectionListener(){
				@Override
				public void selectAction(int selection) {
					showItem(selection);
				}
			});*/
		}
		
		public void addNewMonster(String o, int firstMonster, int secondMonster){
			newMonster.setName(o.toString());
			player.addMonster(newMonster);
			
			try {
				player.delMonster(monstersName[firstMonster]);
				player.delMonster(monstersName[secondMonster]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			//scroll.onTouchEvent(e, magX, magY);
		}
}
