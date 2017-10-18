package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by cingr on 10/15/2017.
 */

public class Brick extends InteravtiveTileObject{
    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","");

    }

    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
    }
}
