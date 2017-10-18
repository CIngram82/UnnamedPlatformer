package com.chris_ingram.unnamed_platformer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.chris_ingram.unnamed_platformer.Sprites.InteravtiveTileObject;

/**
 * Created by cingr on 10/16/2017.
 */

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if ("head".equals(fixA.getUserData()) || "head".equals(fixB.getUserData())){
            Fixture head = fixA.getUserData() == "head" ? fixA: fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() instanceof InteravtiveTileObject){
                ((InteravtiveTileObject) object.getUserData()).onHeadHit();
            }
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
