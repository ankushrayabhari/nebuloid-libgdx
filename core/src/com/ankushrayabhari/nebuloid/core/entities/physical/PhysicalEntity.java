package com.ankushrayabhari.nebuloid.core.entities.physical;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.entities.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public abstract class PhysicalEntity extends Entity {
    private Body body;
    private World world;
    private Vector2 dimensions;

    protected PhysicalEntity(World world, PhysicalEntityConfig config, int code, UUID uuid) {
        super(config.zIndex, code, uuid);
        this.world = world;
        this.dimensions = config.dimensions;

        BodyDef bodyDef = new BodyDef();
        if(config.staticBody) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }
        bodyDef.position.set(config.initialPosition);
        bodyDef.fixedRotation = true;
        bodyDef.angle = config.angle;

        synchronized (world) {
            body = world.createBody(bodyDef);
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(config.dimensions.x/2, config.dimensions.y/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        if(config.massive) {
            fixtureDef.density = 1000;
        }
        else {
            fixtureDef.density = 0;
        }
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        switch (config.type) {
            case ALLY:
                fixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY;
                fixtureDef.filter.maskBits = Constants.MASK_ALLY;
                break;
            case ENEMY:
                fixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY;
                fixtureDef.filter.maskBits = Constants.MASK_ENEMY;
                break;
            case ALLY_PROJECTILE:
                fixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_PROJECTILE;
                fixtureDef.filter.maskBits = Constants.MASK_ALLY_PROJECTILE;
                body.setBullet(true);
                break;
            case ENEMY_PROJECTILE:
                fixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY_PROJECTILE;
                fixtureDef.filter.maskBits = Constants.MASK_ENEMY_PROJECTILE;
                body.setBullet(true);
                break;
            case MESSAGE:
                fixtureDef.filter.categoryBits = Constants.CATEGORY_MESSAGE;
                fixtureDef.filter.maskBits = Constants.MASK_MESSAGE;
                break;
            case LOOTBAG:
                fixtureDef.filter.categoryBits = Constants.CATEGORY_LOOTBAG;
                fixtureDef.filter.maskBits = Constants.MASK_LOOTBAG;
                fixtureDef.density = 0;
        }
        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();

        this.setPosition(body.getPosition());
    }

    protected void destroyBody() {
        this.world.destroyBody(body);
    }

    public abstract void onCollide(PhysicalEntity entity);
    public abstract void endCollide(PhysicalEntity entity);

    @Override
    public void onDeath() {
        this.destroyBody();
    }

    public Body getBody() { return body; }
    public Vector2 getDimensions() { return dimensions; }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }
}
