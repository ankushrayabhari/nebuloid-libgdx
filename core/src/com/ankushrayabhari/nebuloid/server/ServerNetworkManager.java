package com.ankushrayabhari.nebuloid.server;

import com.ankushrayabhari.nebuloid.client.ClientNetworkManager;
import com.ankushrayabhari.nebuloid.client.KeyboardController;
import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.ankushrayabhari.nebuloid.core.entities.physical.PhysicalEntity;
import com.ankushrayabhari.nebuloid.core.network.NetworkManager;
import com.ankushrayabhari.nebuloid.core.network.packets.DeleteEntityPacket;
import com.ankushrayabhari.nebuloid.core.network.packets.InputPacket;
import com.ankushrayabhari.nebuloid.core.network.packets.NewEntityPacket;
import com.ankushrayabhari.nebuloid.core.network.packets.PhysicalEntityStatePacket;
import com.ankushrayabhari.nebuloid.core.network.packets.SelectPlayerPacket;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/28/17.
 */

public class ServerNetworkManager extends NetworkManager {
    private static final ServerNetworkManager instance = new ServerNetworkManager();

    public static ServerNetworkManager getInstance() {
        return instance;
    }

    private Server server;

    public ServerNetworkManager() {
        Log.set(Log.LEVEL_NONE);
        server = new Server();
        server.start();
    }

    public void connect() {
        try {
            server.bind(Constants.TCP_PORT, Constants.UDP_PORT);
        } catch(IOException error) {
            error.printStackTrace();
        }

        NetworkManager.setup(server.getKryo());
        Gdx.app.log("Server Network Manager", "Listening on ports " + Constants.TCP_PORT + "/" +  Constants.UDP_PORT);
    }

    public void addListener(Listener listener) {
        server.addListener(listener);
    }

    public void broadcastPhysicalEntityState(PhysicalEntity entity) {
        PhysicalEntityStatePacket packet = new PhysicalEntityStatePacket();
        PhysicalEntity physicalEntity = (PhysicalEntity) entity;
        packet.uuid = physicalEntity.getUuid();
        packet.x = physicalEntity.getPosition().x;
        packet.y = physicalEntity.getPosition().y;
        packet.vX = physicalEntity.getBody().getLinearVelocity().x;
        packet.vY = physicalEntity.getBody().getLinearVelocity().y;
        packet.angle = physicalEntity.getBody().getAngle();
        packet.vAngular = physicalEntity.getBody().getAngularVelocity();
        server.sendToAllTCP(packet);
    }

    public void broadcastNewEntity(Entity entity) {
        NewEntityPacket packet = new NewEntityPacket();
        packet.entityCode = entity.getEntityCode().ordinal();
        packet.uuid = entity.getUuid();

        server.sendToAllTCP(packet);
    }

    public void broadcastDeadEntity(UUID uuid) {
        DeleteEntityPacket packet = new DeleteEntityPacket();
        packet.uuid = uuid;
        server.sendToAllTCP(packet);
    }

    public void selectPlayerInstance(UUID uuid, int connectionId) {
        SelectPlayerPacket packet = new SelectPlayerPacket();
        packet.uuid = uuid;

        server.sendToTCP(connectionId, packet);
    }

    public void broadcastNewEntityToClient(int connectionId, Entity entity) {
        NewEntityPacket packet = new NewEntityPacket();
        packet.entityCode = entity.getEntityCode().ordinal();
        packet.uuid = entity.getUuid();

        server.sendToTCP(connectionId, packet);
    }
}
