package com.ankushrayabhari.nebuloid.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class Map implements Disposable {
    private Texture texture;
    private Camera camera;

    public Map(Camera camera) {
        texture = new Texture(Gdx.files.internal("map.png"));
        this.camera = camera;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0);
    }


    public void dispose() {
        texture.dispose();
    }
}
