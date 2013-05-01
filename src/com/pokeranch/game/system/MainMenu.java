package com.pokeranch.game.system;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainMenu extends Activity {
	
	private ImageButton newGame, loadGame, helpGame, exitGame; 
	private int state;
	TextView tf;
	private String characterName, monsterName;
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
				onStory();
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
				finish();
			}
		});
	}
	
	public void onPickMonster(){
		final RadioButton b1 = (RadioButton)findViewById(R.id.RadioButtonBulba);
		final RadioButton b2 = (RadioButton)findViewById(R.id.RadioButtonSquir);
		final RadioButton b3 = (RadioButton)findViewById(R.id.RadioButtonCharchar);
		b1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("he", "click");
				if(b1.isChecked()){
					b2.setChecked(false);
					b3.setChecked(false);
				}
				
			}
		});
		b2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("he", "click2a");
				if(b2.isChecked()){
					b1.setChecked(false);
					b3.setChecked(false);
				}
			}
		});
		b3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("he", "click3");
				if(b3.isChecked()){
					b1.setChecked(false);
					b2.setChecked(false);
				}
			}
		});
	}
	
	public void onWriteName(){
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "fonts/Pokemon GB.ttf");
		TextView tv = (TextView)findViewById(R.id.textViewHint);
		final EditText et = (EditText)findViewById(R.id.editTextName);
		Button b = (Button)findViewById(R.id.buttonContinue);
		tv.setTypeface(face);
		b.setTypeface(face);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(et.getText().length()!=0){
					state = 13; // help game
					setContentView(R.layout.main_pick_monster);
					onPickMonster();
				}
				else{
					
				}
			}
		});
	}
	
	public void onStory(){
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "fonts/Pokemon GB.ttf");
		tf = (TextView)findViewById(R.id.textViewDesc);
		TextView tap = (TextView)findViewById(R.id.textViewTap);
		tf.setTypeface(face);
		tap.setTypeface(face);
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayoutStory);
		switch(state){
			case 1:tf.setText("Welcome to PokeRancher World!. An amazing journey is waiting you.");
					break;
		}
		rl.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				switch(state){
					case 1 :
						tf.setText("Please introduce yourself!");
						state = 11;
						break;
					case 11 :
						setContentView(R.layout.main_write_name);
						onWriteName();
						state = 12;
						break;
				}
				return false;
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
