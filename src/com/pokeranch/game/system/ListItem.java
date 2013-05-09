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
import com.pokeranch.game.object.Skill;
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
import android.view.MotionEvent;
import android.widget.Toast;
import android.util.Log;


public class ListItem implements IScreen{
	
	private ScreenManager manager;
	private static ListItem listitem;
	private Bitmap panel, trans;

	int curScreenWidth, curScreenHeight;
	private ScrollComponent scroll;
	private String[] monsterBall, statItem, tm, category;
	private TextComponent text, textMoney;
	Collection<MonsterBall> monsterBalls;
	Collection<StatItem> statItems;
	Collection<TM> tms;
	StringBuilder sbInfo = new StringBuilder();
	
	ArrayList<MonsterBall> arrMonsterBall = new ArrayList<MonsterBall>();
	ArrayList<StatItem> arrStatItem = new ArrayList<StatItem>();
	ArrayList<TM> arrTM = new ArrayList<TM>();
	
	ArrayList<Integer> numMonsterBall = new ArrayList<Integer>();
	ArrayList<Integer> numStatItem = new ArrayList<Integer>();
	ArrayList<Integer> numTM = new ArrayList<Integer>();
	
	
	HashMap <String, Integer> arrItems = new HashMap<String, Integer>();
	
	private boolean show = false;
	
	private BitmapButton backpack, use;
	
	private Player player;
	private int state = 0, index = 0;
	
