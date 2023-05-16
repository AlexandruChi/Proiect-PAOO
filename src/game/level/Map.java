package game.level;

import game.Camera;
import game.Draw;
import game.Window;
import game.component.ObjectTile;
import game.component.Pair;
import game.component.RandomNumber;
import game.component.Tile;
import game.component.position.Corner;
import game.component.position.RelativeCoordinates;
import game.component.texture.Texture;

import java.awt.*;
import java.util.Vector;

// TODO better coordinates system

public class Map {
    private Vector<Tile[][]> map;

    private ObjectTile[][] objectMap;
    private CollisionMap collisionMap;

    private int curentMap;

    private int environment;

    private int nrTrees;
    private int nrEnvTrees;
    private int nrRocks;
    private int nrEnvRocks;

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

        objectMap = new ObjectTile[height][width];
        collisionMap = new CollisionMap();
        collisionMap.collisionMap = new boolean[height][width];

        // TODO add loading for objects from save

        nrTrees = LevelManager.getNrTrees();
        nrEnvTrees = LevelManager.getNrEnvTrees();
        nrRocks = LevelManager.getNrRocks();
        nrEnvRocks = LevelManager.getNrEnvRocks();

        environment = LevelManager.getEnvironment();

        generateObjects(objectMap, collisionMap);

