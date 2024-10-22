package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.blurrymeal.angrybirds.Main;

public class WinLevelState extends State{

    private int levelNo;
    private int score;
    private int highScore;

    private Texture levelWinBG;
    private Texture dullBG;
    private Texture newHighScoreSticker;

    private Texture restartButton;
    private Texture mapIcon;
    private Texture nextLevelButton;

    private State previousState;

    private BitmapFont font;
    private BitmapFont scoreFont;
    private BitmapFont scoreFont2;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;



    public WinLevelState(GameStateManager gsm,State previousState, int level) {
        super(gsm);
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        levelNo = level;
        levelWinBG = new Texture("levelWinBG.png");
        dullBG = new Texture("dullBG.png");
        this.previousState = previousState;
        newHighScoreSticker = new Texture("newHighScoreSticker.png");

        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        parameter.size = 24;
        scoreFont = generator.generateFont(parameter);
        scoreFont.setColor(Color.WHITE);
        parameter.size = 28;
        scoreFont2 = generator.generateFont(parameter);
        generator.dispose();

        score = 0;

        restartButton = new Texture("restartButton.png");
        mapIcon = new Texture("mapIcon.png");
        nextLevelButton = new Texture("nextLevelButton.png");

    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);


            if(touchPos.x >= 452 && touchPos.x <= 522 && touchPos.y >= 120 && touchPos.y <= 190){
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


            if(touchPos.x >= 335 && touchPos.x <= 405 && touchPos.y >= 120 && touchPos.y <= 190){
                gameStateManager.setState(new MapState(gameStateManager));
            }

            if(touchPos.x >= 566 && touchPos.x <= 636 && touchPos.y >= 120 && touchPos.y <= 190){
                if(levelNo == 1){
                    gameStateManager.setState(new Level02State(gameStateManager));
                }else if(levelNo == 2){
                    gameStateManager.setState(new Level03State(gameStateManager));
                }else if(levelNo == 3){
                    gameStateManager.setState(new Level04State(gameStateManager));
                }else if(levelNo == 4){
                    gameStateManager.setState(new Level05State(gameStateManager));
                }else if(levelNo == 5){
                    gameStateManager.setState(new MapState(gameStateManager));
                }
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
        batch.draw(levelWinBG, 260, 120, 445, 325);
        batch.draw(newHighScoreSticker, 510, 200, 150, 125);

        batch.draw(restartButton, 452, 120, 70, 70);
        batch.draw(mapIcon, 335, 120, 70, 70);
        batch.draw(nextLevelButton, 566, 120, 70, 70);


        batch.end();

        batch.begin();
        font.draw(batch, "Level " + levelNo, 310, 403);
        scoreFont.draw(batch, "HighScore: \n" + String.valueOf(highScore), 530, 408);
        scoreFont2.draw(batch, "Score: \n" + String.valueOf(score), 315, 260);
        batch.end();


    }

    @Override
    public void dispose() {

    }
}
