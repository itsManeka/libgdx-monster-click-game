package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.monsterclickgame.attacks.Attack;
import com.monsterclickgame.battle.AttackTypeManager.AttackType;
import com.monsterclickgame.battle.BattleManager;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.customwidgets.LevelBarWidget;
import com.monsterclickgame.customwidgets.PressButton;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.interfaces.AnimationListener;
import com.monsterclickgame.interfaces.PressListener;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.weater.Weater;
import com.monsterclickgame.weater.WeaterManager;

public class HistoryBattleScreen extends BaseScreen {
	private long enemyCurrentHp;
	private long enemyCurrentSp;
	private long enemyCurrentRp;
	private long playerCurrentHp;
	private long playerCurrentSp;
	private long playerCurrentRp;
	private boolean enemyTurn = false;
	private boolean endGame = false;
	
	private Stage stageUi;
	private Stage stageWeater;
	private Stage stageMonster;
	private Stage stageGameEnd;
	private Stage stageAtkInfo;
	
	private Table monsterTable;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private BattleManager manager;
	
	private Label lblEnemyHp;
	private Label lblEnemySp;
	private Label lblEnemyRp;
	private Label lblPlayerHp;
	private Label lblPlayerSp;
	private Label lblPlayerRp; 
	private Label enemyName;
	private Label playerName;
	private Label messageLabel;
	private Button btnDefend;
	private Button btnRest;
	private PressButton btnNormalAttack;
	private PressButton btnSpecialAttackOne;
	private PressButton btnSpecialAttackTwo;
	private Button btnEndGame;
	private MyMonster enemy;
	private MyMonster player;
	private BitmapFont font;
	private BitmapFont barFont;
	private BitmapFont nameFont;
	private BitmapFont messageFont;
	private BitmapFont victoryFont;
	private BitmapFont loseFont;
	private TextButton btnRun;
	private HistoryBattle battle;
	private LevelBarWidget enemyHealtBar;
	private LevelBarWidget playerHealtBar;
	private LevelBarWidget enemyStaminaBar;
	private LevelBarWidget playerStaminaBar;
	private LevelBarWidget enemyResistenceBar;
	private LevelBarWidget playerResistenceBar;
	private AnimatedWidget enemyAnimation;
	private AnimatedWidget playerAnimation;
	
	private Table endGameTable;
	
	private Table weaterTable;
	private Weater weater;
	private AnimatedWidget weaterAnimation;
	
	private Table atkInfoTable;
	private Label infoTypeLbl;
	private Label infoNameLbl;
	private Label infoStaminaLbl;
	private Label infoPowerLbl;
	private Label infoAtkTypeLbl;
	
	public HistoryBattleScreen(MonsterClick game, UserData userData, MyMonster player, HistoryBattle battle) {
		super(game, userData);
		
		this.battle = battle;
		this.player = player;
		this.enemy = battle.getEnemy();
		init();
		
		initStages();
		create();
		createInput();
	}
	
	private void init() {
		this.playerAnimation = new AnimatedWidget(this.player.getAnimation("back"));
		this.playerAnimation.setPlayMode(PlayMode.LOOP);
		this.playerAnimation.addEventListener(playerAnimationFinished);
		this.playerAnimation.setAlign(Align.center);
		this.playerAnimation.setScale(2);
		
		this.enemyAnimation = new AnimatedWidget(this.enemy.getAnimation("stand"));
		this.enemyAnimation.setPlayMode(PlayMode.LOOP);
		this.enemyAnimation.addEventListener(enemyAnimationFinished);
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
		
		this.enemyHealtBar = new LevelBarWidget(this.skinUI.getRegion("green_button05"),
				  								this.skinUI.getRegion("red_button02"));
		this.enemyHealtBar.setProgress(1);
				
		this.playerHealtBar = new LevelBarWidget(this.skinUI.getRegion("green_button05"),
				  								this.skinUI.getRegion("red_button02"));
		this.playerHealtBar.setProgress(1);
		
		this.enemyStaminaBar = new LevelBarWidget(this.skinUI.getRegion("yellow_button05"),
												this.skinUI.getRegion("red_button02"));
		this.enemyStaminaBar.setProgress(1);
		
		this.playerStaminaBar = new LevelBarWidget(this.skinUI.getRegion("yellow_button05"),
													this.skinUI.getRegion("red_button02"));
		this.playerStaminaBar.setProgress(1);
		
		this.enemyResistenceBar = new LevelBarWidget(this.skinUI.getRegion("blue_button05"),
													this.skinUI.getRegion("red_button02"));
		this.enemyResistenceBar.setProgress(1);
		
		this.playerResistenceBar = new LevelBarWidget(this.skinUI.getRegion("blue_button05"),
													this.skinUI.getRegion("red_button02"));
		this.playerResistenceBar.setProgress(1);
		
		this.manager = new BattleManager();
		this.manager.setMonsterEnemy(enemy);
		this.manager.setMonsterPlayer(player);
		this.manager.setTerrain(battle.getTerrain());
	}

