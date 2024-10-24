package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.blurrymeal.angrybirds.Main;

public class HomeState extends State{

    private Texture background;
    private Texture playButton;
    private Texture angryTitle;
    private Texture loadGameButton;

    private BitmapFont font;
    private int score;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public HomeState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        background = new Texture("menubg.jpg");
        playButton = new Texture("playButton.png");
        angryTitle = new Texture("angryBirdTitle.png");
        loadGameButton = new Texture("loadGameButton.png");

        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 28;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if(touchPos.x >= (Main.WIDTH/2) - (playButton.getWidth()/2) && touchPos.x <= (Main.WIDTH/2) - (playButton.getWidth()/2) + playButton.getWidth()
                && touchPos.y >= (Main.HEIGHT/2)-140 && touchPos.y <= (Main.HEIGHT/2)-140 + playButton.getHeight()){

                gameStateManager.setState(new MapState(gameStateManager));
            }

            if(touchPos.x >= (Main.WIDTH/2) - (loadGameButton.getWidth()/5) && touchPos.x <= (Main.WIDTH/2) - (loadGameButton.getWidth()/5) + 250
                && touchPos.y >= (Main.HEIGHT/2)-220 && touchPos.y <= (Main.HEIGHT/2)-220 + 60){

                if(gameStateManager.getStates().size() > 1){
                    gameStateManager.popState();
                }else if(gameStateManager.getStates().size() == 1){
                    System.out.println("No Saved Game");
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
        batch.begin();
        batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        batch.draw(angryTitle, (Main.WIDTH/2) - (playButton.getWidth()/2)-160, (Main.HEIGHT/2)+50);
        batch.draw(playButton, (Main.WIDTH/2) - (playButton.getWidth()/2), (Main.HEIGHT/2)-140);
        batch.draw(loadGameButton, (Main.WIDTH/2) - (loadGameButton.getWidth()/5) , (Main.HEIGHT/2)-220, 250, 60);
        batch.end();

        batch.begin();
        font.draw(batch, "LOAD GAME", (Main.WIDTH/2) - (playButton.getWidth()/2) + 58, (Main.HEIGHT/2)-178);
        batch.end();
    }


    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        angryTitle.dispose();
    }
}
