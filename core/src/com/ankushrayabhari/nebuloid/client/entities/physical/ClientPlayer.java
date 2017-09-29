package com.ankushrayabhari.nebuloid.client.entities.physical;


import com.ankushrayabhari.nebuloid.client.entities.Drawable;
import com.ankushrayabhari.nebuloid.core.entities.physical.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

import java.util.UUID;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class ClientPlayer extends Player implements Drawable {
    private Sprite sprite;

    public ClientPlayer(World world, UUID uuid) {
        super(world, uuid);
        sprite = new Sprite(new Texture("playerShip1_green.png"));
        sprite.setSize(this.getDimensions().x, this.getDimensions().y);
        sprite.setOrigin(this.getDimensions().x / 2, this.getDimensions().y / 2);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(this.getPosition().x - this.getDimensions().x / 2, this.getPosition().y - this.getDimensions().y / 2);
        sprite.setRotation(this.getBody().getAngle() * MathUtils.radiansToDegrees - 90);
        sprite.draw(batch);
    }

}