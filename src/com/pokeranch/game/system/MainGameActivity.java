package com.pokeranch.game.system;

import com.pokeranch.game.object.DBLoader;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainGameActivity extends Activity {
	private DisplayMetrics metrics;	
	private MainGameView mainGameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		StringBuilder sb = new StringBuilder();
		sb.append(metrics.densityDpi); //low = 120 160 240
		Log.d("harits dpi", sb.toString());
		mainGameView = new MainGameView(this, metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
		setContentView(mainGameView);	
	}
	
	@Override
	protected void onPause() {
		Log.d("POKE STAT", "onPause");
		if(mainGameView!=null) mainGameView.pauseThread(); //matiin thread
		super.onPause();			
	}
	
	@Override
	protected void onStop(){
		mainGameView = null;
		Log.d("POKE STAT", "onStop");
		Log.d("POKE STAT", (BitmapManager.getInstance() == null) + " ");
		System.gc();
		super.onStop();
		finish();
	}
}