	@Override
	public void create() {
		setDebug(false);
		
		this.stageMonster = addStage();
		this.stageWeater = addStage();
		this.stageAtkInfo = addStage();
		this.stageUi = addStage();
		this.stageGameEnd = addStage();
		
		createFont();
		createBarFont();
		createNameFont();
		createMessageFont();
		createVictoryFont();
		createLoseFont();
		
		createMonsterTable();
		createWeaterTable();
		createAtkInfoTable();
		createUiTable();
		createEndGameTable();
	}
	
	private void createAtkInfoTable() {
		atkInfoTable = createTable();
		atkInfoTable.setFillParent(true);
		atkInfoTable.defaults().pad(3);
		atkInfoTable.pad(80);
		atkInfoTable.setVisible(false);
		
		infoNameLbl = createLabel("Ataque", nameFont, null);
		infoNameLbl.setAlignment(Align.center);
		
		infoTypeLbl = createLabel(" Normal ", font, skinUI.getDrawable("Normal"));
		infoTypeLbl.setAlignment(Align.center);
		
		Label lblStamina = createLabel("Energia: ", nameFont, null);
		lblStamina.setAlignment(Align.right);
		
		infoStaminaLbl = createLabel("0", nameFont, null);
		infoStaminaLbl.setAlignment(Align.left);

		Label lblPower = createLabel("Força: ", nameFont, null);
		lblPower.setAlignment(Align.right);
		
		infoPowerLbl = createLabel("0", nameFont, null);
		infoPowerLbl.setAlignment(Align.left);
		
		infoAtkTypeLbl = createLabel("Corpo-a-corpo", nameFont, null);
		infoAtkTypeLbl.setAlignment(Align.center);
		
		atkInfoTable.add(infoNameLbl).expandX().colspan(2);
		atkInfoTable.row();
		atkInfoTable.add(infoTypeLbl).expandX().colspan(2);
		atkInfoTable.row();
		atkInfoTable.add(lblStamina).expandX().left();
		atkInfoTable.add(infoStaminaLbl).expandX().right();
		atkInfoTable.row();
		atkInfoTable.add(lblPower).expandX().left();
		atkInfoTable.add(infoPowerLbl).expandX().right();
		atkInfoTable.row();
		atkInfoTable.add(infoAtkTypeLbl).expandX().colspan(2);
		
		stageAtkInfo.addActor(atkInfoTable);
	}
	
	private void setAttackInfo(Attack attack) {
		infoNameLbl.setText(attack.getName());
		
		infoTypeLbl.setText(" " + attack.getType().toString() + " ");
		infoTypeLbl.getStyle().background = skinUI.getDrawable(attack.getType().toString());
		
		infoStaminaLbl.setText(attack.getStamina()+"");
		infoPowerLbl.setText(attack.getPower()+"");
		infoAtkTypeLbl.setText(attack.getAttackType().toString());
	}
	
	private void createEndGameTable() {
		endGameTable = createTable();
		endGameTable.setFillParent(true);
		
		btnEndGame = createTextButton("Vitória!", victoryFont, null, null);
		btnEndGame.setVisible(false);
		btnEndGame.setName("victory");
		btnEndGame.addListener(endGameListener);
		
		endGameTable.add(btnEndGame).expand().fill();
		
		this.stageGameEnd.addActor(endGameTable);
	}
	
