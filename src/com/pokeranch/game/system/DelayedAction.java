package com.pokeranch.game.system;

public abstract class DelayedAction {
	int count = 0;
	public void update(){
		count++;
		if(count >= getDelay()){
			doAction();
		}
	}
	
	public void updateFrequently(int tick){
		count++;
		if(count % tick == 0){
			doAction();
		}
	}
	
	public boolean finished(){
		return count >= getDelay();
	}
	
	public void forceFinish(){
		count = getDelay();
	}
	
	public void reset(){
		count = 0;
	}
	
	public abstract void doAction();
	public abstract int getDelay(); 
		// isinya cuma return mau berapa delaynya (skip berapa update sebelum doAction())
}
