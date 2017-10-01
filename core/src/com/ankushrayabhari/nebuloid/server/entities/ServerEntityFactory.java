package com.ankushrayabhari.nebuloid.server.entities;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.ankushrayabhari.nebuloid.core.network.packets.NewEntityPacket;
import com.ankushrayabhari.nebuloid.server.entities.physical.ServerPlayer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/28/17.
 */

public class ServerEntityFactory {
    private static ServerEntityFactory instance = new ServerEntityFactory();

    public static ServerEntityFactory getInstance() {
        return instance;
    }

    public Entity spawnEntity(NewEntityPacket test, World world, int connectionId) {
        Constants.EntityCode code = Constants.EntityCode.values()[test.entityCode];
        Entity newEntity = null;
        switch (code) {
            case PLAYER:
                newEntity = new ServerPlayer(world, connectionId, UUID.randomUUID());
                break;
        }

        return newEntity;
    }
}