		public ListItem(Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			//ss = new ScrollComponent(context, 100,0);
			
			player = _player;
			arrItems = player.getAllItem();
			
			sbInfo.append("Hello! I'm your backpack\n");
			
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			
			trans = BitmapManager.getInstance().get("trans");
			panel = trans;
			
			backpack = new BitmapButton(BitmapManager.getInstance().get("backpack"),20,50);
			use = new BitmapButton(BitmapManager.getInstance().get("use"),100,180);
			
			use.addTouchListener(new TouchListener() {
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
					boolean continueNow = true;
					if(state == 2){
						if(numStatItem.get(index) == 0){
							continueNow = false;
						}
					}
					else if(state == 3){
						if(numStatItem.get(index) == 0){
							continueNow = false;
						}
					}
					if(!continueNow){
						MessageManager.alert("No item");
					}
					else{
						int i=0;
						Collection<Monster> colMonster = player.getAllMonster().values();
						Iterator<Monster> it = colMonster.iterator();
						String[] monsters = new String[colMonster.size()];
						String[] monstersName = new String[colMonster.size()];
						ArrayList<Monster> monsterData = new ArrayList<Monster>();
						while(it.hasNext()){
							Monster mb = it.next();
							monsters[i] = mb.getName().toString()+" ("+mb.getSpecies().getName().toString()+" Lv."+mb.getLevel()+")";
							monstersName[i] = mb.getName().toString();
							monsterData.add(mb);
							i++;
						}
						final ArrayList<Monster> monsterData2 = monsterData;
						
						if(state == 2){
							MessageManager.singleChoice("Choose your monster!", monsters, new Action(){
								@Override
								public void proceed(Object o) {
									Monster mm = new Monster();
									mm = monsterData2.get(Integer.parseInt(o.toString()));
									
									mm.giveItem(arrStatItem.get(index));
									try {
										player.delItem(arrStatItem.get(index).getName(), 1);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									numStatItem.set(index, numStatItem.get(index) - 1);
									statItem[index] = ""+ numStatItem.get(index)+"x "+(arrStatItem.get(index).getName());
									scroll = new ScrollComponent(statItem,220,100,curScreenHeight,new SelectionListener(){
										@Override
										public void selectAction(int selection) {
											showStatItem(selection);
										}
									});
								}
		
								@Override
								public void cancel() {
									// TODO Auto-generated method stub
									
								}
							});
						}
						else if(state == 3){
							MessageManager.singleChoice("Choose your monster!", monsters, new Action(){
								@Override
								public void proceed(Object o) {
									Monster mm = new Monster();
									mm = monsterData2.get(Integer.parseInt(o.toString()));
									try {
										//player.delItem(arrStatItem.get(index).getName(), 1);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									Collection<Skill> skills = mm.getAllSkill().values();
									String[] skillName;
									Iterator<Skill> it2 = skills.iterator();
									skillName = new String[skills.size()];
									int ii2 = 0;
									
									boolean found = false;
									
									Collection<TM> tms = DBLoader.getInstance().getAllTms();
									Iterator<TM> it3 = tms.iterator();
									String[] arrayTM = new String[tms.size()];
									int iii = 0;
									while(it3.hasNext()){
										arrayTM[iii] = it3.next().getName();
										iii++;
									}
									
									while(it2.hasNext()){
										Skill ss = it2.next();
										skillName[ii2] = ss.getName();
										ii2++;
										//log.d("heheheheheh", "index = " + index);
										//log.d("hehehheehhe", "name = "+skillName[ii2-1]);
										if(skillName[ii2-1].equals(arrTM.get(index).getName().toString())){
											found = true;
										}
									}
									if(found){
										MessageManager.alert("Your monster already learned this TM");
									}
									else{
										if(skills.size() < 4){
											//bisa langsung add
											mm.addSkill(arrTM.get(index).getSkill());
											MessageManager.alert(""+arrTM.get(index).getSkill().getName()+" has added to the monster");
											numTM.set(index, numTM.get(index) - 1);
											tm[index] = ""+ numTM.get(index)+"x "+(arrTM.get(index).getName());
											scroll = new ScrollComponent(tm,220,100,curScreenHeight,new SelectionListener(){
												@Override
												public void selectAction(int selection) {
													showTM(selection);
												}
											});
										}
										else{
											
											final Monster mm2 = mm;
											final String[] arrayTM2 = skillName;
											MessageManager.singleChoice("You have to delete a skill before you can learn the new TM", skillName, new Action() {
												
												@Override
												public void proceed(Object o) {
													// TODO Auto-generated method stub
													//log.d("ubah",""+DBLoader.getInstance().getSkill(arrayTM2[Integer.parseInt(o.toString())]).getName());
													mm2.delSkill(DBLoader.getInstance().getSkill(arrayTM2[Integer.parseInt(o.toString())]));
													numTM.set(index, numTM.get(index) - 1);
													tm[index] = ""+ numTM.get(index)+"x "+(arrTM.get(index).getName());
													
													mm2.addSkill(arrTM.get(index).getSkill());
													MessageManager.alert(""+arrTM.get(index).getSkill().getName()+" has added to the monster");
													
													scroll = new ScrollComponent(tm,220,100,curScreenHeight,new SelectionListener(){
														@Override
														public void selectAction(int selection) {
															showTM(selection);
														}
													});
												}
												
												@Override
												public void cancel() {
													// TODO Auto-generated method stub
													
												}
											});
											
										}
									}
									
									
								}
		
								@Override
								public void cancel() {
									// TODO Auto-generated method stub
									
								}
							});
							
						}
					}
				}
			});
			
			monsterBalls = DBLoader.getInstance().getAllBalls();
			statItems = DBLoader.getInstance().getAllStatItems();
			tms = DBLoader.getInstance().getAllTms();
			
			monsterBall = new String[monsterBalls.size()+1];
			statItem = new String[statItems.size()+1];
			tm = new String[tms.size()+1];
			
			int i = 0;
			Iterator<MonsterBall> it = monsterBalls.iterator();
			while(it.hasNext()){
				MonsterBall mb = it.next();
				if(arrItems.get(mb.getName())== null){
					monsterBall[i] = "0x "+mb.getName();
					numMonsterBall.add(0);
				}
				else{
					monsterBall[i] = ""+ arrItems.get(mb.getName())+"x "+mb.getName();
					numMonsterBall.add(arrItems.get(mb.getName()));
				}
				arrMonsterBall.add(mb);
				i++;
			}
			monsterBall[i] = "Back";
			
			i = 0;
			Iterator<StatItem> it2 = statItems.iterator();
			while(it2.hasNext()){
				StatItem si = it2.next();
				if(arrItems.get(si.getName())== null){
					statItem[i] = "0x "+si.getName();
					numStatItem.add(0);
				}
				else{
					statItem[i] = ""+ arrItems.get(si.getName())+"x "+si.getName();
					numStatItem.add(arrItems.get(si.getName()));
				}
				arrStatItem.add(si);
				i++;
			}
			statItem[i] = "Back";
			
			i = 0;
			Iterator<TM> it3 = tms.iterator();
			while(it3.hasNext()){
				TM teem = it3.next();
				if(arrItems.get(teem.getName())==null){
					tm[i] = "0x "+teem.getName();
					numTM.add(0);
				}
				else{
					tm[i] = ""+ arrItems.get(teem.getName())+"x "+teem.getName();
					numTM.add(arrItems.get(teem.getName()));
				}
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
			
			text = new TextComponent("", 10, 25);
			textMoney = new TextComponent("", 10, 180);
	
		}
		
		public static void initialize(Player _player, int screenWidth, int screenHeight){
			listitem = new ListItem(_player, screenWidth, screenHeight);
		}

		public static ListItem getInstance(){
			return listitem;
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
		
		private void showMonsterBall(final int num){
			state = 1;
			//log.d("test", ""+monsterBall.length+" "+num);
			index = num;
			if(num == monsterBall.length-1){
				//back
				state = 0;
				scroll = new ScrollComponent(category,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						showCategory(selection);
					}
				});
				show = false;
			}
			else{
				if(num!=-1){
					StringBuilder sb = new StringBuilder();
					sb.append("Item Type : Monster Ball\n");
					sb.append("Selected Item : " + arrMonsterBall.get(num).getName() +"\n");
					sb.append("Catch Rate : " + arrMonsterBall.get(num).getCatchRate()+"\n\n");
					sb.append("Price : " + arrMonsterBall.get(num).getPrice());
					text.setText(sb.toString());
					show = true;
				}
			}
		}
		
		private void showStatItem(final int num){
			state = 2;
			//log.d("test", ""+statItem.length+" "+num);
			index = num;
			if(num == statItem.length-1){
				//back
				scroll = new ScrollComponent(category,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						showCategory(selection);
					}
				});
				show = false;
				state = 0;
			}
			else{
				if(num!=-1){
					StringBuilder sb = new StringBuilder();
					sb.append("Item Type : Stat Item\n");
					sb.append("Selected Item : " + arrStatItem.get(num).getName() +"\n\n");
					sb.append("**** Powers ****\n");
					sb.append("Attack : " + arrStatItem.get(num).getItemEffect().getAttack()+"\n");
					sb.append("Defense : " + arrStatItem.get(num).getItemEffect().getDefense()+"\n");
					sb.append("HP : " + arrStatItem.get(num).getItemEffect().getHP()+"\n");
					sb.append("MP : " + arrStatItem.get(num).getItemEffect().getMP()+"\n");
					sb.append("Special effects : " + arrStatItem.get(num).getItemEffect().getEffect().toString()+"\n\n");
					sb.append("Price : " + arrStatItem.get(num).getPrice());
					text.setText(sb.toString());
					show = true;
			
				}
			}
		}
		
		private void showTM(final int num){
			state = 3;
			index = num;
			//log.d("test", ""+tm.length+" "+num);
			if(num == tm.length-1){
				//back
				scroll = new ScrollComponent(category,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						showCategory(selection);
					}
				});
				show = false;
				state = 0;
			}
			else{
				if(num!=-1){
					StringBuilder sb = new StringBuilder();
					sb.append("Item type : TM\n");
					sb.append("Selected Item : " + tm[num] +"\n\n\n");
					sb.append("**** SKILL INFORMATION ****\n");
					sb.append("Skill Name : " + arrTM.get(num).getSkill().getName()+"\n");
					sb.append("Cost : "+arrTM.get(num).getSkill().getCost()+"\n");
					sb.append("Damage : " + arrTM.get(num).getSkill().getDamage()+"\n");
					sb.append("Element : " + arrTM.get(num).getSkill().getElement().getName()+"\n");
					if(arrTM.get(num).getSkill().getNextSkill()!=null)
						sb.append("Next Skill : " + arrTM.get(num).getSkill().getNextSkill().getName()+"\n");
					sb.append("Price : " + arrTM.get(num).getPrice());
					text.setText(sb.toString());
					show = true;
				}
			}
		}
		
		private void showCategory(int num){
			state = 0;
			switch(num){
			case 0:
				////log.d("Category", " "+num);
				scroll = new ScrollComponent(monsterBall,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						////log.d("test",""+selection);
						showMonsterBall(selection);
					}
				});
				break;
			case 1:
				//log.d("Category", " "+num);
				scroll = new ScrollComponent(statItem,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						////log.d("test",""+selection);
						showStatItem(selection);
					}
				});
				break;
			case 2:
				//log.d("Category", " "+num);
				scroll = new ScrollComponent(tm,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						//log.d("test",""+selection);
						showTM(selection);
					}
				});
				break;
			case 3:
				//terminates
				ScreenManager.getInstance().pop();
				break;
			}
		}
		
		@Override
		public void update() {
			//nothing todo here
			
			
		}
		
		public void refresh(){
			monsterBalls = DBLoader.getInstance().getAllBalls();
			statItems = DBLoader.getInstance().getAllStatItems();
			tms = DBLoader.getInstance().getAllTms();
			
			monsterBall = new String[monsterBalls.size()+1];
			statItem = new String[statItems.size()+1];
			tm = new String[tms.size()+1];
			
			int i = 0;
			Iterator<MonsterBall> it = monsterBalls.iterator();
			while(it.hasNext()){
				MonsterBall mb = it.next();
				if(arrItems.get(mb.getName())== null){
					monsterBall[i] = "0x "+mb.getName();
					numMonsterBall.add(0);
				}
				else{
					monsterBall[i] = ""+ arrItems.get(mb.getName())+"x "+mb.getName();
					numMonsterBall.add(arrItems.get(mb.getName()));
				}
				arrMonsterBall.add(mb);
				i++;
			}
			monsterBall[i] = "Back";
			
			i = 0;
			Iterator<StatItem> it2 = statItems.iterator();
			while(it2.hasNext()){
				StatItem si = it2.next();
				if(arrItems.get(si.getName())== null){
					statItem[i] = "0x "+si.getName();
					numStatItem.add(0);
				}
				else{
					statItem[i] = ""+ arrItems.get(si.getName())+"x "+si.getName();
					numStatItem.add(arrItems.get(si.getName()));
				}
				arrStatItem.add(si);
				i++;
			}
			statItem[i] = "Back";
			
			i = 0;
			Iterator<TM> it3 = tms.iterator();
			while(it3.hasNext()){
				TM teem = it3.next();
				if(arrItems.get(teem.getName())==null){
					tm[i] = "0x "+teem.getName();
					numTM.add(0);
				}
				else{
					tm[i] = ""+ arrItems.get(teem.getName())+"x "+teem.getName();
					numTM.add(arrItems.get(teem.getName()));
				}
				arrTM.add(teem);
				i++;
			}
			tm[i] = "Back";
			
			category = new String[4];
			category[0] = "Monster Ball";
			category[1] = "Stat Item";
			category[2] = "TM";
			category[3] = "Back";
			
			scroll = new ScrollComponent(category,220,100,curScreenHeight,new SelectionListener(){
				@Override
				public void selectAction(int selection) {
					showCategory(selection);
				}
			});
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
			text.draw(canvas);
			Integer it = player.getMoney();
			textMoney.setText("Money : " + it.toString());
			textMoney.draw(canvas);
			if(!show)
				backpack.draw(canvas);
			if(state == 2 || state == 3){
				use.draw(canvas);
			}
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			scroll.onTouchEvent(e, magX, magY);
			if(state == 2 || state == 3){
				use.onTouchEvent(e, magX, magY);
			}
		}
}
