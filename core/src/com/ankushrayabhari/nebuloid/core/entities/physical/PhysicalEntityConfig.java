package com.ankushrayabhari.nebuloid.core.entities.physical;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class PhysicalEntityConfig {
    public int zIndex;
    public boolean staticBody;
    public Constants.PhysicalEntityTypes type;
    public Vector2 initialPosition;
    public Vector2 dimensions;
    public float angle;
    public boolean massive;
}
