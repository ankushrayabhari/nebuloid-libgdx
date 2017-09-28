package com.ankushrayabhari.nebuloid.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class Map implements Disposable {
    private Texture texture;

    public Map() {
        texture = new Texture(Gdx.files.internal("map.png"));
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0);
    }


    public void dispose() {
        texture.dispose();
    }
}
