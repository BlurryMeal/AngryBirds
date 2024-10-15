package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blurrymeal.angrybirds.Main;

public class HomeState extends State{

    private Texture background;
    private Texture playButton;
    private Texture angryTitle;

    public HomeState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("menubg.jpg");
        playButton = new Texture("playButton.png");
        angryTitle = new Texture("angryBirdTitle.png");
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gameStateManager.setState(new MapState(gameStateManager));
            dispose();
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        batch.draw(angryTitle, (Main.WIDTH/2) - (playButton.getWidth()/2)-160, (Main.HEIGHT/2)+50);
        batch.draw(playButton, (Main.WIDTH/2) - (playButton.getWidth()/2), (Main.HEIGHT/2)-140);
        batch.end();
    }


    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        angryTitle.dispose();
    }
}
