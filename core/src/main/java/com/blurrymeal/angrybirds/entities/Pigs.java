
package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blurrymeal.angrybirds.Main;

public class Pigs {
    private Texture texture;
    private float width;
    private float height;
    private Body body;


    public Pigs(Texture texture, float x, float y, float width, float height, World world) {
        this.texture = texture;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / Main.PPM, y / Main.PPM);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f/Main.PPM, height / 2f/Main.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Not relevant for static bodies
        fixtureDef.friction = 0.5f; // Adjust as needed
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update(float deltaTime) {
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * Main.PPM - width / 2f,
            body.getPosition().y * Main.PPM - height / 2f,
            width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
