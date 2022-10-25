package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.monsterclickgame.customwidgets.MonsterButton;
import com.monsterclickgame.customwidgets.PagedScrollPane;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.history.battles.HistoryBattle;
import com.monsterclickgame.monsters.MyMonster;

public class SelectMonsterScreen extends BaseScreen {
	private Stage stage;
	
	private HistoryBattle battle;
	
	private int index;
	
	private Skin skinUI;
	private Label titleLabel;
	private Button backButton;
	private BitmapFont font;
	private BitmapFont titleFont;
	private TextureAtlas atlasUI;
	
	private PagedScrollPane scroll;
	
	public SelectMonsterScreen(MonsterClick game, UserData userData, HistoryBattle battle) {
		super(game, userData);
		
		this.battle = battle;
		
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
        
        this.titleLabel = createTitleLabel("Meus monstros");
		
        Table container = createTable();
        container.setFillParent(true);
        container.add(this.titleLabel).padTop(10);
        container.row();
        container.add(this.scroll).expand().fill().pad(0, 10, 0, 10);
        container.row();
        container.add(this.backButton).expandX().left();
        
        stage.addActor(container);
	}
	
	public void createScroll () {
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
        scrollStyle.background = new Image(new Texture("BGs/Others/Pane.png")).getDrawable();
		this.scroll = new PagedScrollPane(null, scrollStyle);
		this.scroll.setScrollingDisabled(false, true);
		this.scroll.setFlingTime(0.1f);
		this.scroll.setPageSpacing(20);
        
        this.index = 0;
        int pages = ((int)(userData.getNumberMonsters() / 6)) + 1;
        for (int p = 0; p < pages; p ++) {
        	Table monsters = new Table();
        	monsters.defaults().pad(10, 35, 10, 35);
        	for (int linha = 0; linha < 3; linha ++) {
        		monsters.row();
        		for (int coluna = 0; coluna < 2; coluna ++) {
        			this.index++;
        			if (userData.getMyMonsters().size >= index) {
        				MyMonster monster;
        				monster = getMonster();
        				if (monster != null) {
        					monsters.add(getMonsterButton(monster)).size(100, 130);
        				} else {
        					monsters.add().size(100, 130);
        				}
        			} else {
        				monsters.add().size(100, 130);
        			}
        		}
        	}
        	this.scroll.addPage(monsters);
        }
	}
	
	private MyMonster getMonster() {
		if (userData.getMyMonsters().get(index - 1).isEgg()) {
			index ++;
			if (userData.getMyMonsters().size >= index) {
				return getMonster();
			} else {
				return (MyMonster) null;
			}
		} else {
			return userData.getMyMonsters().get(index - 1);
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
	
	private MonsterButton getMonsterButton (MyMonster monster) {
		ButtonStyle style = new ButtonStyle();
		style.up = style.down = null;
		MonsterButton button = new MonsterButton(style, monster);
		button.bottom();
		button.stack(new Image((TextureRegion) monster.getAnimation("stand").getKeyFrame(0)));
		
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = this.font;
		lblStyle.background = skinUI.getDrawable("blue_button03");
		Label lblName = new Label(monster.getName(), lblStyle);
		lblName.setAlignment(Align.center);
		
		button.row();
		button.add(lblName).width(150).height(20).padTop(10);
		
		button.addListener(monsterClickListener);
		
		return button;
	}
	
	private ClickListener monsterClickListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
            game.setGameState(new HistoryBattleScreen(game, userData, 
            		((MonsterButton) event.getListenerActor()).getMonster(), battle));
        }
	};
	
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
	
	private void back() {
		this.game.setGameState(new SelectHistoryBattle(game, userData, battle.getHistory()));
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
