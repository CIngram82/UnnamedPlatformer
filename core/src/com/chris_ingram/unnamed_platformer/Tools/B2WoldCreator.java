package com.chris_ingram.unnamed_platformer.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.chris_ingram.unnamed_platformer.Sprites.Brick;
import com.chris_ingram.unnamed_platformer.Sprites.ChestBlock;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public class B2WoldCreator {
    public B2WoldCreator(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ UnnamedPlatformer.PPM,(rect.getY()+rect.getHeight()/2)/UnnamedPlatformer.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth()/2)/UnnamedPlatformer.PPM,(rect.getHeight()/2)/UnnamedPlatformer.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/UnnamedPlatformer.PPM,(rect.getY()+rect.getHeight()/2)/UnnamedPlatformer.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth()/2)/UnnamedPlatformer.PPM,(rect.getHeight()/2)/UnnamedPlatformer.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(world,map,rect);
        }

        // create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new ChestBlock(world, map, rect);
        }
    }
}
