package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blurrymeal.angrybirds.Main;

import java.util.ArrayList;
import java.util.List;

public class BlueBird {
    private Texture texture;
    private Body body;
    private float width;
    private float height;
    private float maxDamage = 40f;
    private boolean specialAbilityActivated = false;
    private List<BlueBird> splitBirds = new ArrayList<>();

    private boolean inMotion;

    public BlueBird(Texture texture, World world, float x, float y, float width, float height) {
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / Main.PPM, y / Main.PPM);
        this.body = world.createBody(bodyDef);

        body.setUserData(this);


        CircleShape shape = new CircleShape();
        shape.setRadius((texture.getWidth() / 2) / Main.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 200.0f;
        fixtureDef.friction = 5.0f;
        fixtureDef.restitution = 0.1f;



        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("BlueBird");


        System.out.println(fixture.getUserData());

        body.setLinearDamping(2f);

        shape.dispose();

        inMotion = false;
    }

    public void update(float deltaTime) {
        if (!inMotion) {
            body.setType(BodyDef.BodyType.StaticBody);
        }
    }

    public boolean hasStopped() {
        return Math.abs(body.getLinearVelocity().x) < 0.1f &&
            Math.abs(body.getLinearVelocity().y) < 0.1f;
    }

    public float calculateDamageForce(float impactVelocity) {
        float normalizedDamage = Math.min(Math.abs(impactVelocity) / 20f, 1f);
        return normalizedDamage * maxDamage;
    }

    public void launch(Vector2 initialVelocity) {
        body.setType(BodyDef.BodyType.DynamicBody);
        body.applyLinearImpulse(initialVelocity, body.getWorldCenter(), true);
        inMotion = true;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * Main.PPM - texture.getWidth() / 2,
            body.getPosition().y * Main.PPM - texture.getHeight() / 2);
    }



    public Vector2 getPosition() {
        return body.getPosition().scl(Main.PPM);
    }

    public boolean isInMotion() {
        return inMotion;
    }

    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void activateAbility() {
        if (inMotion && !specialAbilityActivated) {
            System.out.println("Blue Bird special ability: Splitting into three birds!");
            specialAbilityActivated = true;

            // Get the current world, position, and velocity
            World world = body.getWorld();
            Vector2 currentPosition = body.getPosition();
            Vector2 currentVelocity = body.getLinearVelocity();

            // Define angle variations for splitting
            float[] angles = {-30, 30}; // Angles in degrees for left and right birds

            // Create and launch split birds
            for (float angle : angles) {
                // Rotate velocity vector for each split bird
                Vector2 splitVelocity = new Vector2(currentVelocity);
                splitVelocity.setAngleDeg(splitVelocity.angleDeg() + angle);

                // Create new split bird
                BlueBird splitBird = new BlueBird(
                    texture,
                    world,
                    currentPosition.x * Main.PPM, // Adjust position slightly
                    currentPosition.y * Main.PPM,
                    width,
                    height
                );

                // Launch the bird with the rotated velocity
                splitBird.launch(splitVelocity);
                splitBirds.add(splitBird);
            }
        }
    }

    public List<BlueBird> getSplitBirds() {
        return splitBirds;
    }
}
