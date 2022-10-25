package com.monsterclickgame.screens.talking;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.monsterclickgame.characters.Maneka;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.screens.StartMenu;

public class NoMonsters extends TalkingBase {
	private Maneka maneka;
	private AnimatedWidget animation;
	
	public NoMonsters(MonsterClick game, UserData userData) {
		super(game, userData);
		
		create();
	}
	
	@Override
	protected void initElements() {
		maneka = new Maneka();
		
		animation = new AnimatedWidget(maneka.getAnimation("talking"));
		animation.setScale(3);
		animation.setPlayMode(PlayMode.LOOP);
	}
	
	@Override
	protected void initialTexts() {
		texts.add("Hey!");
		texts.add("Você não tem monstros para batalhar...");
		texts.add("Volte quando tiver algum monstro.");
		texts.add("estagiários...");
		
		texts.reverse();	
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
