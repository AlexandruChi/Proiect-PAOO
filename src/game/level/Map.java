package game.level;

import game.level.LevelManager;

public class Map {
    private LevelManager levelManager;
    private char[][] map;
    private int curentMap;
    private int width;
    private int height;

    public Map(String file) {
        levelManager = new LevelManager(file);
        curentMap = 1;
        width = levelManager.getWidth();
        height = levelManager.getHeight();
    }

    public boolean loadNextMap() {
        curentMap++;
        map = levelManager.loadMap(curentMap);
        return map != null;
    }
}
