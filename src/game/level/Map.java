package game.level;

import game.Camera;
import game.Draw;
import game.Window;
import game.component.Pair;
import game.component.Tile;
import game.component.position.Corner;
import game.component.position.RelativeCoordinates;
import game.component.texture.Texture;

import java.awt.*;
import java.util.Vector;

// TODO better coordinates system

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
        Tile tile;
        for (int l = 0; l < nrLayers; l++) {
            for (int i = 0; i < height / mapScale / Tile.getLayerScale(l + 1); i++) {
                for (int j = 0; j < width / mapScale / Tile.getLayerScale(l + 1); j++) {
                    tile = map.get(l)[i][j];
                    Texture texture;
                    if (Tile.hasCorner(tile) && getCorner(l, i, j) != 0) {
                        texture = Tile.getTileCornerTexture(tile)[getCorner(l, i, j) - 1];

                        if (hasOverlap(l, i, j)) {
                            Draw.draw(graphics, camera, Tile.getTileTexture(Tile.getNormal(tile)), j * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale, i * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale);
                        }

                    } else {
                        texture = Tile.getTileTexture(tile);
                    }
                    Draw.draw(graphics, camera, texture, j * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale, i * Tile.getTileScale(tile) * Window.objectSize * Map.mapScale);
                }
            }
        }
    }

    public boolean canWalkOn(int x, int y) {

        // Tile -> layer indexat de la 1
        // Map  -> layer indexat de la 0

        boolean ok = false;
        if (y < 0 || y >= heightPX || x < 0 || x >= widthPX) {
            return false;
        } else {
            for (int layer = 0; layer < nrLayers; layer++) {
                if (Tile.canWalkOn(Tile.getNormal(Tile.getLayerTile(layer + 1))) || Tile.canWalkOn(Tile.getEnv(Tile.getLayerTile(layer + 1)))) {
                    int tileX = x / Window.objectSize / mapScale / Tile.getLayerScale(layer + 1);
                    int tileY = y / Window.objectSize / mapScale / Tile.getLayerScale(layer + 1);
                    if (map.get(layer)[tileY][tileX] != null) {
                        if (Tile.canWalkOn(map.get(layer)[tileY][tileX])) {
                            if (getCorner(layer, tileY, tileX) == 0 || (hasOverlap(layer, tileY, tileX) && Tile.canWalkOn(Tile.getNormal(map.get(layer)[tileY][tileX])))) {
                                ok = true;
                            } else if (!ok){
                                ok = !Corner.isInCorner(Tile.getLayerScale(layer + 1) * Window.objectSize * mapScale, RelativeCoordinates.getRelativeCoordinates(new Pair<>(tileX * Tile.getLayerScale(layer + 1) * Window.objectSize * mapScale, tileY * Tile.getLayerScale(layer + 1) * Window.objectSize * mapScale), new Pair<>((x), (y))), getCorner(layer, tileY, tileX));
                            }
                        } else {
                            ok = false;
                        }
                    }
                }
            }
        }

        return ok;
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
                if ((Tile.getNormal(map.get(layer)[y - difY][x]) == Tile.getNormal(map.get(layer)[y][x])) && (Tile.getNormal(map.get(layer)[y][x - difX]) == Tile.getNormal(map.get(layer)[y][x]))) {
                    return i;
                }
            }
        }
        return 0;
    }

    private boolean hasOverlap(int layer, int y, int x) {
        if (y <= 0 || y >= (height / mapScale / Tile.getLayerScale(layer + 1)) - 1 || x <= 0 || x >= (width / mapScale / Tile.getLayerScale(layer + 1)) - 1) {
            return false;
        }

        return Tile.getNormal(map.get(layer)[y][x]) == Tile.getNormal(map.get(layer)[y][x + 1]) && Tile.getNormal(map.get(layer)[y][x]) == Tile.getNormal(map.get(layer)[y][x - 1]) && Tile.getNormal(map.get(layer)[y][x]) == Tile.getNormal(map.get(layer)[y + 1][x]) && Tile.getNormal(map.get(layer)[y][x]) == Tile.getNormal(map.get(layer)[y - 1][x]);
    }
}
