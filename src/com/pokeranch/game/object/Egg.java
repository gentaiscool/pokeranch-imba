package com.pokeranch.game.object;

public class Egg extends Item{
	
	private Time hatchTime;
	private Species species;
	
	//ctor
	public Egg(Time ht) {
		// TODO Auto-generated constructor stub
		setSpecies(DBLoader.getInstance().getRandomSpecies());
		setHatchTime(ht);
	}
	
	public boolean isHatch(){
		if((getHatchTime().getYear() == 0) && (getHatchTime().getMonth() == 0) && (getHatchTime().getDay() == 0) && 
				(getHatchTime().getHour() == 0) && (getHatchTime().getMinute() == 0)){
			return true;
		}
		else
			return false;
	}
	
	//hatch
	public void updateHatchTime(int m)
	{
		getHatchTime().decMinute(m);
	}

	//getter dan setter
	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public Time getHatchTime() {
		return hatchTime;
	}

	public void setHatchTime(Time hatchTime) {
		this.hatchTime = hatchTime;
	}
	
}
