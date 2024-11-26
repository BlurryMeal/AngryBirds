package com.blurrymeal.angrybirds;

import com.badlogic.gdx.physics.box2d.*;
import com.blurrymeal.angrybirds.entities.Obstacles;
import com.blurrymeal.angrybirds.entities.Pigs;
import com.blurrymeal.angrybirds.entities.RedBird;
import com.badlogic.gdx.physics.box2d.ContactImpulse;

public abstract class Collision implements ContactListener {
    private static final float DESTRUCTION_THRESHOLD = 50.0f;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object objA = fixtureA.getUserData();
        Object objB = fixtureB.getUserData();

        if (objA instanceof RedBird && objB instanceof Pigs) {
            handleBirdPigCollision((RedBird)objA, (Pigs)objB);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        float[] normalImpulses = contactImpulse.getNormalImpulses();
        if (normalImpulses[0] > DESTRUCTION_THRESHOLD) {
            handleObjectDestruction(fixtureA.getBody(), fixtureB.getBody());
        }
    }
    private void handleBirdPigCollision(RedBird bird, Pigs pig) {
        // Implement pig destruction logic
        pig.takeDamage(10f);
//        bird.applySpecialEffect(); // Potential bird-specific interaction
    }

    private void handleObjectDestruction(Body bodyA, Body bodyB) {
        // Logic for determining object destruction based on collision force
        Object objectA = bodyA.getUserData();
        Object objectB = bodyB.getUserData();

        // Check if objects can be destroyed
//        if (objectA instanceof Obstacles) {
//            ((Obstacles)objectA).damage();
//        }
//        if (objectB instanceof Obstacles) {
//            ((Obstacles)objectB).damage();
//        }
    }


}

