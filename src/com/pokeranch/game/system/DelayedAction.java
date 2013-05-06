package com.pokeranch.game.system;

public abstract class DelayedAction {
	int count = 0;
	public void update(){
		count++;
		if(count >= getDelay()){
			doAction();
		}
	}
	
	public boolean finished(){
		return count >= getDelay();
	}
	
	public void reset(){
		count = 0;
	}
	
	public abstract void doAction();
	public abstract int getDelay(); // set delay, isinya cuma return mau berapa delaynya
}
