package com.pokeranch.game.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.pokeranch.game.object.Status.Effect;

import android.graphics.Point;

public class Monster{
	private String name;
	private int level, exp, evoExp, bonusCash, bonusExp;
	private Status status, fullStatus;
	private Species species;
	private HashMap<String, Skill> skills; 
	private Time age;
	private static int maxNumSkill; //jumlah skill maksimal
	private Random random = new Random();
	
	//ctor
	public Monster(){
		name = "";
	}

	//cctor
	public Monster(String name, Species species, int level){
		random.setSeed(1);
		
		this.name = name;
		this.species = species;
		this.level = level;
		exp = 0;
		evoExp = (int) ((2.7*level+17) * 3 * Math.pow (1.02,(level-1))); //--> 50 - 6058 (3 - 21 x lawan selvl)
		bonusCash = 5*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(15);//--> max 500
		bonusExp = 3*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(9);//--> max 300

		fullStatus = species.getBaseStat();
		
		Status delta = new Status(10, 10, 10, 10, Effect.NONE); //rumus?
		
		for (int i = 2; i <=level; i++){
			fullStatus.updateBy(delta.getHP(), delta.getMP(), delta.getAttack(), delta.getDefense(), delta.getEffect());
		}
		status = fullStatus;
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
	
	boolean addExp(int x){
		Random random = new Random();
		random.setSeed(1);
		exp+=x;
		if (exp>=evoExp){
			if (level==100){
				exp = evoExp;
			}else{
				exp = exp - evoExp;
			evoExp = (int) ((2.7*level+17) * 3 * Math.pow(1.02,(level-1))); //--> 50 - 6058 (3 - 21 x lawan selvl)
			bonusCash = 5*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(15);//--> max 500
			bonusExp = 3*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(9);//--> max 300
			
			level++;
			Status delta = new Status(10, 10, 10, 10, Effect.NONE); //rumus?
			
			fullStatus.updateBy(delta.getHP(), delta.getMP(), delta.getAttack(), delta.getDefense(), delta.getEffect());
			status.updateBy(delta.getHP(), delta.getMP(), delta.getAttack(), delta.getDefense(), delta.getEffect());
			
			if(getSpecies().getEvoLevel() == level){//sudah saatnya berubah!!!
					evolve();
				}
				
				return true;
			}
		}
		return false;
	}
	
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
		status.updateBy(st.getHP(), st.getMP(), st.getAttack(), st.getDefense(), st.getEffect());
		if(status.getHP()>fullStatus.getHP()) 
			status.setHP(fullStatus.getHP());
		if(status.getMP()>fullStatus.getMP()) 
			status.setMP(fullStatus.getMP());
		if(status.getAttack()>fullStatus.getAttack()) 
			status.setAttack(fullStatus.getAttack());
		if(status.getDefense()>fullStatus.getDefense()) 
			status.setDefense(fullStatus.getDefense());
			
		if(status.getHP()<0) 
			status.setHP(0);
		if(status.getMP()<0) 
			status.setMP(0);
		if(status.getAttack()<0) 
			status.setAttack(0);
		if(status.getDefense()<0) 
			status.setDefense(0);
	}
	
	
	Point inflictDamage(Skill sk, Status lawan){
		Point newPoint = new Point();
		Status damage = sk.getDamage();
		
		//critical hit
		random.setSeed(1);
		float critical = 1.f;
		if(random.nextInt(100)<10) 
			critical = 2.f;
		
		//element factor
		float elmtFactor = species.getElement().getDamageFactor(sk.getElement());
		
		int hp = status.getHP();
		
		status.updateBy(damage.getHP(), damage.getMP(), damage.getAttack(), damage.getDefense(), damage.getEffect());
		
		//HP calculation
		status.setHP( (int) (hp + ((float)(damage.getHP()) * ((float)(lawan.getAttack()) /  (float)(status.getDefense())) * critical + 0.5f)));
		if(status.getHP() <= 0)
			status.setHP(0);
			
		int elmt = 0; float eps = 0.0000001f;
		if (damage.getHP()==0){
			critical = 1;
			elmt = 2;
		}else{
			if (Math.abs(elmtFactor) < eps) elmt = 0;
			else if(Math.abs(elmtFactor - 0.5) < eps) elmt = 1;
			else if(Math.abs(elmtFactor - 1) < eps) elmt = 2;
			else if(Math.abs(elmtFactor - 2) < eps) elmt = 3;
		}
		
		//creates Point
		newPoint.set((int) (critical-1), elmt);
		
		return newPoint;
	}
	
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
	Skill getSkill(int num){
		ArrayList<Skill> s = (ArrayList<Skill>) skills.values();
		return s.get(num);
	}
	
