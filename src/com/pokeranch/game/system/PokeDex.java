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
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
		tv.setText((String)((Species) species[0]).getName());
		numMonster.setText("001");
		typeMonster.setText((String)((Species) species[0]).getElement().getName());
		
		for(int i=0; i<=species.length-1;i++){
			tableRow = new TableRow(getBaseContext());
			textView = new TextView(getBaseContext());
			textView.setText((String)((Species) species[i]).getName());
			final Integer ii = new Integer(i);
			textView.setOnClickListener(new View.OnClickListener() {
				@SuppressLint("UseValueOf")
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					TextView tv = (TextView)findViewById(R.id.textViewNamaMonster);
					TextView typeMonster = (TextView)findViewById(R.id.textViewTypeMonster);
					TextView numMonster = (TextView)findViewById(R.id.textViewMonsterNumber);
					ImageView monsterImage = (ImageView)findViewById(R.id.imageViewMonster);
					tv.setTypeface(face);
					typeMonster.setTypeface(face);
					Drawable d = getImageByName(((Species) species[ii]).getName().toString().toLowerCase(), PokeDex.this);
					monsterImage.setImageDrawable(d);
					Integer ii2 = ii+1;
					if(ii2>9){
						numMonster.setText("0"+ii2.toString());
					}
					else{
						numMonster.setText("00"+ii2.toString());
					}
					typeMonster.setText((String)((Species) species[ii]).getElement().getName());
					tv.setText((String)((Species) species[ii]).getName());
				}
			});
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
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
