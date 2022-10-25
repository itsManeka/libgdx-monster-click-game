package com.monsterclickgame.monsters;

import com.badlogic.gdx.utils.ArrayMap;
import com.monsterclickgame.battle.AreaManager.Area;
import com.monsterclickgame.battle.TypeManager.Type;

public class MonsterManager {
	private ArrayMap<String, Monster> monsters;

	public MonsterManager() {
		monsters = new ArrayMap<String, Monster>();
		
		populate();
	}
	
	private void populate() {
		add("Dewott", "dewott", Area.Ground, Type.Water, 10, 18, 10, 12, 25, 30, 31, 38, 31, 33, 3, 8, 5, 10, 3, 5, new String[]{"punch", "tackle"}, new String[]{"superPunch", "retaliate", "waterGun", "razorShell", "aquaJet", "hydroPump"});
		add("Gallade", "gallade", Area.Ground, Type.Grass, 15, 20, 13, 19, 30, 38, 21, 39, 16, 18, 10, 20, 5, 10, 4, 6, new String[]{"punch"}, new String[] {"superPunch", "retaliate"});
		add("Flygon", "flygon", Area.Flying, Type.Grass, 10, 17, 10, 15, 35, 40, 12, 18, 15, 20, 10, 18, 5, 10, 4, 6, new String[]{"tackle"}, new String[]{"fly", "fly"});
		add("Electivire", "electivire", Area.Ground, Type.Electric, 40, 43, 20, 27, 50, 52, 30, 35, 29, 32, 10, 20, 20, 25, 4, 8, new String[]{"punch", "quickattack"}, new String[]{"thundershock", "thunderpunch", "thunder"});
		add("Bisharp", "bisharp", Area.Ground, Type.Steel, 39, 42, 30, 36, 28, 32, 20, 27, 20, 30, 8, 10, 5, 8, 3, 6, new String[]{"punch", "quickattack", "tackle", "scratch"}, new String[]{"retaliate", "superPunch", "metalclaw", "ironhead"});
		add("Timburr", "timburr", Area.Ground, Type.Normal, 39, 45, 30, 37, 16, 18, 30, 38, 29, 35, 2, 6, 7, 10, 35, 43, new String[]{"punch", "tackle"}, new String[]{"focuspunch", "superPunch", "rockthrow", "hammerarm"});
		add("Abomasnow", "abomasnow", Area.Ground, Type.Ice, 28, 33, 23, 26, 18, 21, 29, 32, 28, 31, 8, 12, 10, 25, 20, 25, new String[]{"punch", "tackle"}, new String[]{"blizzard", "icywind", "icepunch", "snowwarning", "superPunch"});
		add("Marowak", "marowak", Area.Ground, Type.Rock, 20, 25, 45, 47, 15, 17, 22, 27, 23, 29, 10, 15, 8, 16, 25, 35,  new String[]{"punch", "tackle", "headbutt", "quickattack"}, new String[]{"boneclub", "bonemerang", "smackdown", "rocktomb", "rockthrow"});
		add("Clefable", "clefable", Area.Ground, Type.Normal, 20, 25, 23, 28, 10, 15, 30, 35, 20, 23, 8, 10, 10, 18, 5, 8, new String[]{"punch", "slap"}, new String[]{"moonblast", "wakeupslap", "superPunch"});
		add("Hoothoot", "hoothoot", Area.Flying, Type.Normal, 10, 15, 10, 15, 20, 25, 25, 30, 23, 29, 5, 8, 5, 8, 2, 4, new String[]{"tackle", "peck", "scratch"}, new String[]{"fly", "airslash", "takedown"});
		add("Makuhita", "makuhita", Area.Ground, Type.Normal, 25, 30, 10, 15, 10, 15, 30, 35, 20, 35, 5, 10, 11, 16, 20, 35, new String[]{"tackle", "punch", "slap", "armthrust"}, new String[]{"superPunch", "retaliate", "hammerarm", "wakeupslap", "takedown", "knockoff", "closecombat"});
		add("Meowth", "meowth", Area.Ground, Type.Normal, 17, 22, 13, 17, 40, 45, 15, 20, 10, 15, 2, 8, 10, 15, 1, 4, new String[]{"tackle", "furyswipes", "slap", "scratch"}, new String[]{"bite", "retaliate", "slash"});
		add("Poochyena", "poochyena", Area.Ground, Type.Normal, 18, 22, 13, 17, 13, 17, 13, 17, 13, 17, 2, 6, 3, 5, 3, 8, new String[]{"tackle"}, new String[]{"bite", "takedown", "crunch"});
		add("Stantler", "stantler", Area.Ground, Type.Normal, 40, 45, 27, 32, 38, 43, 31, 36, 28, 33, 8, 12, 15, 26, 15, 29, new String[]{"tackle", "astonish"}, new String[]{"takedown", "jumpkick", "stomp", "headbutt"});
		add("Ursaring", "ursaring", Area.Ground, Type.Normal, 45, 50, 40, 45, 25, 30, 35, 40, 33, 39, 3, 12, 20, 30, 25, 30, new String[]{"tackle", "punch", "scratch", "armthrust", "slap"}, new String[]{"superPunch", "takedown", "bite", "retaliate", "smackdown", "focuspunch", "hammerarm", "slash"});
		add("Weedle", "weedle", Area.Ground, Type.Normal, 11, 16, 10, 15, 20, 25, 15, 20, 10, 15, 1, 2, 3, 6, 1, 3, new String[]{"tackle"}, new String[]{"bugbite", "poisonsting"});
	}
	
	private void add(String name, String patchName, Area area, Type type, 
			int minAtk, int maxAtk, int minDef, int maxDef, int minSpd, int maxSpd, 
			int minHp, int maxHp, int minSp, int maxSp, int minPctStatusUp, int maxPctStatusUp,
			int minClicksToUp, int maxClicksToUp, int minRp, int maxRp,
			String possibleNormalAttacks[], String possibleSpecialAttack[]) {
		Monster monster = new Monster();
		monster.setMonsterName(name);
		monster.setMonsterPatchName(patchName);
		monster.setArea(area);
		monster.setType(type);
		monster.setMinAtk(minAtk);
		monster.setMaxAtk(maxAtk);
		monster.setMinDef(minDef);
		monster.setMaxDef(maxDef);
		monster.setMinSpd(minSpd);
		monster.setMaxSpd(maxSpd);
		monster.setMinHp(minHp);
		monster.setMaxHp(maxHp);
		monster.setMinSp(minSp);
		monster.setMaxSp(maxSp);
		monster.setMinRp(minRp);
		monster.setMaxRp(maxRp);
		monster.setMinClicksToUp(minClicksToUp);
		monster.setMaxClicksToUp(maxClicksToUp);
		monster.setMinPctStatusUp(minPctStatusUp);
		monster.setMaxPctStatusUp(maxPctStatusUp);
		monster.addPossibleNormalAttack(possibleNormalAttacks);
		monster.addPossibleSpecialAttack(possibleSpecialAttack);
		
		this.monsters.put(patchName, monster);
	}
	
	public Monster getMonster(String key) {
		Monster monster = monsters.get(key);
		
		if (!monster.isAnimationCreated()) {
			monster.setAnimationCreated(true);
			monster.createAnimations();
		}
		
		return monsters.get(key);
	}
}
