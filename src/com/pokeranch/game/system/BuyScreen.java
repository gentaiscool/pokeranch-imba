package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

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


public class BuyScreen implements IScreen{
	
	private ScreenManager manager;

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
	
	private boolean show = false;
	
	private BitmapButton buy;
	
	private Player player;
	
		public BuyScreen(Player _player, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			//ss = new ScrollComponent(context, 100,0);
			
			player = _player;
			
			sbInfo.append("Welcome to our shop\n");
			sbInfo.append("Take your time and buy the items\n\n\n");
			
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			
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
			
			category = new String[6];
			category[0] = "Monster Ball";
			category[1] = "Stat Item";
			category[2] = "TM";
			category[3] = "Torch";
			category[4] = "Monster Egg";
			category[5] = "Back";
			
			scroll = new ScrollComponent(category,220,100,screenHeight,new SelectionListener(){
				@Override
				public void selectAction(int selection) {
					if(selection!=-1)
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
			
			/*
			newgame = new BitmapButton(BitmapManager.getInstance().get("newgame"),253,66);
			loadgame = new BitmapButton(BitmapManager.getInstance().get("loadgame"),  265, 105);
			helpgame = new BitmapButton(BitmapManager.getInstance().get("helpgame"), 253, 144);
			exitbutton = new BitmapButton(BitmapManager.getInstance().get("exitbutton"), 20, 150);
			pokeball = new BitmapButton(BitmapManager.getInstance().get("pokeball"),100,32);
			logo = new BitmapButton(BitmapManager.getInstance().get("logo"),20,32);
			 */			
			
			text = new TextComponent("", 10, 25);
			textMoney = new TextComponent("", 10, 180);
	
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
		
		private void showMonsterEgg(){
			StringBuilder sb = new StringBuilder();
			sb.append("MonsterEgg\n");
			sb.append("Hatch me if you can\n\n\n");
			sb.append("Price : 1000");

			text.setText(sb.toString());
			show = true;
			buy.addTouchListener(new TouchListener() {
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
					MessageManager.prompt("Give a name for your new hatched monster", new Action() {
						
						@Override
						public void proceed(Object o) {
							// TODO Auto-generated method stub
							if(o.toString().length()!=0){
								Monster newMonster = new Monster(o.toString(), DBLoader.getInstance().getRandomSpecies(1), 5);
								MessageManager.alert("You get "+newMonster.getName()+" (" + newMonster.getSpecies().getName()+")");
								player.addMonster(newMonster);
								player.setMoney(player.getMoney()-1000);
							}
							else if(o.toString().length() == 0){
								MessageManager.alert("The input is not valid for a name. Please retry the transaction process");
							}
							else{
								MessageManager.alert("The input is not valid for a name. Please retry the transaction process");
							}
						}
						
						@Override
						public void cancel() {
							// TODO Auto-generated method stub
							MessageManager.alert("The transaction is aborted");
						}
					});
				}
			});
		}
		
		private void showMonsterBall(final int num){
			Log.d("test", ""+monsterBall.length+" "+num);
			if(num == monsterBall.length-1){
				//back
				scroll = new ScrollComponent(category,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						if(selection!=-1)
						showCategory(selection);
					}
				});
				show = false;
			}
			else{
				StringBuilder sb = new StringBuilder();
				sb.append("Item Type : Monster Ball\n");
				sb.append("Selected Item : " + arrMonsterBall.get(num).getName() +"\n");
				sb.append("Catch Rate : " + arrMonsterBall.get(num).getCatchRate()+"\n\n");
				sb.append("Price : " + arrMonsterBall.get(num).getPrice());
				text.setText(sb.toString());
				show = true;
				buy.addTouchListener(new TouchListener() {
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
						MessageManager.prompt("How many items do you want to buy? (max 100)", new Action() {
							@Override
							public void proceed(Object o) {
								// TODO Auto-generated method stub
								//check
								if(isInt(o.toString())){
									if(o.toString().length()==0){
										MessageManager.alert("Input is expected");
									}
									else if(num > 100){
										MessageManager.alert("The amount exceeds the limit");
									}
									else if (player.getMoney() < arrMonsterBall.get(num).getPrice() * Integer.parseInt(o.toString())){
										MessageManager.alert("Your money is not enough to buy them");
									}
									else{
										MessageManager.alert("You bought "+o.toString() + "x "+ arrMonsterBall.get(num).getName());
										player.addItem(arrMonsterBall.get(num), Integer.parseInt(o.toString()));
										player.setMoney(player.getMoney()-arrMonsterBall.get(num).getPrice() * Integer.parseInt(o.toString()));
									}						
								}
								else MessageManager.alert("Your input is not a valid number");
							}
							
							@Override
							public void cancel() {
								// TODO Auto-generated method stub
							}
						});
					}
				});
			}
		}
		
