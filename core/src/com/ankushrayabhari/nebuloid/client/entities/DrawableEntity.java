package com.ankushrayabhari.nebuloid.client.entities;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.entities.Entity;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public abstract class DrawableEntity extends Entity implements Drawable {
    protected DrawableEntity(int zIndex, Constants.EntityCode entityCode, UUID uuid) {
        super(zIndex, entityCode, uuid);
    }
}
