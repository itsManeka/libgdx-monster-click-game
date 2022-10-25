package com.monsterclickgame.generic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

public class Entity {
	private boolean animationCreated = false;
	
	protected Animation animation;
	protected ArrayMap <String, Animation> animations;
	protected TextureAtlas atlas;
	
	public Entity() {
		
	}
	
	protected String getPatch() {
		return "";
	}
	
	public void createAnimations(String entityPatch) {
		String patch = entityPatch;
		
		this.atlas = new TextureAtlas(Gdx.files.internal(patch));
		
		this.animations = new ArrayMap<String, Animation>();
		for (TextureAtlas.AtlasRegion reg : atlas.getRegions()) {
			TextureRegion[] regions = this.atlas.findRegions(reg.name).toArray(TextureRegion.class);
			
			this.animation = new Animation(1/12f, regions);
			this.animations.put(reg.name, animation);
		}
	}
	
	public void createAnimations() {
		String patch = getPatch();
		
		createAnimations(patch);
	}
	
	public Animation getAnimation(String animation) {
		return this.animations.get(animation);
	}

	public boolean isAnimationCreated() {
		return animationCreated;
	}

	public void setAnimationCreated(boolean animationCreated) {
		this.animationCreated = animationCreated;
	}
}
