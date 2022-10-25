package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.screens.talking.NoMonsters;

public class StartMenu extends BaseScreen {
	private Stage stage;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private Image logo;
	private Label battleLbl;
	private Label historyLbl;
	private Label bestiaryLbl;
	private Label myMonsterLbl;
	private Label helpLbl;
	private Label storeLbl;
	private Button battleBtn;
	private Button historyBtn;
	private Button bestiaryBtn;
	private Button myMonsterBtn;
	private Button helpBtn;
	private Button storeBtn;
	private BitmapFont font;
	
	public StartMenu(MonsterClick game, UserData userData) {
		super(game, userData);
		
		init();
		
		this.initStages();
		this.create();
		this.createInput();
	}
	
	private void init() {
		this.logo = new Image(new Texture("Misc/logo.png"));
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
	}

	@Override
	public void create() {
		setDebug(false);
		
		createFont();
		
		this.stage = addStage();
		
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = font;
		this.battleLbl = new Label("Batalha", lblStyle);
		this.battleLbl.setAlignment(Align.center);
		this.historyLbl = new Label("História", lblStyle);
		this.historyLbl.setAlignment(Align.center);
		this.bestiaryLbl = new Label("Bestiário", lblStyle);
		this.bestiaryLbl.setAlignment(Align.center);
		this.myMonsterLbl = new Label("Monstros", lblStyle);
		this.myMonsterLbl.setAlignment(Align.center);
		this.storeLbl = new Label("Loja", lblStyle);
		this.storeLbl.setAlignment(Align.center);
		this.helpLbl = new Label("Ajuda", lblStyle);
		this.helpLbl.setAlignment(Align.center);
		
		this.battleBtn = createTextButton(font, skinUI.getDrawable("batleButtonUp"), skinUI.getDrawable("batleButtonDown"));
        this.battleBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	
            }
        });
        this.battleBtn.row();
        this.battleBtn.add(battleLbl).center().expandY().padTop(110);
        
        this.historyBtn = createTextButton(font, skinUI.getDrawable("historyButtonUp"), skinUI.getDrawable("historyButtonDown"));
        this.historyBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	if (userData.getNumberMonsters() > 0) {
            		game.setGameState(new SelectHistory(game, userData));
            	} else {
            		game.setGameState(new NoMonsters(game, userData));
            	}
            }
        });
        this.historyBtn.row();
        this.historyBtn.add(historyLbl).center().expandY().padTop(110);
        
        this.bestiaryBtn = createTextButton(font, skinUI.getDrawable("bestiaryButtonUp"), skinUI.getDrawable("bestiaryButtonDown"));
        this.bestiaryBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	
            }
        });
        this.bestiaryBtn.row();
        this.bestiaryBtn.add(bestiaryLbl).center().expandY().padTop(110);
        
        this.myMonsterBtn = createTextButton(font, skinUI.getDrawable("myMonstersButtonUp"), skinUI.getDrawable("myMonstersButtonDown"));
        this.myMonsterBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	game.setGameState(new MyMonstersView(game, userData));
            }
        });
        this.myMonsterBtn.row();
        this.myMonsterBtn.add(myMonsterLbl).center().expandY().padTop(110);
        
        this.storeBtn = createTextButton(font, skinUI.getDrawable("storeButtonUp"), skinUI.getDrawable("storeButtonDown"));
        this.storeBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	
            }
        });
        this.storeBtn.row();
        this.storeBtn.add(storeLbl).center().expandY().padTop(110);
        
        this.helpBtn = createTextButton(font, skinUI.getDrawable("helpButtonUp"), skinUI.getDrawable("helpButtonDown"));
        this.helpBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	
            }
        });
        this.helpBtn.row();
        this.helpBtn.add(helpLbl).center().expandY().padTop(110);
		
		Table table = createTable();
		table.defaults().pad(10f, 5f, 5f, 5f);
		table.setFillParent(true);
		table.top();
		table.add(this.logo).colspan(4).fillX().pad(20f, 20f, 50f, 20f);
		table.row();
		table.add(this.historyBtn).expandX().size(80).right();
		table.add(this.myMonsterBtn).expandX().size(80).left();
		table.row();
		table.add(this.battleBtn).expandX().size(80).right();
		table.add(this.bestiaryBtn).expandX().size(80).left();
		table.row();
		table.add(this.storeBtn).expandX().size(80).right();
		table.add(this.helpBtn).expandX().size(80).left();
		
		stage.addActor(table);
	}
	
	private void createFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 12;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private TextButton createTextButton(BitmapFont font, Drawable up, Drawable down) {
		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = font;
		btnStyle.up = up;
		btnStyle.down = down;
		btnStyle.over = up;
		btnStyle.pressedOffsetX = 1;
		btnStyle.pressedOffsetY = -1;
        TextButton button = new TextButton("", btnStyle);
        return button;
	}
}
