package com.monsterclickgame.screens.talking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.history.History;
import com.monsterclickgame.screens.SelectHistoryBattle;
import com.monsterclickgame.utils.CustomTimeUtils;

public class CaveEntrance extends TalkingBase {
	private History history;
	
	public CaveEntrance(MonsterClick game, UserData userData, History history) {
		super(game, userData);
		
		this.history = history;
	}

	@Override
	protected void initialTexts() {
		texts.add("...");
		texts.add("Parece ser essa a caverna...");
		texts.add("E eu achando que estagiário só fazia café...");
		
		texts.reverse();	
	}

	@Override
	protected void addElements() {
		String imgName;
		String terrainPatch;
		
		imgName = "CaveEntrance";
		if (CustomTimeUtils.isNight()) {
			imgName += "_night";
		}
		
		terrainPatch = "BGs/Others/" + imgName + ".png";
				
		if (!Gdx.files.internal(terrainPatch).exists()) {
			terrainPatch = "BGs/Others/CaveEntrance.png";
		}

		table.setBackground(new Image(new Texture(terrainPatch)).getDrawable());
	}
	
	@Override
	protected void nextButtonClicked() {
		if (!nextText()) {
			game.setGameState(new SelectHistoryBattle(game, userData, history));
		}
	}
}
