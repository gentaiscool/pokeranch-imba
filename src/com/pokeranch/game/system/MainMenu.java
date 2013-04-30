package com.pokeranch.game.system;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

public class MainMenu extends Activity {
	
	private ImageButton newGame, loadGame, helpGame, exitGame; 
	private int state;
	//private Bitmap newgame;
	//private Resources res;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		//newgame = BitmapFactory.decodeResource(res, R.drawable.newgamebutton2);
		onUpdate();
	}
	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
		
	}
	public void onUpdate(){
		state = 0; //main menu
		
		newGame = (ImageButton) findViewById(R.id.imageButtonNewGame);
		newGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state = 1; // new game
				setContentView(R.layout.main_welcome_story);
			}
		});
		loadGame = (ImageButton) findViewById(R.id.imageButtonLoadGame);
		loadGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state = 2; // load game
			}
		});
		helpGame = (ImageButton) findViewById(R.id.imageButtonHelpGame);
		helpGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state = 3; // help game
				setContentView(R.layout.main_help);
			}
		});
		exitGame = (ImageButton) findViewById(R.id.imageButtonExitGame);
		exitGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state = 4; // exit game
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        Log.d(this.getClass().getName(), "back button pressed");
	    }
	   // return super.onKeyDown(keyCode, event);	   
	    switch(state){
	    	case 0 :
	    		return super.onKeyDown(keyCode, event);
	    	case 1 :
	    		//nothing to do
	    		break;
	    	case 3 :
	    		state = 0;
	    		setContentView(R.layout.main_menu);
	    		onUpdate();
	    		break;
	    }
	    return false;
	}
	
}
