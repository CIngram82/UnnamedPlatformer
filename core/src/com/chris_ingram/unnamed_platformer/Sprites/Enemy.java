package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.chris_ingram.unnamed_platformer.Screens.PlayScreen;

/**
 * Created by cingr on 10/20/2017.
 */

public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
    }

    protected abstract void defineEnemy();
}
