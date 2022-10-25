package com.monsterclickgame.customwidgets;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.screens.talking.TalkingBase;

public class BattleButton extends TextButton {
	private HistoryBattle battle;
	private TalkingBase talking;
	
	public BattleButton(String text, TextButtonStyle style, HistoryBattle battle) {
		super(text, style);
		
		this.battle = battle;
	}
	
	public BattleButton(String text, TextButtonStyle style, TalkingBase talking) {
		super(text, style);
		
		this.talking = talking;
	}
	
	public TalkingBase getTalking() {
		return this.talking;
	}
	
	public HistoryBattle getBattle() {
		return this.battle;
	}
}
