package com.ankushrayabhari.nebuloid.core.entities.physical;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.network.packets.InputPacket;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 9/27/17.
 */

public class Player extends PhysicalEntity {
    private float health;
    private InputPacket input;
    private final int rotateSpeed = 2;
    private final int speed = 1000;
    private Vector2 moveDir;
    private float angularVelocity;

    public Player(World world, UUID uuid) {
        super(world, getConfig(), Constants.EntityCode.PLAYER.ordinal(), uuid);
        input = new InputPacket();
        moveDir = new Vector2(0,0);
        angularVelocity = 0;
    }

    @Override
    public void update(float delta) {
        if(input.moveLeft) {
            angularVelocity = 1;
        }
        else if(input.moveRight) {
            angularVelocity = -1;
        }
        else {
            angularVelocity = 0;
        }
        angularVelocity *= rotateSpeed;

        float angle = this.getBody().getAngle();
        moveDir.set(MathUtils.cos(angle), MathUtils.sin(angle));

        if(input.moveUp) {
            moveDir.scl(speed);
        }
        else if(input.moveDown) {
            moveDir.scl(-speed);
        }
        else {
            moveDir.scl(0);
        }

        this.getBody().setAngularVelocity(angularVelocity);
        this.getBody().setLinearVelocity(moveDir);
    }

    @Override
    public void onCollide(PhysicalEntity entity) {

    }

    @Override
    public void endCollide(PhysicalEntity entity) {

    }

    private static PhysicalEntityConfig getConfig() {
        PhysicalEntityConfig config = new PhysicalEntityConfig();
        config.initialPosition = new Vector2(175, 1875);
        config.angle = 90 * MathUtils.degreesToRadians;
        config.dimensions = new Vector2(15,15);
        config.massive = false;
        config.zIndex = 10;
        config.type = Constants.PhysicalEntityTypes.ALLY;
        config.staticBody = false;
        return config;
    }

    public void applyInput(InputPacket packet) {
        this.input = packet;
    }
}
