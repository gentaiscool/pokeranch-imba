package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.UrlQuerySanitizer.ParameterValuePair;
import android.util.Log;
import android.view.MotionEvent;

import com.pokeranch.game.object.*;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;

public class BattleScreen implements IScreen {
	//gui
	private BitmapButton attack, useItem, change, escape;
	private ArrayList<Touchables> touch;
	private Bitmap background, bar;
	private SkillAnimation animation;
	private Bitmap poke1, poke2;
	private int x1, y1, x2, y2; //posisi gambar avatar pokemon
	private int _x1, _y1, _x2, _y2; //posisi asli gambar
	private int geserTop = 58; //buat geser layout background ke atas
	private TextComponent message;
	private BattleStatusBar stat1, stat2;
	private boolean clicked = false;
	
	private DelayedAction delayAction = null, pokeAnim = null;
	
	//system
	public enum BattleMode {WILD, STADIUM, PVP};
	private enum BattleState {START, WAIT_INPUT, NO_INPUT, AI_MOVE, 
							ANIMATING_SKILL, ANIMATING_HEALTH, ANIMATING_OUT, ANIMATING_IN, 
							CATCH, NAMING, DELAY, EVO_INFO, MATI, END};
	
	private Player player1, player2, current, enemy;
	private int turn; //player no berapa yg sedang jalan
	private BattleMode mode;
	private BattleState state;
	
	public BattleScreen(Player player1, Player player2, BattleMode mode){
		this.player1 = player1;
		this.player2 = player2;
		this.mode = mode;
		current = this.player1;
		enemy = this.player2;
		
		if (mode == BattleMode.WILD){
			StringBuilder ss = new StringBuilder("A Wild ");
			ss.append(player2.getCurrentMonster().getName() + " Appear!");
			message = new TextComponent(ss.toString(), 10, (int) MainGameView.standardHeight - 45);
		}else{
			StringBuilder ss = new StringBuilder("Trainer ");
			ss.append(player2.getName() + " Challenge You!");
			message = new TextComponent(ss.toString(), 10, (int) MainGameView.standardHeight - 45);
		}
		
		state = BattleState.START;
		turn = 1;
		
		//init gui
		
		_x1 = 5;
		_y1 = 115 - geserTop;
		
		_x2 = 175;
		_y2 = 0;
		
		x1 = _x1;
		y1 = _y1;
		
		x2 = _x2;
		y2 = _y2;
		
		refreshImage();
		
		background = BitmapManager.getInstance().get("battle_day_land");
		bar = BitmapManager.getInstance().get("battle_bar");
		
		stat1 = new BattleStatusBar(player1.getCurrentMonster(),(int) MainGameView.standardWidth - 125,(int) MainGameView.standardHeight - 60 - geserTop);
		stat2 = new BattleStatusBar(player2.getCurrentMonster(),10,10);
		
		touch = new ArrayList<Touchables>();
		animation = null;
		
		int buttonTop = 28;
		int buttonLeft = 60;
		int marginTop = (int) MainGameView.standardHeight - 56;
		int marginLeft = 202;
		
		attack = new BitmapButton(BitmapManager.getInstance().get("attackbutton"), marginLeft, marginTop);
		useItem = new BitmapButton(BitmapManager.getInstance().get("itembutton"), marginLeft + buttonLeft, marginTop);
		change = new BitmapButton(BitmapManager.getInstance().get("changebutton"), marginLeft, marginTop + buttonTop);
		escape = new BitmapButton(BitmapManager.getInstance().get("escapebutton"), marginLeft + buttonLeft, marginTop + buttonTop);
		
		initListener();
	}
	
	@Override
	public void update() {
		if(delayAction!=null){
			delayAction.update();
			if (delayAction.finished()){
				delayAction=null;
			}
		}
		
		if(pokeAnim!=null){
			pokeAnim.updateFrequently(3 * GameLoop.MAX_FPS / 60);
			if (pokeAnim.finished()){
				pokeAnim=null;
				stat1.setVisible(true);
				stat2.setVisible(true);
				
				if(state==BattleState.ANIMATING_IN){
					x1=_x1;
					x2=_x2;
					nextTurn();
				}else if(state==BattleState.ANIMATING_OUT){
					state = BattleState.MATI;
				}
			}
		}
		
		switch(state){
		case ANIMATING_SKILL:
			//setelah kena attack
			animation.update();
			if(animation.finished()) {
				enemy.getCurrentMonster().inflictDamage(animation.getSkill(), current.getCurrentMonster());
				current.getCurrentMonster().updateStatusBy(animation.getSkill().getCost());
				stat1.fetchData();
				stat2.fetchData();
				state = BattleState.ANIMATING_HEALTH;
			}
		break;
		case ANIMATING_HEALTH:
			//setelah animating skill
			stat1.update();
			stat2.update();
			if(stat1.animationFinished() && stat2.animationFinished())
				if(!cekmati()) nextTurn();
		break;
		case MATI:
			//setelah animating_out
			Monster m = enemy.getCurrentMonster();
			message.setText(m.getName() + " fainted.");
			
			if(turn==1 && mode!=BattleMode.PVP){
				message.appendText(current.getCurrentMonster().getName() + " get " + m.getBonusExp() + " EXP");
				int hasil = current.getCurrentMonster().addExp(m.getBonusExp());
				cekLevelAndEvo(hasil);
			}
							
			if (state != BattleState.EVO_INFO) autochange();
		break;
		default:
		}
	}
	
