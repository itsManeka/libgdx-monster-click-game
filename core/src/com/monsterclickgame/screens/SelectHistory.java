package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.monsterclickgame.customwidgets.HistoryButton;
import com.monsterclickgame.customwidgets.PagedScrollPane;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.history.History;
import com.monsterclickgame.history.HistoryManager;

public class SelectHistory extends BaseScreen {
	private Stage stage;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private Label titleLabel;
	private Button backButton;
	private BitmapFont font;
	private BitmapFont titleFont;
	private PagedScrollPane scroll;
	
	public SelectHistory(MonsterClick game, UserData userData) {
		super(game, userData);
		
		init();
		
		initStages();
		create();
		createInput();
	}
	
	private void init() {
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
	}
	
	@Override
	public void create() {
		setDebug(false);
		
		this.stage = addStage();
		
		createTitleFont();
		createHistoryFont();
        
		createScroll();
        
        this.backButton = createButton("", font, 
				skinUI.getDrawable("backbuttonUp"), 
				skinUI.getDrawable("backButtonDown"));
        this.backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	back();
            }
        });
        
        this.titleLabel = createTitleLabel("Histórias");
		
        Table container = createTable();
        container.setFillParent(true);
        container.add(this.titleLabel).padTop(10);
        container.row();
        container.add(this.scroll).expand().fill().pad(0, 40, 0, 40);
        container.row();
        container.add(this.backButton).expandX().left();
        
        stage.addActor(container);
	}
	
	private void createScroll() {
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
        this.scroll = new PagedScrollPane(null, scrollStyle);
		this.scroll.setScrollingDisabled(false, true);
		this.scroll.setFlingTime(0.1f);
		this.scroll.setPageSpacing(30);
		
		HistoryManager manager = new HistoryManager(game, userData);
		
		int pages = manager.size();
        for (int p = 0; p < pages; p ++) {
        	Table historyTable = new Table();
        	
        	History history = manager.getNext();
        	Drawable bg = new Image(new Texture("BGs/History/" + history.getHistoryBgName() + ".png")).getDrawable();
        	
        	HistoryButton historyButton;
        	historyButton = createHistoryButton(history, this.font, bg, bg);
        	
        	historyTable.add(historyButton).size(270, 500);
        	
        	this.scroll.addPage(historyTable);
        }
	}
	
	private void createHistoryFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 40;
		fontPar.borderWidth = 2;
		fontPar.color = Color.WHITE;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private Button createButton(String text, BitmapFont font, Drawable up, Drawable down) {
		TextButtonStyle style = new TextButtonStyle();
		style.font = font;
		style.up = up;
		style.down = down;
		style.over = up;
        style.pressedOffsetX = 1;
        style.pressedOffsetY = -1;
        Button button = new TextButton(text, style);
        
        return button;
	}
	
	private HistoryButton createHistoryButton(History history, BitmapFont font, Drawable up, Drawable down) {
		TextButtonStyle style = new TextButtonStyle();
		style.font = font;
		style.up = up;
		style.down = down;
		style.over = up;
        HistoryButton button = new HistoryButton(history.getHistoryName(), style, history);
        button.getLabel().setAlignment(Align.top);
        button.getLabel().setWrap(true);
        button.addListener(historyClickListener);
        
        return button;
	}
	
	public ChangeListener historyClickListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			 game.setGameState(new SelectHistoryBattle(game, userData, 
	            		((HistoryButton) event.getListenerActor()).getHistory()));
		}
	};
	
	private void back() {
		this.game.setGameState(new StartMenu(game, userData));
	}
	
	private void createTitleFont() {
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
	}
	
	private Label createTitleLabel(String text) {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = this.titleFont;
		Label lblTitle = new Label(text, lblStyle);
		
		return lblTitle;
	}
}