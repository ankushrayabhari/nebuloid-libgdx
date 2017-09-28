package com.ankushrayabhari.nebuloid.core;

import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.ankushrayabhari.nebuloid.core.entities.EntityComparator;
import com.ankushrayabhari.nebuloid.client.entities.ClientPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class GameEngine {/*
    private SpriteBatch batch;
    private Map map;
    private OrthographicCamera camera;
    private World world;
    private KeyboardController inputController;
    private LinkedList<Entity> entityList, addList;
    private EntityComparator comparator;
    private ClientPlayer player;
    private Box2DDebugRenderer debugRenderer;

    public GameEngine() {
        world = new World(new Vector2(0,0), false);
        world.setContactListener(new CollisionListener());
        entityList = new LinkedList<Entity>();
        addList = new LinkedList<Entity>();
    }

    public void init() {
        camera = new OrthographicCamera(
                Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2
        );

        batch = new SpriteBatch();
        map = new Map();

        inputController = new KeyboardController();
        Gdx.input.setInputProcessor(inputController);

        comparator = new EntityComparator();

        player = (ClientPlayer) addEntity(new ClientPlayer(this));
        debugRenderer = new Box2DDebugRenderer();
    }

    public void update(float delta) {
        world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
        updateEntities(delta);
    }

    public void render(float delta) {
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 pos = player.getBody().getPosition();
        float x = pos.x, y = pos.y;

        if(pos.x < Gdx.graphics.getWidth() / 4) {
            x = Gdx.graphics.getWidth() / 4;
        }
        else if(pos.x > 2048 - Gdx.graphics.getWidth() / 4) {
            x = 2048 - Gdx.graphics.getWidth() / 4;
        }

        if(pos.y < Gdx.graphics.getHeight() / 4) {
            y = Gdx.graphics.getHeight() / 4;
        }
        else if(pos.y > 2048 - Gdx.graphics.getHeight() / 4) {
            y = 2048 - Gdx.graphics.getHeight() / 4;
        }

        camera.position.set(x, y, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(batch);
        drawEntities(batch);
        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    public Entity addEntity(Entity entity) {
        addList.add(entity);
        return entity;
    }

    private void drawEntities(SpriteBatch batch) {
        for(Entity entity : entityList) {
            Vector3 upperBound = camera.unproject(new Vector3(Gdx.graphics.getWidth()+200, -Gdx.graphics.getHeight()-200, 0));
            Vector3 lowerBound = camera.unproject(new Vector3(-Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()+200, 0));
            Vector2 position = entity.getPosition();
            if ((position.x > lowerBound.x && position.x < upperBound.x) && (position.y > lowerBound.y && position.y < upperBound.y)) {
                entity.draw(batch);
            }
        }
    }

    private void updateEntities(float delta) {
        for(Entity entity : addList) {
            entityList.add(entity);
        }
        addList.clear();

        Collections.sort(entityList, comparator);
        Iterator<Entity> iterator = entityList.iterator();
        while(iterator.hasNext()) {
            Entity entity = iterator.next();
            if(entity.isDead()) {
                entity.onDeath();
                iterator.remove();
            }
            else {
                entity.update(delta);
            }
        }
    }

    public KeyboardController getInputController() {
        return inputController;
    }

    public World getWorld() {
        return world;
    }

    public void dispose() {
        map.dispose();
    }
*/}
