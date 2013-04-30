package com.pokeranch.game.system;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

import com.pokeranch.game.system.MainGameView;
import com.pokeranch.game.object.DBLoader;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics); // dapetin ukuran layar
		MainGameView mainGameView = new MainGameView(this,metrics.widthPixels,metrics.heightPixels);
		setContentView(mainGameView);	
	}


}
