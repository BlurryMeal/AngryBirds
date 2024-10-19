package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.blurrymeal.angrybirds.Main;

public class MenuState extends State{

    private Texture MenuBackground;
    private Texture resumeButton;
    private Texture mapButton;
    private Texture exitButton;


    public MenuState(GameStateManager gsm){
        super(gsm);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MenuBackground = new Texture("pausedMenuBG.png");
        resumeButton = new Texture("resumeMenuButton.png");
        mapButton = new Texture("mapIcon.png");
        exitButton = new Texture("exitMenuButton.png");

        Level01State level01State = new Level01State(gsm);
        Level02State level02State = new Level02State(gsm);
        Level03State level03State = new Level03State(gsm);
        Level04State level04State = new Level04State(gsm);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            //Resume Button
            if(touchPos.x >= Main.WIDTH/2 - 175 && touchPos.x <= Main.WIDTH/2 - 175 + 80 && touchPos.y >= Main.HEIGHT / 2 - 105 && touchPos.y <= Main.HEIGHT / 2 - 105 + 80){
                gameStateManager.popState();
            }

            //Map Button
            if (touchPos.x >= Main.WIDTH / 2 - 45 && touchPos.x <= Main.WIDTH / 2 - 45 + 80 && touchPos.y >= Main.HEIGHT / 2 - 105 && touchPos.y <= Main.HEIGHT / 2 - 105 + 80){
                gameStateManager.popState();
                gameStateManager.setState(new MapState(gameStateManager));
            }

            //Exit Button
            if (touchPos.x >= Main.WIDTH / 2 + 85 && touchPos.x <= Main.WIDTH / 2 + 85 + 80 && touchPos.y >= Main.HEIGHT / 2 - 105 && touchPos.y <= Main.HEIGHT / 2 - 105 + 80){
                gameStateManager.popState();
                gameStateManager.setState(new HomeState(gameStateManager));
            }
        }

    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(MenuBackground, 0, 0, 960, 540);
        batch.end();

        batch.begin();
        batch.draw(resumeButton,Main.WIDTH/2 - 175, Main.HEIGHT/2 - 105, 80, 80);
        batch.draw(mapButton, Main.WIDTH/2-45, Main.HEIGHT/2 - 105, 80, 80);
        batch.draw(exitButton, Main.WIDTH/2 + 85, Main.HEIGHT/2 - 105, 80, 80);

        batch.end();
    }

    @Override
    public void dispose() {

    }
}
