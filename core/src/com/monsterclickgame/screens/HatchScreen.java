package com.monsterclickgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.customwidgets.TalkLabel;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.interfaces.AnimationListener;
import com.monsterclickgame.interfaces.TalkLabelListener;
import com.monsterclickgame.monsters.MyMonster;

public class HatchScreen extends BaseScreen{
	private boolean isHatched;
	
	private Stage stage;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private Button nextButton;
	private TalkLabel message;
	private MyMonster monster;
	private BitmapFont font;
	private AnimatedWidget animation;
	
	private Array<String> texts;
	
	public HatchScreen(MonsterClick game, UserData userData, MyMonster monster) {
		super(game, userData);
		
		this.monster = monster;
		
		init();
		
		initStages();
		create();
		createInput();
	}
	
	private void init() {
		this.texts = new Array<String>();
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
	}
	
	private void createInitialTexts() {
		this.texts.add("Ãh?");
		this.texts.add("Parece que seu ovo está chocando...");
		
		this.texts.reverse();
	}
	
	private void createHatchedTexts() {
		this.texts.add("Parabéns!");
		this.texts.add("Você chocou um " + monster.getName() + ".");
		
		this.texts.reverse();
	}
	
	@Override
	public void create() {
		setDebug(false);
		
		stage = addStage();
		
		createFont();
		createInitialTexts();
		
		this.animation = new AnimatedWidget(monster.getAnimation("stand"));
		this.animation.setFrame("stand");
		this.animation.setPlayMode(PlayMode.LOOP);
		this.animation.addEventListener(new AnimationListener() {
			@Override
			public void onAnimationFinished() {
				animationFinished();
			}
		});
		
		message = createMessage(texts.pop(), skinUI.getDrawable("grey_button05"));
		message.setAlignment(Align.center);
		message.setWrap(true);
		message.addEventListener(new TalkLabelListener() {
			@Override
			public void onTextFinished() {
				textFinished();
			}
		});
		
		nextButton = createButton(skinUI.getDrawable("nextUp"), skinUI.getDrawable("nextDown"));
		nextButton.setVisible(false);
		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				next();
			}
		});
		
		Table table = createTable();
		table.setFillParent(true);
		table.background(new Image(new Texture("BGs/Others/Hatch.png")).getDrawable());
		table.defaults().pad(10);
		table.add(animation).expandY();
		table.row();
		table.add(nextButton).expandX().right().bottom().padBottom(5);
		table.row();
		table.add(message).expandX().bottom().left().size(340, 140).padTop(0);
		
		stage.addActor(table);
	}
	
	private void createFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 18;
		fontPar.color = Color.BLACK;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private Button createButton(Drawable up, Drawable down) {
		ButtonStyle btnStyle = new ButtonStyle();
		btnStyle.down = down;
		btnStyle.up = up;
		Button button = new Button(btnStyle);
		return button;
	}
	
	private TalkLabel createMessage(String message, Drawable bg) {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = font;
		lblStyle.background = bg;
		TalkLabel talkLabel = new TalkLabel(message, lblStyle);
		return talkLabel;
	}
	
	private void next() {
		nextButton.setVisible(false);
		if (texts.size > 0) {
			message.newText(texts.pop());
		} else {
			if (!isHatched) {
				message.setText("");
				animation.setFrame("hatch");
				animation.setAnimation(monster.getAnimation("hatch"));
				animation.setState(0);
				animation.setPlayMode(PlayMode.NORMAL);
			} else {
				game.setGameState(new MonsterView(game, userData, monster));
			}
		}
	}
	
	private void textFinished() {
		nextButton.setVisible(true);
	}
	
	private void animationFinished() {
		if (animation.getFrame() == "hatch") {
			monster.hatch();
			
			animation.setFrame("stand");
			animation.setAnimation(monster.getAnimation("stand"));
			animation.setState(0);
			animation.setPlayMode(PlayMode.LOOP);
			
			isHatched = true;
			createHatchedTexts();
			nextButton.setVisible(true);
		}
	}
}
