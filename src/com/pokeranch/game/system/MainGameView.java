package com.pokeranch.game.system;

import com.pokeranch.game.object.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class MainGameView extends SurfaceView implements SurfaceHolder.Callback {
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
		DialogueBox.initialize();
		MessageManager.setContext(context);
		ScreenManager.initialize();
		PlayerSaveLoader.initialize(context);
		
		manager = ScreenManager.getInstance();
		realScreenHeight = screenHeight;
		realScreenWidth = screenWidth;
		magnificationY = (((float) screenHeight) / standardHeight);
		magnificationX = (((float) screenWidth) / standardWidth);
			
		matrix = new Matrix();
		matrix.setScale(magnificationX, magnificationY,0,0);
		
		curPlayer = new Player();
		MainMenu mm = new MainMenu(context, screenWidth, screenHeight);
		manager.push(mm);

	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// inisialisasi thread
		initThread();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {		
		releaseThread();
		
		DBLoader.release();
		PlayerSaveLoader.release();
		BitmapManager.release();
		ScreenManager.release();
		
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
