package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.blurrymeal.angrybirds.Main;

public class GameEndState extends State{

    private Texture gameEndBG;
    private Texture gameEndContainer;
    private Texture exitMenuButton;
    private Texture mapIcon;

    private BitmapFont font;
    private BitmapFont textFont;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;


    public GameEndState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);

        gameEndBG = new Texture("gameEndBG.jpg");
        gameEndContainer = new Texture("gameEndContainer.png");

        exitMenuButton = new Texture("exitMenuButton.png");
        mapIcon = new Texture("mapIcon.png");

        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        parameter.size = 24;
        textFont = generator.generateFont(parameter);
        textFont.setColor(Color.WHITE);
        generator.dispose();
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);


            if(touchPos.x >= 435 && touchPos.x <= 525 && touchPos.y >= 128 && touchPos.y <= 218){
                gameStateManager.setState(new HomeState(gameStateManager));
            }


            if(touchPos.x >= 304 && touchPos.x <= 394 && touchPos.y >= 128 && touchPos.y <= 218){
                gameStateManager.setState(new MapState(gameStateManager));
            }

        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(gameEndBG, 0, 0, 960, 540);
        batch.draw(gameEndContainer, 260, 120, 445, 325);
        batch.draw(exitMenuButton, 435, 128, 90, 90);
        batch.draw(mapIcon, 304, 128, 90, 90);
        batch.end();

        batch.begin();
        font.draw(batch, "CONGRATULATIONS!", 310, 380);
        textFont.draw(batch, "You have successfully finished\n Angry Birds AP Edition!", 300, 285);
        batch.end();

    }

    @Override
    public void dispose() {

    }
}
