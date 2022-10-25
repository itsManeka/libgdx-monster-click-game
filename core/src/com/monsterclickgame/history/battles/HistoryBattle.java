package com.monsterclickgame.history.battles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.history.History;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.terrain.Terrain;

public class HistoryBattle {
	private Array<String> possibleWeaters;
	private Array<Integer> probabilityWeater;
	
	private Terrain terrain;
	private MyMonster enemy;
	private History history;
	
	private String battleName;
	
	public HistoryBattle() {
		possibleWeaters = new Array<String>();
		probabilityWeater = new Array<Integer>();
	}
	
	public String getBattleName() {
		return battleName;
	}

	public void setBattleName(String battleName) {
		this.battleName = battleName;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	public MyMonster getEnemy() {
		return enemy;
	}

	public void setEnemy(MyMonster enemy) {
		this.enemy = enemy;
	}
	
	public History getHistory() {
		return this.history;
	}
	
	public void setHistory(History history) {
		this.history = history;
	}
	
	public void addPossibleWeaters(String keys[], Integer prob[]) {
		for (int i = 0; i < keys.length; i ++) {
			possibleWeaters.add(keys[i]);
			probabilityWeater.add(prob[i]);
		}
	}
	
	public String getWeater() {
		int random;
		int prob;
		
		String weater = "";
		
		for (int i = 0; i < possibleWeaters.size; i++) {
			prob = probabilityWeater.get(i);
			random = MathUtils.random(0, 100);
			
			if (random <= prob) {
				weater = possibleWeaters.get(i);
				break;
			}
		}
				
		return weater;
	}
}
