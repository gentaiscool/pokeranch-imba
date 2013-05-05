package com.pokeranch.game.system;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.pokeranch.game.object.DBLoader;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		
		new GameLoader(this).start();
	}
	
	private class GameLoader extends Thread{
		private Context context;
		public GameLoader(Context context){
			this.context = context;
		}
		
		@Override
		public void run(){
			BitmapManager.initialize(context.getApplicationContext().getResources());
			BitmapManager.getInstance().put("landmonster", R.drawable.landmonster);
			BitmapManager.getInstance().put("test", R.drawable.ic_launcher);
			BitmapManager.getInstance().put("images", R.drawable.images);
			BitmapManager.getInstance().put("up", R.drawable.up);
			BitmapManager.getInstance().put("left", R.drawable.left);
			BitmapManager.getInstance().put("right", R.drawable.right);
			BitmapManager.getInstance().put("down", R.drawable.down);
			BitmapManager.getInstance().put("chara", R.drawable.chara);
			BitmapManager.getInstance().put("a_button", R.drawable.a);
			
			/******************* BATTLE RESOURCES **********************/
			
			BitmapManager.getInstance().put("attackbutton", R.drawable.attackbutton);
			BitmapManager.getInstance().put("changebutton", R.drawable.changebutton);
			BitmapManager.getInstance().put("itembutton", R.drawable.itembutton);
			BitmapManager.getInstance().put("escapebutton", R.drawable.escapebutton);
			BitmapManager.getInstance().put("battle_bar", R.drawable.bar);
			
			//animasi skill
			BitmapManager.getInstance().put("Swim", R.drawable.swim);
			
			//battle avatar
			BitmapManager.getInstance().put("Squir_back", R.drawable.squir_back);
			BitmapManager.getInstance().put("Squir_front", R.drawable.squir_front);
			BitmapManager.getInstance().put("Bulba_back", R.drawable.bulba_back);
			BitmapManager.getInstance().put("Bulba_front", R.drawable.bulba_front);
			BitmapManager.getInstance().put("Charchar_back", R.drawable.charchar_back);
			BitmapManager.getInstance().put("Charchar_front", R.drawable.charchar_front);
			
			//battle background
			BitmapManager.getInstance().put("battle_day_land", R.drawable.battle_day_land);
			
			//potong map
			BitmapManager.getInstance().putMap(R.drawable.spritefull, 45, 43, 1, 16);

			//load database, agak lama ternyata/
			DBLoader.initialize(context.getApplicationContext().getAssets());
			DBLoader.getInstance().loadMap("map.dat");
			
			ScreenManager.initialize();
			
			Intent intent = new Intent(context, MainGameActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
