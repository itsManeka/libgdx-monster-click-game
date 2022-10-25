package com.monsterclickgame.terrain;

import com.badlogic.gdx.utils.ArrayMap;
import com.monsterclickgame.battle.TypeManager.Type;

public class TerrainManager {
	private ArrayMap<String, Terrain> terrains;
	
	public TerrainManager() {
		terrains = new ArrayMap<String, Terrain>();
		
		populate();
	}
	
	private void populate() {
		add("beach", Type.Water);
		add("beachunderwatter", Type.Water);
		add("cave", Type.Rock);
		add("city", Type.Steel);
		add("dampcave", Type.Rock);
		add("deepsea", Type.Water);
		add("desert", Type.Rock);
		add("earthycave", Type.Rock);
		add("forest", Type.Grass);
		add("icecave", Type.Ice);
		add("meadow", Type.Grass);
		add("mountain", Type.Rock);
		add("river", Type.Water);
		add("route", Type.Grass);
		add("thunderplains", Type.Electric);
		add("underwater", Type.Water);
		add("volcano", Type.Fire);
	}
	
	private void add(String patchName, Type type) {
		Terrain terrain = new Terrain();
		terrain.setPatchName(patchName);
		terrain.setType(type);
		
		this.terrains.put(patchName, terrain);
	}
	
	public Terrain getTerrain(String key) {
		Terrain t = this.terrains.get(key);
		
		if (!t.isTerrainCreated()) {
			t.setTerrainCreated(true);
			t.createTerrain();
		}
		
		return t;
	}
}
