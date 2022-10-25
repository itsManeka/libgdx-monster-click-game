package com.monsterclickgame.history;

import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;

public class HistoryManager {
	private MonsterClick game;
	private UserData userData;
	
	private int index;
	
	private Array<History> historys;
	
	public HistoryManager(MonsterClick game, UserData userData) {
		this.game = game;
		this.userData = userData;
		
		this.index = 0;
		
		historys = new Array<History>();
		
		populate();
	}
	
	private void populate() {
		historys.add(new HistoryGreatBear(game, userData));
		/*historys.add(new History1(game, userData));
		historys.add(new History2(game, userData));*/
	}
	
	public int size() {
		return this.historys.size;
	}
	
	public History getNext() {
		if (historys.size > index) {
			History history;
			
			history = historys.get(index);
			index ++;
			
			return history;
		} else {
			return null;
		}
	}
	
	public int getIndex() {
		return this.index;
	}
}