package com.pokeranch.game.object;

import java.util.Scanner;

public class MonsterBall extends Item
{
	private float catchRate;	
	//ctor
	
	public MonsterBall(){
		// TODO Auto-generated constructor stub
		catchRate = 0;
	}
	
	@Override
	public void load(Scanner scan) {
		try{
			catchRate = scan.nextInt();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		super.load(scan);
	}
	
	//ctor berparameter
	public MonsterBall(float x)
	{
		catchRate = x;
	}
	
	//getter dan setter
	public float getCatchRate()
	{
		return catchRate;
	}

	void setCatchRate(float x)
	{
		catchRate = x;
	}

	int itemType()
	{
		return 2;
	}
}

