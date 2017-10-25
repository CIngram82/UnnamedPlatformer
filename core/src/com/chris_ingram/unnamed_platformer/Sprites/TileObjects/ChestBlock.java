package com.chris_ingram.unnamed_platformer.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
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
    public ChestBlock(PlayScreen playScreen, Rectangle bounds){
        super(playScreen,bounds);
        tileSet = map.getTileSets().getTileSet("atlas");
        fixture.setUserData(this);
        setCategoryFilter(UnnamedPlatformer.CHEST_BIT);
    }

    @Override
    public void onHeadHit() {
        getCell().setTile(tileSet.getTile(OPEN_CHEST));
        if(isClosed){
            UnnamedPlatformer.manager.get("audio/sounds/coin.wav", Sound.class).play();
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,
                    body.getPosition().y+ 16/UnnamedPlatformer.PPM),
                    Mushroom.class));
            Hud.addScore(1000);
            isClosed = false;
        }else {
            UnnamedPlatformer.manager.get("audio/sounds/bump.wav", Sound.class).play();

        }
    }


}
