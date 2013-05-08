package com.pokeranch.game.system;

import com.pokeranch.game.object.*;
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
	public static final int standardDensity = 160;
	public static int screenDensity;
	
	public GameLoop thread;
	private Matrix matrix = new Matrix();	
	private Paint paint = new Paint();
	private ScreenManager manager;
	private Player curPlayer;
	float magnificationX, magnificationY;
	public enum ButtonClick {LEFT, RIGHT, UP, DOWN, OK, CANCEL, NONE, ACTION};
	
	@SuppressWarnings("static-access")
	public MainGameView(Context context, int screenWidth, int screenHeight, int screenDensity) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		
		this.screenDensity = screenDensity;
		
		MessageManager.setContext(context);
		ScreenManager.initialize();
		
		manager = ScreenManager.getInstance();
		
		magnificationY = (((float) screenHeight) / standardHeight);
		magnificationX = (((float) screenWidth) / standardWidth);
			
		Log.d("harits", "sh: " + screenHeight +", sw: " + screenWidth);
		Log.d("harits", "magX: " + magnificationX +", magY: " + magnificationY);
		matrix.setScale(magnificationX, magnificationY,0,0);
		
		curPlayer = new Player();
		AreaManager am = new AreaManager(context, screenWidth, screenHeight, curPlayer);
		am.setCurArea(DBLoader.getInstance().getArea("FIELD"));
		am.setPlayerCord(new Point(0,0));
		//manager.push(am);
		
		//MainMenu mm = new MainMenu(context, screenWidth, screenHeight);
		//manager.push(mm);
		
		Pokedex pokedex = new Pokedex(screenWidth, screenHeight);
		manager.push(pokedex);
		
		Player pl = new Player();
		Player pl2 = new Player();
		Monster m = Monster.getRandomMonster(10, 1);
		Monster m2 = Monster.getRandomMonster(10, 1);
		pl.addMonster(m);
		pl.setCurrentMonster(m.getName());
		
		pl2.addMonster(m2);
		pl2.setCurrentMonster(m2.getName());
		
		//manager.push(new BattleScreen(pl,pl2, BattleMode.WILD));
		
		paint.setTextSize(40);
		paint.setTypeface(Typeface.MONOSPACE);
		paint.setColor(Color.BLACK);
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
