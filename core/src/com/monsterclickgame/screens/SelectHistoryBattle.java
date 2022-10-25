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
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.customwidgets.BattleButton;
import com.monsterclickgame.customwidgets.PagedScrollPane;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.history.History;
import com.monsterclickgame.history.History.Type;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.screens.talking.TalkingBase;

public class SelectHistoryBattle extends BaseScreen {
	private Stage stage;
	
	private History history;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private Label titleLabel;
	private Button backButton;
	private BitmapFont font;
	private BitmapFont titleFont;
	private PagedScrollPane scroll;
	
	public SelectHistoryBattle(MonsterClick game, UserData userData, History history) {
		super(game, userData);
		
		this.history = history;
		
		init();
		
		initStages();
		create();
		createInput();
	}
	
	public void init() {
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
	}

	@Override
	public void create() {
		setDebug(false);
		
		stage = addStage();
		
		createFont();
		createTitleFont();
		
		createScroll();
        
        this.backButton = createButton("", font, 
				skinUI.getDrawable("backbuttonUp"), 
				skinUI.getDrawable("backButtonDown"));
        this.backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	back();
            }
        });
        
        this.titleLabel = createTitleLabel(history.getHistoryName());
		
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
		scrollStyle.background = new Image(new Texture("BGs/Others/Pane.png")).getDrawable();;
        this.scroll = new PagedScrollPane(null, scrollStyle);
		this.scroll.setScrollingDisabled(false, true);
		this.scroll.setFlingTime(0.1f);
		this.scroll.setPageSpacing(30);
		
		int index = 0;
		int pages = ((int)(history.getBattlesSize() / 4)) + 1;
		for (int p = 0; p < pages; p ++) {
        	Table historyTable = new Table();
        	historyTable.defaults().pad(10, 35, 10, 35);
        	
        	for (int b = 0; b < 4; b ++) {
        		index ++;
        		Table battleTable = new Table();
        		
        		historyTable.row();	
        		if (history.getBattlesSize() >= index) {
            		int battle = index - 1;
        			
            		if (history.getType(battle) == Type.Battle) {
		        		AnimatedWidget image = new AnimatedWidget(history.getBattle(battle).getEnemy().getAnimation("icon"));
		        		image.setPlayMode(PlayMode.LOOP);
		        		Label nameLabel = createLabel(" " + history.getBattle(battle).getEnemy().getName(), skinUI.getDrawable("red_button02"));
		        		Label levellabel = createLabel(" Nvl: " + history.getBattle(battle).getEnemy().getLevel(), skinUI.getDrawable("red_button02"));
		        		Label batleLabel = createLabel(" " + history.getBattle(battle).getBattleName(), history.getBattle(battle).getTerrain().getTerrainDrawable());
		        		Image weterLabel = new Image(skinUI.getDrawable("red_button02"));
		        		BattleButton playButton = createBattleButton(history.getBattle(battle), this.font, skinUI.getDrawable("playbuttonup"), skinUI.getDrawable("playbuttondown"));
		        		
		        		battleTable.add(image).size(30);
		        		battleTable.add(nameLabel).size(120, 30);
		        		battleTable.add(levellabel).size(60, 30);
		        		battleTable.row();
		        		battleTable.add(batleLabel).colspan(3).size(210, 50);
		        		battleTable.row();
		        		battleTable.add(weterLabel).colspan(2).size(150, 30);
		        		battleTable.add(playButton).size(60, 30);
		        		battleTable.row();
            		} else {
            			BattleButton talkingButton = createBattleButton(history.getTalking(battle), this.font, skinUI.getDrawable("playbuttonup"), skinUI.getDrawable("playbuttondown"));
            			
            			battleTable.add(talkingButton).size(210, 110);
            			battleTable.row();
            		}
        		} else {
        			battleTable.add().size(210, 110);
        			battleTable.row();
        		}
        		
        		historyTable.add(battleTable).expand();
        	}
        	
        	this.scroll.addPage(historyTable);        	
        }
	}
	
	private void createFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 18;
		fontPar.color = Color.WHITE;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void back() {
		this.game.setGameState(new SelectHistory(game, userData));
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
	
	private Label createLabel(String text, Drawable bg) {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = this.font;
		lblStyle.background = bg;
		Label lblTitle = new Label(text, lblStyle);
		
		return lblTitle;
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
	
	private BattleButton createBattleButton(HistoryBattle battle, BitmapFont font, Drawable up, Drawable down) {
		TextButtonStyle style = new TextButtonStyle();
		style.up = up;
		style.down = down;
		style.over = up;
		style.font = font;
        BattleButton button = new BattleButton("", style, battle);
        button.addListener(battleClickListener);
        
        return button;
	}
	
	private BattleButton createBattleButton(TalkingBase talking, BitmapFont font, Drawable up, Drawable down) {
		TextButtonStyle style = new TextButtonStyle();
		style.up = up;
		style.down = down;
		style.over = up;
		style.font = font;
        BattleButton button = new BattleButton("", style, talking);
        button.addListener(talkingClickListener);
        
        return button;
	}
	
	public ChangeListener battleClickListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			game.setGameState(new SelectMonsterScreen(game, userData, 
	            		((BattleButton) event.getListenerActor()).getBattle()));
		}
	};
	
	public ChangeListener talkingClickListener = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			game.setGameState(((BattleButton) event.getListenerActor()).getTalking(), true);
		};
	};
}
