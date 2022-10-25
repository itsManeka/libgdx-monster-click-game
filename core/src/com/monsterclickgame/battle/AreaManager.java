package com.monsterclickgame.battle;

import com.monsterclickgame.battle.AttackTypeManager.AttackType;
import com.monsterclickgame.monsters.MyMonster;

public final class AreaManager {	
	public enum Area {
		Flying,
		Ground
	}
	
	static public float getModifierArea(MyMonster source, MyMonster target, AttackType sourceAttakType) {
		float modifier = 1f;
		
		if ((source.getArea() == Area.Ground) || !source.isFlying()) {
			if (target.getArea() == Area.Flying && target.isFlying()) {
				if (sourceAttakType == AttackType.Melee) {
					modifier = 0f;
				} else {
					modifier = .75f;
				}
			}
		}
		
		return modifier;
	}
}
