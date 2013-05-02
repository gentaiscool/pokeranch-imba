package com.pokeranch.game.system;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
	
	private ImageButton newGame, loadGame, helpGame, exitGame, pokedexButton; 
	private int state;
	private TextView tf;
	private String characterName, monsterName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
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
				setContentView(R.layout.main_load_pick_character);
			}
		});
		helpGame = (ImageButton) findViewById(R.id.imageButtonHelpGame);
		helpGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				state = 3; // help game
				setContentView(R.layout.main_help);
				//Intent i = new Intent(getBaseContext(),PokeDex.class);
				//i.putExtra("characterName", characterName);
				//i.putExtra("monsterName", monsterName); 
				//startActivity(i);
				//finish();
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
		pokedexButton = (ImageButton) findViewById(R.id.imageButtonPokeDex);
		pokedexButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				state = 4;
				//setContentView(R.layout.pokedex);
				Intent i = new Intent(getBaseContext(),PokeDex.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	public void onPickMonster(){
		final RadioButton b1 = (RadioButton)findViewById(R.id.RadioButtonBulba);
		final RadioButton b2 = (RadioButton)findViewById(R.id.RadioButtonSquir);
		final RadioButton b3 = (RadioButton)findViewById(R.id.RadioButtonCharchar);
		final Button button = (Button)findViewById(R.id.buttonMonsterPicked);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!b1.isChecked()&&!b2.isChecked()&&!b3.isChecked()){
					AlertDialog ad = makeAndShowDialogBox("Gotcha!", "You haven't pick your starter monster");
					ad.show();
				}
				else{
					//send data to other activity
					Intent i = new Intent(getBaseContext(),MainTest.class);
					i.putExtra("characterName", characterName);
					i.putExtra("monsterName", monsterName); 
					startActivity(i);
					finish();
				}
			}
		});
		b1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(b1.isChecked()){
					b2.setChecked(false);
					b3.setChecked(false);
					monsterName = "Bulba";
				}
			}
		});
		b2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(b2.isChecked()){
					b1.setChecked(false);
					b3.setChecked(false);
					monsterName="Squir";
				}
			}
		});
		b3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(b3.isChecked()){
					b1.setChecked(false);
					b2.setChecked(false);
					monsterName="Charchar";
				}
			}
		});
	}
	
	public void onLoad(){
		
	}
	
	public void onWriteName(){
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "fonts/Pokemon GB.ttf");
		TextView tv = (TextView)findViewById(R.id.textViewHint);
		EditText et = (EditText)findViewById(R.id.editTextName);
		Button b = (Button)findViewById(R.id.buttonContinue);
		
	
		tv.setTypeface(face);
		et.setTypeface(face);
		b.setTypeface(face);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*if(et.getText().length()!=0){
					state = 13; // pick monster
					characterName = et.getText().toString();
					setContentView(R.layout.main_pick_monster);
					onPickMonster();
				}
				else{
					AlertDialog ad = makeAndShowDialogBox("Gotcha!","You haven't type your name!");
					ad.show();
				}*/
			}
		});
	}
	
private AlertDialog makeAndShowDialogBox(String title, String message){
        AlertDialog myDialogBox = 
        	new AlertDialog.Builder(this) 
        	//set message, title, and icon
        	.setTitle(title) 
        	.setMessage(message) 
        	.create(); 	
        	return myDialogBox;
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
	        //Log.d(this.getClass().getName(), "back button pressed");
	    }
	   // return super.onKeyDown(keyCode, event);	   
	    switch(state){
	    	case 0 :
	    		return super.onKeyDown(keyCode, event);
	    	case 1 :
	    		//nothing to do
	    		break;
	    	case 2 :
	    		state = 0;
	    		setContentView(R.layout.main_menu);
	    		onUpdate();
	    		break;
	    	case 3 :
	    		state = 0;
	    		setContentView(R.layout.main_menu);
	    		onUpdate();
	    		break;
	    	case 4 :
	    		state = 0;
	    		setContentView(R.layout.main_menu);
	    		onUpdate();
	    		break;
	    	case 12 :
	    		state = 0;
	    		setContentView(R.layout.main_menu);
	    		onUpdate();
	    		break;
	    	case 13 :
	    		state = 12;
	    		setContentView(R.layout.main_write_name);
	    		onWriteName();
	    		break;
	    }
	    return false;
	}
}