	private void setLoseGameButton() {
		btnEndGame.setName("lose");
		((TextButton) btnEndGame).setText("Derrota!");
		TextButtonStyle btnStyle = (TextButtonStyle) btnEndGame.getStyle();
		btnStyle.font = loseFont;
		btnEndGame.setStyle(btnStyle);
	}
	
	private void createWeaterTable() {
		WeaterManager weaterManager = new WeaterManager();
		weater = weaterManager.getWeater(battle.getWeater());
		
		weaterTable = createTable();
		weaterTable.setFillParent(true);
		
		if (weater != null) {
			manager.setWeater(weater);
			
			weaterAnimation = new AnimatedWidget(weater.getAnimation("action"));
			weaterAnimation.setPlayMode(PlayMode.LOOP);
			
			weaterTable.add(weaterAnimation).expand().bottom().left();
		}
		
		this.stageWeater.addActor(weaterTable);
	}
	
	private void setWeater(Weater weater) {
		manager.setWeater(weater);
		
		if (this.weater == null) {
			this.weater = weater;
			
			weaterAnimation = new AnimatedWidget(this.weater.getAnimation("action"));
			weaterAnimation.setPlayMode(PlayMode.LOOP);
			
			weaterTable.add(weaterAnimation).expand().bottom().left();
		} else {
			this.weater = weater;
			
			weaterAnimation.setAnimation(this.weater.getAnimation("action"));
			weaterAnimation.setState(0);
		}
	}
	
	private void createMonsterTable() {
		monsterTable = createTable();
		monsterTable.setFillParent(true);
		monsterTable.background(this.battle.getTerrain().getTerrainDrawable());
		monsterTable.defaults().pad(2);
		
		monsterTable.pad(10);
		monsterTable.add().expand();
		monsterTable.row();
		monsterTable.add(this.enemyAnimation).bottom().right().expandY().padRight(20);
		monsterTable.row();
		monsterTable.add(this.playerAnimation).bottom().left().expandY().padLeft(20);
		monsterTable.row();
		monsterTable.add().expand();
		
		this.stageMonster.addActor(monsterTable);
	}
	
