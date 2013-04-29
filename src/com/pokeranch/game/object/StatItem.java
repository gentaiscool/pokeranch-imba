package com.pokeranch.game.object;

public class StatItem extends Item{
	
	private boolean permanent;
	private Status.Effect cureStatus;
	private Status itemEffect;
	
	//konstructor
	public StatItem()
	{
		permanent = false;
	}
	
	//equals
	public boolean equals(StatItem comparedStat)
	{
		if(this.permanent == comparedStat.permanent && this.cureStatus.equals(comparedStat.cureStatus) && this.itemEffect.equals(comparedStat.itemEffect)){
			return true;
		}
		else return false;
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

	Status.Effect getCureStat()
	{
		return cureStatus;
	}

	void setCureStat(Status.Effect cureff) 
	{
		cureStatus=cureff;
	}

	Status getItemEffect()
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
