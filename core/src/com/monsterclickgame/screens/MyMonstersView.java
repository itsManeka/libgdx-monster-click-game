package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.monsterclickgame.configs.Config;
import com.monsterclickgame.customwidgets.MonsterButton;
import com.monsterclickgame.customwidgets.PagedScrollPane;
import com.monsterclickgame.customwidgets.UserBar;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.GameState;
import com.monsterclickgame.monsters.MyMonster;

public class MyMonstersView extends GameState {
	private boolean debug = false;
	
	private Skin skinUI;
	private Stage stage;
	private Button backBtn;
	private UserBar userBar;
	private BitmapFont font;
	private BitmapFont titleFont;
	private TextureAtlas atlasUI;
	private PagedScrollPane scroll;
	
	public MyMonstersView(MonsterClick game, UserData userData) {
		super(game, userData);
		create();
	}
	
	@Override
	public void create() {
		setDebug(false);
		
		this.stage = new Stage(new StretchViewport(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
		
		InputMultiplexer i = new InputMultiplexer();
		i.addProcessor(stage);
		Gdx.input.setInputProcessor(stage);
		
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 18;
		fontPar.color = Color.WHITE;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
		
		FreeTypeFontGenerator titleFontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter titleFontPar = new FreeTypeFontParameter();
		titleFontPar.size = 30;
		titleFontPar.color = Color.GREEN;
		titleFontPar.borderWidth = 3;
		titleFontPar.borderColor = new Color(0, .37f, 0, 1);
		titleFontPar.shadowOffsetY = 3;
		titleFontPar.shadowColor = new Color(0, .37f, 0, 1);
		this.titleFont = titleFontGen.generateFont(titleFontPar);
		titleFontGen.dispose();
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
		
		userBar = new UserBar(skinUI, userData);
		
		Table container = createTable();
        container.setFillParent(true);
        this.stage.addActor(userBar);
        this.stage.addActor(container);
		
		TextButtonStyle btnBackStyle = new TextButtonStyle();
        btnBackStyle.font = font;
        btnBackStyle.up = skinUI.getDrawable("backbuttonUp");
        btnBackStyle.down = skinUI.getDrawable("backButtonDown");
        btnBackStyle.over = skinUI.getDrawable("backbuttonUp");
        btnBackStyle.pressedOffsetX = 1;
        btnBackStyle.pressedOffsetY = -1;
        this.backBtn = new TextButton("", btnBackStyle);
        this.backBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	game.setGameState(new StartMenu(game, userData));
            }
        });
        
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = this.titleFont;
		Label lblTitle = new Label("Meus Monstros", lblStyle);
		
        ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
        scrollStyle.background = new Image(new Texture("BGs/Others/Pane.png")).getDrawable();
		this.scroll = new PagedScrollPane(null, scrollStyle);
		this.scroll.setScrollingDisabled(false, true);
		this.scroll.setFlingTime(0.1f);
		this.scroll.setPageSpacing(20);
        
        int index = 0;
        int pages = ((int)(userData.getMyMonsters().size / 6)) + 1;
        
        for (int p = 0; p < pages; p ++) {
        	Table monsters = new Table();
        	monsters.defaults().pad(10, 35, 10, 35);
        	for (int linha = 0; linha < 3; linha ++) {
        		monsters.row();
        		for (int coluna = 0; coluna < 2; coluna ++) {
        			index++;
        			if (userData.getMyMonsters().size >= index) {
        				monsters.add(getMonsterButton(userData.getMyMonsters().get(index - 1))).size(100, 130);
        			} else {
        				monsters.add().size(100, 130);
        			}
        		}
        	}
        	this.scroll.addPage(monsters);
        }
        
        container.add(lblTitle).top().padTop(30).padBottom(5);
        container.row();
        container.add(this.scroll).expand().fill().pad(10);
        container.row();
        container.add(this.backBtn).expandX().left();
	}
	
	protected Table createTable() {
		Table table = new Table();
		if (debug) table.debug();
		return table;
	}

	private void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public Button getMonsterButton (MyMonster monster) {
		ButtonStyle style = new ButtonStyle();
		style.up = style.down = null;
		Button button = new MonsterButton(style, monster);
		button.bottom();
		button.stack(new Image((TextureRegion) monster.getAnimation("stand").getKeyFrame(0)));
		
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = this.font;
		lblStyle.background = skinUI.getDrawable("blue_button03");
		Label lblName = new Label(monster.getName(), lblStyle);
		lblName.setAlignment(Align.center);
		
		button.row();
		button.add(lblName).width(150).height(20).padTop(10);
		
		button.addListener(levelClickListener);
		
		return button;
	}
	
	public ClickListener levelClickListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
            game.setGameState(new MonsterView(game, userData, 
            		((MonsterButton) event.getListenerActor()).getMonster()));
        }
	};

	@Override
	public void draw() {
		prepareBG();
		this.stage.draw();
	}
	
	protected void prepareBG() {
		Gdx.gl.glClearColor(.3f, .3f, .6f, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void update(float delta) {
		this.stage.act(Math.min(delta, 1 / 30f));
		userBar.update();
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}
}
