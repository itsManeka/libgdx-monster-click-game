package com.monsterclickgame.monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.monsterclickgame.attacks.Attack;
import com.monsterclickgame.attacks.AttackManager;
import com.monsterclickgame.battle.AreaManager.Area;
import com.monsterclickgame.battle.TypeManager.Type;
import com.monsterclickgame.eggs.Egg;

public class MyMonster {
	private int pctDrop = 0;
	
	private boolean isDefending = false;
	private boolean isFlying = false;
	private boolean isResting = false;
	
	private boolean isEgg;
	
	private long level;
	
	private long clicks;

	private int atkBase;
	private int defBase;
	private int spdBase;
	private int hpBase;
	private int spBase;
	private int rpBase;
	private int clicksToUpBase;
	
	private int atk;
	private int def;
	private int spd;
	private int hp;
	private int sp;
	private int rp;
	private int pctStatusUp;
	private int clicksToUp;
	
	private int sumAttributes;
	private int sumMinAttributes;
	private int sumMaxAttributes;
	
	private String myMonsterName;

	private Egg egg;
	private Monster monster;
	
	private Attack normalAttack;
	private Attack specialAttack1;
	private Attack specialAttack2;
	
	private AttackManager attackManager;
	
	public MyMonster(Egg egg, Monster monster) {
		this.level = 0;
		this.clicks = 0;
		
		this.isEgg = true;
		
		this.egg = egg;
		this.monster = monster;
		
		sumAttributes = 0;
		sumMaxAttributes = 0;
		
		clicksToUp = clicksToUpBase = MathUtils.random(monster.getMinClicksToUp(), monster.getMaxClicksToUp());
	}
	
	@SuppressWarnings("rawtypes")
	public Animation getAnimation(String animation) {
		if (this.isEgg) 
			return egg.getAnimation(animation);
		else
			return monster.getAnimation(animation);
	}
	
	public String getName() {
		if (this.isEgg)
			return "Ovo";
		else {
			if (myMonsterName == null) 
				return monster.getMonsterName();
			else
				return myMonsterName;
		}
	}
	
	public String getOriginalName() {
		return monster.getMonsterName();
	}
	
	public void setMyMonsterName(String myMonsterName) {
		this.myMonsterName = myMonsterName;
	}

	public void increaseClick() {
		this.clicks ++;
	}
	
	public long getClicksToUp() {
		return this.clicksToUp;
	}
	
	public void setEgg(boolean b) {
		this.isEgg = b;
	}
	
	public void setNormalAttack(Attack attack) {
		this.normalAttack = attack;
	}
	
	public void setSpecialAttack1(Attack attack) {
		this.specialAttack1 = attack;
	}
	
	public void setSpecialAttack2(Attack attack) {
		this.specialAttack2 = attack;
	}
	
	public long getLevel() {
		return this.level;
	}
	
	public float getAtk() {
		return this.atk;
	}
	
	public float getDef() {
		return this.def;
	}
	
	public float getSpd() {
		return this.spd;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPctStatusUp() {
		return pctStatusUp;
	}

	public void setPctStatusUp(int pctStatusUp) {
		this.pctStatusUp = pctStatusUp;
	}

	public int getAtkBase() {
		return atkBase;
	}

	public void setAtkBase(int atkBase) {
		this.atkBase = atkBase;
	}

	public int getDefBase() {
		return defBase;
	}

	public void setDefBase(int defBase) {
		this.defBase = defBase;
	}

	public int getSpdBase() {
		return spdBase;
	}

	public void setSpdBase(int spdBase) {
		this.spdBase = spdBase;
	}

	public int getHpBase() {
		return hpBase;
	}

	public void setHpBase(int hpBase) {
		this.hpBase = hpBase;
	}

	public int getSpBase() {
		return spBase;
	}

	public void setSpBase(int spBase) {
		this.spBase = spBase;
	}

	public Type getType() {
		return this.monster.getType();
	}
	
	public Area getArea() {
		return this.monster.getArea();
	}
	
	public boolean getIsDefending() {
		return this.isDefending;
	}
	
	public void setIsDefending(boolean isDefending) {
		this.isDefending = isDefending;
	}
	
	public void resetBattleStatus () {
		this.isFlying = false;
		this.isDefending = false;
		this.isResting = false;
	}
	
	public Attack getNormalAttack() {
		return this.normalAttack;
	}
	
	public Attack getSpecialAttack1() {
		return this.specialAttack1;
	}
	
	public Attack getSpecialAttack2() {
		return this.specialAttack2;
	}
	
	public long getClicks() {
		return this.clicks;
	}
	
	
	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}

