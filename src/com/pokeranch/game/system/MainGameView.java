package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.*;

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
	public GameLoop thread;
	private Matrix matrix = new Matrix();	
	private Paint paint = new Paint();
	private ScreenManager manager;
	private Player curPlayer;
	float magnificationX, magnificationY;
	public enum ButtonClick {LEFT, RIGHT, UP, DOWN, OK, CANCEL, NONE, ACTION};
	
	public MainGameView(Context context, int screenWidth, int screenHeight) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		MessageManager.setContext(context);
		
		/*loading image*/
		BitmapManager.initialize(context.getApplicationContext().getResources());
		BitmapManager.getInstance().put("landmonster", R.drawable.landmonster);
		BitmapManager.getInstance().put("test", R.drawable.ic_launcher);
		BitmapManager.getInstance().put("images", R.drawable.images);
		BitmapManager.getInstance().put("up", R.drawable.up);
		BitmapManager.getInstance().put("left", R.drawable.left);
		BitmapManager.getInstance().put("right", R.drawable.right);
		BitmapManager.getInstance().put("down", R.drawable.down);
		BitmapManager.getInstance().put("chara", R.drawable.chara);
		BitmapManager.getInstance().put("attackbutton", R.drawable.attackbutton);
		BitmapManager.getInstance().put("changebutton", R.drawable.changebutton);
		BitmapManager.getInstance().put("itembutton", R.drawable.itembutton);
		BitmapManager.getInstance().put("escapebutton", R.drawable.escapebutton);
		BitmapManager.getInstance().put("a", R.drawable.a);
		//animasi skill
		BitmapManager.getInstance().put("Swim", R.drawable.swim);
		
		//battle avatar
		BitmapManager.getInstance().put("Squir_back", R.drawable.squir_back);
		BitmapManager.getInstance().put("Squir_front", R.drawable.squir_front);
		BitmapManager.getInstance().put("Bulba_back", R.drawable.bulba_back);
		BitmapManager.getInstance().put("Bulba_front", R.drawable.bulba_front);
		BitmapManager.getInstance().put("Charchar_back", R.drawable.charchar_back);
		BitmapManager.getInstance().put("Charchar_front", R.drawable.charchar_front);
		
		//potong map
		BitmapManager.getInstance().putMap(R.drawable.spritefull, 45, 43, 1, 16);
		
		/*load database, agak lama ternyata*/
		DBLoader.initialize(context.getAssets());
		DBLoader.getInstance().loadMap("map.dat");
		
		System.gc();
		
		ScreenManager.initialize();
		
		manager = ScreenManager.getInstance();
		
		magnificationX = screenHeight/240;
		magnificationY = screenWidth/320;
		Log.d("harits", "sh: " + screenHeight +", sw: " + screenWidth);
		Log.d("harits", "magX: " + magnificationX +", magY: " + magnificationY);
		matrix.setScale(magnificationX, magnificationY,0,0);
		curPlayer = new Player();
		AreaManager am = new AreaManager(context, screenWidth, screenHeight, curPlayer);
		am.setCurArea(DBLoader.getInstance().getArea("FIELD"));
		am.setPlayerCord(new Point(0,0));
		manager.push(am);
		
		MainMenu mm = new MainMenu(context, screenWidth, screenHeight);
		//manager.push(mm);
		
		Player pl = new Player();
		Player pl2 = new Player();
		Monster m = Monster.getRandomMonster(2, 1);
		Monster m2 = Monster.getRandomMonster(2, 1);
		pl.addMonster(m);
		pl.setCurrentMonster(m.getName());
		
		pl2.addMonster(m2);
		pl2.setCurrentMonster(m2.getName());
		
		//manager.push(new BattleScreen(pl,pl2));
		
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
		manager.onTouchEvent(event, magnificationX);
		return true;
	}	
}
