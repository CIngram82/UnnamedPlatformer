package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.chris_ingram.unnamed_platformer.Screens.PlayScreen;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public class Hero extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, POWERINGUP }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;

    private TextureRegion heroStand;
    private Animation<TextureRegion> heroRun;
    private Animation<TextureRegion> heroJump;
    private Animation<TextureRegion> heroFall;

    private TextureRegion heroPowerUpStand;
    private Animation<TextureRegion> heroPowerUpRun;
    private Animation<TextureRegion> heroPowerUpJump;
    private Animation<TextureRegion> heroPowerUpFall;

    private Animation<TextureRegion> heroPoweringUp;

    private boolean runningRight;
    private float stateTimer;
    private boolean heroPoweredUp;
    private boolean runPowerUpAnimation;


    public Hero(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //normal sprites Orange and blue
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
        heroStand = new TextureRegion(screen.getAtlas().findRegion("idle"),0,8,32,64);
        setBounds(0,0,12/UnnamedPlatformer.PPM,24/UnnamedPlatformer.PPM);
        setRegion(heroStand);

        // power up sprites Purple and green
        for (int i = 0; i < 6; i++ )
            frames.add(new TextureRegion(screen.getPowerUpAtlas().findRegion("run"),i * 32,8,32,64));
        heroPowerUpRun = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getPowerUpAtlas().findRegion("jump"),i*32,0,32,64));
        heroPowerUpJump = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        for (int i = 0; i < 3; i++ )
            frames.add(new TextureRegion(screen.getPowerUpAtlas().findRegion("fall"),i*32,0,31,48));
        heroPowerUpFall = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        heroPowerUpStand = new TextureRegion(screen.getPowerUpAtlas().findRegion("idle"),0,8,32,64);

        // Change from orange and blue to purple and green;
        frames.add(new TextureRegion(screen.getAtlas().findRegion("idle"),0,8,32,64));
        frames.add(new TextureRegion(screen.getPowerUpAtlas().findRegion("idle"),0,8,32,64));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("idle"),0,8,32,64));
        frames.add(new TextureRegion(screen.getPowerUpAtlas().findRegion("idle"),0,8,32,64));
        heroPoweringUp = new Animation<TextureRegion>(0.2f,frames);
        frames.clear();

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x -(getWidth()/ 2),b2body.getPosition().y -(getHeight()/ 2));
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case POWERINGUP:
                region = heroPoweringUp.getKeyFrame(stateTimer);
                if (heroPoweringUp.isAnimationFinished(stateTimer))
                    runPowerUpAnimation = false;
                break;
            case JUMPING:
                region = heroPoweredUp ? heroPowerUpJump.getKeyFrame(stateTimer) : heroJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = heroPoweredUp ? heroPowerUpRun.getKeyFrame(stateTimer, true) : heroRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = heroPoweredUp ? heroPowerUpFall.getKeyFrame(stateTimer) :heroFall.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = heroPoweredUp ? heroPowerUpStand : heroStand;
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
        if(runPowerUpAnimation)
            return State.POWERINGUP;

        else if(b2body.getLinearVelocity().y >0)
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
        shape.setRadius(6/UnnamedPlatformer.PPM);
        fdef.filter.categoryBits = UnnamedPlatformer.HERO_BIT;
        fdef.filter.maskBits = UnnamedPlatformer.GROUND_BIT |
                UnnamedPlatformer.CHEST_BIT |
                UnnamedPlatformer.BRICK_BIT |
                UnnamedPlatformer.OBJECT_BIT |
                UnnamedPlatformer.ENEMY_BIT |
                UnnamedPlatformer.ITEM_BIT|
                UnnamedPlatformer.ENEMY_HEAD_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/UnnamedPlatformer.PPM, 6/UnnamedPlatformer.PPM),new Vector2(2/UnnamedPlatformer.PPM,6/UnnamedPlatformer.PPM));
        fdef.shape = head;
        fdef.filter.categoryBits = UnnamedPlatformer.HERO_HEAD_BIT;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape feet = new EdgeShape();
        head.set(new Vector2(-2/UnnamedPlatformer.PPM, -6/UnnamedPlatformer.PPM),new Vector2(2/UnnamedPlatformer.PPM,-6/UnnamedPlatformer.PPM));
        fdef.shape = feet;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void heroPowerUp(){
        runPowerUpAnimation = true;
        heroPoweredUp = true;
        UnnamedPlatformer.manager.get("audio/sounds/powerup.wav", Sound.class).play();

    }
}
