package com.monsterclickgame.attacks;

import com.badlogic.gdx.utils.ArrayMap;
import com.monsterclickgame.battle.AttackTypeManager.AttackType;
import com.monsterclickgame.battle.TypeManager.Type;
import com.monsterclickgame.weater.Weater;
import com.monsterclickgame.weater.WeaterManager;

public class AttackManager {
	private ArrayMap<String, Attack> attacks;
	
	public AttackManager() {
		attacks = new ArrayMap<String, Attack>();
		
		populate();
	}
	
	private void populate() {
		WeaterManager weaterManager = new WeaterManager();
		
		add("Soco", "punch", Type.Normal, AttackType.Melee, 40, 1);
		add("Super soco", "superPunch", Type.Normal, AttackType.Melee, 60, 5);
		add("Pistola de água", "waterGun", Type.Water, AttackType.Projectile, 40, 9);
		add("Atacar", "tackle", Type.Normal, AttackType.Melee, 40, 1);
		add("Concha Navalha", "razorShell", Type.Water, AttackType.Projectile, 75, 10);
		add("Jato de água", "aquaJet", Type.Water, AttackType.Projectile, 40, 5);
		add("Cauda de água", "aquaTail", Type.Water, AttackType.Projectile, 90, 11);
		add("Retaliação", "retaliate", Type.Normal, AttackType.Melee, 70, 9);
		add("Bomba de água", "hydroPump", Type.Water, AttackType.Projectile, 110, 15);
		add("Voar", "fly", Type.Normal, AttackType.Fly, 0, 6);
		add("Ataque rápido", "quickattack", Type.Normal, AttackType.Melee, 40, 1);
		add("Choque do trovão", "thundershock", Type.Electric, AttackType.Projectile, 40, 3);
		add("Soco do trovão", "thunderpunch", Type.Electric, AttackType.Melee, 75, 8);
		add("Trovão", "thunder", Type.Electric, AttackType.Projectile, 110, 15);
		add("Arranhar", "scratch", Type.Normal, AttackType.Melee, 40, 1);
		add("Garra de metal", "metalclaw", Type.Steel, AttackType.Melee, 50, 6);
		add("Cabeça de ferro", "ironhead", Type.Steel, AttackType.Melee, 80, 9);
		add("Jogar rocha", "rockthrow", Type.Rock, AttackType.Projectile, 50, 3);
		add("Soco focado", "focuspunch", Type.Normal, AttackType.Melee, 150, 20);
		add("Braço de martelo", "hammerarm", Type.Normal, AttackType.Melee, 100, 15);
		add("Alerta de neve", "snowwarning", Type.Ice, AttackType.Weater, 0, 10, weaterManager.getWeater("snow"));
		add("Soco de gelo", "icepunch", Type.Ice, AttackType.Melee, 75, 7);
		add("Vento gelado", "icywind", Type.Ice, AttackType.Projectile, 55, 5);
		add("Nevasca", "blizzard", Type.Ice, AttackType.Projectile, 110, 20);
		add("Cabeçada", "headbutt", Type.Normal, AttackType.Melee, 70, 1);
		add("Clube dos ossos", "boneclub", Type.Normal, AttackType.Melee, 65, 8);
		add("Ossomerangue", "bonemerang", Type.Normal, AttackType.Projectile, 50, 5);
		add("Esmagar", "smackdown", Type.Rock, AttackType.Melee, 50, 6);
		add("Túmulo de rocha", "rocktomb", Type.Rock, AttackType.Projectile, 60, 8);
		add("Tapa", "slap", Type.Normal, AttackType.Melee, 15, 1);
		add("Bofetada de despertar", "wakeupslap", Type.Normal, AttackType.Melee, 70, 15);
		add("Explosão de lua", "moonblast", Type.Normal, AttackType.Projectile, 95, 18);
		add("Bicar", "peck", Type.Normal, AttackType.Melee, 35, 1);
		add("Derrubar", "takedown", Type.Normal, AttackType.Melee, 90, 15);
		add("Golpe aereo", "airslash", Type.Normal, AttackType.Melee, 75, 12);
		add("Confiança no braço", "armthrust", Type.Normal, AttackType.Melee, 40, 1);
		add("Nocautear", "knockoff", Type.Normal, AttackType.Melee, 65, 8);
		add("Corpo a corpo", "closecombat", Type.Normal, AttackType.Melee, 120, 20);
		add("Mordida", "bite", Type.Normal, AttackType.Melee, 60, 6);
		add("Garras furiosas", "furyswipes", Type.Normal, AttackType.Melee, 18, 1);
		add("Cortar", "slash", Type.Normal, AttackType.Melee, 70, 15);
		add("Mastigar", "crunch", Type.Normal, AttackType.Melee, 80, 15);
		add("Espantar", "astonish", Type.Normal, AttackType.Projectile, 30, 1);
		add("Pisotear", "stomp", Type.Normal, AttackType.Melee, 65, 8);
		add("Coice", "jumpkick", Type.Normal, AttackType.Melee, 100, 18);
		add("Ferroada venenosa", "poisonsting", Type.Normal, AttackType.Melee, 15, 1);
		add("Mordida de inseto", "bugbite", Type.Normal, AttackType.Melee, 60, 8);
	}
	
	private void add(String name, String patchName, Type type, AttackType contact, 
			float power, int stamina, Weater weater) {
		Attack attack = new Attack();
		
		attack.setName(name);
		attack.setPatchName(patchName);
		attack.setType(type);
		attack.setAttackType(contact);
		attack.setPower(power);
		attack.setStamina(stamina);
		attack.setWeater(weater);
		
		this.attacks.put(patchName, attack);
	}
	
	private void add(String name, String patchName, Type type, AttackType contact, 
			float power, int stamina) {
		add(name, patchName, type, contact, power, stamina, null);
	}
	
	public Attack getAttack(String key) {
		return this.attacks.get(key);
	}
}