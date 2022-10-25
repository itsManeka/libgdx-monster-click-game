package com.monsterclickgame.monsters;

import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.battle.AreaManager.Area;
import com.monsterclickgame.battle.TypeManager.Type;
import com.monsterclickgame.generic.Entity;

public class Monster extends Entity{
	private Array<String> possibleNormalAttack;
	private Array<String> possibleSpecialAttack;
	
	private Type type;
	private Area area;
	
	private int minAtk;
	private int maxAtk;
	private int minDef;
	private int maxDef;
	private int minSpd;
	private int maxSpd;
	private int minHp;
	private int maxHp;
	private int minSp;
	private int maxSp;
	private int minRp;
	private int maxRp;
	
	private int minClicksToUp;
	private int maxClicksToUp;
	
	private int minPctStatusUp;
	private int maxPctStatusUp;
	
	private String monsterName;
	private String monsterPatchName;
	
	public Monster() {
		possibleNormalAttack = new Array<String>();
		possibleSpecialAttack = new Array<String>();
	}
	
	@Override
	protected String getPatch() {
		return "Monsters/" + monsterPatchName + "/" + monsterPatchName + ".pack";
	}
	
	public void addPossibleNormalAttack(String keys[]) {
		for (String s : keys) {			
			possibleNormalAttack.add(s);
		}
	}
	
	public void addPossibleSpecialAttack(String keys[]) {
		for (String s : keys) {			
			possibleSpecialAttack.add(s);
		}
	}
	
	public String getRandomNormalAttack () {
		return this.possibleNormalAttack.random();
	}
	
	public String getRandomSpecialAttack () {
		return this.possibleSpecialAttack.random();
	}
	
	public String getMonsterName () {
		return this.monsterName;
	}
	
	public Area getArea() {
		return this.area;
	}
	
	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public void setMinAtk(int minAtk) {
		this.minAtk = minAtk;
	}

	public void setMaxAtk(int maxAtk) {
		this.maxAtk = maxAtk;
	}

	public void setMinDef(int minDef) {
		this.minDef = minDef;
	}

	public void setMaxDef(int maxDef) {
		this.maxDef = maxDef;
	}

	public void setMinSpd(int minSpd) {
		this.minSpd = minSpd;
	}

	public void setMaxSpd(int maxSpd) {
		this.maxSpd = maxSpd;
	}

	public int getMinHp() {
		return minHp;
	}

	public void setMinHp(int minHp) {
		this.minHp = minHp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getMinSp() {
		return minSp;
	}

	public void setMinSp(int minSp) {
		this.minSp = minSp;
	}

	public int getMaxSp() {
		return maxSp;
	}

	public void setMaxSp(int maxSp) {
		this.maxSp = maxSp;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

	public void setMonsterPatchName(String monsterPatchName) {
		this.monsterPatchName = monsterPatchName;
	}
	
	public String getMonsterPatchName() {
		return this.monsterPatchName;
	}

	public int getMinAtk() {
		return minAtk;
	}

	public int getMaxAtk() {
		return maxAtk;
	}

	public int getMinDef() {
		return minDef;
	}

	public int getMaxDef() {
		return maxDef;
	}

	public int getMinSpd() {
		return minSpd;
	}

	public int getMaxSpd() {
		return maxSpd;
	}

	public int getMinPctStatusUp() {
		return minPctStatusUp;
	}

	public void setMinPctStatusUp(int minPctStatusUp) {
		this.minPctStatusUp = minPctStatusUp;
	}

	public int getMaxPctStatusUp() {
		return maxPctStatusUp;
	}

	public void setMaxPctStatusUp(int maxPctStatusUp) {
		this.maxPctStatusUp = maxPctStatusUp;
	}

	public int getMinClicksToUp() {
		return minClicksToUp;
	}

	public void setMinClicksToUp(int minClicksToUp) {
		this.minClicksToUp = minClicksToUp;
	}

	public int getMaxClicksToUp() {
		return maxClicksToUp;
	}

	public void setMaxClicksToUp(int maxClicksToUp) {
		this.maxClicksToUp = maxClicksToUp;
	}

	public int getMinRp() {
		return minRp;
	}

	public void setMinRp(int minRp) {
		this.minRp = minRp;
	}

	public int getMaxRp() {
		return maxRp;
	}

	public void setMaxRp(int maxRp) {
		this.maxRp = maxRp;
	}
}