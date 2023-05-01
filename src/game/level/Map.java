package game.level;

import game.Camera;
import game.Draw;
import game.Window;
import game.component.Tile;
import game.component.position.Position;

import java.awt.*;
import java.util.Vector;

public class Map {
    private Vector<Tile[][]> map;
    private int curentMap;

    public static final int mapScale = 2;

    public static final int width = 500;
    public static final int height = 500;

    public static final int widthPX = width * Window.objectSize;
    public static final int heightPX = height * Window.objectSize;

    public static final int nrLayers = 4;

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
        // TODO side tiles
        Tile tile;
        for (int l = 0; l < nrLayers; l++) {
            for (int i = 0; i < height / mapScale / Tile.getLayerScale(l + 1); i++) {
                for (int j = 0; j < width / mapScale / Tile.getLayerScale(l + 1); j++) {
                    tile = map.get(l)[i][j];
                    Draw.draw(graphics, camera, Tile.getTileTexture(tile), j * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale, i * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale);
                }
            }
        }
    }

    public boolean canWalkOn(int x, int y) {
        if (y < 0 || y > heightPX || x < 0 || x > widthPX) {
            return false;
        }
        return true;
    }
}
