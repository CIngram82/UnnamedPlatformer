package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public class ChestBlock extends InteravtiveTileObject {
    @Override
    public void onHeadHit() {
        Gdx.app.log("Chest","");
    }

    public ChestBlock(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
        fixture.setUserData(this);
    }
}
