package com.monsterclickgame.customwidgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.monsterclickgame.interfaces.PressListener;

public class PressButton extends Button {
	private float startTime;
	private float elapsedTime;
	private boolean initPress;
	private boolean hasPressed;
	
	private PressListener pressListener;
	
	public PressButton(ButtonStyle buttonStyle) {
		super(buttonStyle);
	}
	
	public void addPressListener(PressListener pressListener) {
		this.pressListener = pressListener;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		elapsedTime += delta;
		
		if (isPressed()) {
			if (!initPress) {
				startTime = elapsedTime;
				initPress = true;
			}
		} else {
			initPress = false;
		}
		
		if (initPress) {
			if (((elapsedTime - startTime) >= 1f)) {
				hasPressed = true;
				if (pressListener != null) pressListener.onPressed();
				
				startTime = elapsedTime;
			}
		}
		
		if (!isPressed() && hasPressed) {
			if (pressListener != null) pressListener.onRelease();
			hasPressed = false;
		}
	}
	
	public boolean hasPressed() {
		return hasPressed;
	}
}
