package game.level;

import game.Camera;
import game.Draw;
import game.Window;
import game.component.Tile;
import game.component.position.Position;
import game.component.texture.MakeTexture;
import game.component.texture.Texture;
import game.graphics.ImageLoader;

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
                    Texture texture;
                    if (Tile.hasCorner(tile) && getCorner(l, i, j) != 0) {
                        texture = Tile.getTileCornerTexture(tile)[getCorner(l, i, j) - 1];
                    } else {
                        texture = Tile.getTileTexture(tile);
                    }
                    Draw.draw(graphics, camera, texture, j * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale, i * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale);
                }
            }
        }
    }

    public boolean canWalkOn(int x, int y) {
        if (y < 0 || y >= heightPX || x < 0 || x >= widthPX) {
            return false;
        }
        return true;
    }

    private int getCorner(int layer, int y, int x) {
        if (y <= 0 || y >= (height / mapScale / Tile.getLayerScale(layer + 1)) - 1 || x <= 0 || x >= (width / mapScale / Tile.getLayerScale(layer + 1)) - 1) {
            return 0;
        }

        for (int i = 1; i <= 4; i++) {
            int difX, difY;

            if (i == 1) {
                difX = 1;
                difY = -1;
            } else if (i == 2) {
                difX = -1;
                difY = -1;
            } else if (i == 3) {
                difX = -1;
                difY = 1;
            } else {
                difX = 1;
                difY = 1;
            }

            if ((map.get(layer)[y + difY][x] != map.get(layer)[y][x]) && (map.get(layer)[y][x + difX] != map.get(layer)[y][x])) {
                if ((map.get(layer)[y - difY][x] == map.get(layer)[y][x]) && (map.get(layer)[y][x - difX] == map.get(layer)[y][x])) {
                    return i;
                }
            }
        }
        return 0;
    }
}
