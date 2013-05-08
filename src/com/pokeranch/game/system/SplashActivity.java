package com.pokeranch.game.system;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
	
	// load resource nya ada di thread
	private class GameLoader extends Thread{
		private Context context;
		
		private void initImage() throws Exception{
			//init bitmap manager
			
			BitmapManager.initialize(context.getApplicationContext().getResources());
			
			/******************** Main Menu ******************/
			BitmapManager.getInstance().put("newgame", R.drawable.newgamebutton2);
			BitmapManager.getInstance().put("loadgame", R.drawable.loadgamebutton2);
			BitmapManager.getInstance().put("helpgame", R.drawable.helpbutton);
			BitmapManager.getInstance().put("exitbutton", R.drawable.exitbutton);
			BitmapManager.getInstance().put("pokeball", R.drawable.pokeball);
			
			BitmapManager.getInstance().put("logo", R.drawable.logo);
			
			/********************* Main Story & Choose Monster *****************/
			BitmapManager.getInstance().put("professoroak", R.drawable.professoroak);
			BitmapManager.getInstance().put("frame", R.drawable.frame);
			
			
			/******************** PokeDex ********************/
			BitmapManager.getInstance().put("panel", R.drawable.panelground);
			BitmapManager.getInstance().put("pokedextablet", R.drawable.pokedex);
			BitmapManager.getInstance().put("pokedexlogo", R.drawable.pokedexlogo);
			
			BitmapManager.getInstance().put("trans", R.drawable.transparent);	
			
			//pokemons
			BitmapManager.getInstance().put("Charchar", R.drawable.charchar);
			BitmapManager.getInstance().put("Squir", R.drawable.squir);
			BitmapManager.getInstance().put("Bulba", R.drawable.bulba);
			BitmapManager.getInstance().put("Ivy", R.drawable.ivy);
			
			/******************** Area **********************/
			
			//sprite
			BitmapManager.getInstance().put("landmonster", R.drawable.landmonster);
			BitmapManager.getInstance().put("chara", R.drawable.chara);
			BitmapManager.getInstance().put("test", R.drawable.ic_launcher);
			BitmapManager.getInstance().put("images", R.drawable.images);
			
			//button
			BitmapManager.getInstance().put("up", R.drawable.up);
			BitmapManager.getInstance().put("left", R.drawable.left);
			BitmapManager.getInstance().put("right", R.drawable.right);
			BitmapManager.getInstance().put("down", R.drawable.down);
			BitmapManager.getInstance().put("a_button", R.drawable.a);
			
			//kotakbuatjam
			BitmapManager.getInstance().put("kotakjam",R.drawable.kotakjam);
			
			/******************* Battle **********************/
			
			//battle panel
			BitmapManager.getInstance().put("attackbutton", R.drawable.attackbutton);
			BitmapManager.getInstance().put("changebutton", R.drawable.changebutton);
			BitmapManager.getInstance().put("itembutton", R.drawable.itembutton);
			BitmapManager.getInstance().put("escapebutton", R.drawable.escapebutton);
			BitmapManager.getInstance().put("battle_bar", R.drawable.bar);
			
			//animasi skill
			BitmapManager.getInstance().put("Swim", R.drawable.swim);
			BitmapManager.getInstance().put("Cut", R.drawable.cut);
			BitmapManager.getInstance().put("Vine", R.drawable.vine);
			BitmapManager.getInstance().put("Flame", R.drawable.flame);
			BitmapManager.getInstance().put("FireBall", R.drawable.fireball);
			BitmapManager.getInstance().put("Waterfall", R.drawable.waterfall);
			BitmapManager.getInstance().put("Push", R.drawable.push);
			
			//battle avatar
			BitmapManager.getInstance().put("Squir_back", R.drawable.squir_back);
			BitmapManager.getInstance().put("Squir_front", R.drawable.squir_front);
			BitmapManager.getInstance().put("Bulba_back", R.drawable.bulba_back);
			BitmapManager.getInstance().put("Bulba_front", R.drawable.bulba_front);
			BitmapManager.getInstance().put("Charchar_back", R.drawable.charchar_back);
			BitmapManager.getInstance().put("Charchar_front", R.drawable.charchar_front);
			BitmapManager.getInstance().put("Ivy_back", R.drawable.ivy_back);
			BitmapManager.getInstance().put("Ivy_front", R.drawable.ivy_front);
			
			//battle background
			BitmapManager.getInstance().put("battle_day_land", R.drawable.battle_day_land);
			
			//potong map
			BitmapManager.getInstance().putMap("", R.drawable.spritefull, 45, 43, 1, 16);
			BitmapManager.getInstance().putMap("b", R.drawable.spritefull2, 11, 15, 0, 16);
			//Log.d("harits1", "berhasil load semua map");
			//mencegah penambahan resource setelah load
			BitmapManager.getInstance().lockPut();
		}
		
		public GameLoader(Context context){
			this.context = context;
		}
		
		@Override
		public void run(){
			try{
				initImage();
			}catch (Exception e){
				e.printStackTrace();
			}

			//load database
			DBLoader.initialize(context.getApplicationContext().getAssets());
			DBLoader.getInstance().loadMap("map.dat");
			
			System.gc();
			
			Intent intent = new Intent(context, MainGameActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
