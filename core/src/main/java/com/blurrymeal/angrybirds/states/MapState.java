package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MapState extends State{

    private Texture mapBackground;

    private Texture level01Icon;
    private Texture level02Icon;
    private Texture level03Icon;
    private Texture level04Icon;
    private Texture level05Icon;
    private Texture goBackIcon;

    public MapState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mapBackground = new Texture("mapBackground.png");
        level01Icon = new Texture("level01Icon.png");
        level02Icon = new Texture("level02Icon.png");
        level03Icon = new Texture("level03Icon.png");
        level04Icon = new Texture("level04Icon.png");
        level05Icon = new Texture("level05Icon.png");
        goBackIcon = new Texture("goBackIcon.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            //Go Back
            if(touchPos.x >= 40 && touchPos.x <= 150 && touchPos.y >= 460 && touchPos.y <= 500){
                gameStateManager.setState(new HomeState(gameStateManager));
            }

            //Level 01
            if(touchPos.x >= 50 && touchPos.x <= 130 && touchPos.y >= 60 && touchPos.y <= 140){
                gameStateManager.setState(new Level01State(gameStateManager));
                setCurrentStateLevel(1);
            }

            //Level 02
            if(touchPos.x >= 20 && touchPos.x <= 100 && touchPos.y >= 200 && touchPos.y <= 280){
                gameStateManager.setState(new Level02State(gameStateManager));
                setCurrentStateLevel(2);
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
        batch.draw(mapBackground, 0, 0, 960,540);
        batch.draw(level01Icon, 50, 60, 80,80);
        batch.draw(level02Icon, 20, 200, 80,80);
        batch.draw(level03Icon, 200, 170, 80,80);
        batch.draw(level04Icon, 350, 150, 80,80);
        batch.draw(level05Icon, 580, 195, 80,80);
        batch.draw(goBackIcon, 40, 460, 110,40);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
