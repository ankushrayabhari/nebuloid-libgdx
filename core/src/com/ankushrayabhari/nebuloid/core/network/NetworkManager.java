package com.ankushrayabhari.nebuloid.core.network;

import com.ankushrayabhari.nebuloid.core.network.packets.*;
import com.ankushrayabhari.nebuloid.core.network.serializers.UUIDSerializer;
import com.esotericsoftware.kryo.Kryo;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public abstract class NetworkManager {
    public static void setup(Kryo kryo) {
        kryo.register(UUID.class, new UUIDSerializer());
        kryo.register(InputPacket.class);
        kryo.register(NewEntityPacket.class);
        kryo.register(DeleteEntityPacket.class);
        kryo.register(SelectPlayerPacket.class);
        kryo.register(PhysicalEntityStatePacket.class);
    }
}
