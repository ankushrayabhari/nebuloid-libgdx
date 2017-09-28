package com.ankushrayabhari.nebuloid.core;

import com.ankushrayabhari.nebuloid.core.entities.physical.PhysicalEntity;
import com.ankushrayabhari.nebuloid.core.entities.physical.PhysicalEntity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {
        PhysicalEntity eA = (PhysicalEntity) contact.getFixtureA().getBody().getUserData();
        PhysicalEntity eB = (PhysicalEntity) contact.getFixtureB().getBody().getUserData();
        if(eA != null) eA.endCollide(eB);
        if(eB != null) eB.endCollide(eA);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        PhysicalEntity eA = (PhysicalEntity) contact.getFixtureA().getBody().getUserData();
        PhysicalEntity eB = (PhysicalEntity) contact.getFixtureB().getBody().getUserData();
        if(eA != null) eA.onCollide(eB);
        if(eB != null) eB.onCollide(eA);
    }
}
