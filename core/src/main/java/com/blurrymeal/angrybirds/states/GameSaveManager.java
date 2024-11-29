package com.blurrymeal.angrybirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.Serializable;

public class GameSaveManager {
    private static final String SAVE_FILE_NAME = "savedgame.json";

    public static class SavedGameState implements Serializable {
        public int currentLevel;
        public int score;
        public int birdCounter;
        public int pigCounter;

        // Add more level-specific attributes as needed
        public SavedGameState() {}

        // Constructor for Level01State
        public SavedGameState(Level01State level) {
            this.currentLevel = 1;
            this.score = level.getScore();
            this.birdCounter = level.getBirdCounter();
            this.pigCounter = level.getPigCounter();
        }

        // Constructor for Level02State
        public SavedGameState(Level02State level) {
            this.currentLevel = 2;
            this.score = level.getScore();
            this.birdCounter = level.getBirdCounter();
            this.pigCounter = level.getPigCounter();
        }

        public SavedGameState(Level03State level) {
            this.currentLevel = 3;
            this.score = level.getScore();
            this.birdCounter = level.getBirdCounter();
            this.pigCounter = level.getPigCounter();
        }

        // Add more constructors for additional levels as needed
        // For example: public SavedGameState(Level03State level) { ... }
    }

    public static void saveGame(State currentLevel) {
        try {
            SavedGameState savedState;

            // Use instanceof to determine the correct level type
            if (currentLevel instanceof Level01State) {
                savedState = new SavedGameState((Level01State) currentLevel);
            } else if (currentLevel instanceof Level02State) {
                savedState = new SavedGameState((Level02State) currentLevel);
            } else if(currentLevel instanceof Level03State) {
                savedState = new SavedGameState((Level03State) currentLevel);
            }
            else {
                throw new IllegalArgumentException("Unsupported level type");
            }

            Json json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);

            String saveData = json.toJson(savedState);

            FileHandle saveFile = Gdx.files.local(SAVE_FILE_NAME);
            saveFile.writeString(saveData, false);

            System.out.println("Game saved successfully!");
        } catch (Exception e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public static SavedGameState loadGame() {
        try {
            FileHandle saveFile = Gdx.files.local(SAVE_FILE_NAME);

            if (!saveFile.exists()) {
                System.out.println("No saved game found.");
                return null;
            }

            Json json = new Json();
            SavedGameState loadedState = json.fromJson(SavedGameState.class, saveFile.readString());

            System.out.println("Game loaded successfully!");
            return loadedState;
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    public static boolean hasSavedGame() {
        FileHandle saveFile = Gdx.files.local(SAVE_FILE_NAME);
        return saveFile.exists();
    }
}
