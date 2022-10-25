
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.monsterclickgame.battle.TypeManager;
import com.monsterclickgame.customwidgets.AnimatedWidget;
import com.monsterclickgame.customwidgets.LevelBarWidget;
import com.monsterclickgame.customwidgets.PagedScrollPane;
import com.monsterclickgame.customwidgets.UserBar;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;
import com.monsterclickgame.generic.BaseScreen;
import com.monsterclickgame.interfaces.AnimationListener;
import com.monsterclickgame.monsters.MyMonster;

public class MonsterView extends BaseScreen {
	private Stage stageBG;
	private Stage stageMonster;
	private Stage stageAction;
	private Stage stageUserBar;
	private Stage stageStatus;
	
	private Skin skinUI;
	private TextureAtlas atlasUI;
	
	private Image bg;
	private Label clicks;
	private Label maxClicks;
	private Label levelLabel;
	private Button editName;
	private Button backBtn;
	private Button clickBtn;
	private TextField nameLabel;
	private BitmapFont font;
	private BitmapFont typeFont;
	private BitmapFont statusFont;
	private LevelBarWidget levelBar;
	private AnimatedWidget animatedWidget;
	private PagedScrollPane statusPane;
	
	private Label hp;
	private Label sp;
	private Label rp;
	private Label atk;
	private Label def;
	private Label spd;
	private Label pct;
	
	private UserBar userBar;
	
	private MyMonster monster;
	
	public MonsterView(MonsterClick game, UserData userData, MyMonster monster) {
		super(game, userData);
		
		this.monster = monster;
		
		this.init();
		
		this.initStages();
		this.create();
		this.createInput();
	}
	
	private void init() {
		createFont();
		createTypeFont();
		createStatusFont();
		
		String bg;
		if (monster.isEgg()) {
			bg = TypeManager.Type.Normal.toString();
		} else {
			bg = this.monster.getType().toString();
		}
		this.bg = new Image(new Texture("BGs/View/" + bg + ".png"));
		
		this.animatedWidget = new AnimatedWidget(monster.getAnimation("stand"));
		this.animatedWidget.setPlayMode(PlayMode.LOOP);
		this.animatedWidget.addEventListener(animationListener);
		
		this.atlasUI = new TextureAtlas(Gdx.files.internal("UI/KenneyUIPack/KenneyUIPack.pack"));
		this.skinUI = new Skin(atlasUI);
		
		this.levelBar = new LevelBarWidget(skinUI.getRegion("blue_button05"),
				  skinUI.getRegion("green_button05"));
	}

	@Override
	public void create() {
		setDebug(false);
		
		this.stageBG = addStage();
		this.stageMonster = addStage();
		this.stageAction = addStage();
		this.stageUserBar = addStage();
		this.stageStatus = addStage();
		
		createBGTable();
		createMonsterTable();
		createActionTable();
		createUserBarTable();
		
		if (!monster.isEgg()) createStatusTable();
	}
	
	private void createFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 18;
		fontPar.color = Color.WHITE;
		this.font = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createStatusFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 15;
		fontPar.color = Color.WHITE;
		fontPar.borderWidth = 2;
		this.statusFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createTypeFont() {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kenpixel_mini.ttf"));
		FreeTypeFontParameter fontPar = new FreeTypeFontParameter();
		fontPar.size = 13;
		fontPar.color = Color.WHITE;
		this.typeFont = fontGen.generateFont(fontPar);
		fontGen.dispose();
	}
	
	private void createBGTable() {
		Table table = createTable();
		table.center();
		table.setFillParent(true);
		
		table.add(bg).padBottom(120).padTop(60).padLeft(40).padRight(40);
		
		this.stageBG.addActor(table);
	}
	
	private void createMonsterTable() {
		Table table2 = createTable();
		table2.center();
		table2.setFillParent(true);
		table2.add(animatedWidget).padTop(80);
		
		this.stageMonster.addActor(table2);
	}
	
	private void createStatusTable() {
		createStatusPane();
		
		Table table = createTable();
		table.setFillParent(true);
		table.add(statusPane).expand().fill().pad(90, 30, 120, 30);
		
		stageStatus.addActor(table);
	}
	
