package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blurrymeal.angrybirds.Main;

public class YellowBird {
    private Texture texture;
    private Body body;
    private float width;
    private float height;
    private float maxDamage = 40f;

    private boolean inMotion;

    public YellowBird(Texture texture, World world, float x, float y, float width, float height) {
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / Main.PPM, y / Main.PPM);
        this.body = world.createBody(bodyDef);

        body.setUserData(this);


        CircleShape shape = new CircleShape();
        shape.setRadius((width / 2) / Main.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100.0f;
        fixtureDef.friction = 5.0f;
        fixtureDef.restitution = 0.1f;



        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("YellowBird");


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

    public void activateAbility() {

        Vector2 currentVelocity = getBody().getLinearVelocity();
        Vector2 boostedVelocity = currentVelocity.scl(2);
        getBody().setLinearVelocity(boostedVelocity);

        System.out.println("Special ability activated! Speed doubled.");
    }
}
