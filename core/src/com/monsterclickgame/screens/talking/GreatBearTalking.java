package com.monsterclickgame.screens.talking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.monsterclickgame.characters.Jesse;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.history.History;
import com.monsterclickgame.screens.SelectHistoryBattle;
import com.monsterclickgame.utils.CustomTimeUtils;

public class GreatBearTalking extends TalkingBase {
	private Jesse jesse;
	private History history;
	private AnimatedWidget animation;
	
	public GreatBearTalking(MonsterClick game, UserData userData, History history) {
		super(game, userData);
		
		this.history = history;
	}
	
	@Override
	protected void initElements() {
		jesse = new Jesse();
		
		animation = new AnimatedWidget(jesse.getAnimation("talking"));
		animation.setScale(3);
		animation.setPlayMode(PlayMode.LOOP);
	}
	
	@Override
	protected void initialTexts() {
		texts.add("Hey!!!");
		texts.add("Você é o estagiário encarregado de coletar as informações certo?");
		texts.add("Vejo que você já chocou seu ovo...");
		
		if (userData.getMyMonsters().size == 1) {
			texts.add("É um ótimo " + userData.getMyMonsters().get(0).getOriginalName() + ".");
		} else {
			texts.add("São ótimos monstros");
		}
		
		texts.add("Recebemos a informação de um monstro em uma caverna na floresta...");
		texts.add("Você deve ir até lá e coletar as informações sobre esse monstro!");
		
		if (CustomTimeUtils.isNight()) {
			texts.add("Eu mesmo iria, mas marquei uma gelada com os amigos do trampo...");
		} else {
			texts.add("Eu mesmo iria, mas tenho muito trabalho a fazer...");
		}
		
		texts.add("Siga pela rota até a floresta, depois siga em frente até achar a caverna.");
		texts.add("Boa sorte. Já estou atrasado...");
		
		texts.reverse();	
	}

	@Override
	protected void addElements() {
		String imgName;
		String terrainPatch;
		
		imgName = "route";
		if (CustomTimeUtils.isNight()) {
			imgName += "_night";
		}
		
		terrainPatch = "Terrain/route/" + imgName + ".png";
				
		if (!Gdx.files.internal(terrainPatch).exists()) {
			terrainPatch = "Terrain/route/route.png";
		}

		table.setBackground(new Image(new Texture(terrainPatch)).getDrawable());
		table.add(animation).left().expandY().bottom().padLeft(30).padBottom(-10);
	}
	
	@Override
	protected void nextButtonClicked() {
		animation.setAnimation(jesse.getAnimation("talking"));
		if (!nextText()) {
			game.setGameState(new SelectHistoryBattle(game, userData, history));
		}
	}
	
	@Override
	protected void talkFinished() {
		animation.setAnimation(jesse.getAnimation("stand"));
	}
}
