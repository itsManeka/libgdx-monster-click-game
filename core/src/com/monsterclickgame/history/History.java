package com.monsterclickgame.history;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.eggs.EggManager;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.monsters.MonsterManager;
import com.monsterclickgame.screens.talking.TalkingBase;
import com.monsterclickgame.terrain.TerrainManager;

public class History {
	public enum Type {
		Battle,
		Talking
	}
	
	private MonsterClick game;
	private UserData userData;
	
	protected EggManager eggmanager;
	protected MonsterManager monstermanager;
	protected TerrainManager terrainManager;
	
	private String historyName;
	private String historyBgName;
	
	private ArrayMap<Integer, HistoryBattle> historyBattles;
	private ArrayMap<Integer, TalkingBase> talkings;
	private Array<Integer> keys;
	private Array<Type> types;
	
	public History(MonsterClick game, UserData userData) {
		this.game = game;
		this.userData = userData;
		
		this.eggmanager = new EggManager();
		this.monstermanager = new MonsterManager();
		this.terrainManager = new TerrainManager();
		 
		this.keys = new Array<Integer>();
		this.types = new Array<Type>();
		this.historyBattles = new ArrayMap<Integer,HistoryBattle>();
		this.talkings = new ArrayMap<Integer, TalkingBase>();
	}
	
	protected void addBattle(HistoryBattle battle) {
		Integer key = keys.size - 1;
		keys.add(key);
		types.add(Type.Battle);
		this.historyBattles.put(key, battle);
	}
	
	protected void addTalking(TalkingBase talking) {
		Integer key = keys.size - 1;
		keys.add(key);
		types.add(Type.Talking);
		this.talkings.put(key, talking);
	}
	
	public Type getType(int index) {
		return types.get(index);
	}
	
	public int getBattlesSize() {
		return this.keys.size;
	}
	
	public HistoryBattle getBattle(int index) {
		Integer key = keys.get(index);
		
		return this.historyBattles.get(key);
	}
	
	public TalkingBase getTalking(int index) {
		Integer key = keys.get(index);
		
		return this.talkings.get(key);
	}

	public String getHistoryName() {
		return this.historyName;
	}

	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}

	public String getHistoryBgName() {
		return this.historyBgName;
	}

	public void setHistoryBgName(String historyBgName) {
		this.historyBgName = historyBgName;
	}
}