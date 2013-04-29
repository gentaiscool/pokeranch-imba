package com.pokeranch.game.system;

import android.os.Bundle;
import android.app.Activity;
import com.pokeranch.game.object.DBLoader;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DBLoader.initialize(this.getAssets());
	}


}
