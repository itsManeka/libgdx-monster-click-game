package com.monsterclickgame.customwidgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.monsterclickgame.monsters.MyMonster;

public class MonsterButton extends Button {
	private MyMonster monster;
	
	public MonsterButton(ButtonStyle style, MyMonster monster) {
		super(style);
		
		this.monster = monster;
	}
	
	public MyMonster getMonster() {
		return this.monster;
	}
}