	private void createUiTable() {
		createMessageLabel();
		
		this.playerCurrentHp = player.getHp();
		this.lblPlayerHp = createLabel(this.playerCurrentHp + "/" + player.getHp(), this.barFont, null);
		this.lblPlayerHp.setAlignment(Align.left);

		this.playerCurrentSp = player.getSp();
		this.lblPlayerSp = createLabel(this.playerCurrentSp + "/" + player.getSp(), this.barFont, null);
		this.lblPlayerSp.setAlignment(Align.left);
		
		this.playerCurrentRp = player.getRp();
		this.lblPlayerRp = createLabel(this.playerCurrentRp + "/" + player.getRp(), this.barFont, null);
		this.lblPlayerRp.setAlignment(Align.left);
		
		this.enemyCurrentHp = enemy.getHp();
		this.lblEnemyHp = createLabel(this.enemyCurrentHp + "/" + enemy.getHp(), this.barFont, null);
		this.lblEnemyHp.setAlignment(Align.right);
		
		this.enemyCurrentSp = enemy.getSp();
		this.lblEnemySp = createLabel(this.enemyCurrentSp + "/" + enemy.getSp(), this.barFont, null);
		this.lblEnemySp.setAlignment(Align.right);
		
		this.enemyCurrentRp = enemy.getRp();
		this.lblEnemyRp = createLabel(this.enemyCurrentRp + "/" + enemy.getRp(), this.barFont, null);
		this.lblEnemyRp.setAlignment(Align.left);
		
		this.enemyName = createLabel(this.enemy.getName(), this.nameFont, null);
		this.enemyName.setAlignment(Align.left);
		
		this.playerName = createLabel(this.player.getName(), this.nameFont, null);
		this.playerName.setAlignment(Align.right);
		
		this.btnDefend = createButton(this.skinUI.getDrawable("defOptUp"), this.skinUI.getDrawable("defOptDown"), this.skinUI.getDrawable("defOptDisabled"));
		this.btnDefend.addListener(defendListener);
		
		this.btnRest = createButton(this.skinUI.getDrawable("restOptUp"), this.skinUI.getDrawable("restOptDown"), this.skinUI.getDrawable("restOptDisabled"));
		this.btnRest.addListener(restListener);
		
		this.btnNormalAttack = createButton(this.skinUI.getDrawable("atackOptUp"), this.skinUI.getDrawable("atackOptDown"), this.skinUI.getDrawable("atackOptDisabled"));
		this.btnNormalAttack.addListener(normalAttackListener);
		this.btnNormalAttack.addPressListener(pressNormalAtk);
		
		this.btnSpecialAttackOne = createButton(this.skinUI.getDrawable("atackOptUp"), this.skinUI.getDrawable("atackOptDown"), this.skinUI.getDrawable("atackOptDisabled"));
		this.btnSpecialAttackOne.addListener(specialAttackOneListener);
		this.btnSpecialAttackOne.addPressListener(pressSpecialAtkOne);
		
		this.btnSpecialAttackTwo = createButton(this.skinUI.getDrawable("atackOptUp"), this.skinUI.getDrawable("atackOptDown"), this.skinUI.getDrawable("atackOptDisabled"));
		this.btnSpecialAttackTwo.addListener(specialAttackTwoListener);
		this.btnSpecialAttackTwo.addPressListener(pressSpecialAtkTwo);
		
		this.btnRun = createTextButton("Fugir", this.font, this.skinUI.getDrawable("blue_button07"), this.skinUI.getDrawable("blue_button08"), this.skinUI.getDrawable("disabled_button"));
		this.btnRun.addListener(runListener);
				
		Table container = createTable();
		container.setFillParent(true);
		container.defaults().pad(2);
		container.pad(5);
		container.add(messageLabel).size(340, 70).top().colspan(5);
		container.row();
		container.add(enemyHealtBar).height(10).colspan(2);
		container.add(lblEnemyHp).height(10).colspan(3).left();
		container.row();
		container.add(enemyStaminaBar).height(10).colspan(2);
		container.add(lblEnemySp).height(10).colspan(3).left();
		container.row();
		container.add(enemyResistenceBar).height(10).colspan(2);
		container.add(lblEnemyRp).height(10).colspan(3).left();
		container.row();
		container.add(enemyName).colspan(5).top().left();
		container.row();
		container.add().expand().colspan(5);
		container.row();
		container.add(lblPlayerHp).colspan(3).right();
		container.add(playerHealtBar).height(10).colspan(2);
		container.row();
		container.add(lblPlayerSp).colspan(3).right();
		container.add(playerStaminaBar).height(10).colspan(2);
		container.row();
		container.add(lblPlayerRp).colspan(3).right();
		container.add(playerResistenceBar).height(10).colspan(2);
		container.row();
		container.add(btnSpecialAttackOne).size(50).bottom().left();
		container.add(playerName).colspan(4).top().right();
		container.row();
		container.add(btnNormalAttack).size(70);
		container.add(btnSpecialAttackTwo).size(50).bottom().left();
		container.add(btnRun).size(50, 35).bottom().expandX();
		container.add(btnRest).size(50).bottom().right();
		container.add(btnDefend).size(70);
		
		this.stageUi.addActor(container);
	}
	
	private void createFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 16;
		fontPar.color = Color.WHITE;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createNameFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 16;
		fontPar.color = Color.WHITE;
		fontPar.borderWidth = 1;
		this.nameFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createBarFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 12;
		fontPar.color = Color.WHITE;
		fontPar.borderWidth = 1;
		this.barFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createMessageFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 16;
		fontPar.color = Color.BLACK;
		this.messageFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createVictoryFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 35;
		fontPar.color = Color.GREEN;
		fontPar.borderWidth = 3;
		fontPar.borderColor = Color.FOREST;
		this.victoryFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createLoseFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 35;
		fontPar.color = Color.SCARLET;
		fontPar.borderWidth = 3;
		fontPar.borderColor = Color.RED;
		this.loseFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createMessageLabel() {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = this.messageFont;
		lblStyle.background = skinUI.getDrawable("grey_button05");
		this.messageLabel = new Label("Batalha!", lblStyle);
		this.messageLabel.setWrap(true);
		this.messageLabel.setAlignment(Align.center);
	}
	
