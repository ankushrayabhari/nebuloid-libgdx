package com.ankushrayabhari.nebuloid.core.entities;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.badlogic.gdx.math.Vector2;
import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public abstract class Entity {
    private int zIndex;
    private boolean dead;
    private Vector2 position;
    private Constants.EntityCode entityCode;
    private UUID uuid;

    protected Entity(int zIndex, Constants.EntityCode entityCode, UUID uuid) {
        this.zIndex = zIndex;
        this.dead = false;
        this.position = new Vector2();
        this.entityCode = entityCode;
        this.uuid = uuid;
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

    public Constants.EntityCode getEntityCode() {
        return entityCode;
    }

    public UUID getUuid() {
        return uuid;
    }
}
