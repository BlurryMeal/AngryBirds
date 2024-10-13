package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float width;
    private float height;


    public Bird(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.position = new Vector2(x,y);
        this.velocity = new Vector2(0,0);
        this.width = width;
        this.height = height;
    }

    public void update(float deltaTime) {
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture,position.x,position.y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }
}
