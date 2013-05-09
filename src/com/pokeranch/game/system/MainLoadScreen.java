package com.pokeranch.game.system;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.MonsterBall;
import com.pokeranch.game.object.Player;
import com.pokeranch.game.object.PlayerSaveLoader;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.object.StatItem;
import com.pokeranch.game.object.TM;
import com.pokeranch.game.system.BitmapButton.TouchListener;
import com.pokeranch.game.system.MessageManager.Action;
import com.pokeranch.game.system.ScrollComponent.SelectionListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;


public class MainLoadScreen implements IScreen{
	
	private String filepath = "SavedGames";
	private ScreenManager manager;

	private Bitmap panel, trans;

	int curScreenWidth, curScreenHeight;
	private ScrollComponent scroll;
	private String[] monsterBall, statItem, tm, loadFiles;
	private TextComponent text, textMoney;
	Collection<MonsterBall> monsterBalls;
	Collection<StatItem> statItems;
	Collection<TM> tms;
	StringBuilder sbInfo = new StringBuilder();
	
	ArrayList<MonsterBall> arrMonsterBall = new ArrayList<MonsterBall>();
	ArrayList<StatItem> arrStatItem = new ArrayList<StatItem>();
	ArrayList<TM> arrTM = new ArrayList<TM>();
	
	private boolean show = false;
	
	private BitmapButton buy;
	
	private Context curContext;
	
		public MainLoadScreen(Context context, int screenWidth, int screenHeight){
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			
			curContext = context;
			
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			File[] fis = context.getExternalFilesDir(filepath).listFiles();
			
			ArrayList<String> files = new ArrayList<String>();
			
			
			int ii = 0;
			for(File file : fis){
				if(file.getName().contains(".sav") && !file.getName().equals(".sav")){
					files.add(file.getName());
				}
			}
			loadFiles = new String[files.size()+1];		
			for(String str : files){
				loadFiles[ii]= str;
				ii++;
			}
			
			loadFiles[ii]="Back";
			
			scroll = new ScrollComponent(loadFiles,220,100,screenHeight,new SelectionListener(){
				@Override
				public void selectAction(int selection) {
					showLoadFiles(selection);
				}
			});
					
			text = new TextComponent("Load saved file", 10, 25);
			textMoney = new TextComponent("", 10, 180);
	
		}
				
		public void showLoadFiles(int num){
			if(num == loadFiles.length-1){
				ScreenManager.getInstance().pop();
			}
			else if(num!=-1){
				Log.d("namamaa", loadFiles[num].substring(0, loadFiles[num].length()-4)+"");
				Player player = PlayerSaveLoader.getInstance().loadPlayer(loadFiles[num].substring(0, loadFiles[num].length()-4));
				AreaManager am = new AreaManager(curContext, curScreenWidth, curScreenHeight, player);
				am.setCurArea(DBLoader.getInstance().getArea("HOME"));
				am.setPlayerCord(new Point(8,5));
				ScreenManager.getInstance().push(am);
			}
		}
		
		@Override
		public void update() {
			//nothing todo here
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			canvas.drawColor(Color.WHITE);
			scroll.draw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			scroll.onTouchEvent(e, magX, magY);
		}
}
