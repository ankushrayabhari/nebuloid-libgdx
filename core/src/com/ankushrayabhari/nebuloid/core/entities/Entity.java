package com.ankushrayabhari.nebuloid.core.entities;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public abstract class Entity {
    private int zIndex;
    private boolean dead;
    private Vector2 position;

    protected Entity(int zIndex) {
        this.zIndex = zIndex;
        this.dead = false;
        this.position = new Vector2();
    }

    public abstract void update(float delta);

    public abstract void onDeath();

    public boolean isDead() { return dead; }

    public void setDead() {
        this.dead = true;
        this.zIndex = 100;
    }

    public int getzIndex() { return zIndex; }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() { return position; }
}
