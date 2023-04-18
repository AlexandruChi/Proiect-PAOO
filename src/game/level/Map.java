package game.level;

import game.Camera;
import game.Draw;
import game.component.texture.Texture;
import game.graphics.assets.MapAssets;

import java.awt.*;

public class Map {
    public static final char water = 0;
    public static final char ground = 1;
    public static final char road = 2;

    private char[][] map;
    private int curentMap;

    public static final int width = 5;
    public static final int height = 5;

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

    public void draw(Graphics graphics, Camera camera) {
        Texture tile = null;
        // TODO better map print
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (map[i][j]) {
                    case 0 -> tile = MapAssets.water;
                    case 1 -> tile = MapAssets.ground;
                    case 2 -> tile = MapAssets.road;
                }
                if (tile != null) {
                    Draw.draw(graphics, camera, tile, j * tile.width, i * MapAssets.tileSize);
                }
            }
        }
    }
}