	@SuppressWarnings("unused")
	private PressButton createButton(Drawable up, Drawable down) {
		return createButton(up, down, null);
	}
	
	private PressButton createButton(Drawable up, Drawable down, Drawable disabled) {
		ButtonStyle btnStyle = new ButtonStyle();
		btnStyle.up = up;
		btnStyle.down = down;
		btnStyle.disabled = disabled;
		PressButton button = new PressButton(btnStyle);
		return button;
	}
	
	private TextButton createTextButton(String text, BitmapFont font, Drawable up, Drawable down) {
		return createTextButton(text, font, up, down, null);
	}
	
	private TextButton createTextButton(String text, BitmapFont font, Drawable up, Drawable down, Drawable disabled) {
		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = font;
		btnStyle.up = up;
		btnStyle.down = down;
		btnStyle.disabled = disabled;
		TextButton button = new TextButton(text, btnStyle);
		return button;
	}
	
	private Label createLabel(String text, BitmapFont font, Drawable bg) {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = font;
		lblStyle.background = bg;
		Label label = new Label(text, lblStyle);
		return label;
	}
	
	private void disableButtons() {
		this.btnRest.setDisabled(true);
		this.btnDefend.setDisabled(true);
		this.btnNormalAttack.setDisabled(true);
		this.btnSpecialAttackOne.setDisabled(true);
		this.btnSpecialAttackTwo.setDisabled(true);
		this.btnRun.setDisabled(true);
	}
	
	private void enableButtons() {
		this.btnRest.setDisabled(false);
		this.btnDefend.setDisabled(false);
		this.btnNormalAttack.setDisabled(false);
		this.btnSpecialAttackOne.setDisabled(false);
		this.btnSpecialAttackTwo.setDisabled(false);
		this.btnRun.setDisabled(false);
	}
	
	private void apllyDamangeEnemy(float damange) {
		enemyCurrentHp -= damange;
		if (enemyCurrentHp < 0) enemyCurrentHp = 0;
		
		this.enemyHealtBar.decrementProgress(getBarProgress(enemy.getHp(), damange));
		
		this.lblEnemyHp.setText(enemyCurrentHp + "/" + enemy.getHp());
		
		checkEndGame();
	}
	
	private void apllyDamangePlayer(float damange) {
		playerCurrentHp -= damange;
		if (playerCurrentHp < 0) playerCurrentHp = 0;
		
		this.playerHealtBar.decrementProgress(getBarProgress(player.getHp(), damange));
		
		this.lblPlayerHp.setText(playerCurrentHp + "/" + player.getHp());
		
		checkEndGame();
	}
	
	private void checkEndGame() {
		if (playerCurrentHp == 0 || enemyCurrentHp == 0) {
			if (playerCurrentHp == 0) {
				setLoseGameButton();
			}
			
			endGame = true;
			player.resetBattleStatus();
			enemy.resetBattleStatus();
			
			btnEndGame.setVisible(true);
		}
	}
	
	private boolean apllyResistencePlayer() {
		playerCurrentRp -= 1;
		if (playerCurrentRp < 0) playerCurrentRp = 0;
		
		this.playerResistenceBar.decrementProgress(getBarProgress(player.getRp(), 1));
		
		this.lblPlayerRp.setText(playerCurrentRp + "/" + player.getRp());
		
		return playerCurrentRp > 0;
	}
	
	private boolean apllyResistenceEnemy() {
		enemyCurrentRp -= 1;
		if (enemyCurrentRp < 0) enemyCurrentRp = 0;
		
		this.enemyResistenceBar.decrementProgress(getBarProgress(enemy.getRp(), 1));
		
		this.lblEnemyRp.setText(enemyCurrentRp + "/" + enemy.getRp());
		
		return enemyCurrentRp > 0;
	}
	
