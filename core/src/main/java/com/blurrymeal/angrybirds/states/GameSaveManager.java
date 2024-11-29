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

        public SavedGameState() {}

        public SavedGameState(Level01State level) {
            this.currentLevel = 1;
            this.score = level.getScore();
            this.birdCounter = level.getBirdCounter();
            this.pigCounter = level.getPigCounter();
        }
    }

    public static void saveGame(Level01State currentLevel) {
        try {
            SavedGameState savedState = new SavedGameState(currentLevel);

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
