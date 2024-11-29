package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.blurrymeal.angrybirds.Main;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Obstacles implements ContactListener {
    private Texture texture;
    private Vector2 position;
    private float width;
    private float height;
    private Body body;
    private float rotation; // New rotation parameter
    private World world;
    private float health;


    public Obstacles(Texture texture, float x, float y,float width, float height ,World world, float initialRotation) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.rotation = initialRotation;
        this.world = world;
        this.health = 50f;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((x + width / 2) / Main.PPM, (y + height / 2) / Main.PPM);
        bodyDef.angle = rotation * MathUtils.degreesToRadians; // Convert rotation to radians

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Main.PPM, height / 2 / Main.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 2f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setLinearDamping(2f);
        body.setFixedRotation(false);

        world.setContactListener(this);

        shape.dispose();
    }

    public void update(float deltaTime) {
        // Update position and rotation
        position.set(body.getPosition().x * Main.PPM, body.getPosition().y * Main.PPM);
        rotation = body.getAngle() * MathUtils.radiansToDegrees;

    }

    public void render(SpriteBatch batch) {
        // Render with rotation
        batch.draw(texture,
            position.x - width / 2,
            position.y - height / 2,
            width / 2,
            height / 2,
            width,
            height,
            1f,
            1f,
            rotation,
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false,
            false);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == this || fixtureB.getUserData() == this) {
            Body otherBody = (fixtureA.getUserData() == this) ?
                fixtureB.getBody() : fixtureA.getBody();

            Vector2 relativeVelocity = body.getLinearVelocity().sub(otherBody.getLinearVelocity());

            float rotationForce = Math.abs(relativeVelocity.len()) * 5f;

            float rotationDirection = Math.signum(MathUtils.random(-1f, 1f));

            body.applyAngularImpulse(rotationForce * rotationDirection, true);
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

    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return this.body;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        body.setTransform(body.getPosition(), rotation * MathUtils.degreesToRadians);
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getWidth() {
        return texture.getWidth();
    }

    public float getHeight(){
        return texture.getHeight();
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
