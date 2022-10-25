package com.monsterclickgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.monsterclickgame.data.UserData;
import com.monsterclickgame.eggs.Egg;
import com.monsterclickgame.eggs.EggManager;
import com.monsterclickgame.generic.GameState;
import com.monsterclickgame.monsters.Monster;
import com.monsterclickgame.monsters.MonsterManager;
import com.monsterclickgame.monsters.MyMonster;
import com.monsterclickgame.screens.StartMenu;

public class MonsterClick extends Game {
	private GameState state;
	private UserData userData;
	private FPSLogger fpsLogger;
	
	@Override
	public void create () {
		this.userData = new UserData();
		
		//this.state = new WelcomeToMonsterClick(this, this.userData);
		
		//-- teste
		EggManager eggmanager = new EggManager();
		MonsterManager monstermanager = new MonsterManager();
		
		Egg egg = eggmanager.getEgg("egg");
		Monster monster = monstermanager.getMonster(egg.getRandomMonster());
		
		MyMonster myMonster = new MyMonster(egg, monster);
		myMonster.hatch();
		userData.addMonster(myMonster);
		this.state = new StartMenu(this, this.userData);
		//--
		
		this.fpsLogger = new FPSLogger();
		
		Timer.schedule(new Task() {
			@Override
			public void run() {
				timer();
			}
		}, 1f, 1f);
	}
	
	public void timer() {
		userData.decrementTime1Second();
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		
		this.state.render(delta);
		
//		this.fpsLogger.log();
	}
	
	public void setGameState(GameState state, boolean create) {
		this.state = state;
		
		if (create) {
			state.create();
		}
	}
	
	public void setGameState(GameState state) {
		setGameState(state, false);
	}
	
	@Override
	public void dispose () {
		this.state.dispose();
		super.dispose();
	}
}
