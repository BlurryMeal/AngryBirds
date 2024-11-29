package com.blurrymeal.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blurrymeal.angrybirds.states.GameStateManager;
import com.blurrymeal.angrybirds.states.HomeState;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;

    public static final float PPM = 100f;

    public static final String TITLE = "Angry Birds";

    private GameStateManager gameStateManager;
    private SpriteBatch batch;

    private Texture image;
    private Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        image = new Texture("libgdx.png");
        Gdx.gl.glClearColor(0, 0, 0, 1);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("AngryBirdsTheme.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);
        backgroundMusic.play();

        gameStateManager.pushState(new HomeState(gameStateManager));

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(batch);

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