	private void autochange(){
		try{	
			enemy.setCurrentMonster(enemy.getNextMonster());
			
			refreshStatBar();
			refreshImage();
			if(turn==1){
				message.appendText("Enemy use " + enemy.getCurrentMonster().getName());
			}else{
				message.appendText("You use " + enemy.getCurrentMonster().getName());
			}
			
			state=BattleState.ANIMATING_IN;
			
			pokeAnim = new PokeIn();
			
		}catch (Exception e){
			state = BattleState.END;
			endBattle();
		}
	}
	
	private void change(int c){
		Collection<Monster> mons = current.getAllMonster().values();
		
		Monster [] monster = new Monster[mons.size() - 1];
		int n = 0;
		for(Monster m : mons){
			if (!m.getName().equals(current.getCurrentMonster().getName())){
				monster[n] = m;
				n++;
			}
		}
		
		Monster m = monster[c];
		current.setCurrentMonster(m);
		refreshImage();
		refreshStatBar();
		
		
		if(mode==BattleMode.PVP)
			message.setText("Player " + turn + " use " + m.getName());
		else
			message.setText(((turn==1)? "You use " : "Enemy use ") + m.getName());

		state = BattleState.DELAY;
		delayAction = new DelayerTurn();
	}
	
	private void endBattle(){
		String s1 = mode==BattleMode.PVP ? "Player 1" : "You";
		String s2 = mode==BattleMode.PVP ? "Player 2" : "Enemy";
		boolean p1win;
		
		if(turn==1){
			p1win = true;
			message.setText(s2 + " lose!");
			poke2 = BitmapManager.getInstance().get("trans");
			stat2.setVisible(false);
			stat1.refreshData();
		}else{
			p1win = false;
			message.setText(s1 + " lose!");
			poke1 = BitmapManager.getInstance().get("trans");
			stat1.setVisible(false);
			stat2.refreshData();
		}
		
		if(mode==BattleMode.PVP) return;
		
		if(p1win){
			player1.addWin(1);
			player1.addBattle(1);
			int duit;
			if(mode==BattleMode.WILD){
				duit = player2.getCurrentMonster().getBonusCash();
			}else{
				duit = player2.getMoney();
			}
			
			player1.setMoney(player1.getMoney()+duit);
			message.appendText("You get $" + duit);
		}else{
			player1.addLose(1);
			player1.addBattle(1);
		}
		
		
	}
	
	private void attack(Skill sk){
		int x = turn==1? x2 : x1;
		int y = turn==1? y2 : y1;
		message.appendText(current.getCurrentMonster().getName() + " use " + sk.getName() + "!");
		animation = new SkillAnimation(sk, 4*GameLoop.MAX_FPS/60, x, y, 4);
		state = BattleState.ANIMATING_SKILL;
	}
	
	private void attack(int choice){
		Skill sk = current.getCurrentMonster().getSkill(choice);
		
		if(current.getCurrentMonster().getStatus().getMP() + sk.getCost().getMP() >= 0){
			attack(sk);
		}else{
			message.setText("Your monster doesn't have\nenough MP!");
		}
	}
	
	private void cekLevelAndEvo(int hasil){
		switch(hasil){
		case 1:
			message.appendText(current.getCurrentMonster().getName() + "'s level increased!");
			stat1.refreshData();
		break;
		case 2:
			message.appendText(current.getCurrentMonster().getName() + " evolves into a " + current.getCurrentMonster().getSpecies().getName() +"!");
			state = BattleState.EVO_INFO;
			refreshImage();
			stat1.refreshData();
			poke2 = BitmapManager.getInstance().get("trans");
			stat2.setVisible(false);
			
			delayAction = new DelayedAction() {
				@Override
				public int getDelay() {
					return GameLoop.MAX_FPS*4/3;
				}
				@Override
				public void doAction() {
					stat2.setVisible(true);
					autochange();								
				}
			};
		break;
		}
	}
	
