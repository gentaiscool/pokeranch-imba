package com.pokeranch.game.system;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainTest extends Activity {
	private DisplayMetrics metrics;	
	private MainGameView mainGameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		mainGameView = new MainGameView(this,metrics.widthPixels,metrics.heightPixels);
		setContentView(mainGameView);	
		
	}

	protected void onPause() {
		mainGameView.pauseThread(); //matiin thread
		super.onPause();			
	}
}
