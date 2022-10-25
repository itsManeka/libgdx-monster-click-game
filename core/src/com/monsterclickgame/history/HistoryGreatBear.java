package com.monsterclickgame.history;

import com.monsterclickgame.data.UserData;
import com.monsterclickgame.eggs.Egg;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.monsters.Monster;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.screens.talking.CaveEntrance;
import com.monsterclickgame.screens.talking.GreatBearTalking;
import com.monsterclickgame.terrain.Terrain;

public class HistoryGreatBear extends History {
	public HistoryGreatBear(MonsterClick game, UserData userData) {
		super(game, userData);
		
		this.setHistoryBgName("greatbear");
		this.setHistoryName("O Grande Urso");
		
		Egg egg = eggmanager.getEgg("egg");
		Monster monster = monstermanager.getMonster("meowth");
		Terrain terrain =  terrainManager.getTerrain("route");
		
		MyMonster myMonster = new MyMonster(egg, monster);
		myMonster.hatch();
		myMonster.setPctDrop(8);
		
		HistoryBattle battleOne = new HistoryBattle();
		battleOne.setBattleName("O caminho da floresta");
		battleOne.setEnemy(myMonster);
		battleOne.setTerrain(terrain);
		battleOne.setHistory(this);
		
		//-- 
		
		Egg egg2 = eggmanager.getEgg("egg");
		Monster monster2 = monstermanager.getMonster("poochyena");
		Terrain terrain2 =  terrainManager.getTerrain("route");
		
		MyMonster myMonster2 = new MyMonster(egg2, monster2);
		myMonster2.hatch();
		myMonster2.setPctDrop(5);
		
		HistoryBattle battleTwo = new HistoryBattle();
		battleTwo.setBattleName("Um lobo perdido");
		battleTwo.setEnemy(myMonster2);
		battleTwo.setTerrain(terrain2);
		battleTwo.setHistory(this);
		
		//--
		
		Egg egg3 = eggmanager.getEgg("egg");
		Monster monster3 = monstermanager.getMonster("weedle");
		Terrain terrain3 =  terrainManager.getTerrain("forest");
		
		MyMonster myMonster3 = new MyMonster(egg3, monster3);
		myMonster3.hatch();
		myMonster3.setPctDrop(10);
		
		HistoryBattle battleThree = new HistoryBattle();
		battleThree.setBattleName("Saudades repelente");
		battleThree.setEnemy(myMonster3);
		battleThree.setTerrain(terrain3);
		battleThree.setHistory(this);
		
		//--
		
		Egg egg4 = eggmanager.getEgg("egg");
		Monster monster4 = monstermanager.getMonster("ursaring");
		Terrain terrain4 =  terrainManager.getTerrain("cave");
		
		MyMonster myMonster4 = new MyMonster(egg4, monster4);
		myMonster4.hatch();
		myMonster4.setPctDrop(1);
		
		HistoryBattle battleFour = new HistoryBattle();
		battleFour.setBattleName("O grande urso");
		battleFour.setEnemy(myMonster4);
		battleFour.setTerrain(terrain4);
		battleFour.setHistory(this);
		
		//--
		
		addTalking(new GreatBearTalking(game, userData, this));
		addBattle(battleOne);
		addBattle(battleThree);
		addBattle(battleTwo);
		addTalking(new CaveEntrance(game, userData, this));
		addBattle(battleFour);
	}
}
