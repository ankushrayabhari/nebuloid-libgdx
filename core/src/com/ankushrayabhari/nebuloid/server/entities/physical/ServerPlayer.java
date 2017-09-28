package com.ankushrayabhari.nebuloid.server.entities.physical;

import com.ankushrayabhari.nebuloid.core.entities.physical.Player;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public class ServerPlayer extends Player {
    private int connectionID;

    public ServerPlayer(World world, int connectionID) {
        super(world);
        this.connectionID = connectionID;
    }

    public int getConnectionID() {
        return connectionID;
    }
}