	private void createStatusPane() {
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		this.statusPane = new PagedScrollPane(null, scrollStyle);
		this.statusPane.setScrollingDisabled(false, true);
		this.statusPane.setFlingTime(0.1f);
		this.statusPane.setPageSpacing(30);
		
		int pages = 2;
		for (int p = 0; p < pages; p++) {
			Table statusTable = new Table();
			statusTable.defaults().pad(2, 30, 2, 30);
        	
			if (p != 0) {
				Label esp = createLabel(monster.getOriginalName(), statusFont, null);
				esp.setAlignment(Align.center);
				Label type = createLabel(" " + monster.getType().toString() + " ", typeFont, skinUI.getDrawable(monster.getType().toString()));
				type.setAlignment(Align.center);
				
				hp = createLabel("PV: " + monster.getHp(), statusFont, null);
				sp = createLabel("PE: " + monster.getSp(), statusFont, null);
				rp = createLabel("PR: " + monster.getRp(), statusFont, null);
				atk = createLabel("Ataque: " + monster.getAtk(), statusFont, null);
				def = createLabel("Desesa: " + monster.getDef(), statusFont, null);
				spd = createLabel("Velocidade: " + monster.getSpd(), statusFont, null);
				pct = createLabel("Crescimento: " + (monster.getPctStatusUp() / 100f) + "%", statusFont, null);
				
				Label atktitle = createLabel("Ataques", statusFont, null);
				atktitle.setAlignment(Align.center);
				
				Label atk1Name = createLabel("Atq norm.: " + monster.getNormalAttack().getName(), statusFont, null);
				Label atk2Name = createLabel("Atq espe.: " + monster.getSpecialAttack1().getName(), statusFont, null);
				Label atk3Name = createLabel("Atq espe.: " + monster.getSpecialAttack2().getName(), statusFont, null);
				
				statusTable.add(esp).top().padTop(20).width(210);
				statusTable.row();
				statusTable.add(type).height(18).expandX();
				statusTable.row();
				statusTable.add().expand();
				statusTable.row();
				statusTable.add(hp).left().expandX();
				statusTable.row();
				statusTable.add(sp).left().expandX();
				statusTable.row();
				statusTable.add(rp).left().expandX();
				statusTable.row();
				statusTable.add(atk).left().expandX();
				statusTable.row();
				statusTable.add(def).left().expandX();
				statusTable.row();
				statusTable.add(spd).left().expandX();
				statusTable.row();
				statusTable.add(pct).left().expandX();
				statusTable.row();
				statusTable.add().expand();
				statusTable.row();
				statusTable.add(atktitle).expandX().padBottom(10);
				statusTable.row();
				statusTable.add(atk1Name).left().expandX();
				statusTable.row();
				statusTable.add(atk2Name).left().expandX();
				statusTable.row();
				statusTable.add(atk3Name).left().expandX();
				statusTable.row();
				statusTable.add().expand().padBottom(20);
			} else {
				statusTable.add().width(210).expand();
			}
			
			this.statusPane.addPage(statusTable);
		}
 	}
	
	private Button createButton(String text, BitmapFont font, Drawable up, Drawable down) {
		return createButton(text, font, up, down, null);
	}
	
	private Button createButton(String text, BitmapFont font, Drawable up, Drawable down, Drawable disabled) {
		TextButtonStyle style = new TextButtonStyle();
		style.font = font;
		style.up = up;
		style.down = down;
		style.over = up;
		style.disabled = disabled;
        style.pressedOffsetX = 1;
        style.pressedOffsetY = -1;
        Button button = new TextButton(text, style);
        
        return button;
	}
	
	private Label createLabel(String text, BitmapFont font, Drawable bg) {
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.font = font;
		lblStyle.background = bg;
		Label label = new Label(text, lblStyle);
		
		return label;
	}
	
	private TextField createTextField(String text, BitmapFont font, Drawable bg) {
		TextFieldStyle style = new TextFieldStyle();
		style.fontColor = Color.WHITE;
		style.disabledFontColor = Color.LIGHT_GRAY;
		style.focusedFontColor = Color.CYAN;
		style.background = bg;
		style.font = font;
		TextField textField = new TextField(text, style);
		return textField;
	}
	
