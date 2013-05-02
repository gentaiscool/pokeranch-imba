package com.pokeranch.game.system;

import java.util.ArrayList;
import java.util.Collection;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.system.R.id;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PokeDex extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pokedex);
		
		final Typeface face = Typeface.createFromAsset(getAssets(),
	            "fonts/Pokemon GB.ttf");
		TextView iv = (TextView)findViewById(R.id.textViewNamaMonster);
		TextView typeMonster = (TextView)findViewById(R.id.textViewTypeMonster);
		TextView numMonster = (TextView)findViewById(R.id.textViewMonsterNumber);
		TextView tv = (TextView)findViewById(R.id.textViewNamaMonster);
		TextView hpMonster = (TextView)findViewById(R.id.textViewHPBase);
		TextView mpMonster = (TextView)findViewById(R.id.textViewMPBase);
		TextView attackMonster = (TextView)findViewById(R.id.textViewAttackBase);
		TextView defenseMonster = (TextView)findViewById(R.id.textViewDefenseBase);
		
		ImageView monsterImage = (ImageView)findViewById(R.id.imageViewMonster);
		
		iv.setTypeface(face);
		typeMonster.setTypeface(face);
		tv.setTypeface(face);
		
		//Database
		DBLoader.initialize(getBaseContext().getAssets());
		final Object[] species = DBLoader.getInstance().getAllSpecies().toArray();
		
		TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayoutPokeDex);
		TableRow tableRow;
		TextView textView;
		
		//default

		Drawable d = getImageByName(((Species) species[0]).getName().toString().toLowerCase(), this);
		monsterImage.setImageDrawable(d);
		
		Species species1= (Species) species[0];
		
		tv.setText((String)species1.getName());
		numMonster.setText("001");
		typeMonster.setText((String)(species1).getElement().getName());
		hpMonster.setText("HP: " + species1.getBaseStat().getHP());
		mpMonster.setText("MP: " + species1.getBaseStat().getMP());
		attackMonster.setText("Attack: " + species1.getBaseStat().getAttack());
		defenseMonster.setText("Defense: " + species1.getBaseStat().getDefense());
		
		
		//tableRow
		for(int i=0; i<=species.length-1;i++){
			final TableRow tableRow2 = new TableRow(getBaseContext());
			tableRow2.setMinimumHeight(40);
			
			textView = new TextView(getBaseContext());
			textView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					switch(event.getAction()){            
				            case MotionEvent.ACTION_DOWN:
				            	tableRow2.setBackgroundColor(0xFFFFFFFF); //white
				                break;          
				            case MotionEvent.ACTION_CANCEL:             
				            case MotionEvent.ACTION_UP:
				            	tableRow2.setBackgroundColor(0xFF000000); //black
				                break;
				    } 
					return false;
				}
			});
			int i2 = i+1;
			if(i2>9){
				textView.setText("0"+i2+"  "+(String)((Species) species[i]).getName());
			}
			else{
				textView.setText("00"+i2+"  "+(String)((Species) species[i]).getName());
			}
			final Integer ii = new Integer(i);
			textView.setTypeface(face);
			textView.setOnClickListener(new View.OnClickListener() {
				@SuppressLint("UseValueOf")
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					TextView tv = (TextView)findViewById(R.id.textViewNamaMonster);
					TextView typeMonster = (TextView)findViewById(R.id.textViewTypeMonster);
					TextView numMonster = (TextView)findViewById(R.id.textViewMonsterNumber);
					ImageView monsterImage = (ImageView)findViewById(R.id.imageViewMonster);
					Species sp = (Species)species[ii];
					TextView hpMonster = (TextView)findViewById(R.id.textViewHPBase);
					TextView mpMonster = (TextView)findViewById(R.id.textViewMPBase);
					TextView attackMonster = (TextView)findViewById(R.id.textViewAttackBase);
					TextView defenseMonster = (TextView)findViewById(R.id.textViewDefenseBase);
					
					tv.setTypeface(face);
					typeMonster.setTypeface(face);
					Drawable d = getImageByName(sp.getName().toString().toLowerCase(), PokeDex.this);
					monsterImage.setImageDrawable(d);
					Integer ii2 = ii+1;
					if(ii2>9){
						numMonster.setText("0"+ii2.toString());
					}
					else{
						numMonster.setText("00"+ii2.toString());
					}
					
					typeMonster.setText((String)sp.getElement().getName());
					hpMonster.setText("HP: " + sp.getBaseStat().getHP());
					mpMonster.setText("MP: " + sp.getBaseStat().getMP());
					attackMonster.setText("Attack: " + sp.getBaseStat().getAttack());
					defenseMonster.setText("Defense: " + sp.getBaseStat().getDefense());
					
					typeMonster.setText((String)sp.getElement().getName());
					tv.setText((String)sp.getName());
				}
			});
			tableRow2.addView(textView);
			tableLayout.addView(tableRow2);
		}
	}

	public Drawable getImageByName(String nameOfTheDrawable, Activity a){
	    Drawable drawFromPath;
	    int path = a.getResources().getIdentifier(nameOfTheDrawable, 
	                                    "drawable", "com.pokeranch.game.system"); 

	    Options options = new BitmapFactory.Options();
	    options.inScaled = false;
	    Bitmap source = BitmapFactory.decodeResource(a.getResources(), path, options);

	    drawFromPath = new BitmapDrawable(getResources(), source);  

	    return drawFromPath;
	}
}
