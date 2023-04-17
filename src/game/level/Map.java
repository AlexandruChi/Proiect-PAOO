package game.level;

import game.level.LevelManager;

import java.awt.*;

public class Map {
    private char[][] map;
    private int curentMap;
    public static final int width = 500;
    public static final int height = 500;

    public Map(String file) {
        LevelManager.readFile(file);
        curentMap = 0;
        if (!loadNextMap()) {
            // TODO add error
            System.exit(1);
        }
    }

    public boolean loadNextMap() {
        curentMap++;
        if (!LevelManager.loadLevel(curentMap)) {
            return false;
        }
        map = LevelManager.loadMap();
        return true;
    }

    public void draw(Graphics graphics) {
        // TODO map print
    }
}
