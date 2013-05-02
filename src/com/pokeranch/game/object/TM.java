package com.pokeranch.game.object;

import java.util.Scanner;

public class TM extends Item{

	private Skill skill;
	
	//ctor
	TM(){
		
	}
	
	public void load(Scanner scan) {
		// TODO Auto-generated method stub
		super.load(scan);
		skill = DBLoader.getInstance().getSkill(this.name);
	}

	//getter dan setter
	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	
}
