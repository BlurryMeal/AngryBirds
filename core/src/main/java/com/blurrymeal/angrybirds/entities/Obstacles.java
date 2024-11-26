
package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.blurrymeal.angrybirds.Main;
import com.blurrymeal.angrybirds.states.State;
import com.badlogic.gdx.physics.box2d.*;


public class Obstacles {
    private Texture texture;
    private Vector2 position;
    private float width;
    private float height;
    private Body body;

    public Obstacles(Texture texture, float x, float y, World world) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) / Main.PPM, (y + height / 2) / Main.PPM); // Center position

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Main.PPM, height / 2 / Main.PPM); // Half-width and half-height

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f; // Optional
        fixtureDef.restitution = 0.1f; // Optional

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update(float deltaTime) {
        position.set(body.getPosition().x, body.getPosition().y);
    }


    public void render(SpriteBatch batch) {
        float renderX = body.getPosition().x * Main.PPM - width / 2;
        float renderY = body.getPosition().y * Main.PPM - height / 2;

        batch.draw(texture, renderX, renderY, width, height);    }

    public void dispose() {
        texture.dispose();
    }

}
