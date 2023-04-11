package game.level;

import java.io.FileReader;

public class LevelManager {
    //TODO read data from file and database
    private String filePath;
    private FileReader file;
    private char[][] map;
    private int width, height;

    public LevelManager(String file) {
        filePath = file;
    }

    public char[][] loadMap(int map) {
        return this.map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
