package com.monsterclickgame.customwidgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.utils.CustomTimeUtils;

public class UserBar extends Table {
	Skin skinUI;
	Label lblName;
	Label lblClicks;
	Label lblGoldCoins;
	Label lblSilverCoins;
	Label lblTimer;
	AnimatedWidget goldCoin;
	AnimatedWidget silverCoin;
	
	UserData userData;
	
	public UserBar(Skin skinUI, UserData userData) {
		super();
		
		this.skinUI = skinUI;
		this.userData = userData;
				
		create();
	}
	
	public void create() {
		this.setFillParent(true);
		
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 10;
		BitmapFont font = fontGen.generateFont(fontPar);
		fontGen.dispose();
		
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = font;
		lblStyle.background = skinUI.getDrawable("blue_button01");
		
		lblName = new Label("  " + userData.getUserName(), lblStyle);
		lblClicks = new Label("  clicks: " + userData.getClicks() + "/" + userData.getMaxClicks(), lblStyle);
		lblGoldCoins = new Label("  G. Coins: " + userData.getGoldCoins(), lblStyle);
		lblSilverCoins = new Label("  S. Coins: " + userData.getSilverCoins(), lblStyle);
		lblTimer = new Label("  " + CustomTimeUtils.formatMillisInTime(userData.getMillisTime()) , lblStyle);
		
		this.add(lblName).height(20).width(70);
		this.add(lblTimer).height(20).width(50);
		this.add(lblClicks).height(20).width(100);
		this.add(lblSilverCoins).height(20).width(70);
		this.add(lblGoldCoins).height(20).width(70);
		this.top();
	}
	
	public void update() {
		lblClicks.setText("   clicks: " + userData.getClicks() + "/" + userData.getMaxClicks());
		lblTimer.setText("  " + CustomTimeUtils.formatMillisInTime(userData.getMillisTime()));
	} 
}
