package com.ankushrayabhari.nebuloid.client;

import com.ankushrayabhari.nebuloid.client.entities.ClientEntityFactory;
import com.ankushrayabhari.nebuloid.client.entities.Drawable;
import com.ankushrayabhari.nebuloid.client.entities.physical.ClientPlayer;
import com.ankushrayabhari.nebuloid.core.CollisionListener;
import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.ankushrayabhari.nebuloid.core.entities.EntityComparator;
import com.ankushrayabhari.nebuloid.core.entities.physical.PhysicalEntity;
import com.ankushrayabhari.nebuloid.core.network.packets.DeleteEntityPacket;
import com.ankushrayabhari.nebuloid.core.network.packets.InputPacket;
import com.ankushrayabhari.nebuloid.core.network.packets.NewEntityPacket;
import com.ankushrayabhari.nebuloid.core.network.packets.PhysicalEntityStatePacket;
import com.ankushrayabhari.nebuloid.core.network.packets.SelectPlayerPacket;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class ClientGameEngine implements Disposable {
    private SpriteBatch batch;
    private World world;
    private ClientMap map;
    private OrthographicCamera camera;
    private KeyboardController inputController;
    private HashMap<UUID, Entity> idToEntityMap;
    private HashMap<UUID, PhysicalEntityStatePacket> lastReceivedState;
    private LinkedList<Entity> entityList, addList;
    private UUID playerId;
    private EntityComparator comparator;

    public ClientGameEngine() {
        idToEntityMap = new HashMap<UUID, Entity>();
        playerId = null;
        world = new World(new Vector2(0,0), false);
        world.setContactListener(new CollisionListener());
        entityList = new LinkedList<Entity>();
        addList = new LinkedList<Entity>();
        comparator = new EntityComparator();
        lastReceivedState = new HashMap<UUID, PhysicalEntityStatePacket>();
    }

    public void init() {
        camera = new OrthographicCamera(
                Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2
        );

        batch = new SpriteBatch();
        map = new ClientMap(world, camera);

        inputController = new KeyboardController();
        Gdx.input.setInputProcessor(inputController);

        ClientNetworkManager.getInstance().connect();
        ClientNetworkManager.getInstance().addListener(new Listener() {
            public void received (Connection connection, Object object) {
                handleReceived(connection, object);
            }
        });

        ClientNetworkManager.getInstance().requestSpawnEntity(Constants.EntityCode.PLAYER);
    }

    public void render(float delta) {
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (playerId == null) return;
        ClientPlayer player = (ClientPlayer) idToEntityMap.get(playerId);
        if(player == null) return;


        InputPacket packet = ClientNetworkManager.getInstance().sendInputPacket(inputController, camera);
        player.applyInput(packet);

        world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
        Collections.sort(entityList, comparator);
        updateEntities(delta);

        updateCamera(camera, player.getPosition());

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(batch);
        drawEntities(batch);
        batch.end();
    }

    private void updateCamera(Camera camera, Vector2 playerPosition) {
        Vector2 position = playerPosition.cpy();

        float halfWidth = Gdx.graphics.getWidth() / 4;
        float halfHeight = Gdx.graphics.getHeight() / 4;

        if(position.x < halfWidth) position.x = halfWidth;
        else if(position.x > map.getWidth() - halfWidth) position.x = map.getWidth() - halfWidth;

        if(position.y < halfHeight) position.y = halfHeight;
        else if(position.y > map.getHeight() - halfHeight) position.y = map.getHeight() - halfHeight;

        camera.position.set(position, 0);
        camera.update();
    }

    private void updateEntities(float delta) {
        synchronized (addList) {
            for(Entity entity : addList) {
                entityList.add(entity);
                idToEntityMap.put(entity.getUuid(), entity);
            }
            addList.clear();
        }

        Collections.sort(entityList, comparator);
        Iterator<Entity> iterator = entityList.iterator();

        while(iterator.hasNext()) {
            Entity entity = iterator.next();

            if(entity.isDead()) {
                entity.onDeath();
                iterator.remove();
            } else {
                entity.update(delta);
            }

            PhysicalEntityStatePacket state = lastReceivedState.get(entity.getUuid());
            if(state != null) {
                Body body = ((PhysicalEntity) entity).getBody();
                Vector2 position = body.getPosition();

                float xDelta = Math.abs(position.x - (state.x + state.vX * Constants.TIME_STEP));
                float yDelta = Math.abs(position.y - (state.y + state.vY * Constants.TIME_STEP));

                boolean xInSync = xDelta < 0.01;
                boolean yInSync = yDelta < 0.01;
                boolean angleInSync = Math.abs(body.getAngle() - (state.angle + state.vAngular * Constants.TIME_STEP)) < 0.001;

                Vector2 newPos = body.getPosition();
                Vector2 newVelocity = body.getLinearVelocity();
                float angle = body.getAngle();
                float angularVelocity = body.getAngularVelocity();

                if(!xInSync || !yInSync) {
                    newPos.x = state.x;
                    newPos.y = state.y;
                    newVelocity.x = state.vX;
                    newVelocity.y = state.vY;
                }

                if(!angleInSync) {
                    angle = state.angle;
                    angularVelocity = state.vAngular;
                }

                body.setTransform(newPos, angle);
                body.setLinearVelocity(newVelocity);
                body.setAngularVelocity(angularVelocity);
            }
        }
    }

    private void drawEntities(SpriteBatch batch) {
        Vector3 upperBound = camera.unproject(new Vector3(Gdx.graphics.getWidth()+200, -Gdx.graphics.getHeight()-200, 0));
        Vector3 lowerBound = camera.unproject(new Vector3(-Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()+200, 0));

        for(Entity entity : entityList) {
            if(entity instanceof Drawable) {
                Vector2 position = entity.getPosition();
                if ((position.x > lowerBound.x && position.x < upperBound.x) && (position.y > lowerBound.y && position.y < upperBound.y)) {
                    ((Drawable) entity).draw(batch);
                }
            }

        }
    }

    private void addEntity(Entity e) {
        synchronized (addList) {
            addList.add(e);
        }

        idToEntityMap.put(e.getUuid(), e);
    }

    private void deleteEntity(UUID uuid) {
        Entity ent = idToEntityMap.get(uuid);
        if(ent != null) ent.setDead();
        idToEntityMap.remove(uuid);
    }

    private void setPlayerId(UUID id) {
        playerId = id;
    }

    private void updatePhysicalEntityState(PhysicalEntityStatePacket entityStatePacket) {
        lastReceivedState.put(entityStatePacket.uuid, entityStatePacket);
    }

    private void handleReceived (Connection connection, final Object object) {
        if(object instanceof SelectPlayerPacket) {
            setPlayerId(((SelectPlayerPacket) object).uuid);
        } else if(object instanceof NewEntityPacket) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    addEntity(ClientEntityFactory.spawn((NewEntityPacket) object, world));
                }
            });
        } else if(object instanceof DeleteEntityPacket) {
            deleteEntity(((DeleteEntityPacket) object).uuid);
        } else if(object instanceof PhysicalEntityStatePacket) {
            updatePhysicalEntityState((PhysicalEntityStatePacket) object);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        world.dispose();
    }
}
