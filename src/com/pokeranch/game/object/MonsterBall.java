package com.pokeranch.game.object;

public class MonsterBall extends Item
{
	private float catchRate;	
	//ctor
	
	public MonsterBall(){
		// TODO Auto-generated constructor stub
		catchRate = 0;
	}
	
	//ctor berparameter
	public MonsterBall(float x)
	{
		catchRate = x;
	}
	
	//getter dan setter
	float getCatchRate()
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

