package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blurrymeal.angrybirds.Main;
import com.blurrymeal.angrybirds.entities.Bird;
import com.blurrymeal.angrybirds.entities.Obstacle;
import com.blurrymeal.angrybirds.entities.Pig;

import java.util.ArrayList;
import java.util.List;

public class Level05State extends State{

    private final int BIRDSLINGPOS_X=72;
    private final int BIRDSLINGPOS_Y=205;
    private Texture slingshot;
    private Texture background;

    private boolean isDragging = false;
    private Vector2 birdPosition;
    private List<Vector2> trajectoryPoints;
    private ArrayList<Bird> birds;
    private Texture redBirdTexture;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Pig> pigs;

    private Texture pauseButton;
    private Texture restartButton;
    private Texture skipButton;
    private Texture scoreContainer;
    private Texture birdCountContainer;
    private Texture pigCountContainer;

    private int pigCounter = 3;
    private int birdCounter = 3;

    private BitmapFont font;
    private BitmapFont scoreFont;
    private int score;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private State level06State;

    private Texture LoseButton;
    private Texture WinButton;

    private Texture loadGameButton;
    private BitmapFont saveGameFont;


    public Level05State(GameStateManager gsm) {
        super(gsm);

        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        redBirdTexture = new Texture("redBird.png");
        birds = new ArrayList<Bird>();
        birds.add(new Bird(redBirdTexture, BIRDSLINGPOS_X, BIRDSLINGPOS_Y, 31, 31));
        birds.add(new Bird(redBirdTexture, 55, 137, 31, 31));
        birds.add(new Bird(redBirdTexture, 25, 137, 31, 31));
        slingshot = new Texture("slingshot.png");
        background = new Texture("level5BG.png");
        birdPosition = new Vector2(BIRDSLINGPOS_X, BIRDSLINGPOS_Y);
        trajectoryPoints = new ArrayList<>();

//        level06State = new Level06State(gsm);

        LoseButton = new Texture("LoseButton.png");
        WinButton = new Texture("WinButton.png");

        pigs = new ArrayList<Pig>();
        pigs.add(new Pig(new Texture("smallPig.png"), 640, 139, 27, 27));
        pigs.add(new Pig(new Texture("smallPig.png"), 640, 198, 27, 27));
        pigs.add(new Pig(new Texture("smallPig.png"), 642, 256, 27, 27));

        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Obstacle(new Texture("woodVertObst.png"), 628, 255));
        obstacles.add(new Obstacle(new Texture("woodVertObst.png"), 676, 255));
        obstacles.add(new Obstacle(new Texture("woodHoriObst.png"), 629, 314));

