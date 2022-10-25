package com.monsterclickgame.weater;

import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.battle.TypeManager.Type;

public class WeaterManager {
	private Array<Weater> weaters;
	
	public WeaterManager() {
		weaters = new Array<Weater>();
		
		populate();
	}
	
	private void populate() {
		add("rain", Type.Water);
		add("snow", Type.Ice);
		add("thunderrain", Type.Electric);
	}
	
	private void add(String patchName, Type type) {
		Weater weater = new Weater();
		weater.setType(type);
		weater.setWeaterPatchName(patchName);
		
		weaters.add(weater);
	}
	
	public Weater getWeater(String key) {
		for (Weater w : weaters) {
			if (w.getWeaterPatchName() == key) {
				if (!w.isAnimationCreated()) {
					w.setAnimationCreated(true);
					w.createAnimations();
				}
				return w;
			}
		}
		return null;
	}
}
