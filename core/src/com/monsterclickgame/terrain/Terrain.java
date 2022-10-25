package com.monsterclickgame.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.monsterclickgame.battle.TypeManager.Type;
import com.monsterclickgame.utils.CustomTimeUtils;

public class Terrain {
	boolean terrainCreated = false;
	
	private String patchName;
	
	private Type type;
	
	private Image terrain;
	
	public Terrain() {
		
	}
	
	public void createTerrain() {
		String imgName;
		String terrainPatch;
		
		imgName = patchName;
		if (CustomTimeUtils.isNight()) {
			imgName += "_night";
		}
		
		terrainPatch = "Terrain/" + patchName + "/" + imgName + ".png";
				
		if (!Gdx.files.internal(terrainPatch).exists()) {
			terrainPatch = "Terrain/" + patchName + "/" + patchName + ".png";
		}
		
		Texture texture = new Texture(terrainPatch);
		terrain = new Image(texture);
	}

	public String getPatchName() {
		return patchName;
	}

	public void setPatchName(String patchName) {
		this.patchName = patchName;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Image getTerrain() {
		return terrain;
	}
	
	public Drawable getTerrainDrawable() {
		return this.terrain.getDrawable();
	}

	public boolean isTerrainCreated() {
		return terrainCreated;
	}

	public void setTerrainCreated(boolean terrainCreated) {
		this.terrainCreated = terrainCreated;
	}
}
