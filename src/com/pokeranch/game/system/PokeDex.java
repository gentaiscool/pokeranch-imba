package com.pokeranch.game.system;

import com.pokeranch.game.system.R.id;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
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
		
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "fonts/Pokemon GB.ttf");
		TextView iv = (TextView)findViewById(R.id.textViewNamaMonster);
		TextView typeMonster = (TextView)findViewById(R.id.textViewTypeMonster);
		iv.setTypeface(face);
		typeMonster.setTypeface(face);
		
		TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayoutPokeDex);
		TableRow tableRow;
		TextView textView;
		for(int i=0; i<=30;i++){
			tableRow = new TableRow(getBaseContext());
			textView = new TextView(getBaseContext());
			textView.setText("Bulba"+i);
			final Integer ii = new Integer(i);
			textView.setOnClickListener(new View.OnClickListener() {
			
			
				@SuppressLint("UseValueOf")
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					TextView tv = (TextView)findViewById(R.id.textViewNamaMonster);
					tv.setText("Bulba"+ii);
				}
			});
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
		}
	}
}
