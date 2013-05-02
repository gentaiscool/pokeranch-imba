package com.pokeranch.game.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.pokeranch.game.object.Status.Effect;

import android.graphics.Point;
import android.util.Log;

public class Monster{
	private String name;
	private int level, exp, evoExp, bonusCash, bonusExp;
	private Status status, fullStatus;
	private Species species;
	private HashMap<String, Skill> skills; 
	private Time age;
	private int maxNumSkill;
	private Random random = new Random();
	
	//ctor
	public Monster(){
		name = "";
		status = new Status();
		fullStatus = new Status();
		//Time = 
	}

	//cctor
	public Monster(String name, Species species, int level){
		
		this.name = name;
		this.species = species;
		this.level = level;
		skills = new HashMap<String, Skill>();
		
		exp = 0;
		evoExp = (int) ((2.7*level+17) * 3 * Math.pow (1.02,(level-1))); //--> 50 - 6058 (3 - 21 x lawan selvl)
		bonusCash = 5*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(15);//--> max 500
		bonusExp = 3*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(9);//--> max 300

		fullStatus = species.getBaseStat();
		
		Status delta = new Status(10, 10, 10, 10, Effect.NONE); //rumus?
		
		for (int i = 2; i <=level; i++){
			fullStatus.updateBy(delta.getHP(), delta.getMP(), delta.getAttack(), delta.getDefense(), delta.getEffect());
		}
		
		for (int i = 0; i < species.getBaseSkillNum(); i++)
			addSkill(species.getBaseSkill(i));
		
		status = new Status();
		status.set(fullStatus.getHP(), fullStatus.getMP(), fullStatus.getAttack(), fullStatus.getDefense(), fullStatus.getEffect());
	}

	//setter getter
	public String getName(){
		return name;
	}
	
	public void setName(String nm){
		name = nm;
	}
	
	public int getLevel(){
		return level;
	}
	
	public int getExp(){
		return exp;
	}
	
	public boolean addExp(int x){
		Random random = new Random();
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
	
	public int getEvoExp(){
		return evoExp;
	}
	
	public int getBonusCash(){
		return bonusCash;
	}
	
	public int getBonusExp(){
		return bonusExp;
	}
	
	public Status getFullStatus(){
		return fullStatus;
	}
	
	public Status getStatus(){
		return status;
	}
	
	public void updateStatusBy(Status st){
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
	
	
	public Point inflictDamage(Skill sk, Status lawan){
		Point newPoint = new Point();
		Status damage = sk.getDamage();
		
		//critical hit
		float critical = 1.f;
		if(random.nextInt(100)<10) 
			critical = 2.f;
		
		//element factor
		float elmtFactor = species.getElement().getDamageFactor(sk.getElement());
		
		int hp = status.getHP();
		
		status.updateBy(damage.getHP(), damage.getMP(), damage.getAttack(), damage.getDefense(), damage.getEffect());
		
		//HP calculation
		//status.setHP( (int) (hp + ((float)(damage.getHP()) * ((float)(lawan.getAttack()) /  (float)(status.getDefense())) * critical + 0.5f)));
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
	
	public void restoreStatus(){
		status = fullStatus;
	}
	
	public Species getSpecies(){
		return species;
	}
	
	public Time getAge(){
		return age;
	}
	
	public void addAgeByMinute(int minute){
		age.addMinute(minute);
	}
	
	public int getSkillNum(){
		return skills.size();
	}
	public Skill getSkill(int num){
		Object[] s = (skills.values()).toArray();
		return (Skill) s[num];
	}
	
	public Skill getSkill(String name){
		return skills.get(name);
	}
	
	public Skill getRandomSkill(){
	/* Asumsi: List skill yang ada pada Monster ini sudah 
	   ada didefinisikan didatabase skill */
		int num;
		ArrayList<Skill> s = (ArrayList<Skill>) skills.values();
		num = random.nextInt(skills.size());
		return s.get(num);	
	}
	
	public void addSkill(Skill sk){
		if (!isMaxNumSkill()){
			skills.put(sk.getName(), sk);
		}
	}
	
	public void delSkill(Skill sk){
		skills.remove(sk.getName());
	}


	public boolean evolve(){
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
	
	public boolean evolveSkill(){
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

	public boolean isMaxNumSkill(){
		return maxNumSkill == 4;
	}
	
	public void giveItem(StatItem item){
		fullStatus.updateBy(item.getItemEffect().getHP(), item.getItemEffect().getMP(), item.getItemEffect().getAttack(), item.getItemEffect().getDefense(), item.getItemEffect().getEffect());
	}
	
	public static Monster getRandomMonster(int level, int maxRating){
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
	
	public String toString(StringBuilder str){
		str.append( "NamaMonster: "+name+"\n"+
					"Umur: "+age.toString()+"\n"+
					"Spesies: "+species.getName()+" Level: "+level+"\n"+
					"Exp: "+exp+" EvoExp: "+evoExp+"\n"+
					"BonusCash: "+bonusCash+" BonusExp: "+bonusExp+"\n"+
					"Status(hp,mp,att,def,eff): "+ status.toString()+" / "+fullStatus.toString()+"\n");
	    Set namaSkill = skills.keySet();
	      // Get an iterator
	    Iterator<String>i = namaSkill.iterator();
	    str.append("Skill:\n");
	    while(i.hasNext()) {
	    	str.append(i.next()+"\n");
	    }
	    return str.toString();
	}
}