	public boolean isEgg() {
		return this.isEgg;
	}
	
	public void resetClicks() {
		this.clicks = 0;
	}
	
	public void hatch() {
		setEgg(false);
		
		this.level ++;
		
		this.attackManager = new AttackManager();
		
		this.normalAttack = attackManager.getAttack(this.monster.getRandomNormalAttack());
		this.specialAttack1 = attackManager.getAttack(this.monster.getRandomSpecialAttack());
		this.specialAttack2 = attackManager.getAttack(this.monster.getRandomSpecialAttack());
		
		atk = atkBase = MathUtils.random(monster.getMinAtk(), monster.getMaxAtk());
		def = defBase = MathUtils.random(monster.getMinDef(), monster.getMaxDef());
		spd = spdBase = MathUtils.random(monster.getMinSpd(), monster.getMaxSpd());
		hp = hpBase = MathUtils.random(monster.getMinHp(), monster.getMaxHp());
		sp = spBase = MathUtils.random(monster.getMinSp(), monster.getMaxSp());
		rp = rpBase = MathUtils.random(monster.getMinRp(), monster.getMaxRp());
		pctStatusUp = MathUtils.random(monster.getMinPctStatusUp(), monster.getMaxPctStatusUp());
		
		sumAttributes = atkBase + defBase + spdBase + hpBase + spBase + rpBase + pctStatusUp;
		sumMaxAttributes = monster.getMaxAtk() + monster.getMaxDef() + monster.getMaxSpd() +
				monster.getMaxHp() + monster.getMaxSp() + monster.getMaxRp() + monster.getMaxPctStatusUp();
		sumMinAttributes = monster.getMinAtk() + monster.getMinDef() + monster.getMinSpd() +
				monster.getMinHp() + monster.getMinSp() + monster.getMinRp() + monster.getMinPctStatusUp();
	}

	public int getSumMaxAttributes() {
		return sumMaxAttributes;
	}

	public int getSumAttributes() {
		return sumAttributes;
	}

	public int getSumMinAttributes() {
		return sumMinAttributes;
	}
	
	public void levelUp() {
		this.level ++;
		
		atk = statusUp(atk, atkBase);
		def = statusUp(def, defBase);
		spd = statusUp(spd, spdBase);
		hp = statusUp(hp, hpBase);
		sp = statusUp(sp, spBase);
		rp = statusUp(rp, spBase);
		clicksToUp = statusUp(clicksToUp, clicksToUpBase);
	}
	
	private int statusUp(int status, int statusBase) {
		return status + ((int) (((float )statusBase * ((float) pctStatusUp / 100f) + 1f))); 
	}
	
	public boolean isUsingResistence() {
		return isFlying;
	}

	public boolean isResting() {
		return isResting;
	}

	public void setResting(boolean isResting) {
		this.isResting = isResting;
	}

	public int getPctDrop() {
		return pctDrop;
	}

	public void setPctDrop(int pctDrop) {
		
		this.pctDrop = pctDrop;
	}
	
	public MyMonster dropEgg() {
		Egg egg = new Egg();
		egg.setEggPatchName("egg");
		egg.createAnimations();
		
		Monster monster = new MonsterManager().getMonster(this.monster.getMonsterPatchName());
		
		MyMonster myMonster = new MyMonster(egg, monster);
		
		return myMonster;
	}
}
