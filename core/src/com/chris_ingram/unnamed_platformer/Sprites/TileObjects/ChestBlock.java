package com.chris_ingram.unnamed_platformer.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chris_ingram.unnamed_platformer.Scenes.Hud;
import com.chris_ingram.unnamed_platformer.Screens.PlayScreen;
import com.chris_ingram.unnamed_platformer.Sprites.Items.ItemDef;
import com.chris_ingram.unnamed_platformer.Sprites.Items.Mushroom;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public class ChestBlock extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int OPEN_CHEST = 1613;
    private boolean isClosed = true;
    public ChestBlock(PlayScreen playScreen, MapObject object){
        super(playScreen,object);
        tileSet = map.getTileSets().getTileSet("atlas");
        fixture.setUserData(this);
        setCategoryFilter(UnnamedPlatformer.CHEST_BIT);
    }

    @Override
    public void onHeadHit() {
        if(!isClosed)
            UnnamedPlatformer.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else{
            if (object.getProperties().containsKey("mushroom")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,
                        body.getPosition().y+ 16/UnnamedPlatformer.PPM),
                        Mushroom.class));
                UnnamedPlatformer.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            } else {
                UnnamedPlatformer.manager.get("audio/sounds/coin.wav", Sound.class).play();
                Hud.addScore(1000);
            }
            isClosed = false;
        }
        getCell().setTile(tileSet.getTile(OPEN_CHEST));
    }



}
