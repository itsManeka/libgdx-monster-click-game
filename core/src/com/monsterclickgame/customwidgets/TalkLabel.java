package com.monsterclickgame.customwidgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.monsterclickgame.interfaces.TalkLabelListener;

public class TalkLabel extends Label {
	boolean isFinished;
	
	private int caracter;
	private float startTime;
	private float elapsedTime;
	
	private String completeText = "";
	private String currentText = "";
	
	private TalkLabelListener listener;
	
	public TalkLabel(CharSequence text, LabelStyle style) {
		super(text, style);
		
		isFinished = false;
		
		startTime = 0;
		
		caracter = 0;
		completeText = (String) text;
		currentText = completeText.substring(0, caracter);
		this.setText(currentText);
	}
	
	public void newText(CharSequence text) {
		isFinished = false;
		
		startTime = 0;
		elapsedTime = 0;
		
		caracter = 0;
		completeText = (String) text;
		currentText = completeText.substring(0, caracter);
		this.setText(currentText);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		elapsedTime += delta;
		
		if (((elapsedTime - startTime) >= .05f)) {
			caracter ++;
			if (completeText.length() >= caracter) {
				currentText = completeText.substring(0, caracter);
				this.setText(currentText);
				startTime = elapsedTime;
			} else {
				if (this.listener != null && !isFinished) {
					listener.onTextFinished();
					isFinished = true;
				}
			}
		}
	}
	
	public void addEventListener(TalkLabelListener listener) {
		this.listener = listener;
	}
}
