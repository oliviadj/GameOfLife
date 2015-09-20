package com.olivia.task.gameoflife;

public class SavedInstanceObj {

    private static SavedInstanceObj instance = null;

    public static Cell[][] gameState = new Cell[Config.NUMBER_OF_ROW][Config.NUMBER_OF_COLUMN];

    public static boolean isGameSaved;

    public static SavedInstanceObj getInstance(){
        if (instance == null) {
            instance = new SavedInstanceObj();
        }
        return instance;
    }

}