	private void apllyStaminaPlayer(int stamina) {
		playerCurrentSp -= stamina;
		if (playerCurrentSp < 0) playerCurrentSp = 0;
		
		this.playerStaminaBar.decrementProgress(getBarProgress(player.getSp(), stamina));
		
		this.lblPlayerSp.setText(playerCurrentSp + "/" + player.getSp());
	}
	
	private void apllyStaminaEnemy(int stamina) {
		enemyCurrentSp -= stamina;
		if (enemyCurrentSp < 0) enemyCurrentSp = 0;
		
		this.enemyStaminaBar.decrementProgress(getBarProgress(enemy.getSp(), stamina));
		
		this.lblEnemySp.setText(enemyCurrentSp + "/" + enemy.getSp());
	}
	
	private void incrementStaminaPlayer(int stamina) {
		playerCurrentSp += stamina;
		if (playerCurrentSp > player.getSp()) playerCurrentSp = player.getSp();
		
		this.playerStaminaBar.incrementProgress(getBarProgress(player.getSp(), stamina));
		
		this.lblPlayerSp.setText(playerCurrentSp + "/" + player.getSp());
	}
	
	private void incrementStaminaEnemy(int stamina) {
		enemyCurrentSp += stamina;
		if (enemyCurrentSp > enemy.getSp()) enemyCurrentSp = enemy.getSp();
		
		this.enemyStaminaBar.incrementProgress(getBarProgress(enemy.getSp(), stamina));
		
		this.lblEnemySp.setText(enemyCurrentSp + "/" + enemy.getSp());
	}
	
	private void incrementResistencePlayer(int resistence) {
		playerCurrentRp += resistence;
		if (playerCurrentRp > player.getRp()) playerCurrentRp = player.getRp();
		
		this.playerResistenceBar.incrementProgress(getBarProgress(player.getRp(), resistence));
		
		this.lblPlayerRp.setText(playerCurrentRp + "/" + player.getRp());
	}
	
	private void incrementResistenceEnemy(int resistence) {
		enemyCurrentRp += resistence;
		if (enemyCurrentRp > enemy.getRp()) enemyCurrentRp = enemy.getRp();
		
		this.enemyResistenceBar.incrementProgress(getBarProgress(enemy.getRp(), resistence));
		
		this.lblEnemyRp.setText(enemyCurrentRp + "/" + enemy.getRp());
	}
	
	private float getBarProgress(float max, float value) {
		float progress;
		
		progress = (((value * 100) / max) / 100);
		
		return progress;
	}
	
	private void enemyTurn() {
		int maxOpc = 3;
		Attack attack;
		
		if (enemyCurrentRp == 0) {
			maxOpc ++;
		}
		
		switch (MathUtils.random(1, maxOpc)) {
		case 1:
			attack = enemy.getNormalAttack();
			break;

		case 2:
			attack = enemy.getSpecialAttack1();
			break;

		case 3:
			attack = enemy.getSpecialAttack2();
			break;
			
		default:
			attack = null;
			break;
		}
		
		if (attack != null) {
			manager.attack(false, attack);
			
			if (attack.getStamina() <= enemyCurrentSp) {
				apllyStaminaEnemy(attack.getStamina());
				
		        messageLabel.setText(enemy.getName() + " usou " + attack.getName() + ".");
		        
		        enemyAnimation.setFrame("frontattack");
		        enemyAnimation.setAnimation(enemy.getAnimation("frontattack"));
		        enemyAnimation.setState(0);
		        enemyAnimation.setPlayMode(PlayMode.NORMAL);
		        
		        if (attack.getAttackType() == AttackType.Fly) {
		        	if (this.enemy.isFlying()) {
		        		this.enemy.setFlying(false);
		        		this.monsterTable.getCell(enemyAnimation).bottom();
		        	} else {
			        	this.enemy.setFlying(true);
			        	this.monsterTable.getCell(enemyAnimation).top();
		        	}
		        }
		        
		        if (attack.getAttackType() == AttackType.Weater) {
		        	setWeater(attack.getWeater());
		        }
			} else {
				enemy.setIsDefending(true);
	            
	            messageLabel.setText(enemy.getName() + " usou defender.");
	            
	            enemyAnimation.setFrame("defending");
	            enemyAnimation.setAnimation(enemy.getAnimation("stand"));
	            enemyAnimation.setState(0);
	            enemyAnimation.setPlayMode(PlayMode.NORMAL);
			}
		} else {
			if (enemy.isFlying()) {
        		enemy.setFlying(false);
        		monsterTable.getCell(enemyAnimation).bottom();
        	}
        	
        	incrementResistenceEnemy(enemy.getRp());
        	
			messageLabel.setText(enemy.getName() + " usou descansar.");

			enemyAnimation.setFrame("rest");
			enemyAnimation.setAnimation(enemy.getAnimation("stand"));
			enemyAnimation.setState(0);
			enemyAnimation.setPlayMode(PlayMode.NORMAL);
		}
	}
	
