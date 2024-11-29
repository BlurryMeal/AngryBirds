package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Level01StateTest {

    private Level01State level01State;
    private GameStateManager mockGameStateManager;

    @BeforeEach
    void setUp() {
        mockGameStateManager = new GameStateManager();
        level01State = new Level01State(mockGameStateManager);
    }

    @Test
    void testLevel01StateInitialization() {
        assertNotNull(level01State);
        assertEquals(0, level01State.getScore());
    }

    @Test
    void testInitialBirdCounter() {
        assertEquals(3, level01State.getBirdCounter());
    }

    @Test
    void testInitialPigCounter() {
        assertEquals(2, level01State.getPigCounter());
    }

    @Test
    void testCalculateTrajectory() {
        Vector2 startPos = new Vector2(100, 200);
        Vector2 velocity = new Vector2(10, 15);

        List<Vector2> trajectoryPoints = level01State.calculateTrajectory(startPos, velocity, 0.1f, 30);

        assertNotNull(trajectoryPoints);
        assertTrue(trajectoryPoints.size() > 0);

        // Check first point is the start position
        assertEquals(startPos.x, trajectoryPoints.get(0).x, 0.01);
        assertEquals(startPos.y, trajectoryPoints.get(0).y, 0.01);
    }

    @Test
    void testRestoreScore() {
        int testScore = 150;
        level01State.restoreScore(testScore);
        assertEquals(testScore, level01State.getScore());
    }

    @Test
    void testRestoreBirdCounter() {
        int originalBirdCount = level01State.getBirdCounter();
        level01State.restoreBirdCounter(1);

        assertEquals(1, level01State.getBirdCounter());
        assertTrue(level01State.getBirdCounter() <= originalBirdCount);
    }

    @Test
    void testRestorePigCounter() {
        int originalPigCount = level01State.getPigCounter();
        level01State.restorePigCounter(1);

        assertEquals(1, level01State.getPigCounter());
        assertTrue(level01State.getPigCounter() <= originalPigCount);
    }
}
