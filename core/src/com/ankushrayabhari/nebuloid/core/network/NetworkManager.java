package com.ankushrayabhari.nebuloid.core.network;

import com.esotericsoftware.kryo.Kryo;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public class NetworkManager {
    public static void registerClasses(Kryo kryo) {
        kryo.register(InputPacket.class);
    }
}
