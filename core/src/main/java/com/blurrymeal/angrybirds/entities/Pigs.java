
package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blurrymeal.angrybirds.Main;

public class Pigs implements ContactListener {
    private Texture texture;
    private float width;
    private float height;
    private Body body;
    private float health = 100f;
    private World world;
    private boolean isDestroyed = false;
    private float GROUND_HEIGHT;
    private int pigScore = 800;


    public Pigs(Texture texture, float x, float y, float width, float height, World world, float groundHeight) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.world = world;
        this.GROUND_HEIGHT = groundHeight;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Main.PPM, y / Main.PPM);
        this.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f/Main.PPM);

        body.setUserData(this);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Not relevant for static bodies
        fixtureDef.friction = 0.5f; // Adjust as needed
        fixtureDef.restitution = 0.1f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Pig");

        System.out.println(fixture.getUserData());

        body.setLinearDamping(4f);
        world.setContactListener(this);

        shape.dispose();
    }

    public int getScore(){
        return pigScore;
    }

    public void setPigScore(int pigScore){
        this.pigScore = pigScore;
    }

    public void update(float deltaTime) {
        if (body.getPosition().y * Main.PPM <= GROUND_HEIGHT +20) {
            destroy();
        }

        if (isDestroyed) {
            world.destroyBody(body);
        }

    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * Main.PPM - width / 2f,
            body.getPosition().y * Main.PPM - height / 2f,
            width, height);
    }

    public void dispose() {
        texture.dispose();
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            destroy();
        }
    }
    private void destroy() {
        isDestroyed = true;
    }
    public Body getBody() {
        return body;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        System.out.println("help");

        if((fixtureA.getUserData().equals("Pig") && fixtureB.getUserData().equals("RedBird")) || (fixtureA.getUserData().equals("RedBird") && fixtureB.getUserData().equals("Pig"))) {
            Pigs pig = null;
            if (fixtureA.getUserData().equals("Pig")) {
                pig = (Pigs) fixtureA.getBody().getUserData();
            } else {
                pig = (Pigs) fixtureB.getBody().getUserData();
            }

            if (pig != null) {
                pig.destroy();
            }
        }

        if((fixtureA.getUserData().equals("Pig") && fixtureB.getUserData().equals("BlueBird")) || (fixtureA.getUserData().equals("BlueBird") && fixtureB.getUserData().equals("Pig"))) {
            Pigs pig = null;
            if (fixtureA.getUserData().equals("Pig")) {
                pig = (Pigs) fixtureA.getBody().getUserData();
            } else {
                pig = (Pigs) fixtureB.getBody().getUserData();
            }

            if (pig != null) {
                pig.destroy();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
