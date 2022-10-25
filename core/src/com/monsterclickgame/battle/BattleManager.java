package com.monsterclickgame.battle;

import com.badlogic.gdx.math.MathUtils;
import com.monsterclickgame.attacks.Attack;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.terrain.Terrain;
import com.monsterclickgame.weater.Weater;

public class BattleManager {
	private Weater weater;
	private Terrain terrain;
	private MyMonster enemy;
	private MyMonster player;
	private MyMonster source;
	private MyMonster target;
	
	private Attack attack;
	
	public BattleManager() {
		
	}
	
	public BattleManager (MyMonster player, MyMonster enemy) {
		this();
		
		this.enemy = enemy;
		this.player = player;
	}
	
	public void setMonsterPlayer(MyMonster monster) {
		this.player = monster;
	}
	
	public void setMonsterEnemy(MyMonster monster) {
		this.enemy = monster;
	}
	
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public void setWeater(Weater weater) {
		this.weater = weater;
	}
	
	public void attack(boolean isPlayer, Attack attack) {
		this.attack = attack;
		
		if (isPlayer) {
			this.source = player;
			this.target = enemy;
		} else {
			this.source = enemy;
			this.target = player;
		}
	}
	
	public float getDamange() {
		float damange = 0f;
		
		damange = (((float) source.getLevel() / 5f) + 2f);
		damange = (damange * attack.getPower() * (source.getAtk() / target.getDef()));
		damange = ((damange / 50f) + 2f);
		damange = (damange * getModifier());
		
		return damange;
	}
	
	private float getModifier() {
		float modifier = 1f;
		
		float terrain = getTerrainModifier();
		float weater = getWeaterModifier();
		float critical = getCriticalModifier();
		float random = getRandomModifier();
		float stab = getSTABModifier();
		float type = TypeManager.getModifierType(source.getType(), target.getType());
		float area = AreaManager.getModifierArea(source, target, attack.getAttackType());
		float defending = getDefenfingModifier();
		float resting = getRestingModifier();
		
		modifier = (terrain * weater * critical * random * stab * type * area * defending * resting);
		
		return modifier;
	}
	
	private float getRandomModifier() {
		float random = 1f;
		
		random = MathUtils.random(.85f, 1f);
		
		return random;
	}
	
	private float getDefenfingModifier() {
		float defending = 1f;
		
		if (target.getIsDefending()) {
			defending = .25f;
		}
		
		return defending;
	}
	
	private float getRestingModifier() {
		float resting = 1f;
		
		if (target.isResting()) {
			resting = 1.75f;
		}
		
		return resting;
	}
	
	private float getCriticalModifier() {
		float critical = 1f;
		int probability = 255;
		int randomNumber = 0;
		
		probability = (int) (((float) source.getSpd() / 2f) / 256f);
		randomNumber = MathUtils.random(0, 255);
		
		if (randomNumber < probability) {
			critical = (((2f * (float) source.getLevel()) + 5f) / ( (float) source.getLevel() + 5f));
		}
		
		return critical;
	}
	
	private float getSTABModifier() {
		float stab = 1f;
		
		if (source.getType() == attack.getType()) {
			stab = 1.5f;
		}
		
		return stab;
	}
	
	private float getTerrainModifier() {
		float terrain = 1f;
		
		if (source.getType() == this.terrain.getType()) {
			terrain = 1.25f;
		}
		
		return terrain;
	}
	
	private float getWeaterModifier() {
		float weater = 1f;
		
		if (this.weater != null) {
			if (source.getType() == this.weater.getType()) {
				weater += .25f;
			}
			
			if (attack.getType() == this.weater.getType()) {
				weater += .25f;
			}
		}
		
		return weater;
	}
	
	public Attack getAttack() {
		return this.attack;
	}
}