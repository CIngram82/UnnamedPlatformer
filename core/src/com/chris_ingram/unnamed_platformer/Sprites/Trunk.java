package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.chris_ingram.unnamed_platformer.Screens.PlayScreen;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/20/2017.
 */

public class Trunk extends Enemy{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    public Trunk(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i =0; i < 7 ; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("totem_walk"), i* 32 ,0 , 32,32));
        walkAnimation = new Animation<TextureRegion>(0.25f, frames);
        stateTime = 0;
        setBounds(getX(),getY(), 16/UnnamedPlatformer.PPM, 16/UnnamedPlatformer.PPM );

    }
    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(walkAnimation.getKeyFrame(stateTime,true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(128 / UnnamedPlatformer.PPM,32/UnnamedPlatformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/UnnamedPlatformer.PPM);
        fdef.filter.categoryBits = UnnamedPlatformer.ENEMY_BIT;
        fdef.filter.maskBits = UnnamedPlatformer.GROUND_BIT |
                UnnamedPlatformer.CHEST_BIT |
                UnnamedPlatformer.BRICK_BIT |
                UnnamedPlatformer.ENEMY_BIT |
                UnnamedPlatformer.OBJECT_BIT |
                UnnamedPlatformer.HERO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }


}
