
package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.blurrymeal.angrybirds.Main;
import com.blurrymeal.angrybirds.entities.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level01State extends State{

    private final int BIRDSLINGPOS_X=82;
    private final int BIRDSLINGPOS_Y=242;
    private Texture slingshot;
    private Texture background;

    private boolean isDragging = false;
    private Vector2 birdPosition;
    private List<Vector2> trajectoryPoints;
    private ArrayList<RedBird> birds;
    private Texture redBirdTexture;
    private Texture redBirdTexture2;
    private ArrayList<Pigs> pigs;
    private ArrayList<Obstacles> obstacles;

    private Texture pauseButton;
    private Texture restartButton;
    private Texture skipButton;
    private Texture scoreContainer;
    private Texture birdCountContainer;
    private Texture pigCountContainer;

    private int pigCounter;
    private int birdCounter;

    private BitmapFont font;
    private BitmapFont scoreFont;

    private int score;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private State level02State;

    private Texture LoseButton;
    private Texture WinButton;
    private Texture loadGameButton;
    private BitmapFont saveGameFont;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private static final float TIME_STEP = 1/60f;

    private ArrayList<Pigs> pigsToRemove = new ArrayList<>();


    private float winTimer = 0f;
    private boolean isPigsClear = false;
    private static final float WIN_DELAY = 1f;

    private float loseTimer = 0f;
    private static final float LOSE_DELAY = 1.5f;
    private boolean isBirdsClear = false;

    private void createGround(World world) {
        float groundHeight = 160;

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(0, groundHeight / Main.PPM);

        Body groundBody = world.createBody(groundBodyDef);

        float groundWidth = 2000 / Main.PPM;
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(groundWidth, 5 / Main.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0;


        Fixture fixture = groundBody.createFixture(fixtureDef);
        fixture.setUserData(this);

        groundShape.dispose();
    }





    public Level01State(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        world.step(1/60f, 6, 2);


        createGround(world);


        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        redBirdTexture = new Texture("redBird.png");
        redBirdTexture2 = new Texture("redBird.png");
        birds = new ArrayList<RedBird>();
        birds.add(new RedBird(redBirdTexture, world,  BIRDSLINGPOS_X, BIRDSLINGPOS_Y, 31, 31));
        birds.add(new RedBird(redBirdTexture, world,  BIRDSLINGPOS_X, BIRDSLINGPOS_Y, 31, 31));
        birds.add(new RedBird(redBirdTexture, world,  BIRDSLINGPOS_X, BIRDSLINGPOS_Y, 31, 31));

//        birds.add(new Bird(redBirdTexture, 40, 170, 31, 31));
//        birds.add(new Bird(redBirdTexture, 10, 170, 31, 31));

        slingshot = new Texture("slingshot.png");
        background = new Texture("level1BG.jpg");
        birdPosition = new Vector2(BIRDSLINGPOS_X, BIRDSLINGPOS_Y);
        trajectoryPoints = new ArrayList<>();

        level02State = new Level02State(gsm);

        LoseButton = new Texture("LoseButton.png");
        WinButton = new Texture("WinButton.png");


        obstacles = new ArrayList<Obstacles>();
        obstacles.add(new Obstacles(new Texture("woodVertObst.png"), 340, 170, world, 0f));
        obstacles.add(new Obstacles(new Texture("woodVertObst.png"), 360, 225, world, 90f));
        obstacles.add(new Obstacles(new Texture("woodVertObst.png"), 380, 200, world, 0f));
        obstacles.add(new Obstacles(new Texture("woodVertObst.png"), 580, 170, world, 0f));
        obstacles.add(new Obstacles(new Texture("woodVertObst.png"), 620, 170, world, 0f));
        obstacles.add(new Obstacles(new Texture("woodVertObst.png"), 595, 225, world, 90f));

        obstacles.add(new Obstacles(new Texture("woodVertObst.png"), 1250, 170, world, 0f));


        pigs = new ArrayList<Pigs>();
        pigs.add(new Pigs(new Texture("smallPig.png"), 600, 350, 27, 27,world, 160f));
        pigs.add(new Pigs(new Texture("smallPig.png"), 360, 350, 27, 27,world, 160f));

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
                gameStateManager.setState(level02State);
                setCurrentStateLevel(2);
            }

            //Restart Button
            if(touchPos.x >= 800 && touchPos.x <= 860 && touchPos.y >= 460 && touchPos.y <= 520){
                gameStateManager.setState(this.clone());
            }

            //Lose Button
            if(touchPos.x >= 880 && touchPos.x <= 960 && touchPos.y >= 250 && touchPos.y <= 310){
                gameStateManager.setState(new LoseLevelState(gameStateManager, this, 1, this.getScore()));
            }

            //Win Button
            if(touchPos.x >= 880 && touchPos.x <= 960 && touchPos.y >= 320 && touchPos.y <= 380){
                gameStateManager.setState(new WinLevelState(gameStateManager, this, 1, this.getScore()));
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

            for (RedBird bird : birds){
                bird.getPosition().set(birdPosition.x, birdPosition.y);
            }

            Vector2 velocity = new Vector2(BIRDSLINGPOS_X - birdPosition.x, BIRDSLINGPOS_Y - birdPosition.y).scl(3);
            trajectoryPoints = calculateTrajectory(birdPosition, velocity, 0.1f, 30);
        }

        if (!Gdx.input.isTouched() && isDragging) {
            isDragging = false;
            Vector2 velocity = new Vector2(BIRDSLINGPOS_X - birdPosition.x, BIRDSLINGPOS_Y - birdPosition.y).scl(3);
            for (RedBird bird : birds) {
                if (!bird.isInMotion()) {
                    bird.launch(velocity);
                    break;
                }
            }

            trajectoryPoints.clear();
        }
    }



    @Override
    public void update(float delta) {
        handleInput();


        pigCounter = pigs.size();
        birdCounter = birds.size();

        Iterator<RedBird> birdIterator = birds.iterator();
        boolean allBirdsStationary = true;
        boolean birdRemoved = false;
        while (birdIterator.hasNext()) {
            RedBird bird = birdIterator.next();
            bird.update(delta);

            if (bird.isInMotion() && bird.hasStopped()) {
                bird.getBody().getWorld().destroyBody(bird.getBody());
                birdIterator.remove();
                birdCounter--;
                birdRemoved = true;
                break;
            }else if(bird.isInMotion()){
                allBirdsStationary = false;
            }
        }

        if (birdRemoved && !birds.isEmpty()) {
            birdPosition.set(BIRDSLINGPOS_X, BIRDSLINGPOS_Y);

            RedBird nextBird = birds.get(0);

            nextBird.getBody().setTransform(
                BIRDSLINGPOS_X / Main.PPM,
                BIRDSLINGPOS_Y / Main.PPM,
                0
            );
            nextBird.getBody().setType(BodyDef.BodyType.StaticBody);

            trajectoryPoints.clear();
        }



        if(pigCounter == 0 && !isPigsClear){
            isPigsClear = true;
            winTimer = 0f;
        }

        if(birdCounter == 0 && pigCounter > 0 && !isBirdsClear){
            loseTimer = 0f;
            isBirdsClear = true;
        }

        if(isBirdsClear){
            loseTimer += delta;
            if(loseTimer >= LOSE_DELAY){
                gameStateManager.setState(new LoseLevelState(gameStateManager, this, 1, this.getScore()));
            }
        }

        if(isPigsClear){
            winTimer += delta;

            if(winTimer >= WIN_DELAY){
                gameStateManager.setState(new WinLevelState(gameStateManager, this, 1, this.getScore()));
                return;
            }
        }

        world.step(TIME_STEP, 6, 2);

        for (RedBird bird: birds){
            bird.update(delta);
        }

        for(Obstacles obstacle : obstacles) {
            obstacle.update(delta);
        }



        for (Iterator<Pigs> iterator = pigs.iterator(); iterator.hasNext(); ) {
            Pigs pig = iterator.next();
            pig.update(delta);

            if (pig.isDestroyed()) {
                iterator.remove();
                pigCounter--;
                score += pig.getScore();
            }
        }

        for(Pigs pig : pigs) {
            pig.update(delta);
        }

    }

    public int getScore() {
        return score;
    }


    @Override
    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        batch.draw(slingshot, 40, 160);
        batch.draw(pauseButton,730, 460, 60, 60);
        batch.draw(restartButton,800, 460, 60, 60);
        batch.draw(skipButton,870, 460, 60, 60);

        batch.draw(LoseButton, 880, 250, 60, 60);
        batch.draw(WinButton, 880, 320, 60, 60);

        for(Obstacles obstacle : obstacles) {
            obstacle.render(batch);
        }

        for(Pigs pig : pigs) {
            pig.render(batch);
        }

        for (RedBird bird: birds){
            bird.render(batch);
        }


        // Draw trajectory
        for (Vector2 point : trajectoryPoints) {
            batch.draw(redBirdTexture2, point.x, point.y, 5, 5);
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
//        debugRenderer.render(world, camera.combined.cpy().scl(Main.PPM));
    }


    @Override
    public void dispose() {
        for (Obstacles obstacle : obstacles) {
            obstacle.dispose();
        }
        redBirdTexture.dispose();
        slingshot.dispose();
        background.dispose();
        font.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

    @Override
    public Level01State clone(){
        return new Level01State(gameStateManager);
    }





    public List<Vector2> calculateTrajectory(Vector2 startPos, Vector2 velocity, float timeStep, int numPoints) {
        List<Vector2> trajectoryPoints = new ArrayList<>();
        Vector2 gravity = new Vector2(0, -9.8f);

        for (int i = 0; i < numPoints; i++) {
            float t = i * timeStep;
            float posX = startPos.x + velocity.x * t;
            float posY = startPos.y + velocity.y * t + 0.5f * gravity.y * t * t;

            trajectoryPoints.add(new Vector2(posX, posY));

            if (posY < 0) break; // Stop when the trajectory hits the ground
        }

        return trajectoryPoints;
    }



}
