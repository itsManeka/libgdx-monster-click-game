package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.monsters.MyMonster;

public class WinScreen extends BaseScreen {
	private Stage stage;
	
	private MyMonster drop;
	private MyMonster enemy;
	private MyMonster player;
	
	private HistoryBattle battle;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private Label winLbl;
	private Label eggDrop;
	private TextButton okBtn;
	private BitmapFont font;
	private BitmapFont winFont;
	private AnimatedWidget animation;
	private AnimatedWidget eggDropAnimation;
	
	public WinScreen(MonsterClick game, UserData userData, MyMonster player, HistoryBattle battle) {
		super(game, userData);
		
		this.battle = battle;
		this.enemy = battle.getEnemy();
		this.player = player;
				
		init();
		
		initStages();
		create();
		createInput();
	}
	
	private void init() {
		animation = new AnimatedWidget(player.getAnimation("frontattack"));
		animation.setPlayMode(PlayMode.LOOP);
		animation.setScale(2);
		
		eggDropAnimation = new AnimatedWidget();
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
	}
	
	@Override
	public void create() {
		setDebug(false);
		
		stage = addStage();
		
		createWinFont();
		createNormalFont();
		
		createWinTable();
	}
	
	private void checkDrop() {
		int pctDrop;
		int randomDrop;
		
		pctDrop = enemy.getPctDrop();
		randomDrop = MathUtils.random(1, 100);
		
		if (randomDrop <= pctDrop) {
			drop = enemy.dropEgg();
			
			userData.addMonster(drop);
			
			eggDropAnimation.setAnimation(drop.getAnimation("stand"));
			eggDropAnimation.setPlayMode(PlayMode.LOOP);
			eggDropAnimation.setState(0);
		} else {
			drop = null;
		}
	}
	
	private void createWinTable() {
		Table table = createTable();
		table.setFillParent(true);
		table.defaults().pad(5);
		
		winLbl = createLabel("Vitória", winFont);
		okBtn = createTextButton("Ok", font, skinUI.getDrawable("green_button07"), skinUI.getDrawable("green_button08"));
		okBtn.addListener(okListener);
		
		checkDrop();
		
		table.add(winLbl).expand().top().padTop(10).colspan(2);
		table.row();
		table.add(animation).expand().center().padRight(40).colspan(2);
		table.row();
		
		if (drop != null) {
			eggDrop = createLabel("Ovo de " + enemy.getOriginalName() + " encontrado.", font);
			eggDrop.setWrap(true);
			eggDrop.setAlignment(Align.left);
			
			table.add(eggDropAnimation).expandX().size(40).center().right().padLeft(20);
			table.add(eggDrop).left().expandX().size(150, 40).bottom().left().padRight(20);
		} else {
			eggDrop = createLabel("Nenhum ovo encontrado.", font);
			eggDrop.setWrap(true);
			eggDrop.setAlignment(Align.center);
			
			table.add(eggDrop).size(190, 40).colspan(2);
		}
		
		table.row();
		table.add().expand().colspan(2);
		table.row();
		table.add(okBtn).expand().top().colspan(2);
		
		this.stage.addActor(table);
	}
	
	@Override
	protected void prepareBG() {
		Gdx.gl.glClearColor(.3f, .85f, .3f, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	private void createWinFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 45;
		fontPar.color = Color.GREEN;
		fontPar.borderWidth = 3;
		fontPar.borderColor = Color.FOREST;
		this.winFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createNormalFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 15;
		fontPar.color = Color.WHITE;
		fontPar.borderWidth = 1;
		fontPar.borderColor = Color.BLACK;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private Label createLabel(String text, BitmapFont font) {
		return createLabel(text, font, null);
	}
	
	private Label createLabel(String text, BitmapFont font, Drawable bg) {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = font;
		lblStyle.background = bg;
		Label label = new Label(text, lblStyle);
		
		return label;
	}
	
	private TextButton createTextButton(String text, BitmapFont font, Drawable up, Drawable down) {
		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = font;
		btnStyle.up = up;
		btnStyle.down = down;
		TextButton button = new TextButton(text, btnStyle);
		return button;
	}
	
	private ChangeListener okListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			game.setGameState(new SelectHistoryBattle(game, userData, battle.getHistory()));			
		}
	};
}
