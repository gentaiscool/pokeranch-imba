package com.pokeranch.game.object;

import java.util.HashMap;

public class Monster{
	// TODO Auto-generated constructor stub
	private String name;
	private int level, exp, evoExp, bonusCash, bonusExp;
	private Status status, fullStatus;
	private Species species;
	private HashMap<String, Skill> skills; 
	private Time age;
	private static int maxNumSkill; //jumlah skill maksimal
		
	//ctor
	public Monster(){
		name = "";
	}

	//cctor
	public Monster(String name, Species species, int level){
		//srand(time(NULL));
		this.name = name;
		this.species = species;
		this.level = level;
		exp = 0;
		//evoExp = (2.7*level+17) * 3 * pow (1.02,(level-1)); //--> 50 - 6058 (3 - 21 x lawan selvl)
		//bonusCash = 5*(90*level/100 + species.getCombineRating()/6 * 7 ) + rand() % 15;//--> max 500
		//bonusExp = 3*(90*level/100 + species.getCombineRating()/6 * 7 ) + rand() % 9 ;//--> max 300
		
		int l = level;
		/*
		while (!isMaxNumSkill() < maxNumSkill && l > 0){
			try{
				Skill sk = species.getBaseSkill(l);
				skills.insert(pair<string,Skill>(sk.getName(), sk));
			}catch(...){
	
			}
			l--;
		}*/

		fullStatus = species.getBaseStat();
		/*Status delta(10, 10, 10, 10, "none"); //rumus?
		
		for (int i = 2; i <=level; i++){
			fullStatus+=delta;
		}
		status = fullStatus;
		*/
	}

	//setter getter
	String getName(){
		return name;
	}
	
	void setName(String nm){
		name = nm;
	}
	
	int getLevel(){
		return level;
	}
	
	int getExp(){
		return exp;
	}
	/*
	boolean addExp(int x){
		exp+=x;
		if (exp>=evoExp){
			if (level==100){
				exp = evoExp;
			}else{
				exp = exp - evoExp;
				evoExp = (2.7*level+17) * 3 * pow (1.02,(level-1)); //--> 50 - 6058 (3 - 21 x lawan selvl)
			bonusCash = 5*(90*level/100 + species.getCombineRating()/6 * 7 ) + rand() % 15;//--> max 500
			bonusExp = 3*(90*level/100 + species.getCombineRating()/6 * 7 ) + rand() % 9 ;//--> max 300
			
			level++;
			Status delta(10, 10, 10, 10, NONE); //rumus?
			
			fullStatus += delta;
			status += delta;
			
			if(getSpecies().getEvoLevel() == level){//sudah saatnya berubah!!!
					evolve();
				}
				
				return true;
			}
		}
		return false;
	}*/
	
	int getEvoExp(){
		return evoExp;
	}
	
	int getBonusCash(){
		return bonusCash;
	}
	
	int getBonusExp(){
		return bonusExp;
	}
	
	Status getFullStatus(){
		return fullStatus;
	}
	
	Status getStatus(){
		return status;
	}
	
	void updateStatusBy(Status st){
		//status += st;
		if(status.getHP()>fullStatus.getHP()) 
			status.setHP(fullStatus.getHP());
		if(status.getMP()>fullStatus.getMP()) 
			status.setMP(fullStatus.getMP());
		/*if(status.getAtk()>fullStatus.getAtk()) 
			status.setAtk(fullStatus.getAtk());
		if(status.getDef()>fullStatus.getDef()) 
			status.setDef(fullStatus.getDef());
			*/
		if(status.getHP()<0) 
			status.setHP(0);
		if(status.getMP()<0) 
			status.setMP(0);
		/*if(status.getAtk()<0) 
			status.setAtk(0);
		if(status.getDef()<0) 
			status.setDef(0);
		*/
	}
	/*
	pair<boolean, int> inflictDamage(Skill sk, Status lawan){
		
		Status damage = sk.getDamage();
		
		//critical hit
		
		srand(time(NULL));
		float critical = 1.f;
		if(rand()%100<10) 
			critical = 2.f;
		
		//element factor
		float elmtFactor = species.getElement().getDamageFactor(sk.getElement());
		
		int hp = status.getHP();
		
		status += damage;
		
		//HP calculation
		
		status.setHP( hp + int(float(damage.getHP()) * (float(lawan.getAtk()) /  float(status.getDef())) * critical + 0.5f));
		if(status.getHP() <= 0)
			status.setHP(0);
			
		int elmt; float eps = 0.0000001;
		if (damage.getHP()==0){
			critical = 1;
			elmt = 2;
		}else{
			if (abs(elmtFactor) < eps) elmt = 0;
			else if(abs(elmtFactor - 0.5) < eps) elmt = 1;
			else if(abs(elmtFactor - 1) < eps) elmt = 2;
			else if(abs(elmtFactor - 2) < eps) elmt = 3;
		}
		
		return make_pair(bool(int(critical - 1)), elmt);
	}
	*/
	void restoreStatus(){
		status = fullStatus;
	}
	
	Species getSpecies(){
		return species;
	}
	
	Time getAge(){
		return age;
	}
	
	void addAgeByMinute(int minute){
		age.addMinute(minute);
	}
	
	int getSkillNum(){
		return skills.size();
	}
	/*
	Skill getSkill(int num){
		map<string, Skill>::const_iterator it;
		int ind = 0;
		for(it = skills.begin(); it != skills.end(); ++it){
			if(ind == num)
				return it->second;
			ind++;
		}	
		throw -1;
	}
	
	Skill getSkill(String name) const{
		map<string, Skill>::const_iterator it = skills.find(name);
		
		if (it==skills.end()){
			throw -1;
		}else{
			return it->second;
		}
	}
	*/
	//Skill getRandomSkill(){
	/* Asumsi: List skill yang ada pada Monster ini sudah 
	   ada didefinisikan didatabase skill */
		//srand(time(NULL));
		//map<string, Skill>::const_iterator it;
		
		//it = skills.begin();
		//int n = rand() % skills.size();
		//advance(it, n);
	
		//return it.second;	
	//}
	/*
	void addSkill(Skill sk){
		if (!isMaxNumSkill()){
			skills.insert(pair<string,Skill>(sk.getName(), sk));
		}
	}
	
	void delSkill(Skill sk){
		skills.erase(sk.getName());
	}


	boolean evolve(){
		try{
			if (species.getEvoLevel() > level) 
				return false;
			console.readKey();
			cout<<"What's this? "<<getName()<<" is evolving!"<<endl;
			console.readKey();
			string evo = species.getEvolution();
			cout<<"Your "<<getName()<<" is evolving into "<<evo<<"!"<<endl;
			Species evolution = global::database.getSpecies(evo);
			Status diffstat = evolution.getBaseStat() - species.getBaseStat();
			
			status+=diffstat;
			fullStatus+=diffstat;
			
			species = evolution;
			
			return true;
		}catch(int err){
			return false;
		}
	}

	boolean isMaxNumSkill(){
		return maxNumSkill == skills.size();
	}
	
	void giveItem(StatItem item){
		fullStatus = fullStatus + item.getItemEffect();
	}
*/
}
	