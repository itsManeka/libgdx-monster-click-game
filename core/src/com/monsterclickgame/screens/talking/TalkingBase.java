package com.monsterclickgame.screens.talking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.customwidgets.TalkLabel;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.interfaces.TalkLabelListener;

public class TalkingBase extends BaseScreen{
	private Stage stage;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private Button nextButton;
	private TalkLabel text;
	private BitmapFont messageFont;
	
	protected Array<String> texts;
	protected Table table;

	public TalkingBase(MonsterClick game, UserData userData) {
		super(game, userData);
		
		create();
	}
	
	private void init() {
		texts = new Array<String>();
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
		
		initElements();
		initialTexts();
	}
	
	protected void initElements(){}
	protected void initialTexts(){}
	protected void talkFinished(){}
	protected void nextButtonClicked(){}
	
	private void createMessageFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 18;
		fontPar.color = Color.BLACK;
		this.messageFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	@Override
	public void create() {
		init();
		
		initStages();
		createElements();
		createInput();
	}
	
	protected void createElements(){
		setDebug(false);
		
		createMessageFont();
		
		stage = addStage();
		
		createTalkLabel();
		createNextButton();
		
		table = createTable();
		table.setFillParent(true);
		table.defaults().pad(10);
		
		addElements();
		
		table.add(nextButton).expandX().right().bottom().padBottom(5);
		table.row();
		table.add(text).expandX().bottom().left().size(340, 140).padTop(0);
		
		stage.addActor(table);
	}
	
	protected void addElements() {
		
	}
	
	private void createTalkLabel() {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = messageFont;
		lblStyle.background = skinUI.getDrawable("grey_button05");
		
		if (texts.size > 0) {
			text = new TalkLabel(texts.pop(), lblStyle);
		}
		
		text.setAlignment(Align.center);
		text.setWrap(true);
		text.addEventListener(new TalkLabelListener() {
			@Override
			public void onTextFinished() {
				nextButton.setVisible(true);
				talkFinished();
			}
		});
	}
	
	private void createNextButton() {
		ButtonStyle btnStyle = new ButtonStyle();
		btnStyle.down = skinUI.getDrawable("nextDown");
		btnStyle.up = skinUI.getDrawable("nextUp");
		nextButton = new Button(btnStyle);
		nextButton.setVisible(false);
		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nextButton.setVisible(false);
				nextButtonClicked();
			}
		});
	}
	
	protected boolean nextText() {
		if (texts.size > 0) {
			text.newText(texts.pop());
			return true;
		}
		
		return false;
	}
}
