package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.blurrymeal.angrybirds.Main;
import com.blurrymeal.angrybirds.entities.Pigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PigsTest {
    private Pigs pig;
    private World world;
    private Texture texture;

    @BeforeEach
    public void setUp() {
        // Initialize Box2D world and texture
        world = new World(new Vector2(0, -9.8f), true);
        texture = new Texture("smallPig.png");

        // Create a pig instance
        pig = new Pigs(texture, 100, 100, 50, 50, world, 0);
    }

    @Test
    public void testInitialHealth() {
        assertEquals(100f, pig.getHealth());
    }

    @Test
    public void testTakeDamage() {
        pig.takeDamage(50f);
        assertEquals(50f, pig.getHealth());

        pig.takeDamage(60f);
        assertTrue(pig.isDestroyed());
    }

    @Test
    public void testDestroy() {
        pig.destroy();
        assertTrue(pig.isDestroyed());
    }

    @Test
    public void testUpdate() {
        pig.update(1f);
        assertFalse(pig.isDestroyed());

        pig.takeDamage(100f);
        pig.update(1f);
        assertTrue(pig.isDestroyed());
    }

    @Test
    public void testGetPosition() {
        Vector2 position = pig.getPosition();
        assertEquals(100 / Main.PPM, position.x, 0.01);
        assertEquals(100 / Main.PPM, position.y, 0.01);
    }
}
