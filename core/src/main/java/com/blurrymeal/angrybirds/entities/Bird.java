
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
    private boolean inMotion = false;  // Indicates if the bird is currently flying

    private final Vector2 gravity = new Vector2(0, -9.8f);  // Gravity acting downwards

    public Bird(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.width = width;
        this.height = height;
    }

    public void update(float deltaTime) {
        if (inMotion) {
            velocity.add(gravity.x * deltaTime, gravity.y * deltaTime); // Apply gravity to velocity
            position.add(velocity.x * deltaTime, velocity.y * deltaTime); // Update position based on velocity

            // Stop the bird if it hits the ground
            if (position.y < 0) {
                position.y = 0; // Snap to ground level
                velocity.set(0, 0); // Stop further movement
                inMotion = false;  // Bird has landed
            }
        }
    }

    public void launch(Vector2 initialVelocity) {
        this.velocity.set(initialVelocity);
        this.inMotion = true; // Start moving
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isInMotion() {
        return inMotion;
    }
}
