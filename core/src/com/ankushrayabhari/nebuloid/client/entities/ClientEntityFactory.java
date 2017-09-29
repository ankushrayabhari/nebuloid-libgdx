package com.ankushrayabhari.nebuloid.client.entities;

import com.ankushrayabhari.nebuloid.client.entities.physical.ClientPlayer;
import com.ankushrayabhari.nebuloid.core.Constants.EntityCode;
import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.ankushrayabhari.nebuloid.core.network.packets.NewEntityPacket;
import com.badlogic.gdx.physics.box2d.World;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/28/17.
 */

public class ClientEntityFactory {
    private static ClientEntityFactory instance = new ClientEntityFactory();

    public static Entity spawn(NewEntityPacket packet, World world) {
        return instance.spawnEntity(packet, world);
    }

    private Entity spawnEntity(NewEntityPacket test, World world) {
        EntityCode code = EntityCode.values()[test.entityCode];
        Entity newEntity = null;
        UUID id = UUID.fromString(test.uuid);

        switch(code) {
            case PLAYER: newEntity = new ClientPlayer(world, id); break;
        }

        return newEntity;
    }
}
