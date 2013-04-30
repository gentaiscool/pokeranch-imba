package com.pokeranch.game.system;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.widget.EditText;

public class MessageManager {
	private static Context context;
	private static String title;
	
	public interface Action{
		public void proceed(Object o);
		public void cancel(); //biasanya dikosongin aja
	}
	
	public static void setContext(Context context){
		MessageManager.context = context;
	}
	
	public static void setAppTitle(String title){
		MessageManager.title = title;
	}
	
	//alert biasa pake tombol ok
	public static void alert(String message){
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle(title);
		alert.setMessage(message);
		alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();                      
		    }  
		}); 
		alert.show();
	}
	
	//confirm ok cancel
	public static void confirm(String message, Action action){
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		final Action act = action;
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.proceed(null);
		    }  
		});
		
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.cancel();
		    }  
		});
		
		alert.show();
	}
	
	//minta input string
	public static void prompt(String message, Action action){
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		final Action act = action;
		alert.setTitle(title);
		alert.setMessage(message);
		final EditText input = new EditText(context); 
		alert.setView(input);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.proceed(input.getText());
		    }  
		});
		
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.cancel();
		    }  
		});
		
		alert.show();
	}
	
	//minta pilihan (checkbox)
	private static boolean[] choice;
	public static void multiChoice(String message, CharSequence[] c, Action action){
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		final Action act = action;
		choice = new boolean[3];
		for(int i = 0; i < choice.length; i++) choice[i] = false;
		alert.setTitle(message);
		alert.setMultiChoiceItems(c, null, new OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				choice[which] = isChecked;
			}
		});
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.proceed(choice);
		    }  
		});
		
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.cancel();
		    }  
		});
		
		alert.show();
	}
	
	//minta pilihan (radiobox, pilihan default index 0)
	private static int sChoice = 0;
	public static void singleChoice(String message, CharSequence[] c, Action action){
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		final Action act = action;
		sChoice = 0;
		alert.setTitle(message);
		alert.setSingleChoiceItems(c, 0, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sChoice = which;
			}
		});
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.proceed(sChoice);
		    }  
		});
		
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  
		    @Override  
		    public void onClick(DialogInterface dialog, int which) {  
		        dialog.dismiss();
		        act.cancel();
		    }  
		});
		
		alert.show();
	}
}
