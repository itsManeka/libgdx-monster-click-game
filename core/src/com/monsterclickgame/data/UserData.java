package com.monsterclickgame.data;

import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.monsters.MyMonster;

public class UserData {
	private long clicks = 10;
	private long maxClicks = 100;
	private long goldCoins = 100;
	private long silverCoins = 100;
	private long millisTime = 60000;
	
	private String userName;
	
	private Array<MyMonster> myMonsters;
	
	public UserData() {
		this.userName = "Maneka";
		myMonsters = new Array<MyMonster>();
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void addMonster(MyMonster myMonster) {
		myMonsters.add(myMonster);
	}
	
	public Array<MyMonster> getMyMonsters() {
		return myMonsters;
	}
	
	public int getNumberMonsters() {
		int number = 0;
		
		for (MyMonster m : myMonsters) {
			if (!m.isEgg()) {
				number ++;
			}
		}
		
		return number;
	}
	
	public boolean canClick() {
		return (this.clicks > 0);
	}
	
	public boolean click() {
		this.clicks -= 1;
		
		return canClick();
	}
	
	public long getClicks() {
		return this.clicks;
	}
	
	public long getMaxClicks() {
		return this.maxClicks;
	}

	public long getGoldCoins() {
		return goldCoins;
	}

	public void setGoldCoins(long goldCoins) {
		this.goldCoins = goldCoins;
	}

	public long getSilverCoins() {
		return silverCoins;
	}

	public void setSilverCoins(long silverCoins) {
		this.silverCoins = silverCoins;
	}
	
	public long getMillisTime() {
		return this.millisTime;
	}
	
	public void decrementTime1Second() {
		this.millisTime -= 1000;
		if (this.millisTime == 0) {
			clicks ++;
			this.millisTime = 60000;
		}
	}
}