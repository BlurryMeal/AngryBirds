package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WinLevelState extends State{

    private int levelNo;


    public WinLevelState(GameStateManager gsm, int level) {
        super(gsm);
        levelNo = level;
    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }
}
