package com.monsterclickgame.interfaces;

import java.util.EventListener;

public interface PressListener extends EventListener {
	public void onPressed();
	public void onRelease();
}
