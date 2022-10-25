package com.monsterclickgame.generic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.monsterclickgame.configs.Config;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;

public class BaseScreen extends GameState {
	private boolean debug = false;
	
	private Array<Stage> stages;
	
	public BaseScreen(MonsterClick game, UserData userData) {
		super(game, userData);
	}
	
	protected void initStages() {
		stages = new Array<Stage>();
	}
	
	@Override
	public void create() {

	}
	
	protected void createInput() {
		InputMultiplexer input = new InputMultiplexer();
		for (Stage s : stages) {
			input.addProcessor(s);	
		}
		Gdx.input.setInputProcessor(input);
	}

	@Override
	public void draw() {
		prepareBG();
		
		for (Stage s : stages) {
			s.draw();
		}
	}
	
	protected Stage addStage() {
		Stage stage = new Stage(new StretchViewport(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
		stages.add(stage);
		return stage;
	}
	
	protected void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	protected Table createTable() {
		Table table = new Table();
		if (debug) table.debug();
		return table;
	}
	
	protected void prepareBG() {
		Gdx.gl.glClearColor(.3f, .3f, .6f, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void update(float delta) {
		for (Stage s : stages) {
			s.act(Math.min(delta, 1 / 30f));
		}
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}
}