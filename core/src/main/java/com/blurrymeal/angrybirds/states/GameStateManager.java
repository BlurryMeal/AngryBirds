package com.blurrymeal.angrybirds.states;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    private Stack<State> states;
    public GameStateManager() {
        states = new Stack<State>();
    }

    public void pushState(State state) {
        states.push(state);
    }

    public State popState() {
        return states.pop();
    }

    public void setState(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float delta) {
        states.peek().update(delta);
    }

    public void render(SpriteBatch batch) {
        states.peek().render(batch);
    }
}
