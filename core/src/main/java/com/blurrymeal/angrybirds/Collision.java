//package com.blurrymeal.angrybirds;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.*;
//import com.blurrymeal.angrybirds.entities.Obstacles;
//import com.blurrymeal.angrybirds.entities.Pigs;
//import com.blurrymeal.angrybirds.entities.RedBird;
//import com.blurrymeal.angrybirds.states.Level01State;
//
//public class Collision implements ContactListener {
//    private static final float DESTRUCTION_THRESHOLD = 50.0f;
//
//
//    @Override
//    public void beginContact(Contact contact) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//        System.out.println("WORKS");
//
//        Object objA = fixtureA.getUserData();
//        Object objB = fixtureB.getUserData();
//
//        if (objA instanceof RedBird && objB instanceof Pigs) {
//            handleBirdPigCollision((RedBird) objA, (Pigs) objB);
//        } else if (objA instanceof Pigs && objB instanceof RedBird) {
//            handleBirdPigCollision((RedBird) objB, (Pigs) objA);
//        }
//
//        if (objA instanceof RedBird && objB instanceof Obstacles) {
//            handleBirdObstacleCollision((RedBird) objA, (Obstacles) objB);
//        } else if (objA instanceof Obstacles && objB instanceof RedBird) {
//            handleBirdObstacleCollision((RedBird) objB, (Obstacles) objA);
//        }
//    }
//
//    @Override
//    public void endContact(Contact contact) {
//    }
//
//    @Override
//    public void preSolve(Contact contact, Manifold manifold) {
//    }
//
//    @Override
//    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//
//        float[] normalImpulses = contactImpulse.getNormalImpulses();
//        if (normalImpulses.length > 0 && normalImpulses[0] > DESTRUCTION_THRESHOLD) {
//            handleObjectDestruction(fixtureA.getBody(), fixtureB.getBody());
//        }
//    }
//
//    private void handleBirdPigCollision(RedBird bird, Pigs pig) {
//        pig.takeDamage(10f);
//
//        Vector2 birdVelocity = bird.getBody().getLinearVelocity();
//        Vector2 impulse = birdVelocity.scl(0.06f);
//        pig.getBody().applyLinearImpulse(impulse, pig.getBody().getWorldCenter(), true);
//
//        pig.getBody().applyAngularImpulse(10f, true);
//
//        bird.getBody().setLinearVelocity(0, 0);
//        bird.getBody().setAngularVelocity(0);
//    }
//
//    private void handleBirdObstacleCollision(RedBird bird, Obstacles obstacle) {
//        float angularVelocity = obstacle.getBody().getAngularVelocity();
//
//        Vector2 birdVelocity = bird.getBody().getLinearVelocity();
//        Vector2 impulse = birdVelocity.scl(1.5f);
//        obstacle.getBody().applyLinearImpulse(impulse, obstacle.getBody().getWorldCenter(), true);
//
//        obstacle.getBody().applyTorque(10f, true);
//        obstacle.getBody().setLinearDamping(0.5f);
//        obstacle.getBody().applyAngularImpulse(15f, true);
//        obstacle.getBody().setFixedRotation(false);
//
//        Vector2 currentVelocity = bird.getBody().getLinearVelocity();
//        bird.getBody().setLinearVelocity(currentVelocity.x * -0.5f, currentVelocity.y * -0.5f);
//    }
//
//    private void handleObjectDestruction(Body bodyA, Body bodyB) {
//        Object objectA = bodyA.getUserData();
//        Object objectB = bodyB.getUserData();
//
//        if (objectA instanceof Obstacles) {
//            destroyObstacle((Obstacles) objectA, bodyA);
//        }
//        if (objectB instanceof Obstacles) {
//            destroyObstacle((Obstacles) objectB, bodyB);
//        }
//
//        if (objectA instanceof Pigs) {
//            destroyPig((Pigs) objectA, bodyA);
//        }
//        if (objectB instanceof Pigs) {
//            destroyPig((Pigs) objectB, bodyB);
//        }
//    }
//
//    private void destroyObstacle(Obstacles obstacle, Body body) {
//        body.getWorld().destroyBody(body);
//        obstacle.dispose();
//    }
//
//    private void destroyPig(Pigs pig, Body body) {
//        body.getWorld().destroyBody(body);
//        pig.dispose();
//    }
//}