	Skill getSkill(String name){
		return skills.get(name);
	}
	
	Skill getRandomSkill(){
	/* Asumsi: List skill yang ada pada Monster ini sudah 
	   ada didefinisikan didatabase skill */
		int num;
		random.setSeed(1);
		ArrayList<Skill> s = (ArrayList<Skill>) skills.values();
		num = random.nextInt(skills.size());
		return s.get(num);	
	}
	
	void addSkill(Skill sk){
		if (!isMaxNumSkill()){
			skills.put(sk.getName(), sk);
		}
	}
	
	void delSkill(Skill sk){
		skills.remove(sk.getName());
	}


	boolean evolve(){
		if (species.getEvoLevel() > level) 
			return false;
		Species evo = species.getEvoSpecies();
		Status diffstat = new Status();
		diffstat.substractStatus(evo.getBaseStat(), species.getBaseStat());
		
		status.updateBy(diffstat.getHP(), diffstat.getMP(), diffstat.getAttack(), diffstat.getDefense(), diffstat.getEffect());
		fullStatus.updateBy(diffstat.getHP(), diffstat.getMP(), diffstat.getAttack(), diffstat.getDefense(), diffstat.getEffect());
		
		species = evo;
		
		return true;
	}
	
	boolean evolveSkill(){
		boolean found = false;
		for(int i=0; i<=3; i++){
			if(skills.get(i).getNextSkillLevel() < level){
				continue;
			}
			else{
				found = true;
				Skill skill = skills.get(i);
				this.delSkill(skill);
				this.addSkill(skill.getNextSkill());
			}
		}
		return found;
	}

	boolean isMaxNumSkill(){
		return maxNumSkill == skills.size();
	}
	
	void giveItem(StatItem item){
		fullStatus.updateBy(item.getItemEffect().getHP(), item.getItemEffect().getMP(), item.getItemEffect().getAttack(), item.getItemEffect().getDefense(), item.getItemEffect().getEffect());
	}
	
	public Monster getRandomMonster(int level, int maxRating){
		Species ss;
		ss = DBLoader.getInstance().getRandomSpecies(maxRating);
		return new Monster(ss.getName(), ss, level);
	}
	
	public static Monster combineMonster(Player player1, String nmonster1, String nmonster2) throws Exception{	
		Element elem3;
		//kode dibawah digunakan karena database species tidak mempunyai 
		//monster dengan tipe elemen normal
		//sehingga ketika dicombine tidak mendapatkan apa-apa
		do{
			elem3 = DBLoader.getInstance().getRandomElement();
		}while(elem3.getName() == "Normal");
		
		Monster monster1 = player1.getMonster(nmonster1);
		Monster monster2 = player1.getMonster(nmonster2);
		
		if(monster1 == null || monster2 == null){
			throw new Exception();
		}
		else{
			int rate1 = monster1.getSpecies().getCombineRating();
			int rate2 = monster2.getSpecies().getCombineRating();
			int rate3 = (int) Math.floor(Math.sqrt(rate1*rate1 + rate2*rate2));
			
			Species spec3 = DBLoader.getInstance().getCombinedSpecies(elem3, rate3);
			
			int lvl3 = (monster1.getLevel()+ monster2.getLevel())/2;
			
			return new Monster("", spec3, lvl3);
		}
	}
}