	public void createActionTable() {
		levelBar.incrementProgress(getProgressToIncrement() * this.monster.getClicks());
		
		this.nameLabel = createTextField(this.monster.getName(), font,
				skinUI.getDrawable("blue_panel"));
		this.nameLabel.setAlignment(Align.center);
		this.nameLabel.setDisabled(true);
		
		this.levelLabel = createLabel(""+this.monster.getLevel(), font,
				skinUI.getDrawable("blue_panel"));
		this.levelLabel.setAlignment(Align.center);
		
		this.clicks = createLabel(""+this.monster.getClicks(), font, null);
		this.maxClicks = createLabel(""+this.monster.getClicksToUp(), font, null);
		
		this.editName = createButton("", font,
				skinUI.getDrawable("EditButtonUp"),
				skinUI.getDrawable("editButtonDown"),
				skinUI.getDrawable("EditButtonDisabled"));
		if (monster.isEgg()) this.editName.setDisabled(true);
		this.editName.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (editName.isDisabled()) return;
				editName();	
			}
		});
		
		this.clickBtn = createButton("Click!", font, 
				skinUI.getDrawable("green_button04"), 
				skinUI.getDrawable("green_button05"));
		this.clickBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	click();
            }
        });
        
		this.backBtn = createButton("", font, 
				skinUI.getDrawable("backbuttonUp"), 
				skinUI.getDrawable("backButtonDown"));
        this.backBtn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	back();
            }
        });
        
		Table uiTable = createTable();;
		uiTable.center();
		uiTable.setFillParent(true);
		uiTable.add(levelLabel).right().padTop(40).size(30);
		uiTable.add(nameLabel).top().padTop(40).width(190).height(40).colspan(10);
		uiTable.add(editName).left().padTop(40).size(30);
		uiTable.row();
		uiTable.add().expand();
		addStars(uiTable);
		uiTable.add().expand();
		uiTable.row();
		uiTable.add(clickBtn).expandY().bottom().padBottom(10).colspan(12);
		uiTable.row();
		uiTable.add(clicks).right().padBottom(25).height(20);
		uiTable.add(levelBar).padBottom(25).height(10).colspan(10);
		uiTable.add(maxClicks).left().padBottom(25).height(20);
		uiTable.row();
		uiTable.add(backBtn).expandX().left().colspan(12);
		
		this.stageAction.addActor(uiTable);
	}
	
	private void addStars(Table table) {
		long numberStars;
		String imageName;
		
		numberStars = getNumberStars();
		for (int i = 1; i <= 10; i ++) {
			imageName = "star_off";
			if (numberStars >= i) {
				imageName = "star_on";
			}
			
			Image image = new Image(skinUI.getDrawable(imageName));
			
			table.add(image).size(18).pad(1).top().expandY();
		}
	}
	
	private long getNumberStars () {
		long stars;
		float status;
		float maxStatus;
		float minStatus;
		
		status = monster.getSumAttributes();
		maxStatus = monster.getSumMaxAttributes();
		minStatus = monster.getSumMinAttributes();
		
		stars = (long) (((status - minStatus) * 10) / (maxStatus - minStatus));
		
		return stars;
	}
	
	private void click() {
    	if (userData.canClick()) {
    		userData.click();
    		monster.increaseClick();
    		levelBar.incrementProgress(getProgressToIncrement());
    		clicks.setText(monster.getClicks()+"");
    		if (!userData.canClick()) {
    			clickBtn.setDisabled(true);
    		}
    		
    		if (monster.getClicks() == monster.getClicksToUp()) {
    			monster.resetClicks();
    			clicks.setText(monster.getClicks()+"");
        		
	    		if (monster.isEgg()) {
	    			game.setGameState(new HatchScreen(game, userData, monster));
	    		} else {
	    			monster.levelUp();
	    			
	    			updateStatus();
	    			
	    			maxClicks.setText(monster.getClicksToUp()+"");
	    			levelLabel.setText(monster.getLevel()+"");
	    			
	    			levelBar.setProgress(0);
	    			
	    			animatedWidget.setFrame("frontattack");
	    			animatedWidget.setAnimation(monster.getAnimation("frontattack"));
	    			animatedWidget.setState(0);
	    			animatedWidget.setPlayMode(PlayMode.NORMAL);
	    		}
    		}
    	}
	}
	
	private AnimationListener animationListener = new AnimationListener() {
		@Override
		public void onAnimationFinished() {
			if (animatedWidget.getFrame() == "frontattack") {
				animatedWidget.setFrame("stand");
				animatedWidget.setAnimation(monster.getAnimation("stand"));
				animatedWidget.setState(0);
				animatedWidget.setPlayMode(PlayMode.LOOP);
			}
		}
	};
	
	private float getProgressToIncrement() {
		float progressToIncrement;
    	
    	progressToIncrement = ((100 / (float) monster.getClicksToUp()) / 100);
    	
    	return progressToIncrement;
	}
	
	private void back() {
		game.setGameState(new MyMonstersView(game, userData));
	}
	
	private void editName() {
		if (this.nameLabel.isDisabled()) {
			this.nameLabel.setDisabled(false);
			this.nameLabel.setText("");
			this.nameLabel.setSelection(0, nameLabel.getText().length());
			this.nameLabel.selectAll();
			this.editName.getStyle().up = this.editName.getStyle().over = skinUI.getDrawable("okButtonUp");
			this.editName.getStyle().down = skinUI.getDrawable("okButtonDown");
		} else {
			this.nameLabel.setDisabled(true);
			if (this.nameLabel.getText().contentEquals("")) {
				this.nameLabel.setText(monster.getOriginalName());
			} 
			this.monster.setMyMonsterName(this.nameLabel.getText());
			this.editName.getStyle().up = this.editName.getStyle().over = skinUI.getDrawable("EditButtonUp");
			this.editName.getStyle().down = skinUI.getDrawable("editButtonDown");
		}
	}
	
	private void createUserBarTable() {
		this.userBar = new UserBar(skinUI, userData);
		this.stageUserBar.addActor(userBar);
	}
	
	private void updateStatus() {
		hp.setText("PV: " + monster.getHp());
		sp.setText("PE: " + monster.getSp());
		rp.setText("PR: " + monster.getRp());
		atk.setText("Ataque: " + monster.getAtk());
		def.setText("Desesa: " + monster.getDef());
		spd.setText("Velocidade: " + monster.getSpd());
		pct.setText("Crescimento: " + (monster.getPctStatusUp() / 100f) + "%");
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		userBar.update();
	}
}