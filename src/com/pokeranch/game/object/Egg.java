package com.pokeranch.game.object;

public class Egg extends Item{
	
	Time hatchTime;
	Species species;
	
	//ctor
	public Egg(Time ht) {
		// TODO Auto-generated constructor stub
		species = DBLoader.getInstance().getRandomSpecies();
		hatchTime = ht;
	}
	
	public boolean isHatch(){
		if((hatchTime.getYear() == 0) && (hatchTime.getMonth() == 0) && (hatchTime.getDay() == 0) && 
				(hatchTime.getHour() == 0) && (hatchTime.getMinute() == 0)){
			return true;
		}
		else
			return false;
	}
	
	//hatch
	public void updateHatchTime(int m)
	{
		hatchTime.decMinute(m);
	}
	
}
