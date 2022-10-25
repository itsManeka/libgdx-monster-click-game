package com.monsterclickgame.customwidgets;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.monsterclickgame.history.History;

public class HistoryButton extends TextButton {
	private History history;
	
	public HistoryButton(String text, TextButtonStyle style, History history) {
		super(text, style);
		
		this.history = history;
	}

	public History getHistory() {
		return this.history;
	}
}
