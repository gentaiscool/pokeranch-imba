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
	
	public enum ButtonClick {LEFT, RIGHT, UP, DOWN, OK, CANCEL, NONE};
	
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
		
		//animasi skill
		BitmapManager.getInstance().put("Swim", R.drawable.swim);
		
		//potong map
		BitmapManager.getInstance().putMap(R.drawable.sprite);
		
		/*load database, agak lama ternyata*/
		DBLoader.initialize(context.getAssets());
		DBLoader.getInstance().loadMap("map.dat");
		
		System.gc();
		
		manager = new ScreenManager();
		AreaManager am = new AreaManager(context, screenWidth, screenHeight);
		am.setCurArea(DBLoader.getInstance().getArea("FIELD"));
		am.setPlayerCord(new Point(0,0));
		//manager.push(am);
		
		Player pl = new Player();
		Monster m = Monster.getRandomMonster(2, 1);
		pl.addMonster(m);
		pl.setCurrentMonster(m);
		
		manager.push(new BattleScreen(pl,null));
		
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
		canvas.drawColor(Color.BLACK);
		manager.draw(canvas, 1);
	}

	public void update() {
		manager.update();
	}
	
	public boolean onTouchEvent(MotionEvent event) {	
		manager.onTouchEvent(event);
		return true;
	}	
}
