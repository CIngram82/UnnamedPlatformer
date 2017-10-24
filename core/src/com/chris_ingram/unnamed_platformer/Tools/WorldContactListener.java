package com.chris_ingram.unnamed_platformer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.chris_ingram.unnamed_platformer.Sprites.Enemies.Enemy;
import com.chris_ingram.unnamed_platformer.Sprites.TileObjects.interactiveTileObject;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/16/2017.
 */

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if ("head".equals(fixA.getUserData()) || "head".equals(fixB.getUserData())){
            Fixture head = fixA.getUserData() == "head" ? fixA: fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() instanceof interactiveTileObject){
                ((interactiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef){
            case UnnamedPlatformer.ENEMY_HEAD_BIT | UnnamedPlatformer.HERO_BIT:
                if(fixA.getFilterData().categoryBits == UnnamedPlatformer.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
            case UnnamedPlatformer.ENEMY_BIT | UnnamedPlatformer.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == UnnamedPlatformer.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case UnnamedPlatformer.HERO_BIT | UnnamedPlatformer.ENEMY_BIT:
                Gdx.app.log("hero", "Damage trigger");
                break;
            case UnnamedPlatformer.ENEMY_BIT | UnnamedPlatformer.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
