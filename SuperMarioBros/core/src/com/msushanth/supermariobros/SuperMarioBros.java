package com.msushanth.supermariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.msushanth.supermariobros.Screens.PlayScreen;

public class SuperMarioBros extends Game {

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 208;

	//memory intensive, so we dont want to make a new one every time we want to render something
	public SpriteBatch batch;




	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}




	@Override
	public void render () {
		//delegates the render method to whatever screen is active at the time (the play screen)
		super.render();
	}




	@Override
	public void dispose () {
		batch.dispose();
	}
}
