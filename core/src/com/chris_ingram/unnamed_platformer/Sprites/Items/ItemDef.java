package com.chris_ingram.unnamed_platformer.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by cingr on 10/24/2017.
 */

public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}
