package com.pokeranch.game.object;

import java.util.Scanner;

public class StatItem extends Item{
	
	private boolean permanent;
	private Status.Effect cureStatus;
	private Status itemEffect;
	
	//konstructor
	public StatItem()
	{
		permanent = false;
		itemEffect = new Status();
	}
	
	@Override
	public void load(Scanner scan) {
		try{
			permanent = scan.nextInt() == 1;
			itemEffect.load(scan);
			cureStatus = Status.Effect.valueOf(scan.next());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		super.load(scan);
	}
	
	//getter dan setter
	boolean getPermanent()
	{
		return permanent;
	}

	void setPermanent(boolean p)
	{
		permanent = p;
	}

	public Status.Effect getCureStat()
	{
		return cureStatus;
	}

	void setCureStat(Status.Effect cureff) 
	{
		cureStatus=cureff;
	}

	public Status getItemEffect()
	{
		return itemEffect;
	}

	void setItemEffect(Status s){
		itemEffect = s;
	}

	void cureEffect(Status s){
		if ((name.equals("Awakening")) || (name.equals("Antidote")) || (name.equals("ParalyzCure")) || (name.equals("BurnHeal"))) {
			if (cureStatus == s.getEffect()) {
				//s.setEffect("None");
			} else {
				//throw "Bukan waktu/tempat yang tepat untuk item ini";
			}
		} 
	}

	int itemType() {
		return 1;
	}
}
