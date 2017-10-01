package com.ankushrayabhari.nebuloid.core.network.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/30/17.
 */

public class UUIDSerializer extends Serializer<UUID> {

    @Override
    public void write(Kryo kryo, Output output, UUID object) {
        output.writeString(object.toString());
    }

    @Override
    public UUID read(Kryo kryo, Input input, Class<UUID> type) {
        return UUID.fromString(input.readString());
    }
}

