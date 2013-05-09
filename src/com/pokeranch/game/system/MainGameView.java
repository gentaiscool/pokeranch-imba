package com.pokeranch.game.system;

import com.pokeranch.game.object.*;
import com.pokeranch.game.system.BattleScreen.BattleListener;
import com.pokeranch.game.system.BattleScreen.BattleMode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class MainGameView extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "POKE";
	public static final float standardWidth = 320.f;
	public static final float standardHeight = 240.f;
	public static float realScreenWidth;
	public static float realScreenHeight;
	public static final int standardDensity = 160;
	public static int screenDensity;
	
	public GameLoop thread;
	private Matrix matrix;	
	private ScreenManager manager;
	private Player curPlayer;
	float magnificationX, magnificationY;
	public enum ButtonClick {LEFT, RIGHT, UP, DOWN, OK, CANCEL, NONE, ACTION_A, ACTION_B};
	
	@SuppressWarnings("static-access")
	public MainGameView(Context context, int screenWidth, int screenHeight, int screenDensity) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		
		this.screenDensity = screenDensity;
		
		MessageManager.setContext(context);
		ScreenManager.initialize();
		PlayerSaveLoader.initialize(context);
		
		manager = ScreenManager.getInstance();
		realScreenHeight = screenHeight;
		realScreenWidth = screenWidth;
		magnificationY = (((float) screenHeight) / standardHeight);
		magnificationX = (((float) screenWidth) / standardWidth);
			
		//Log.d("harits", "sh: " + screenHeight +", sw: " + screenWidth);
		//Log.d("harits", "magX: " + magnificationX +", magY: " + magnificationY);
		matrix = new Matrix();
		
		matrix.setScale(magnificationX, magnificationY,0,0);
		
		curPlayer = new Player();
		Monster m1 = new Monster("mybulba", DBLoader.getInstance().getSpecies("Bulba"),7);
		Monster m4 = new Monster("mybulba2", DBLoader.getInstance().getSpecies("Bulba"),8);
		Monster m3 = new Monster("mybulba3", DBLoader.getInstance().getSpecies("Bulba"),9);
		curPlayer.addMonster(m1);
		curPlayer.addMonster(m4);
		curPlayer.addMonster(m3);
		curPlayer.setCurrentMonster(m1);
		AreaManager am = new AreaManager(context, screenWidth, screenHeight, curPlayer);
		//Log.d("harits3","di MainGameView, r c: " +  DBLoader.getInstance().getArea("FIELD").getRow() + " " + DBLoader.getInstance().getArea("FIELD").getColumn());
		am.setCurArea(DBLoader.getInstance().getArea("HOME"));
		am.setPlayerCord(new Point(8,5));
		manager.push(am);
		
		MainMenu mm = new MainMenu(context, screenWidth, screenHeight);
		manager.push(mm);
		
		//Log.d("LM", "chek 1");
		//ListMonster lm = new ListMonster(curPlayer, screenWidth, screenHeight);
		//Log.d("LM", "chek 2");

		//PlayerStatus ps = new PlayerStatus(curPlayer);
		//manager.push(ps);
		//manager.push(lm);

		//ListItem lt = new ListItem(curPlayer, screenWidth, screenHeight);
		//manager.push(lt);
		
		//Pokedex pokedex = new Pokedex(screenWidth, screenHeight);
		//manager.push(pokedex);
		
		/*Player pl = new Player();
		Player pl2 = new Player();
		Monster m = new Monster("mybulba", DBLoader.getInstance().getSpecies("Bulba"),7);
		m.addExp(120);
		Monster m2 = Monster.getRandomMonster(4, 1);
		
		pl.addMonster(m);
		pl.setCurrentMonster(m.getName());

		pl.setMoney(10000);
		pl.addItem(DBLoader.getInstance().getItem("Potion"), 1);
		pl.addItem(DBLoader.getInstance().getItem("Potion"), 2);
		pl.addItem(DBLoader.getInstance().getItem("Cut"), 1);
		pl.addItem(DBLoader.getInstance().getItem("Normal_Ball"), 2);
		pl.addItem(DBLoader.getInstance().getItem("ParalyzCure"), 2);

		pl.addMonster(new Monster("myChar", DBLoader.getInstance().getSpecies("Charchar"),10));
		pl.addMonster(Monster.getRandomMonster(10, 1));

		
		pl2.addMonster(m2);
		pl2.setCurrentMonster(m2.getName());

		pl2.addMonster(Monster.getRandomMonster(10, 1));

			*/	

		//BuyScreen buymarket = new BuyScreen(pl, screenWidth, screenHeight);
		//manager.push(buymarket);
		
		//BuySellScreen buysellmarket = new BuySellScreen(pl, screenWidth, screenHeight);
		//manager.push(buysellmarket);

		//Combinatorium combi = new Combinatorium(pl, screenWidth, screenHeight);
		//manager.push(combi);
		//manager.push(combi);
		
		//Stadium stadium = new Stadium(pl, screenWidth, screenHeight);
		//manager.push(stadium);
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// inisialisasi thread
		initThread();
		Log.d(TAG, "surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {		
		releaseThread();
		
		DBLoader.release();
		PlayerSaveLoader.release();
		BitmapManager.release();
		ScreenManager.release();
		
		Log.d(TAG, "surface destroyed");
	}
	
	// inisialisasi thread
	public void initThread() {
		if (thread == null || !thread.isAlive()) {
			// jika belom diinisialisasi threadnya atau threadnya sudah tidak hidup lagi, maka
			// instansiasi thread utama
			thread = new GameLoop(getHolder(), this);
			thread.start();
		}
		thread.setRunning(true);
	}
	
	public void pauseThread(){
		if(thread!=null) thread.setRunning(false);
	}
	
	public void continueThread(){
		if(thread!=null) thread.setRunning(true);
	}
	 
	private void releaseThread() {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
				thread = null;
			} catch (InterruptedException e) {
			}
		}
	}

	public void render(Canvas canvas) {
		canvas.setMatrix(matrix);
		canvas.drawColor(Color.WHITE);
		manager.draw(canvas);
	}

	public void update() {
		manager.update();
	}
	
	public boolean onTouchEvent(MotionEvent event) {	
		manager.onTouchEvent(event, magnificationX, magnificationY);
		return true;
	}	
}
