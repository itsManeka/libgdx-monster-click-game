package com.monsterclickgame.eggs;

import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.generic.Entity;

public class Egg extends Entity{
	private Array<String> possibleMonsters;
	
	private String eggPatchName;
	
	public Egg() {
		possibleMonsters = new Array<String>();
	}
	
	@Override
	protected String getPatch() {
		return "Egg/" + eggPatchName + "/" + eggPatchName + ".pack";
	}
	
	public void setEggPatchName(String eggPatcgName) {
		this.eggPatchName = eggPatcgName;
	}
	
	public void addPossibleMonsters(String keys[]) {
		for (String s : keys) {			
			possibleMonsters.add(s);
		}
	}
	
	public String getRandomMonster() {
		return possibleMonsters.random();
	}
}