package com.pokeranch.game.object;

public class TM extends Item{

	private Skill skill;
	
	//ctor
	TM(){
		
	}

	//getter dan setter
	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	
}
