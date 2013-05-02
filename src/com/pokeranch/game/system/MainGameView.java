package com.pokeranch.game.system;

import java.util.ArrayList;

import com.pokeranch.game.object.DBLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class MainGameView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = "testanim";
	public GameLoop thread;
	private Matrix matrix = new Matrix();	
	private Paint paint = new Paint();
	private AreaManager am;
	
	public enum ButtonClick {LEFT, RIGHT, UP, DOWN, OK, CANCEL, NONE};
	
	public MainGameView(Context context, int screenWidth, int screenHeight) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		
		BitmapManager.initialize(context.getApplicationContext().getResources());
		BitmapManager.getInstance().put("landmonster", R.drawable.landmonster);
		BitmapManager.getInstance().put("test", R.drawable.ic_launcher);
		BitmapManager.getInstance().put("images", R.drawable.images);
		BitmapManager.getInstance().put("up", R.drawable.up);
		BitmapManager.getInstance().put("left", R.drawable.left);
		BitmapManager.getInstance().put("right", R.drawable.right);
		BitmapManager.getInstance().put("down", R.drawable.down);
		BitmapManager.getInstance().put("chara", R.drawable.chara);
		//Log.d("harits", "mulaiiii akan memotong2");
		BitmapManager.getInstance().putMap(R.drawable.sprite);
		
		DBLoader.initialize(context.getAssets());
		DBLoader.getInstance().loadMap("map.dat");
		
		//Log.d("harits", "berhasil inisiasi");
		//if(DBLoader.getInstance() == null)
		//	Log.d("harits", "DBLoader null");
		//Log.d("harits", "berhasil bikin Area");
		
		am = new AreaManager(context, screenWidth, screenHeight);
		
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
		am.draw(canvas, 1);
	}

	public void update() {
		am.update();
	}
	
	public boolean onTouchEvent(MotionEvent event) {	
		am.onTouchEvent(event);
		return true;
	}	
}