package com.ankushrayabhari.nebuloid.core;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class GameEngine {/*


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
