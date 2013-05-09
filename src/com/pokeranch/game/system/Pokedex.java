package com.pokeranch.game.system;

import java.util.Collection;
import java.util.Iterator;

import com.pokeranch.game.object.DBLoader;
import com.pokeranch.game.object.Species;
import com.pokeranch.game.system.ScrollComponent.SelectionListener;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;


public class Pokedex implements IScreen{
	
	private ScreenManager manager;
	

	private Bitmap pokedextablet, pokedexlogo, panel, trans;

	int curScreenWidth, curScreenHeight;
	private ScrollComponent scroll;
	private String[] species;
	private TextComponent text;
	
		@SuppressLint("NewApi")
		public Pokedex(int screenWidth, int screenHeight) {
			// TODO Auto-generated constructor stub
			manager = ScreenManager.getInstance();
			//ss = new ScrollComponent(context, 100,0);
			
			curScreenWidth = screenWidth;
			curScreenHeight = screenHeight;
			
			
			trans = BitmapManager.getInstance().get("trans");
			panel = trans;
			
			Collection<Species> sp = DBLoader.getInstance().getAllSpecies();
			species = new String[sp.size()];
			Iterator<Species> it = sp.iterator();
			
			int i = 0;
			while (it.hasNext()){
				species[i] = it.next().getName();
				i++;
			}
			
			scroll = new ScrollComponent(species,220,100,screenHeight,new SelectionListener(){
				@Override
				public void selectAction(int selection) {
					showPoke(selection);
				}
			});
			
			pokedextablet = BitmapManager.getInstance().get("pokedextablet");
			pokedexlogo = BitmapManager.getInstance().get("pokedexlogo");

			text = new TextComponent("", 75, 90);
			
	
		}
		
		private void showPoke(int num){
			if(num==-1){
				panel = trans;
				text.setText("");
			}else{
				panel = BitmapManager.getInstance().get(species[num]+"_front");
				Species s = DBLoader.getInstance().getSpecies(species[num]);
				String snum = num < 10 ? "00" + (num+1) : "0" + (num+1);
				StringBuilder sb = new StringBuilder();
				sb.append(snum + " " + s.getName() +"\n");
				
				
				sb.append(s.getElement().getName() +" Monster\n");
				if(s.getEvoSpecies()==null){
					sb.append("No Evolution\n");
				}else{
					sb.append("Evolution:\n");
					Species s1 = s;
					while(s1.getEvoSpecies()!=null){
						sb.append(s1.getEvoSpecies().getName() + " - Lv. " + s1.getEvoLevel() + "\n");
						s1 = s1.getEvoSpecies();
					}
				}
				sb.append("\nBase Skill :\n");
				for(int i = 0; i < s.getBaseSkillNum(); i++){
					sb.append((i+1) +". "+s.getBaseSkill(i).getName() + "\n");
				}
				
				text.setText(sb.toString());
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
			canvas.drawBitmap(pokedexlogo, new Rect(0,0,pokedexlogo.getWidth(), pokedexlogo.getHeight()), new RectF(2,8,132,60), null);
			canvas.drawBitmap(pokedextablet, new Rect(0,0,pokedextablet.getWidth(), pokedextablet.getHeight()), new RectF(2,68,175,240), null);
			canvas.drawBitmap(panel, new Rect(0,0,panel.getWidth(), panel.getHeight()), new RectF(20,90,70,140), null);
			text.draw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent e, float magX, float magY) {
			// TODO Auto-generated method stub
			
			scroll.onTouchEvent(e, magX, magY);
		}
}
