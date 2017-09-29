package com.ankushrayabhari.nebuloid.server.entities.physical;

import com.ankushrayabhari.nebuloid.core.entities.physical.Player;
import com.badlogic.gdx.physics.box2d.World;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public class ServerPlayer extends Player {
    private int connectionID;

    public ServerPlayer(World world, int connectionID, UUID uuid) {
        super(world, uuid);
        this.connectionID = connectionID;
    }

    public int getConnectionID() {
        return connectionID;
    }
}
