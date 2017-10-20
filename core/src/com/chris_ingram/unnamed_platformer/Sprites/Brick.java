package com.chris_ingram.unnamed_platformer.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.chris_ingram.unnamed_platformer.Scenes.Hud;
import com.chris_ingram.unnamed_platformer.Screens.PlayScreen;
import com.chris_ingram.unnamed_platformer.UnnamedPlatformer;

/**
 * Created by cingr on 10/15/2017.
 */

public class Brick extends interavtiveTileObject {
    @Override
    public void onHeadHit() {
        setCategoryFilter(UnnamedPlatformer.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        UnnamedPlatformer.manager.get("audio/sounds/breakblock.wav", Sound.class).play();

    }

    public Brick(PlayScreen playScreen, Rectangle bounds) {
        super(playScreen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(UnnamedPlatformer.BRICK_BIT);

    }
}