	private AnimationListener playerAnimationFinished = new AnimationListener() {
		@Override
		public void onAnimationFinished() {
			if (playerAnimation.getFrame() == "backattack" ||
					playerAnimation.getFrame() == "defending" ||
					playerAnimation.getFrame() == "rest") {
				if (playerAnimation.getFrame() == "backattack") {
					if (manager.getAttack().getAttackType() != AttackType.Fly &&
						manager.getAttack().getAttackType() != AttackType.Weater) {
						float damange;
						damange = manager.getDamange();
						messageLabel.setText("causou " + damange + " de dano.");
					
						apllyDamangeEnemy(damange);
					}
				}
				
				playerAnimation.setFrame("back");
	            playerAnimation.setAnimation(player.getAnimation("back"));
	            playerAnimation.setState(0);
	            playerAnimation.setPlayMode(PlayMode.LOOP);
	            
	            enemyTurn = true;
			} else {
				if (playerAnimation.getFrame() == "back" && enemyTurn) {
					enemyTurn = false;
					
					if (!endGame) enemyTurn();
				}
			}
		}
	};
	
	private AnimationListener enemyAnimationFinished = new AnimationListener() {
		@Override
		public void onAnimationFinished() {
			if (enemyAnimation.getFrame() == "frontattack" ||
				enemyAnimation.getFrame() == "defending" ||
				enemyAnimation.getFrame() == "rest") {
				if (enemyAnimation.getFrame() == "frontattack") {
					if (manager.getAttack().getAttackType() != AttackType.Fly &&
						manager.getAttack().getAttackType() != AttackType.Weater) {
						float damange;
						damange = manager.getDamange();
						messageLabel.setText("causou " + damange + " de dano.");
						
						apllyDamangePlayer(damange);
					}
				}
				
				enemyAnimation.setFrame("stand");
				enemyAnimation.setAnimation(enemy.getAnimation("stand"));
				enemyAnimation.setState(0);
				enemyAnimation.setPlayMode(PlayMode.LOOP);
				
				enableButtons();
				endTurn();
			}
		}
	};
	
	public void endTurn() {
		if (enemy.isUsingResistence()) {
			if (!apllyResistenceEnemy()) enemyResistenceZero();
		}
		
		if (player.isUsingResistence()) {
			if (!apllyResistencePlayer()) playerResistenceZero();
		}
		
		enemy.setIsDefending(false);
		player.setIsDefending(false);
		
		incrementStaminaEnemy(1);
		incrementStaminaPlayer(1);
	}
	
	public void enemyResistenceZero() {
		if (enemy.isFlying()) {
			this.enemy.setFlying(false);
        	this.monsterTable.getCell(enemyAnimation).bottom();	
		}
	}
	
	public void playerResistenceZero() {
		if (player.isFlying()) {
			this.player.setFlying(false);
        	this.monsterTable.getCell(playerAnimation).bottom();	
		}
	}
	
	public ClickListener normalAttackListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
        	if (((PressButton) event.getListenerActor()).hasPressed()) return;
        	if (((Button) event.getListenerActor()).isDisabled()) return;
        	
