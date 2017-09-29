package com.ankushrayabhari.nebuloid.core.network;

import com.ankushrayabhari.nebuloid.core.network.packets.*;
import com.esotericsoftware.kryo.Kryo;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public abstract class NetworkManager {
    public static void registerClasses(Kryo kryo) {
        kryo.register(InputPacket.class);
        kryo.register(NewEntityPacket.class);
        kryo.register(DeleteEntityPacket.class);
        kryo.register(SelectPlayerPacket.class);
        kryo.register(PhysicalEntityStatePacket.class);
    }
}