        return true;
    }

    private void generateObjects(ObjectTile[][] objectMap, CollisionMap collisionMap) {

        for (int i = 0; i < 2; i++) {
            ObjectTile tile = switch (i) {
                case 0 -> ObjectTile.tree;
                case 1 -> ObjectTile.rock;
                default -> null;
            };

            int nrObjects = switch (tile) {
                case tree -> nrTrees;
                case rock -> nrRocks;
                default -> 0;
            };

            int nrEnvObjects = switch (tile) {
                case tree -> nrEnvTrees;
                case rock -> nrEnvRocks;
                default -> 0;
            };

            for (int j = 0; j < nrObjects; j++) {

                int timer = 0;

                tile = ObjectTile.getNormal(tile);

                boolean ok = false;

                while (timer < 100 && !ok) {
                    timer++;

                    int x = RandomNumber.randomNumber(1, width - 2);
                    int y = RandomNumber.randomNumber(1, height - 2);

                    if (
                                    objectMap[y][x] == null &&
                                    objectMap[y - 1][x] == null &&
                                    objectMap[y + 1][x] == null &&
                                    objectMap[y][x - 1] == null &&
                                    objectMap[y][x + 1] == null &&
                                    objectMap[y - 1][x - 1] == null &&
                                    objectMap[y - 1][x + 1] == null &&
                                    objectMap[y + 1][x - 1] == null &&
                                    objectMap[y + 1][x + 1] == null &&

                                    map.get(3)[y / mapScale / Tile.getLayerScale(4)][x / mapScale / Tile.getLayerScale(4)] == null &&
                                    map.get(2)[y / mapScale / Tile.getLayerScale(3)][x / mapScale / Tile.getLayerScale(3)] == null &&
                                    map.get(1)[y / mapScale / Tile.getLayerScale(2)][x / mapScale / Tile.getLayerScale(2)] != null
                        ) {

                            // TODO check for full hitbox

                        if (map.get(1)[y / mapScale / Tile.getLayerScale(2)][x / mapScale / Tile.getLayerScale(2)] == Tile.ground) {
                            ok = addObject(objectMap, tile, x, y);
                        } else if (
                                        map.get(1)[y / mapScale / Tile.getLayerScale(2)][x / mapScale / Tile.getLayerScale(2)] == Tile.envGround &&
                                        getCorner(1, y / mapScale / Tile.getLayerScale(2), x / mapScale / Tile.getLayerScale(2)) != 0 &&
                                        hasOverlap(1, y / mapScale / Tile.getLayerScale(2), x / mapScale / Tile.getLayerScale(2))
                        ) {

                            int groundTileX = x / mapScale / Tile.getLayerScale(2);
                            int groundTileY = y / mapScale / Tile.getLayerScale(2);

                            if (Corner.isInCorner(Tile.getLayerScale(2) * mapScale, RelativeCoordinates.getRelativeCoordinates(new Pair<>(groundTileX * mapScale * Tile.getLayerScale(2), groundTileY * mapScale * Tile.getLayerScale(2)), new Pair<>((x), (y))), getCorner(1, groundTileY, groundTileX))) {
                                objectMap[y][x] = tile;
                            }
                            ok = true;
                        }
                    }
                }
            }

            for (int j = 0; j < nrEnvObjects; j++) {

                int timer = 0;

                tile = ObjectTile.getEnv(tile);

                boolean ok = false;

                while (timer < 100 && !ok) {
                    timer++;

                    int x = RandomNumber.randomNumber(1, width - 2);
                    int y = RandomNumber.randomNumber(1, height - 2);

                    if (
                                    objectMap[y][x] == null &&
                                    objectMap[y - 1][x] == null &&
                                    objectMap[y + 1][x] == null &&
                                    objectMap[y][x - 1] == null &&
                                    objectMap[y][x + 1] == null &&
                                    objectMap[y - 1][x - 1] == null &&
                                    objectMap[y - 1][x + 1] == null &&
                                    objectMap[y + 1][x - 1] == null &&
                                    objectMap[y + 1][x + 1] == null &&

                                    map.get(3)[y / mapScale / Tile.getLayerScale(4)][x / mapScale / Tile.getLayerScale(4)] == null &&
                                    map.get(2)[y / mapScale / Tile.getLayerScale(3)][x / mapScale / Tile.getLayerScale(3)] == null &&
                                    map.get(1)[y / mapScale / Tile.getLayerScale(2)][x / mapScale / Tile.getLayerScale(2)] != null
                    ) {

                        if (map.get(1)[y / mapScale / Tile.getLayerScale(2)][x / mapScale / Tile.getLayerScale(2)] == Tile.envGround) {
                            ok = addObject(objectMap, tile, x, y);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (objectMap[i][j] != null) {
                    int difX = ObjectTile.getHitBoxSize(objectMap[i][j], environment).getLeft();
                    int difY = ObjectTile.getHitBoxSize(objectMap[i][j], environment).getRight();

                    for (int y = i; y < i + difY; y++) {
                        for (int x = j; x < j + difX; x++) {
                            collisionMap.collisionMap[y][x] = true;
                        }
                    }

                }
            }
        }
    }

    private boolean addObject(ObjectTile[][] objectMap, ObjectTile tile, int x, int y) {
        if (getCorner(1, y / mapScale / Tile.getLayerScale(2), x / mapScale / Tile.getLayerScale(2)) == 0) {
            objectMap[y][x] = tile;
        } else {
            addObjectToCorner(objectMap, tile, x, y);
        }
        return true;
    }

    private void addObjectToCorner(ObjectTile[][] objectMap, ObjectTile tile, int x, int y) {
        int groundTileX = x / mapScale / Tile.getLayerScale(2);
        int groundTileY = y / mapScale / Tile.getLayerScale(2);

        if (!Corner.isInCorner(Tile.getLayerScale(2) * mapScale, RelativeCoordinates.getRelativeCoordinates(new Pair<>(groundTileX * mapScale * Tile.getLayerScale(2), groundTileY * mapScale * Tile.getLayerScale(2)), new Pair<>((x), (y))), getCorner(1, groundTileY, groundTileX))) {
            objectMap[y][x] = tile;
        }
    }

    public void draw(Graphics graphics, Camera camera) {

        // TODO make draw matrix in game state

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

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (objectMap[i][j] != null) {
                    Pair<Integer, Integer> printBox = ObjectTile.getPrintBoxSize(objectMap[i][j], environment);
                    Draw.draw(graphics, camera, ObjectTile.getTexture(objectMap[i][j]), (j - printBox.getLeft()) * Window.objectSize, (i - printBox.getRight()) * Window.objectSize);
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
        } else if (collisionMap.collisionMap[y / Window.objectSize][x / Window.objectSize]) {
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

class CollisionMap {
    public boolean[][] collisionMap;
}