	private void useItem(int choice){
		Object[] items = current.getBattleItem().keySet().toArray();	
		
		String name = (String) items[choice];
	
		try {
			current.delItem(name, 1);
			Item item = DBLoader.getInstance().getItem(name);
			if (item instanceof StatItem){
				Log.d("POKE ITEM", "STATITEM");
				useStatItem((StatItem) item);
			}else{
				Log.d("POKE ITEM", "MONSTERBALL");
				useMonsterBall((MonsterBall) item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void useStatItem(StatItem st){
		message.setText("Use a " + st.getName() + "!");

		current.getCurrentMonster().giveItem(st);
		
		if(st.getPermanent()){
			refreshStatBar();
		}else{
			stat1.fetchData();
			stat2.fetchData();
			state = BattleState.ANIMATING_HEALTH;
		}
	}
	
	private void useMonsterBall(MonsterBall mb){
		if (mode==BattleMode.WILD){
			message.setText("Use a " + mb.getName() + "!");
			
			poke2 = BitmapManager.getInstance().get(mb.getName());
			
			Random r = new Random();
			if(r.nextInt(100) < mb.getCatchRate()){
				//catch berhasil
				state = BattleState.DELAY;
				delayAction = new DelayedAction(){

					@Override
					public void doAction() {
						message.appendText("Yeah! You catched "+enemy.getCurrentMonster().getName()+"!");
						state = BattleState.CATCH;
					}
					@Override
					public int getDelay() {
						return GameLoop.MAX_FPS;
					}
					
				};
			}else{
				//catch gagal
				state = BattleState.DELAY;
				delayAction = new DelayedAction(){

					@Override
					public void doAction() {
						message.appendText("Shoot! It was close!");
						refreshImage();
						delayAction = new DelayerTurn();
					}
					@Override
					public int getDelay() {
						return GameLoop.MAX_FPS;
					}
					
				};
			}
				
		}else{
			message.setText("Can't use a MonsterBall\non a trainer's pokemon!");
			state = BattleState.DELAY;
			delayAction = new DelayerTurn();
		}
	}
	
	private void catched(){
		clicked = true;
		MessageManager.prompt("Give a name to your new monster (must be unique)", new Action() {
			@Override
			public void proceed(Object o) {
				String name = (String) o;
				if(current.getMonster(name)==null){
					Monster m = enemy.getCurrentMonster();
					m.setName(name);
					current.addMonster(m);
					state = BattleState.END;
				}else{
					MessageManager.alert(name + " has been used!");
					clicked = false;
					state = BattleState.CATCH;
				}	
			}
			@Override
			public void cancel() {}
		}, true);
	}
	
	//turn system
	
	private boolean cekmati(){
		//mati : currentmonster hpnya 0
		if( enemy.getCurrentMonster().getStatus().getHP()<=0){
			state = BattleState.ANIMATING_OUT;
			
			if(turn==2){
				stat1.setVisible(false);
			}else{
				stat2.setVisible(false);
			}
			
			pokeAnim = new PokeOut();
			
			return true;
		}else{
			return false;
		}
	}
	
	private void nextTurn(){
		if(state!=BattleState.ANIMATING_IN){
			turn = turn==1 ? 2 : 1;
			Player temp = current;
			current = enemy;
			enemy = temp;
		}
		
		if(mode==BattleMode.PVP)
			message.setText("Player " + turn + " turn");
		else
			message.setText(turn==1? "Your turn" : "Enemy Turn");
		
		if(turn==2) {
			if(mode==BattleMode.PVP) {
				state = BattleState.WAIT_INPUT;
			}
			else {
				state = BattleState.AI_MOVE;
				delayAction = new DelayedAction(){
					@Override
					public void doAction() {player2Turn();}
					@Override
					public int getDelay() {return GameLoop.MAX_FPS/2;}
				};
			}
		} else {
			state = BattleState.WAIT_INPUT;
		}
	}
	
	private void player2Turn(){
		attack(player2.getCurrentMonster().getRandomSkill());
	}
	
	//draw
	
	private class DelayerTurn extends DelayedAction{
		@Override
		public int getDelay() {
			return GameLoop.MAX_FPS / 2;
		}
		@Override
		public void doAction() {
			nextTurn();
		}
	}
	
	private class PokeOut extends DelayedAction{
		@Override
		public int getDelay() {
			return GameLoop.MAX_FPS;
		}
		@Override
		public void doAction() {
			if(turn==2){
				x1-=10;
			}else{
				x2+=10;
			}
		}
	}
	
	private class PokeIn extends DelayedAction{
		@Override
		public int getDelay() {
			return GameLoop.MAX_FPS;
		}
		@Override
		public void doAction() {
			if(turn==2){
				x1+=10;
			}else{
				x2-=10;
			}
		}
	}
	
	private void refreshImage(){
		poke1 = BitmapManager.getInstance().get(player1.getCurrentMonster().getSpecies().getName()+"_back");
		poke2 = BitmapManager.getInstance().get(player2.getCurrentMonster().getSpecies().getName()+"_front");
	}
	
	private void refreshStatBar(){
		stat1.setMonster(player1.getCurrentMonster());
		stat2.setMonster(player2.getCurrentMonster());
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		drawBackground(canvas);
		
		attack.draw(canvas);
		useItem.draw(canvas);
		change.draw(canvas);
		escape.draw(canvas);
		
		if(poke1!=null) canvas.drawBitmap(poke1,new Rect(0,0,poke1.getWidth(), poke1.getHeight()), new RectF(x1,y1,x1+poke1.getWidth()*2,y1+poke1.getHeight()*2),null);
		if(poke2!=null) canvas.drawBitmap(poke2,new Rect(0,0,poke2.getWidth(), poke2.getHeight()), new RectF(x2,y2,x2+poke2.getWidth()*2,y2+poke2.getHeight()*2),null);
		
		stat1.draw(canvas);
		stat2.draw(canvas);
		
		canvas.drawBitmap(bar, 0, (int) MainGameView.standardHeight - 58, null);
		
		message.draw(canvas);
		if(state==BattleState.ANIMATING_SKILL) animation.draw(canvas);
	}
	
	private void drawBackground(Canvas canvas){
		//draw
		Rect src = new Rect(0,geserTop - 15,background.getWidth(),background.getHeight());
		Rect dst = new Rect(0,0,(int) MainGameView.standardWidth, (int) MainGameView.standardHeight-geserTop);
		canvas.drawBitmap(background, src, dst, null);
	}
	
	// aksi-aksi tombol

	private void selectAttack(){	
		String[] selects = new String[4];  
				
		current.getCurrentMonster().getAllSkill().keySet().toArray(selects);// .toArray();// new String[4];
				
		MessageManager.singleChoice("Select a skill to attack", selects, new Action(){
			@Override
			public void proceed(Object o) {
				attack(((Integer) o).intValue());
			}
			@Override
			public void cancel() {}
			
		});
	}
	
	private void selectItem(){
		HashMap<String, Integer> items = current.getBattleItem();	
		if (items.size()==0){
			message.setText("You don't have any usable item!");
		}else{
			String [] c = new String[items.size()];
			int i = 0;
			for(Map.Entry<String, Integer> s : items.entrySet()){
				String name = s.getKey();
				Integer sum = s.getValue();
				c[i] = name + " (" + sum + " left)";
				i++;
			}
			
			MessageManager.singleChoice("Choose an item (Status Item or MonsterBall)", c, new Action(){
				@Override
				public void proceed(Object o) {
					useItem(((Integer) o).intValue());
				}
				@Override
				public void cancel() {}
				
			});
		}
	}
	
	private void changeMonster(){
		Collection<Monster> mons = current.getAllMonster().values();
		if (mons.size()==1){
			message.setText("You only have 1 monster!");
		}else{
			String [] monster = new String[mons.size() - 1];
			int n = 0;
			for(Monster m : mons){
				if (!m.getName().equals(current.getCurrentMonster().getName())){
					monster[n] = m.getName();
					n++;
				}
			}
			
			MessageManager.singleChoice("Select a monster to use", monster, new Action(){
				@Override
				public void proceed(Object o) {
					change(((Integer) o).intValue());
				}
				@Override
				public void cancel() {}
			});
		}
	}
	
	private void tryEscape(){
		if(mode == BattleMode.WILD){
			message.setText("Got away safely!");
			state = BattleState.END;
		}else{
			message.setText("You can't run from a trainer battle!");
		}
	}
	
	@Override
	public void onTouchEvent(MotionEvent e, float magX, float magY) {
		switch(state){
		case START:
			message.setText(current.getCurrentMonster().getName() + ", go!");
			state = BattleState.WAIT_INPUT;
		break;
		
		case WAIT_INPUT:
			for(Touchables t : touch) t.onTouchEvent(e, magX, magY);
		break;	
		case CATCH:
			state = BattleState.NAMING;
			if(!clicked) catched();
		break;
		case END:
			
		break;
		default:
		}
	}
	
	public void initListener(){
		attack.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {selectAttack();}
			@Override
			public void onTouchMove() {}
			@Override
			public void onTouchDown() {}
		});
		
		useItem.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {selectItem();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		change.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {changeMonster();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		escape.addTouchListener(new TouchListener() {
			@Override
			public void onTouchUp() {tryEscape();}
			@Override
			public void onTouchMove() {}			
			@Override
			public void onTouchDown() {}
		});
		
		touch.add(attack);
		touch.add(useItem);
		touch.add(change);
		touch.add(escape);
	}
}
