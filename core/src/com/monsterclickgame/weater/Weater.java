package com.monsterclickgame.weater;

import com.monsterclickgame.battle.TypeManager.Type;
import com.monsterclickgame.generic.Entity;

public class Weater extends Entity {
	private Type type;
	
	private String weaterPatchName;
	
	public Weater() {
		
	}
	
	@Override
	protected String getPatch() {
		return "Weater/" + weaterPatchName + "/" + weaterPatchName + ".pack";
	}

	public Type getType() {
		return type;
	}

	public String getWeaterPatchName() {
		return weaterPatchName;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setWeaterPatchName(String weaterPatchName) {
		this.weaterPatchName = weaterPatchName;
	}
}
