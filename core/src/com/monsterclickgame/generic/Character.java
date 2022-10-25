package com.monsterclickgame.generic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

public class Character {
	protected String characterName;
	protected String characterPatchName;
	
	private Animation animation;
	private ArrayMap <String, Animation> animations;
	private TextureAtlas atlas;
	
	public Character() {
		
	}
	
	protected void createAnimations() {
		String patch = "Character/" + characterPatchName + "/" + characterPatchName + ".pack";
		
		this.atlas = new TextureAtlas(Gdx.files.internal(patch));
		
		this.animations = new ArrayMap<String, Animation>();
		for (TextureAtlas.AtlasRegion reg : atlas.getRegions()) {
			TextureRegion[] regions = this.atlas.findRegions(reg.name).toArray(TextureRegion.class);
			
			this.animation = new Animation(1/20f, regions);
			this.animations.put(reg.name, animation);
		}
	}
	
	public Animation getAnimation(String animation) {
		return this.animations.get(animation);
	}
	
	public String getCharacterName() {
		return this.characterName;
	}
}
