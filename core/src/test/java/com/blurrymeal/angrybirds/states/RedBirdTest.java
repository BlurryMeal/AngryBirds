package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.blurrymeal.angrybirds.entities.RedBird;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedBirdTest {
    private RedBird redBird;
    private World world;
    private Texture texture;

    @BeforeEach
    public void setUp() {
        world = new World(new Vector2(0, -9.8f), true);
        texture = new Texture("redBird.png");
        redBird = new RedBird(texture, world, 100, 100, 50, 50);
    }

    @Test
    public void testInitialPosition() {
        Vector2 position = redBird.getPosition();
        assertEquals(100, position.x, 0.01);
        assertEquals(100, position.y, 0.01);
    }

    @Test
    public void testLaunch() {
        Vector2 initialVelocity = new Vector2(10, 15);
        redBird.launch(initialVelocity);
        assertTrue(redBird.isInMotion());
    }

    @Test
    public void testHasStopped() {
        assertTrue(redBird.hasStopped());
        redBird.launch(new Vector2(10, 15));
        world.step(1 / 60f, 6, 2);
        assertFalse(redBird.hasStopped());
    }

    @Test
    public void testCalculateDamageForce() {
        float damage = redBird.calculateDamageForce(10f);
        assertEquals(20f, damage, 0.01);
    }

    @Test
    public void testDispose() {
        redBird.dispose();
        assertTrue(texture.getTextureData().isPrepared());
    }
}
