package com.chris_ingram.unnamed_platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chris_ingram.unnamed_platformer.Screens.PlayScreen;

public class UnnamedPlatformer extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short HERO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short CHEST_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