		private void showStatItem(final int num){
			Log.d("test", ""+statItem.length+" "+num);
			if(num == statItem.length-1){
				//back
				scroll = new ScrollComponent(category,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						showCategory(selection);
					}
				});
				show = false;
			}
			else{
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
				buy.addTouchListener(new TouchListener() {
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
						MessageManager.prompt("How many items do you want to buy? (max 100)", new Action() {
							@Override
							public void proceed(Object o) {
								// TODO Auto-generated method stub
								//check
								if(isInt(o.toString())){
									if(o.toString().length()==0){
										MessageManager.alert("Input is expected");
									}
									else if(num > 100){
										MessageManager.alert("The amount exceeds the limit");
									}
									else if (player.getMoney() < arrStatItem.get(num).getPrice() * Integer.parseInt(o.toString())){
										MessageManager.alert("Your money is not enough to buy them");
									}
									else{
										MessageManager.alert("You bought "+o.toString() + "x "+ arrStatItem.get(num).getName());
										player.addItem(arrStatItem.get(num), Integer.parseInt(o.toString()));
										player.setMoney(player.getMoney()-arrStatItem.get(num).getPrice() * Integer.parseInt(o.toString()));
									}						
								}
								else MessageManager.alert("Your input is not a valid number");
							}
							
							@Override
							public void cancel() {
								// TODO Auto-generated method stub
							}
						});
					}
				});
			}
		}
		
		private void showTM(final int num){
			Log.d("test", ""+tm.length+" "+num);
			if(num == tm.length-1){
				//back
				scroll = new ScrollComponent(category,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						showCategory(selection);
					}
				});
				show = false;
			}
			else{
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
				//if(arrTM.get(num).getSkill().getNextSkill().getNextSkillLevel() == null)
				//	sb.append("Next Skill Level : "+arrTM.get(num).getSkill().getNextSkillLevel()+"\n");
				sb.append("Price : " + arrTM.get(num).getPrice());
				text.setText(sb.toString());
				show = true;
				buy.addTouchListener(new TouchListener() {
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
						MessageManager.prompt("How many items do you want to buy? (max 100)", new Action() {
							@Override
							public void proceed(Object o) {
								// TODO Auto-generated method stub
								//check
								if(isInt(o.toString())){
									if(o.toString().length()==0){
										MessageManager.alert("Input is expected");
									}
									else if(num > 100){
										MessageManager.alert("The amount exceeds the limit");
									}
									else if (player.getMoney() < arrTM.get(num).getPrice() * Integer.parseInt(o.toString())){
										MessageManager.alert("Your money is not enough to buy them");
									}
									else{
										MessageManager.alert("You bought "+o.toString() + "x "+ arrTM.get(num).getName());
										player.addItem(arrTM.get(num), Integer.parseInt(o.toString()));
										player.setMoney(player.getMoney()-arrTM.get(num).getPrice() * Integer.parseInt(o.toString()));
									}						
								}
								else MessageManager.alert("Your input is not a valid number");
							}
							
							@Override
							public void cancel() {
								// TODO Auto-generated method stub
							}
						});
					}
				});
			}
		}
		
		private void showTorch(){
			StringBuilder sb = new StringBuilder();
			sb.append("Torch\n");
			sb.append("Enlight the darkness\n\n\n");
			sb.append("Price : 100");

			text.setText(sb.toString());
			show = true;
			buy.addTouchListener(new TouchListener() {
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
					if(player.haveTorch()){
						MessageManager.alert("You already have one torch");
					}
					else{
						player.setMoney(player.getMoney()-100);
						player.addItem(DBLoader.getInstance().getItem("Torch"), 1);
					}
				}
			});
		}
		
		private void showCategory(int num){
			switch(num){
			case 0:
				Log.d("Category", " "+num);
				scroll = new ScrollComponent(monsterBall,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						Log.d("test",""+selection);
						showMonsterBall(selection);
					}
				});
				break;
			case 1:
				Log.d("Category", " "+num);
				scroll = new ScrollComponent(statItem,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						Log.d("test",""+selection);
						showStatItem(selection);
					}
				});
				break;
			case 2:
				Log.d("Category", " "+num);
				scroll = new ScrollComponent(tm,220,100,curScreenHeight,new SelectionListener(){
					@Override
					public void selectAction(int selection) {
						Log.d("test",""+selection);
						showTM(selection);
					}
				});
				break;
			case 3:
				Log.d("Category", " "+num);
				showTorch();
				break;
			case 4:
				Log.d("Category", " "+num);
				showMonsterEgg();
				break;
			case 5: 
				//terminates
				ScreenManager.getInstance().pop();
				break;
			}
		}
		
		private void showItem(int num){
			show = true;
			
			if(num==-1){
				panel = trans;
				text.setText("");
			}else{
				panel = BitmapManager.getInstance().get(monsterBall[num]);
				MonsterBall mb = DBLoader.getInstance().getMonsterBall(monsterBall[num]);
				//Species s = DBLoader.getInstance().getSpecies(monsterBall[num]);
				String snum = num < 10 ? "00" + (num+1) : "0" + (num+1);
				StringBuilder sb = new StringBuilder();
				sb.append(mb.getName() +"\n");
				sb.append("Monster Ball");
				
				//sb.append(s.getElement().getName() +" Monster\n");
				/*if(s.getEvoSpecies()==null){
					sb.append("No Evolution\n");
				}else{
					sb.append("Evolution:\n");
					Species s1 = s;
					while(s1.getEvoSpecies()!=null){
						sb.append(s1.getEvoSpecies().getName() + " - Lv. " + s1.getEvoLevel() + "\n");
						s1 = s1.getEvoSpecies();
					}
				}
				sb.append("\nBase Skill :\n");
				for(int i = 0; i < s.getBaseSkillNum(); i++){
					sb.append((i+1) +". "+s.getBaseSkill(i).getName() + "\n");
				}
				*/
				text.setText(sb.toString());
			}
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
			if(show)
				buy.draw(canvas);
			else{
				text.setText(sbInfo.toString());
			}
			text.draw(canvas);
			Integer it = player.getMoney();
			textMoney.setText("Money : " + it.toString());
			textMoney.draw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			if(show)
				buy.onTouchEvent(e, magX, magY);
			scroll.onTouchEvent(e, magX, magY);
		}
}
