package com.chris_ingram.unnamed_platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chris_ingram.unnamed_platformer.Scenes.Hud;
import com.chris_ingram.unnamed_platformer.Sprites.Enemies.Enemy;
import com.chris_ingram.unnamed_platformer.Sprites.Hero;
import com.chris_ingram.unnamed_platformer.Sprites.Items.Item;
import com.chris_ingram.unnamed_platformer.Sprites.Items.ItemDef;
import com.chris_ingram.unnamed_platformer.Sprites.Items.Mushroom;
import com.chris_ingram.unnamed_platformer.Tools.B2WoldCreator;
import com.chris_ingram.unnamed_platformer.Tools.WorldContactListener;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by cingr on 10/14/2017.
 */

public class PlayScreen implements Screen{
    private UnnamedPlatformer game;
    private TextureAtlas atlas;
    private TextureAtlas powerUpAtlas;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Hero player;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WoldCreator creator;

    private Music music;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    public PlayScreen(UnnamedPlatformer game) {
        atlas = new TextureAtlas("hero_and_Enemies.pack");
        powerUpAtlas = new TextureAtlas("hero_and_Enemies_powerUp.pack");
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(UnnamedPlatformer.V_WIDTH /UnnamedPlatformer.PPM,UnnamedPlatformer.V_HEIGHT/UnnamedPlatformer.PPM, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/UnnamedPlatformer.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WoldCreator(this);


        player = new Hero(this);

        world.setContactListener( new WorldContactListener());

        music = UnnamedPlatformer.manager.get("audio/music/Zanzibar.mp3", Music.class);
        music.setVolume(.1f);
        music.setLooping(true);
//        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if (!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == Mushroom.class){
                items.add(new Mushroom(this, idef.position.x,idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TextureAtlas getPowerUpAtlas(){
        return powerUpAtlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(),true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <=2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(),true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >=-2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(),true);


    }
    public void update(float dt){
        handleInput(dt);
        handleSpawningItems();

        world.step(1/60f, 6, 2);
        player.update(dt);
        for(Enemy enemy : creator.getTrunks()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224/UnnamedPlatformer.PPM)
                enemy.b2body.setActive(true);
        }

        for (Item item : items)
            item.update(dt);

        hud.update(dt);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix((gameCam.combined));
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getTrunks())
            enemy.draw(game.batch);
        for (Item item : items)
            item.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
