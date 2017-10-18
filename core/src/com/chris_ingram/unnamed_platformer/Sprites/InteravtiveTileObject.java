package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public abstract class InteravtiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteravtiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ UnnamedPlatformer.PPM,(bounds.getY()+bounds.getHeight()/2)/UnnamedPlatformer.PPM);
        body = world.createBody(bdef);
        shape.setAsBox((bounds.getWidth()/2)/UnnamedPlatformer.PPM,(bounds.getHeight()/2)/UnnamedPlatformer.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit();
}
