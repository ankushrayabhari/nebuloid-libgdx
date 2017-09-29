package com.ankushrayabhari.nebuloid.server;

import com.ankushrayabhari.nebuloid.core.CollisionListener;
import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.client.KeyboardController;
import com.ankushrayabhari.nebuloid.core.entities.EntityComparator;
import com.ankushrayabhari.nebuloid.core.entities.physical.PhysicalEntity;
import com.ankushrayabhari.nebuloid.core.network.NetworkManager;
import com.ankushrayabhari.nebuloid.core.network.packets.InputPacket;
import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.ankushrayabhari.nebuloid.core.network.packets.NewEntityPacket;
import com.ankushrayabhari.nebuloid.core.network.packets.PhysicalEntityStatePacket;
import com.ankushrayabhari.nebuloid.server.entities.ServerEntityFactory;
import com.ankushrayabhari.nebuloid.server.entities.physical.ServerPlayer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ankushrayabhari on 9/26/17.
 */

public class ServerGameEngine extends ApplicationAdapter {
    private World world;
    private LinkedList<Entity> entityList, addList;
    private EntityComparator comparator;
    private HashMap<Integer, ServerPlayer> connectionToPlayerMap;
    private Map<Integer, InputPacket> inputBuffer;

    public ServerGameEngine() {
        world = new World(new Vector2(0,0), false);
        world.setContactListener(new CollisionListener());
        entityList = new LinkedList<Entity>();
        addList = new LinkedList<Entity>();
        connectionToPlayerMap = new HashMap<Integer, ServerPlayer>();
        inputBuffer = new HashMap<Integer, InputPacket>();
        comparator = new EntityComparator();
    }

    @Override
    public void create() {
        ServerNetworkManager.getInstance().connect();
        ServerNetworkManager.getInstance().addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                handleConnect(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                handleDisconnect(connection);
            }

            @Override
            public void received(Connection connection, Object object) {
                handleReceived(connection, object);
            }
        });
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());
    }

    public void update(float delta) {
        world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
        updateEntities(delta);
    }

    private void updateEntities(float delta) {
        synchronized (addList) {
            for(Entity entity : addList) {
                entityList.add(entity);
            }
            addList.clear();
        }

        Collections.sort(entityList, comparator);
        Iterator<Entity> iterator = entityList.iterator();


        synchronized (inputBuffer) {
            while(iterator.hasNext()) {
                Entity entity = iterator.next();

                if(entity instanceof ServerPlayer) {
                    ServerPlayer p = (ServerPlayer) entity;
                    InputPacket lastInput = inputBuffer.get(p.getConnectionID());
                    if(lastInput != null) {
                        p.applyInput(lastInput);
                    }
                    else p.applyInput(new InputPacket());
                }

                if(entity.isDead()) {
                    entity.onDeath();
                    iterator.remove();
                }
                else {
                    entity.update(delta);
                }
            }

            inputBuffer.clear();
        }

        for(Entity entity : entityList) {
            if(entity instanceof PhysicalEntity) {
                ServerNetworkManager.getInstance().broadcastPhysicalEntityState((PhysicalEntity) entity);
            }
        }
    }

    public Entity addEntity(Entity entity) {
        synchronized (addList) {
            addList.add(entity);

            ServerNetworkManager.getInstance().broadcastNewEntity(entity);
        }

        return entity;
    }

    private void handleConnect(Connection connection) {
        System.out.println("new connection: " + connection.getID());
        synchronized (addList) {
            for(Entity entity : entityList) {
                ServerNetworkManager.getInstance().broadcastNewEntityToClient(connection.getID(), entity);
            }
        }
    }

    private void handleDisconnect(Connection connection) {
        System.out.println("disconnected: " + connection.getID());

        ServerPlayer p = connectionToPlayerMap.get(connection.getID());
        if(p != null) p.setDead();

        connectionToPlayerMap.remove(connection.getID());
    }

    private void handleReceived(Connection connection, Object object) {
        if(object instanceof InputPacket) {
            ServerPlayer p = connectionToPlayerMap.get(connection.getID());

            if(p != null)  {
                synchronized (inputBuffer) {
                    inputBuffer.put(connection.getID(), (InputPacket) object);
                }
            }
        } else if(object instanceof NewEntityPacket) {
            NewEntityPacket packet = (NewEntityPacket) object;
            if(Constants.EntityCode.values()[packet.entityCode] == Constants.EntityCode.PLAYER) {
                ServerPlayer p = connectionToPlayerMap.get(connection.getID());

                if(p == null)  {
                    Entity newEnt = ServerEntityFactory.spawn((NewEntityPacket) object, world, connection.getID());
                    addEntity(newEnt);
                    ServerNetworkManager.getInstance().selectPlayerInstance(newEnt.getUuid(), connection.getID());
                    synchronized (connectionToPlayerMap) {
                        connectionToPlayerMap.put(connection.getID(), (ServerPlayer) newEnt);
                    }
                }
            }
        }
    }
}
