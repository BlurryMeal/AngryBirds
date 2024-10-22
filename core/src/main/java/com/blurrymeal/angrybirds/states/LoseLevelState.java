package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.blurrymeal.angrybirds.Main;

public class LoseLevelState extends State{

    private int levelNo;
    private State previousState;
    private int score;

    private Texture loseLevelBG;
    private Texture dullBG;
    private Texture restartButton;
    private Texture mapIcon;

    private BitmapFont font;
    private BitmapFont scoreFont;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;


    public LoseLevelState(GameStateManager gsm,State previousState, int level) {
        super(gsm);
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        levelNo = level;
        this.previousState = previousState;

        score = 0;

        loseLevelBG = new Texture("levelFailedBG.png");
        dullBG = new Texture("dullBG.png");
        restartButton = new Texture("restartButton.png");
        mapIcon = new Texture("mapIcon.png");

        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        parameter.size = 24;
        scoreFont = generator.generateFont(parameter);
        scoreFont.setColor(Color.WHITE);
        generator.dispose();
    }


    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);


            if(touchPos.x >= 435 && touchPos.x <= 525 && touchPos.y >= 128 && touchPos.y <= 218){
                if(levelNo == 1){
                    gameStateManager.setState(new Level01State(gameStateManager));
                }else if(levelNo == 2){
                    gameStateManager.setState(new Level02State(gameStateManager));
                }else if(levelNo == 3){
                    gameStateManager.setState(new Level03State(gameStateManager));
                }else if(levelNo == 4){
                    gameStateManager.setState(new Level04State(gameStateManager));
                }else if(levelNo == 5){
                    gameStateManager.setState(new Level05State(gameStateManager));
                }
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
        previousState.render(batch);

        batch.begin();
        batch.draw(dullBG,0,0,Main.WIDTH,Main.HEIGHT);
        batch.draw(loseLevelBG, 260, 120, 445, 325);
        batch.draw(restartButton, 435, 128, 90, 90);
        batch.draw(mapIcon, 304, 128, 90, 90);
        batch.end();

        batch.begin();
        font.draw(batch, "Level " + levelNo, 310, 380);
        scoreFont.draw(batch, "HighScore: \n" + String.valueOf(score), 530, 385);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