        	playerAttack(player.getNormalAttack());
        }
	};
	
	public ClickListener specialAttackOneListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
        	if (((PressButton) event.getListenerActor()).hasPressed()) return;
        	if (((Button) event.getListenerActor()).isDisabled()) return;
            
        	playerAttack(player.getSpecialAttack1());
        }
	};
	
	public ClickListener specialAttackTwoListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
        	if (((PressButton) event.getListenerActor()).hasPressed()) return;
        	if (((Button) event.getListenerActor()).isDisabled()) return;
            
        	playerAttack(player.getSpecialAttack2());
        }
	};
	
	private void playerAttack (Attack attack) {
		if (attack.getStamina() > playerCurrentSp) {
			messageLabel.setText("Energia insuficiente.");
			return;
		}
		
		manager.attack(true, attack);
        
		apllyStaminaPlayer(attack.getStamina());
		
        messageLabel.setText(player.getName() + " usou " + attack.getName() + ".");
        
        playerAnimation.setFrame("backattack");
        playerAnimation.setAnimation(player.getAnimation("backattack"));
        playerAnimation.setState(0);
        playerAnimation.setPlayMode(PlayMode.NORMAL);
        
        if (attack.getAttackType() == AttackType.Fly) {
        	if (this.player.isFlying()) {
        		this.player.setFlying(false);
        		this.monsterTable.getCell(playerAnimation).bottom();
        	} else {
        		this.player.setFlying(true);
        		this.monsterTable.getCell(playerAnimation).top();
        	}
        }
        
        if (attack.getAttackType() == AttackType.Weater) {
        	setWeater(attack.getWeater());
        }
        
        disableButtons();
	}
	
	public ClickListener defendListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
        	if (((Button) event.getListenerActor()).isDisabled()) return;
            player.setIsDefending(true);
            
            messageLabel.setText(player.getName() + " usou defender.");
            
            playerAnimation.setFrame("defending");
            playerAnimation.setAnimation(player.getAnimation("back"));
            playerAnimation.setState(0);
            playerAnimation.setPlayMode(PlayMode.NORMAL);
        }
	};
	
	public ClickListener restListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
        	if (((Button) event.getListenerActor()).isDisabled()) return;
        	
        	if (player.isFlying()) {
        		player.setFlying(false);
        		monsterTable.getCell(playerAnimation).bottom();
        	}
        	
        	incrementResistencePlayer(player.getRp());
        	
        	messageLabel.setText(player.getName() + " usou descansar.");

            playerAnimation.setFrame("rest");
            playerAnimation.setAnimation(player.getAnimation("back"));
            playerAnimation.setState(0);
            playerAnimation.setPlayMode(PlayMode.NORMAL);
        }
	};
	
	public ChangeListener runListener = new ChangeListener() {
        @Override
        public void changed (ChangeEvent event, Actor actor) {
        	if (((TextButton) event.getListenerActor()).isDisabled()) return;
        	
            game.setGameState(new SelectHistoryBattle(game, userData, battle.getHistory()));
        }
	};
	
	public ChangeListener endGameListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if (event.getTarget().getName() == "victory") {
				game.setGameState(new WinScreen(game, userData, player, battle));
			} else {
				game.setGameState(new SelectHistoryBattle(game, userData, battle.getHistory()));
			}
		}		
	};
	
	private PressListener pressNormalAtk = new PressListener() {
		@Override
		public void onPressed() {
			setAttackInfo(player.getNormalAttack());
			atkInfoTable.setVisible(true);
		}

		@Override
		public void onRelease() {
			atkInfoTable.setVisible(false);
		}
	};
	
	private PressListener pressSpecialAtkOne = new PressListener() {
		@Override
		public void onPressed() {
			setAttackInfo(player.getSpecialAttack1());
			atkInfoTable.setVisible(true);
		}

		@Override
		public void onRelease() {
			atkInfoTable.setVisible(false);
		}
	};
	
	private PressListener pressSpecialAtkTwo = new PressListener() {
		@Override
		public void onPressed() {
			setAttackInfo(player.getSpecialAttack2());
			atkInfoTable.setVisible(true);
		}

		@Override
		public void onRelease() {
			atkInfoTable.setVisible(false);
		}
	};
}