package com.blurrymeal.angrybirds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.blurrymeal.angrybirds.states.State;

public class Obstacle {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float width;
    private float height;
    private static final float GRAVITY = -9.8f;
    private boolean isStatic;


    public Obstacle(Texture texture, float x, float y) {
        this(texture,x,y,texture.getWidth(),texture.getHeight(), true);
    }

    public Obstacle(Texture texture, float x, float y, float width, float height) {
        this(texture,x,y, width, height, true);

    }

    public Obstacle(Texture texture, float x, float y, float width, float height, boolean isStatic) {
        this.texture = texture;
        this.position = new Vector2(x,y);
        this.velocity = new Vector2(0,0);
        this.width = width;
        this.height = height;
        this.isStatic = isStatic;
    }

    public void update(float deltaTime) {
        if(!isStatic){
            velocity.y += GRAVITY*deltaTime;
            position.x += velocity.x*deltaTime;
            position.y += velocity.y*deltaTime;

            if(position.y < 170){
                position.y = 170; // SET TO GROUND LEVEL
                velocity.y = 0;
            }
        }

    }


    public void render(SpriteBatch batch) {
        batch.draw(texture,position.x,position.y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isStatic() {
        return isStatic;
    }

}
