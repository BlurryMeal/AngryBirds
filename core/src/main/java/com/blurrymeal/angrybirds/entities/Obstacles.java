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

    public Obstacles(Texture texture, float x, float y, World world, float initialRotation) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.rotation = initialRotation; // Set initial rotation
        this.world = world;
        this.health = 50f;

        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((x + width / 2) / Main.PPM, (y + height / 2) / Main.PPM);
        bodyDef.angle = rotation * MathUtils.degreesToRadians; // Convert rotation to radians

        // Create body
        body = world.createBody(bodyDef);

        // Create shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Main.PPM, height / 2 / Main.PPM);

        // Create fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 2f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        // Configure body properties
        body.setLinearDamping(2f);
        body.setFixedRotation(false);

        // Register contact listener
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
        // Check if this obstacle is involved in the collision
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == this || fixtureB.getUserData() == this) {
            // Get the other body involved in the collision
            Body otherBody = (fixtureA.getUserData() == this) ?
                fixtureB.getBody() : fixtureA.getBody();

            // Calculate rotation based on relative velocity
            Vector2 relativeVelocity = body.getLinearVelocity().sub(otherBody.getLinearVelocity());

            // Determine rotation force based on collision intensity
            float rotationForce = Math.abs(relativeVelocity.len()) * 5f;

            // Apply a random rotation direction
            float rotationDirection = Math.signum(MathUtils.random(-1f, 1f));

            // Apply angular impulse
            body.applyAngularImpulse(rotationForce * rotationDirection, true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Optional: Add any end contact logic if needed
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Optional: Add any pre-solve logic if needed
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Optional: Add any post-solve logic if needed
    }

    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return this.body;
    }

    // Getter and setter for rotation
    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        body.setTransform(body.getPosition(), rotation * MathUtils.degreesToRadians);
    }
}