        obstacles.add(new Obstacle(new Texture("woodHoriObst.png"), 601, 193));
        obstacles.add(new Obstacle(new Texture("woodHoriObst.png"), 654, 193));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 600, 139));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 680, 139));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 600, 166));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 680, 166));

        obstacles.add(new Obstacle(new Texture("woodHoriObst.png"), 601, 251));
        obstacles.add(new Obstacle(new Texture("woodHoriObst.png"), 654, 251));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 600, 198));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 680, 198));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 600, 224));
        obstacles.add(new Obstacle(new Texture("squareobs.png"), 680, 224));




        pauseButton = new Texture("pauseButton.png");
        restartButton = new Texture("restartButton.png");
        skipButton = new Texture("skipButton.png");
        scoreContainer = new Texture("scoreContainer.png");
        birdCountContainer = new Texture("birdCount.png");
        pigCountContainer = new Texture("pigCount.png");

        loadGameButton = new Texture("loadGameButton.png");

        score = 0;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 22;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);

        parameter.size = 28;
        scoreFont = generator.generateFont(parameter);

        parameter.size = 24;
        saveGameFont = generator.generateFont(parameter);

        generator.dispose();


    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (touchPos.x >= birdPosition.x && touchPos.x <= birdPosition.x + 25 &&
                touchPos.y >= birdPosition.y && touchPos.y <= birdPosition.y + 25) {
                isDragging = true;
            }


            // Pause Button
            if(touchPos.x >= 730 && touchPos.x <= 730 + 60 && touchPos.y >= 460 && touchPos.y <= 460 + 60){
                gameStateManager.pushState(new MenuState(gameStateManager, this));
            }

            // Skip Button
            if(touchPos.x >= 870 && touchPos.x <= 870 + 60 && touchPos.y >= 460 && touchPos.y <= 460 + 60) {
                gameStateManager.setState(new GameEndState(gameStateManager));
            }


            //Restart Button
            if(touchPos.x >= 800 && touchPos.x <= 860 && touchPos.y >= 460 && touchPos.y <= 520){
                gameStateManager.setState(this.clone());
            }

            //Lose Button
            if(touchPos.x >= 880 && touchPos.x <= 960 && touchPos.y >= 250 && touchPos.y <= 310){
                gameStateManager.setState(new LoseLevelState(gameStateManager, this, 5));
            }

            //Win Button
            if(touchPos.x >= 880 && touchPos.x <= 960 && touchPos.y >= 320 && touchPos.y <= 380){
                gameStateManager.setState(new WinLevelState(gameStateManager, this, 5));
            }

            //Save Button
            if(touchPos.x >= 545 && touchPos.x <= 715 && touchPos.y >= Main.HEIGHT - 75 && touchPos.y <= Main.HEIGHT - 25){
                System.out.println("Game Saved");
                gameStateManager.pushState(new HomeState(gameStateManager));
            }

        }

        if (Gdx.input.isTouched() && isDragging) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            birdPosition.x = Math.max(BIRDSLINGPOS_X - 50, Math.min(touchPos.x, BIRDSLINGPOS_X));
            birdPosition.y = Math.max(BIRDSLINGPOS_Y - 50, Math.min(touchPos.y, BIRDSLINGPOS_Y + 50));

            for (Bird bird : birds){
                bird.getPosition().set(birdPosition.x, birdPosition.y);
            }

            Vector2 velocity = new Vector2(BIRDSLINGPOS_X - birdPosition.x, BIRDSLINGPOS_Y - birdPosition.y).scl(3);
            trajectoryPoints = calculateTrajectory(birdPosition, velocity, 0.1f, 30);
        }

        if (!Gdx.input.isTouched() && isDragging) {
            isDragging = false;
        }
    }



    @Override
    public void update(float delta) {
        handleInput();
        for (Bird bird : birds){
            bird.update(delta);
        }
        for(Pig pig : pigs) {
            pig.update(delta);
        }

        for(Obstacle obstacle : obstacles) {
            obstacle.update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        batch.draw(slingshot, 45, 137);
        batch.draw(pauseButton,730, 460, 60, 60);
        batch.draw(restartButton,800, 460, 60, 60);
        batch.draw(skipButton,870, 460, 60, 60);

        batch.draw(LoseButton, 880, 250, 60, 60);
        batch.draw(WinButton, 880, 320, 60, 60);

        for(Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }

        for (Bird bird : birds){
            bird.render(batch);
        }
        for(Pig pig : pigs) {
            pig.render(batch);
        }

        // Draw trajectory
        for (Vector2 point : trajectoryPoints) {
            batch.draw(redBirdTexture, point.x, point.y, 5, 5);  // Draw small circles as trajectory points
        }


        batch.draw(scoreContainer, 10, 470, 260, 50);
        batch.draw(birdCountContainer, 10, 370,65, 92);
        batch.draw(pigCountContainer, 85, 370,65, 92);
        batch.draw(loadGameButton, 545, Main.HEIGHT - 75, 170, 50);
        batch.end();


        batch.begin();
        font.draw(batch, "CURRENT SCORE: " + score, 20, Main.HEIGHT - 36);
        scoreFont.draw(batch,Integer.toString(birdCounter), 35, Main.HEIGHT - 135);
        scoreFont.draw(batch,Integer.toString(pigCounter), 112, Main.HEIGHT - 135);
        saveGameFont.draw(batch, "SAVE GAME", 575, Main.HEIGHT - 40);
        batch.end();
    }


    @Override
    public void dispose() {
        redBirdTexture.dispose();
        slingshot.dispose();
        background.dispose();
        font.dispose();
    }

    @Override
    public Level05State clone(){
        return new Level05State(gameStateManager);
    }




    public List<Vector2> calculateTrajectory(Vector2 startPos, Vector2 velocity, float timeStep, int numPoints) {
        List<Vector2> trajectoryPoints = new ArrayList<>();
        Vector2 gravity = new Vector2(0, -9.8f);  // gravity acting downward

        for (int i = 0; i < numPoints; i++) {
            float t = i * timeStep;
            // Calculate the position using basic kinematic equation: s = ut + 0.5 * a * t^2
            float posX = startPos.x + velocity.x * t;
            float posY = startPos.y + velocity.y * t + 0.5f * gravity.y * t * t;

            trajectoryPoints.add(new Vector2(posX, posY));

            if (posY < 0) break;  // Stop if the point goes below ground level
        }

        return trajectoryPoints;
    }


}
