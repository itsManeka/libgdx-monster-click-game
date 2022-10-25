package com.monsterclickgame.battle;

public final class TypeManager {
	public enum Type {
		Normal,
		Fire,
		Water,
		Grass,
		Electric,
		Rock,
		Ice,
		Steel
	}

	static public float getModifierType(Type source, Type target) {
		float modifier = 1f;
		
		switch (source) {
		case Fire:
			switch (target) {
			case Fire: case Water: case Rock:
				modifier = .5f;
				break;
			case Normal: case Electric:
				modifier = 1f;
				break;
			case Grass: case Ice: case Steel:
				modifier = 2f;
				break;
			}
			break;
		
		case Normal:
			switch (target) {
			case Normal: case Fire: case Water: case Electric:
			case Grass: case Ice:
				modifier = 1f;
				break;
			case Rock: case Steel:
				modifier = .5f;
				break;
			}
			break;
			
		case Water:
			switch (target) {
			case Normal: case Electric: case Ice: case Steel:
				modifier = 1f;
				break;
			case Fire: case Rock:
				modifier = 2f;
				break;
			case Water: case Grass:
				modifier = .5f;
				break; 
			}
			break;
			
		case Grass:
			switch (target) {
			case Steel: case Fire: case Grass:
				modifier = .5f;
				break;
			case Normal: case Electric: case Ice: 
				modifier = 1f;
				break;
			case Rock: case Water:
				modifier = 2f;
				break;
			}
			break;
			
		case Electric:
			switch (target) {
			case Grass: case Electric:
				modifier = .5f;
				break;
			case Steel: case Fire: case Normal: case Ice: case Rock:
				modifier = 1f;
				break;
			case Water:
				modifier = 2f;
				break;
			}
			break;
			
		case Rock:
			switch (target) {
			case Steel:
				modifier = .5f;
				break;
			case Grass: case Electric: case Normal: case Rock: case Water:
				modifier = 1f;
				break;
			case Fire: case Ice:
				modifier = 2f;
				break;
			}
			break;
			
		case Ice:
			switch (target) {
			case Steel: case Water: case Fire: case Ice:
				modifier = .5f;
				break;
			case Electric: case Normal: case Rock:
				modifier = 1f;
				break;
			case Grass:
				modifier = 2f;
				break;
			}
			break;
			
		case Steel:
			switch (target) {
			case Steel: case Water: case Fire: case Electric:
				modifier = .5f;
				break;
			case Normal: case Grass:
				modifier = 1f;
				break;
			case Ice: case Rock:
				modifier = 2f;
				break;
			}
			break;
		}
		
		return modifier;
	}
}
