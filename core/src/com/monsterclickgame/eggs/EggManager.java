package com.monsterclickgame.eggs;

import com.badlogic.gdx.utils.ArrayMap;

public class EggManager {
	private ArrayMap <String, Egg> eggs;
	
	public EggManager() {
		eggs = new ArrayMap<String, Egg>();
		
		populate();
	}
	
	private void populate() {
		add("egg", "egg", new String[]{"timburr", "hoothoot", "meowth", "poochyena"});
	}
	
	private void add(String key, String patchName, String possibleMonsters[]) {
		Egg egg = new Egg();
		egg.setEggPatchName(patchName);
		egg.addPossibleMonsters(possibleMonsters);
		
		eggs.put(key, egg);
	}
	
	public Egg getEgg(String key) {
		Egg egg = eggs.get(key);
		
		if (!egg.isAnimationCreated()) {
			egg.setAnimationCreated(true);
			egg.createAnimations();
		}
		
		return eggs.get(key);
	}
}
