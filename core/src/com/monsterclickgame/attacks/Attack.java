package com.monsterclickgame.attacks;

import com.monsterclickgame.battle.AttackTypeManager.AttackType;
import com.monsterclickgame.battle.TypeManager.Type;
import com.monsterclickgame.weater.Weater;

public class Attack {
	private Type type;
	private AttackType attackType;
	
	private int stamina;
	private float power;
	
	private String name;
	private String patchName;
	
	private Weater weater;
	
	public Attack() {
		
	}
	
	public Type getType() {
		return this.type;
	}
	
	public AttackType getAttackType() {
		return this.attackType;
	}
	
	public float getPower() {
		return this.power;
	}

	public AttackType getContact() {
		return attackType;
	}

	public void setAttackType(AttackType attackType) {
		this.attackType = attackType;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatchName() {
		return patchName;
	}

	public void setPatchName(String patchName) {
		this.patchName = patchName;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setPower(float power) {
		this.power = power;
	}

	public Weater getWeater() {
		return weater;
	}

	public void setWeater(Weater weater) {
		this.weater = weater;
	}
	
	public boolean hasPower() {
		return ((attackType != AttackType.Fly) && (attackType != AttackType.Weater));
	}
}
