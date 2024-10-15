package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class State {

    protected OrthographicCamera camera;
    protected Vector2 mouse;
    protected GameStateManager gameStateManager;

    public static int currentStateLevel;

    protected State(GameStateManager gsm){
        this.gameStateManager = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector2();
    }

    public int getCurrentStateLevel(){
        return currentStateLevel;
    }
    public void setCurrentStateLevel(int currentStateLevel){
        this.currentStateLevel = currentStateLevel;
    }

    protected abstract void handleInput();
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}
