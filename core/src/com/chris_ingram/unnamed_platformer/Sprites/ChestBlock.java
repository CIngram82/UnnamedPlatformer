package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.chris_ingram.unnamed_platformer.Scenes.Hud;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public class ChestBlock extends interavtiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int OPEN_CHEST = 33;
    private boolean isClosed = true;
    public ChestBlock(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
        tileSet = map.getTileSets().getTileSet("atlas");
        fixture.setUserData(this);
        setCategoryFilter(UnnamedPlatformer.CHEST_BIT);
    }

    @Override
    public void onHeadHit() {
        getCell().setTile(tileSet.getTile(OPEN_CHEST));
        if(isClosed){
            Hud.addScore(1000);
            isClosed = false;
        }
    }


}
