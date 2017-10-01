package com.ankushrayabhari.nebuloid.core;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by ankushrayabhari on 9/30/17.
 */

public class Map {

    public Map(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        ChainShape shape = new ChainShape();
        shape.createChain(new float[]{
                0, 0,
                0, Constants.MAP_HEIGHT,
                Constants.MAP_WIDTH, Constants.MAP_HEIGHT,
                Constants.MAP_WIDTH, 0,
                0, 0
        });

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1000;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_WORLD;
        fixtureDef.filter.maskBits = Constants.MASK_WORLD;

        synchronized (world) {
            world.createBody(bodyDef).createFixture(fixtureDef);
        }

        shape.dispose();
    }

    public int getWidth() {
        return Constants.MAP_WIDTH;
    }

    public int getHeight() {
        return Constants.MAP_HEIGHT;
    }
}
