package com.pokeranch.game.object;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.pokeranch.game.object.Status.Effect;

import android.graphics.Point;
import android.util.Log;
//import android.util.Log;

public class Monster{
	private String name;
	private int level, exp, lvlExp, bonusCash, bonusExp;
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
		age = new Time();
		skills = new HashMap<String, Skill>();
	}

	//cctor
	public Monster(String name, Species species, int level){
		random = new Random();
		if(species == null){
			Log.d("species","ini null");
		}
		this.name = name;
		this.species = species;
		this.level = level;
		skills = new HashMap<String, Skill>();
		age = new Time();
		exp = 0;
		lvlExp = (int) ((2.7*level+17) * 3 * Math.pow (1.02,(level-1))); //--> 50 - 6058 (3 - 21 x lawan selvl)
		bonusCash = 5*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(15);//--> max 500
		bonusExp = 3*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(9);//--> max 300

		fullStatus = new Status(species.getBaseStat());
		
		for (int i = 2; i <=level; i++){
			fullStatus.updateBy(10, 10, 10, 10, Effect.NONE); //rumus?
		}
		
		for (int i = 0; i < species.getBaseSkillNum(); i++)
			addSkill(species.getBaseSkill(i));
		
		status = new Status(fullStatus);
	}
	
	public static Species getSpeciesById(int id){
		String name[] = {"Bulba","Ivy","Venu","Venu","Charchar","Charmeleon","Charizard","Squir","Wartotle","Blastoise","Metapod","Metapod","Butter","Weedle","Kakuna","Beedril","Pidgey","Pidgeot","Pidgeotto","Pidgeotto"};
		//Log.d("harits99", "id: " + id + ", monster name: " + name[id]);
		return DBLoader.getInstance().getSpecies(name[id]);
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
	
	public int addExp(int x){
		Random random = new Random();
		exp+=x;
		if (exp>=lvlExp){
			if (level==100){
				exp = lvlExp;
			}else{
				exp = exp - lvlExp;
				lvlExp = (int) ((2.7*level+17) * 3 * Math.pow(1.02,(level-1))); //--> 50 - 6058 (3 - 21 x lawan selvl)
				bonusCash = 5*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(15);//--> max 500
				bonusExp = 3*(90*level/100 + species.getCombineRating()/6 * 7 ) + random.nextInt(9);//--> max 300
				
				level++;
				Status delta = new Status(10, 10, 10, 10, Effect.NONE); //rumus?
				
				fullStatus.updateBy(delta.getHP(), delta.getMP(), delta.getAttack(), delta.getDefense(), delta.getEffect());
				status.updateBy(delta.getHP(), delta.getMP(), delta.getAttack(), delta.getDefense(), delta.getEffect());
				
				evolveSkill();
				
				if (evolve()) return 2;
				
				return 1;
			}
		}
		return 0;
	}
	
	public int getLvlExp(){
		return lvlExp;
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
	
	
	public Point inflictDamage(Skill sk, Monster lawan){
		Point newPoint = new Point();
		Status damage = sk.getDamage();
		Status statlawan = lawan.getStatus();
		
		//critical hit
		float critical = 1.f;
		if(random.nextInt(100)<10) 
			critical = 2.f;
		
		//element factor
		float elmtFactor = species.getElement().getDamageFactor(sk.getElement());
		
		int hp = status.getHP();
		
		status.updateBy(damage.getHP(), damage.getMP(), damage.getAttack(), damage.getDefense(), damage.getEffect());
		
		//HP calculation
		float lvl = (10.f / 3.f)* (float) level / (float) lawan.getLevel();
		float hpcalc = ((float)(damage.getHP()) * ((float)(statlawan.getAttack()) /  (float)(status.getDefense())) * (elmtFactor) * critical + 0.5f) / lvl;
		status.setHP( (int) (hp + hpcalc));
				
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
		status = new Status(fullStatus);
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
	
	public HashMap<String,Skill> getAllSkill(){
		return skills;
	}
	public Skill getSkill(String name){
		return skills.get(name);
	}
	
	public Skill getRandomSkill(){
	/* Asumsi: List skill yang ada pada Monster ini sudah 
	   ada didefinisikan didatabase skill */
		int num;
		Skill[] s = new Skill[4];
		skills.values().toArray(s);
		num = random.nextInt(skills.size());
		return s[num];	
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
		
		Skill[] c = new Skill[4];
		skills.values().toArray(c);
		
		for(int i=0; i < 4; i++){
			int lvl = c[i].getNextSkillLevel();
			if(lvl!=-1 && lvl <= level){
				found = true;
				Skill skill = c[i];
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
		if(item.getPermanent()){
			fullStatus.updateBy(item.getItemEffect().getHP(), item.getItemEffect().getMP(), item.getItemEffect().getAttack(), item.getItemEffect().getDefense(), item.getItemEffect().getEffect());
			restoreStatus();
		}else{
			if(status.getEffect().equals(item.getCureStat())){
				status.setEffect(Effect.NONE);
			}
			updateStatusBy(item.getItemEffect());
		}
	}
	
	public static Monster getRandomMonster(int level, int maxRating){
		Species ss;
		if(maxRating < 1){
			maxRating = 1;
		}
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
			Log.d("error","ini dia"+nmonster1+" "+nmonster2);
			throw new Exception();
		}
		else{
			int rate1 = monster1.getSpecies().getCombineRating();
			int rate2 = monster2.getSpecies().getCombineRating();
			int rate3 = (int) Math.floor(Math.sqrt(rate1*rate1 + rate2*rate2));
			
			Species spec3 = DBLoader.getInstance().getCombinedSpecies(elem3, rate3);
			
			int lvl3 = (monster1.getLevel()+ monster2.getLevel())/2;
			Log.d("error","ini dia2"+spec3.getName());
			return new Monster("", spec3, lvl3);
		}
	}
	
	public void load(Scanner scan){
		scan.next();
		name=scan.next();
		scan.next();
		age=new Time();
		age.load(scan);
		scan.next();
		species=DBLoader.getInstance().getSpecies(scan.next());
		scan.next();
		level=scan.nextInt();
		scan.next();
		exp=scan.nextInt();
		scan.next();
		lvlExp=scan.nextInt();
		scan.next();
		bonusCash=scan.nextInt();
		scan.next();
		bonusExp=scan.nextInt();
		scan.next();
		status=new Status();
		status.load(scan);
		scan.next();
		fullStatus=new Status();
		fullStatus.load(scan);
		scan.next();
		String skillName;
		int i;
		for(i=0;i<4;i++){
			skillName=scan.next();
			skills.put(skillName, DBLoader.getInstance().getSkill(skillName));
		}	
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append( "MonsterName: "+getName()+"\n"+
					"Age: "+getAge().toString()+"\n"+
					"Species: "+getSpecies().getName()+" Level: "+level+"\n"+
					"Exp: "+getExp()+" EvoExp: "+getLvlExp()+"\n"+
					"BonusCash: "+getBonusCash()+" BonusExp: "+getBonusExp()+"\n"+
					"Status(hp,mp,att,def,eff):\n"+ getStatus().toString()+" /\n"+getFullStatus().toString()+"\n");
	    Set<String> namaSkill = getAllSkill().keySet();
	      // Get an iterator
	    Iterator<String> i = namaSkill.iterator();
	    str.append("Skills:\n");
	    while(i.hasNext()) {
	    	str.append(i.next()+" ");
	    }
	    return str.toString();
	}
}
