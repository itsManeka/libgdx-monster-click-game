package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.monsterclickgame.battle.BattleManager;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.customwidgets.LevelBarWidget;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.interfaces.AnimationListener;

public class BattleScreen extends BaseScreen {
	private Stage uiStage;
	private Stage monsterSatge;
	
	private Skin skinUI;
	private Skin skinUI2;
	private Label messageLabel;
	private Label enemyNameLabel;
	private Label playerNameLabel;
	private Button btnRun;
	private Button btnDef;
	private Button btnIten;
	private Button btnAtkEsp1;
	private Button btnAtkEsp2;
	private Button btnAtkNorm;
	private BitmapFont font;
	private TextureAtlas atlasUI;
	private TextureAtlas atlasUI2;
	private AnimatedWidget enemyAnim;
	private AnimatedWidget playerAnim;
	private LevelBarWidget enemyHealtBar;
	private LevelBarWidget playerHealtBar;
	
	private BattleManager manager;
	
	public BattleScreen(MonsterClick game, UserData userData) {
		super(game, userData);
		
		this.manager = new BattleManager(this.userData.getMyMonsters().get(0),
										 this.userData.getMyMonsters().get(1));
		
		this.initStages();
		this.create();
		this.createInput();
	}
	
	@Override
	public void create() {
		setDebug(false);
		
		this.monsterSatge = addStage();
		this.uiStage = addStage();
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPackSpace/KenneyUIPackSpace.pack"));
		this.skinUI = new Skin(atlasUI);
		this.atlasUI2 = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI2 = new Skin(atlasUI2);
		
		//--
		
		this.playerAnim = new AnimatedWidget(this.userData.getMyMonsters().get(0).getAnimation("back"));
		this.playerAnim.setPlayMode(PlayMode.LOOP);
		this.playerAnim.addEventListener(new AnimationListener() {
			@Override
			public void onAnimationFinished() {
				if (playerAnim.getFrame() == "backattack") {
					playerAnim.setAnimation(userData.getMyMonsters().get(0).getAnimation("back"));
					playerAnim.setState(0);
					playerAnim.setPlayMode(PlayMode.LOOP);
				}
			}
		});
		
		this.enemyAnim = new AnimatedWidget(this.userData.getMyMonsters().get(1).getAnimation("stand"));
		this.enemyAnim.setPlayMode(PlayMode.LOOP);
		
		Table monsters = createTable();
		monsters.setFillParent(true);
		monsters.add().expand();
		monsters.row();
		monsters.add().expand();
		monsters.row();
		monsters.background(new Image(new Texture("BGs/Battle.png")).getDrawable());
		monsters.defaults().pad(10);
		monsters.add(this.enemyAnim).right().padRight(20);
		monsters.row();
		monsters.add().expand();
		monsters.row();
		monsters.add(this.playerAnim).left().padLeft(20);
		monsters.row();
		monsters.add().expand();
		monsters.row();
		
		this.monsterSatge.addActor(monsters);
		
		//--
		
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 16;
		fontPar.color = Color.WHITE;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
		
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = this.font;
		lblStyle.background = skinUI.getDrawable("glassPanel");
		this.messageLabel = new Label("Batalha!", lblStyle);
		this.messageLabel.setWrap(true);
		this.messageLabel.setAlignment(Align.center);
		
		ButtonStyle btnAtkStyle = new ButtonStyle();
		btnAtkStyle.up = this.skinUI2.getDrawable("atackOptUp");
		btnAtkStyle.down = this.skinUI2.getDrawable("atackOptDown");
		this.btnAtkNorm = new Button(btnAtkStyle);
		this.btnAtkNorm.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            manager.attack(true, userData.getMyMonsters().get(0).getNormalAttack());
	            messageLabel.setText("Dano: " + manager.getDamange());
	            
	            playerAnim.setFrame("backattack");
	            playerAnim.setAnimation(userData.getMyMonsters().get(0).getAnimation("backattack"));
	            playerAnim.setState(0);
	            playerAnim.setPlayMode(PlayMode.NORMAL);
	        }
		});
		
		ButtonStyle btnDefStyle = new ButtonStyle();
		btnDefStyle.up = this.skinUI2.getDrawable("defOptUp");
		btnDefStyle.down = this.skinUI2.getDrawable("defOptDown");
		this.btnDef = new Button(btnDefStyle);
		this.btnDef.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            
	        }
		});
		
		ButtonStyle btnAtkEsp1Style = new ButtonStyle();
		btnAtkEsp1Style.up = this.skinUI2.getDrawable("atackOptUp");
		btnAtkEsp1Style.down = this.skinUI2.getDrawable("atackOptDown");
		this.btnAtkEsp1 = new Button(btnAtkEsp1Style);
		this.btnAtkEsp1.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            
	        }
		});
		
		ButtonStyle btnAtkEsp2Style = new ButtonStyle();
		btnAtkEsp2Style.up = this.skinUI2.getDrawable("atackOptUp");
		btnAtkEsp2Style.down = this.skinUI2.getDrawable("atackOptDown");
		this.btnAtkEsp2 = new Button(btnAtkEsp2Style);
		this.btnAtkEsp2.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            
	        }
		});
		
		ButtonStyle btnItenStyle = new ButtonStyle();
		btnItenStyle.up = this.skinUI2.getDrawable("defOptUp");
		btnItenStyle.down = this.skinUI2.getDrawable("defOptDown");
		this.btnIten = new Button(btnItenStyle);
		this.btnIten.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            
	        }
		});
		
		TextButtonStyle btnRunStyle = new TextButtonStyle();
		btnRunStyle.font = this.font;
		btnRunStyle.up = this.skinUI2.getDrawable("blue_button07");
		btnRunStyle.down = this.skinUI2.getDrawable("blue_button08");
		this.btnRun = new TextButton("Fugir", btnRunStyle);
		this.btnRun.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	game.setGameState(new StartMenu(game, userData));
            }
        });
		
		LabelStyle lblStyleName = new LabelStyle();
		lblStyleName.font = this.font;
		this.enemyNameLabel = new Label(this.userData.getMyMonsters().get(1).getName(), lblStyleName);
		this.enemyNameLabel.setAlignment(Align.left);
		this.playerNameLabel = new Label(userData.getMyMonsters().get(0).getName(), lblStyleName);
		this.playerNameLabel.setAlignment(Align.right);
		
		this.enemyHealtBar = new LevelBarWidget(skinUI2.getRegion("red_button02"),
				  skinUI2.getRegion("green_button05"));
		this.playerHealtBar = new LevelBarWidget(skinUI2.getRegion("red_button02"),
				  skinUI2.getRegion("green_button05"));
		
		Table container = createTable();
		container.setFillParent(true);
		container.defaults().pad(2);
		container.add(this.messageLabel).size(340, 70).top().padTop(10).colspan(5);
		container.row();
		container.add(this.enemyHealtBar).top().left().padLeft(10).height(10).width(130).colspan(5);
		container.row();
		container.add(this.enemyNameLabel).top().left().padLeft(10).colspan(5);
		container.row();
		container.add().expand().colspan(5);
		container.row();
		container.add(this.btnAtkEsp1).padLeft(10).left();
		container.add().expandX().colspan(3);
		container.add(this.playerNameLabel).bottom().right().padRight(10);
		container.row();
		container.add(this.playerHealtBar).bottom().right().padRight(10).height(10).width(130).colspan(5);
		container.row();
		container.add(this.btnAtkNorm).padLeft(5).size(70);
		container.add(this.btnAtkEsp2).bottom().left().padLeft(20);
		container.add(this.btnRun).center().bottom().size(50, 30).expandX();
		container.add(this.btnIten).padRight(5).right().bottom();
		container.add(this.btnDef).padRight(10).size(70);
		
		this.uiStage.addActor(container);
	}
}
