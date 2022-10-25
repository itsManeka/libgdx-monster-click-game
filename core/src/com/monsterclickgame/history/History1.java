package com.monsterclickgame.history;

import com.monsterclickgame.data.UserData;
import com.monsterclickgame.eggs.Egg;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.monsters.Monster;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.terrain.Terrain;

public class History1 extends History {
	public History1(MonsterClick game, UserData userData) {
		super(game, userData);
		
		this.setHistoryBgName("history1");
		this.setHistoryName("História teste");
		
		Egg egg = eggmanager.getEgg("egg");
		Monster monster = monstermanager.getMonster("gallade");
		Terrain terrain =  terrainManager.getTerrain("route");
		
		MyMonster myMonster = new MyMonster(egg, monster);
		myMonster.hatch();
		
		HistoryBattle battleOne = new HistoryBattle();
		battleOne.setBattleName("Teste");
		battleOne.setEnemy(myMonster);
		battleOne.setTerrain(terrain);
		battleOne.setHistory(this);
		
		Egg egg2 = eggmanager.getEgg("egg");
		Monster monster2 = monstermanager.getMonster("dewott");
		Terrain terrain2 =  terrainManager.getTerrain("beach");
		
		MyMonster myMonster2 = new MyMonster(egg2, monster2);
		myMonster2.hatch();
		
		HistoryBattle battleTwo = new HistoryBattle();
		battleTwo.setBattleName("Teste 2");
		battleTwo.setEnemy(myMonster2);
		battleTwo.setTerrain(terrain2);
		battleTwo.setHistory(this);
		battleTwo.addPossibleWeaters(new String[]{"rain"}, new Integer[]{50});
		
		Egg egg3 = eggmanager.getEgg("egg");
		Monster monster3 = monstermanager.getMonster("flygon");
		Terrain terrain3 =  terrainManager.getTerrain("forest");
		
		MyMonster myMonster3 = new MyMonster(egg3, monster3);
		myMonster3.hatch();
		
		HistoryBattle battleThree = new HistoryBattle();
		battleThree.setBattleName("Teste 3");
		battleThree.setEnemy(myMonster3);
		battleThree.setTerrain(terrain3);
		battleThree.setHistory(this);
		battleThree.addPossibleWeaters(new String[]{"rain"}, new Integer[]{50});
		
		Egg egg4 = eggmanager.getEgg("egg");
		Monster monster4 = monstermanager.getMonster("electivire");
		Terrain terrain4 =  terrainManager.getTerrain("thunderplains");
		
		MyMonster myMonster4 = new MyMonster(egg4, monster4);
		myMonster4.hatch();
		
		HistoryBattle battleFour = new HistoryBattle();
		battleFour.setBattleName("Teste 4");
		battleFour.setEnemy(myMonster4);
		battleFour.setTerrain(terrain4);
		battleFour.setHistory(this);
		battleFour.addPossibleWeaters(new String[]{"rain", "thunderrain"}, new Integer[]{50, 50});
		
		Egg egg5 = eggmanager.getEgg("egg");
		Monster monster5 = monstermanager.getMonster("bisharp");
		Terrain terrain5 =  terrainManager.getTerrain("city");
		
		MyMonster myMonster5 = new MyMonster(egg5, monster5);
		myMonster5.hatch();
		
		HistoryBattle battleFive = new HistoryBattle();
		battleFive.setBattleName("Teste 5");
		battleFive.setEnemy(myMonster5);
		battleFive.setTerrain(terrain5);
		battleFive.setHistory(this);
		battleFive.addPossibleWeaters(new String[]{"rain"}, new Integer[]{20});
		
		addBattle(battleOne);
		addBattle(battleTwo);
		addBattle(battleThree);
		addBattle(battleFour);
		addBattle(battleFive);
	}
}