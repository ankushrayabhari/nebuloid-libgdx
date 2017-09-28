package com.ankushrayabhari.nebuloid.server;

import com.ankushrayabhari.nebuloid.core.CollisionListener;
import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.KeyboardController;
import com.ankushrayabhari.nebuloid.core.entities.EntityComparator;
import com.ankushrayabhari.nebuloid.core.network.NetworkManager;
import com.ankushrayabhari.nebuloid.core.network.InputPacket;
import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.ankushrayabhari.nebuloid.core.entities.physical.Player;
import com.ankushrayabhari.nebuloid.server.entities.physical.ServerPlayer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.BooleanArray;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ankushrayabhari on 9/26/17.
 */

public class ServerGameEngine extends ApplicationAdapter {
    Server server;
    private World world;
    private KeyboardController inputController;
    private LinkedList<Entity> entityList, addList;
    private EntityComparator comparator;
    private HashMap<Integer, ServerPlayer> connectionToPlayerMap;
    private Map<Integer, InputPacket> inputBuffer;
    private Vector2 pos = null;


    public ServerGameEngine() {
        world = new World(new Vector2(0,0), false);
        world.setContactListener(new CollisionListener());
        entityList = new LinkedList<Entity>();
        addList = new LinkedList<Entity>();
        connectionToPlayerMap = new HashMap<Integer, ServerPlayer>();
        inputBuffer = new HashMap<Integer, InputPacket>();
    }

    @Override
    public void create() {
        Log.set(Log.LEVEL_DEBUG);
        server = new Server();

        NetworkManager.registerClasses(server.getKryo());

        try {
            server.start();
            server.bind(Constants.TCP_PORT, Constants.UDP_PORT);
        } catch(IOException error) {
            error.printStackTrace();
        }

        server.addListener(new Listener() {
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

        if(entityList.size() > 0) {
            ServerPlayer p = (ServerPlayer) entityList.get(0);

            System.out.println("Position" + p.getPosition() + " " + p.getBody().getPosition());
        }

        world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
        updateEntities(delta);

    }

    private void updateEntities(float delta) {
        for(Entity entity : addList) {
            entityList.add(entity);
        }
        addList.clear();

        Collections.sort(entityList, comparator);
        Iterator<Entity> iterator = entityList.iterator();


        synchronized (inputBuffer) {
            while(iterator.hasNext()) {
                Entity entity = iterator.next();

                if(entity instanceof ServerPlayer) {
                    ServerPlayer p = (ServerPlayer) entity;
                    InputPacket lastInput = inputBuffer.get(p.getConnectionID());
                    if(lastInput != null) p.applyInput(lastInput);
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
    }

    public Entity addEntity(Entity entity) {
        addList.add(entity);
        return entity;
    }

    private void handleConnect(Connection connection) {
        System.out.println("new connection: " + connection.getID());
        connectionToPlayerMap.put(connection.getID(), (ServerPlayer) addEntity(new ServerPlayer(world, connection.getID())));
    }

    private void handleDisconnect(Connection connection) {
        System.out.println("disconnected: " + connection.getID());

        ServerPlayer p = connectionToPlayerMap.get(connection.getID());
        if(p != null) p.setDead();

        connectionToPlayerMap.remove(connection.getID());
    }

    private void handleReceived(Connection connection, Object object) {
        System.out.println("received info: " + connection.getID());

        if(object instanceof InputPacket) {
            ServerPlayer p = connectionToPlayerMap.get(connection.getID());

            if(p != null)  {
                synchronized (inputBuffer) {
                    inputBuffer.put(p.getConnectionID(), (InputPacket) object);
                }
            }
        }
    }
}
