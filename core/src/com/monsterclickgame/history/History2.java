package com.monsterclickgame.history;

import com.monsterclickgame.data.UserData;
import com.monsterclickgame.eggs.Egg;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.monsters.Monster;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.terrain.Terrain;

public class History2 extends History {
	public History2(MonsterClick game, UserData userData) {
		super(game, userData);
		
		this.setHistoryBgName("history1");
		this.setHistoryName("História teste 2");
		
		Egg egg = eggmanager.getEgg("egg");
		Monster monster = monstermanager.getMonster("timburr");
		Terrain terrain =  terrainManager.getTerrain("mountain");
		
		MyMonster myMonster = new MyMonster(egg, monster);
		myMonster.hatch();
		
		HistoryBattle battleOne = new HistoryBattle();
		battleOne.setBattleName("Teste 1");
		battleOne.setEnemy(myMonster);
		battleOne.setTerrain(terrain);
		battleOne.setHistory(this);
		
		Egg egg2 = eggmanager.getEgg("egg");
		Monster monster2 = monstermanager.getMonster("abomasnow");
		Terrain terrain2 =  terrainManager.getTerrain("icecave");
		
		MyMonster myMonster2 = new MyMonster(egg2, monster2);
		myMonster2.hatch();
		
		HistoryBattle battleTwo = new HistoryBattle();
		battleTwo.setBattleName("Teste 2");
		battleTwo.setEnemy(myMonster2);
		battleTwo.setTerrain(terrain2);
		battleTwo.setHistory(this);
		battleTwo.addPossibleWeaters(new String[]{"snow"}, new Integer[]{20});
		
		Egg egg3 = eggmanager.getEgg("egg");
		Monster monster3 = monstermanager.getMonster("marowak");
		Terrain terrain3 =  terrainManager.getTerrain("earthycave");
		
		MyMonster myMonster3 = new MyMonster(egg3, monster3);
		myMonster3.hatch();
		
		HistoryBattle battleThree = new HistoryBattle();
		battleThree.setBattleName("Teste 3");
		battleThree.setEnemy(myMonster3);
		battleThree.setTerrain(terrain3);
		battleThree.setHistory(this);
		
		addBattle(battleOne);
		addBattle(battleTwo);
		addBattle(battleThree);
	}
}
