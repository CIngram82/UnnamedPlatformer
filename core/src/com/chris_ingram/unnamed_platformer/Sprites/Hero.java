package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.chris_ingram.unnamed_platformer.Screens.PlayScreen;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public class Hero extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion heroStand;
    private Animation<TextureRegion> heroRun;
    private Animation<TextureRegion> heroJump;
    private Animation<TextureRegion> heroFall;
    private boolean runningRight;
    private float stateTimer;


    public Hero(World world, PlayScreen screen){
//        super(screen.getAtlas().findRegion("idle"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 6; i++ )
            frames.add(new TextureRegion(screen.getAtlas().findRegion("run"),i * 32,8,32,64));
        heroRun = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jump"),i*32,0,32,64));
        heroJump = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        for (int i = 0; i < 3; i++ )
            frames.add(new TextureRegion(screen.getAtlas().findRegion("fall"),i*32,0,31,48));
        heroFall = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        defineHero();
//        heroStand = new TextureRegion(getTexture(),356,19,32,64);
        heroStand = new TextureRegion(screen.getAtlas().findRegion("idle"),0,8,32,64);
        setBounds(0,0,12/UnnamedPlatformer.PPM,24/UnnamedPlatformer.PPM);
        setRegion(heroStand);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x -(getWidth()/ 2),b2body.getPosition().y -(getHeight()/ 2));
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = heroJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = heroRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = heroFall.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = heroStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y >0)
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y <0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(50 / UnnamedPlatformer.PPM,64/UnnamedPlatformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8/UnnamedPlatformer.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
