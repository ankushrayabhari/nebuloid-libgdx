package com.ankushrayabhari.nebuloid.client;

import com.ankushrayabhari.nebuloid.core.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class ClientMap extends Map implements Disposable {
    private Texture texture;
    private Camera camera;

    public ClientMap(World world, Camera camera) {
        super(world);
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
