package com.monsterclickgame.screens.talking;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.monsterclickgame.characters.Maneka;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.eggs.Egg;
import com.monsterclickgame.eggs.EggManager;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.monsters.Monster;
import com.monsterclickgame.monsters.MonsterManager;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.screens.StartMenu;

public class WelcomeToMonsterClick extends TalkingBase {
	private Maneka maneka;
	private AnimatedWidget animation;
	
	public WelcomeToMonsterClick(MonsterClick game, UserData userData) {
		super(game, userData);
	}
	
	@Override
	protected void initElements() {
		maneka = new Maneka();
		
		animation = new AnimatedWidget(maneka.getAnimation("talking"));
		animation.setScale(3);
		animation.setPlayMode(PlayMode.LOOP);
		
		createEgg();
	}
	
	@Override
	protected void initialTexts() {
		texts.add("Olá, entã você é o novo estagiário?");
		texts.add("Me chamo " + maneka.getCharacterName() + ", sou o desenvolvedor.");
		texts.add("Bem vindo a MonsterClick!");
		texts.add("Estamos desenvolvendo um novo jogo baseado nos monstros aqui da região...");
		texts.add("Mas para isso precisamos coletar informações dos monstros.");
		texts.add("Bom, eu mesmo faria isso... Mas é um trabalho perigoso...");
		texts.add("Então decidimos contratar um estagiário para fazer.");
		texts.add("Deixamos um ovo junto com suas coisas, choque ele e meu assistente lhe dará mais informaçoes...");
		
		texts.reverse();	
	}
	
	private void createEgg() {
		EggManager eggmanager = new EggManager();
		MonsterManager monstermanager = new MonsterManager();
		
		Egg egg = eggmanager.getEgg("egg");
		Monster monster = monstermanager.getMonster(egg.getRandomMonster());
		
		MyMonster myMonster = new MyMonster(egg, monster);
		userData.addMonster(myMonster);
	}

	@Override
	protected void addElements() {
		table.setBackground(new Image(new Texture("BGs/Others/TutorialRoom.png")).getDrawable());
		table.add(animation).left().expandY().bottom().padLeft(30).padBottom(-10);
	}
	
	@Override
	protected void nextButtonClicked() {
		animation.setAnimation(maneka.getAnimation("talking"));
		if (!nextText()) {
			game.setGameState(new StartMenu(game, userData));
		}
	}
	
	@Override
	protected void talkFinished() {
		animation.setAnimation(maneka.getAnimation("stand"));
	}
}
