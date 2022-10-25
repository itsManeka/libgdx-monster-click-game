package com.monsterclickgame.generic;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.game.MonsterClick;

public abstract class GameState implements Screen, Disposable {
	protected UserData userData;
	protected SpriteBatch batch;
	protected MonsterClick game;
	
	public abstract void create();
    public abstract void draw();

    public void update(float delta) {

    }
	
	public GameState(MonsterClick game, UserData userData) {
		this.game = game;
		this.userData = userData;
		
		batch = new SpriteBatch();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		this.batch.dispose();
	}
}