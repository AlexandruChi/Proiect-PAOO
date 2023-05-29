package game.level;

import game.Camera;
import game.Draw;
import game.Game;
import game.Window;
import game.character.CharacterManager;
import game.component.*;
import game.component.position.Corner;
import game.component.position.Position;
import game.component.position.RelativeCoordinates;
import game.component.texture.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static game.component.ObjectTile.tmp;
import static game.component.ObjectTile.tree;

// TODO better coordinates system

/*
    Clasa petru harta jocului.
    Harta este împărțită în 4 layer-e, fiecare cu un tip diferit de tile, un layer cu obiectele generate pe tile-uri care
    este folosit pentru coliziuni și o listă cu obiecte cu care playerul poate interacționa;
    Dacă nu mai există hărți de încărcat, la final se reîncarcă starea de start a jocului
 */

public class Map {
    private static Map __map;

    private Vector<Tile[][]> map;

    private ObjectTile[][] objectMap;

    private List<Collectable> objectives;

    private int curentMap;

    private int environment;

    private int nrTrees;
    private int nrEnvTrees;
    private int nrRocks;
    private int nrEnvRocks;

    private int nrObjectives;
    private int nrFinishedObjectives;

    /*
        locația de la care se trece la nivelul următor
     */

    private boolean exit;
    private Position exitPosition;

    /*
        constanta mapScale stochează dimensiunea unui tile pe matricea jocului
     */

    public static final int mapScale = 2;

    /*
        mărimea matricei jocului și mărimea hărții în pixeli (folosită pentru afișare și calcularea distanțelor)
     */

    public static final int width = 500;
    public static final int height = 500;

    public static final int widthPX = width * Window.objectSize;
    public static final int heightPX = height * Window.objectSize;

    public static final int nrLayers = 4;

    public static Map getMap() {
        return __map;
    }

    public Map() {
        __map = this;
        exit = false;
        curentMap = 0;
        if (!loadNextMap()) {
            Game.getGame().startScreen();
        }
    }

    /*
        metoda loadNextMap citește datele despre următorul nivel și dacă există încarcă datele și texturile necesare
     */

    public boolean loadNextMap() {
        curentMap++;
        if (!LevelManager.loadLevel(curentMap)) {
            return false;
        }

        map = LevelManager.loadMap();

        environment = LevelManager.getEnvironment();

        objectMap = new ObjectTile[height][width];

        nrTrees = LevelManager.getNrTrees();
        nrEnvTrees = LevelManager.getNrEnvTrees();
        nrRocks = LevelManager.getNrRocks();
        nrEnvRocks = LevelManager.getNrEnvRocks();

        exit = false;
        exitPosition = LevelManager.getExitPosition();
        exitPosition.xPX *= Window.objectSize;
        //exitPosition.xPX *= (int)((double)Game.getGame().getHeight() / 540);
        exitPosition.tmpX = exitPosition.xPX;
        exitPosition.yPX *= Window.objectSize;
        //exitPosition.yPX *= (int)((double)Game.getGame().getHeight() / 540);
        exitPosition.tmpY = exitPosition.yPX;

        generateObjects(objectMap);

        nrObjectives = LevelManager.getNrObjectives();
        nrFinishedObjectives = 0;
        objectives = new ArrayList<>();

        addObjectives();

        if (nrFinishedObjectives == nrObjectives) {
            exit = true;
        }

        return true;
    }

    /*
        generarea aleatorie obiectelor cu care jucătorul poate interacționa
     */

    private void addObjectives() {
        int objectivesToGenerate = nrObjectives;
        nrObjectives = 0;
        int maxCollectables = 30;
        for (int i = 0; nrObjectives != objectivesToGenerate && i <= maxCollectables; i++) {
            int generateTimer = 0;
            boolean ok = false;
            while (generateTimer < 100 && !ok) {
                generateTimer++;
                int x = RandomNumber.randomNumber(0, widthPX - 1);
                int y = RandomNumber.randomNumber(0, heightPX - 1);

                if (canWalkOn(x, y)) {
                    Collectable collectable = new Collectable(new Position(x, y));
                    objectives.add(collectable);
                    ok = true;
                    if (collectable.getType() == Collectable.map) {
                        nrObjectives++;
                    }
                }
            }
        }
    }

    /*
        calculează dacă nu există obstacole între două puncte
     */

    public boolean lineOfSight(Position p1, Position p2) {
        int signX = p2.xPX / Window.objectSize - p1.xPX / Window.objectSize;
        try {
            signX = signX / Math.abs(signX);
        } catch (ArithmeticException e) {
            signX = 0;
        }

        int signY = p2.yPX / Window.objectSize - p1.yPX / Window.objectSize;
        try {
            signY = signY / Math.abs((signY));
        } catch (ArithmeticException e) {
            signY = 0;
        }

        int prevX = -1 , prevY = -1;

        for (int tileY = p1.yPX / Window.objectSize; switch (signY) {
            case 1 -> tileY <= p2.yPX / Window.objectSize;
            case -1 -> tileY >= p2.yPX / Window.objectSize;
            default -> false;
        }; tileY += signY) {
            for (int tileX = p1.xPX / Window.objectSize; switch (signX) {
                case 1 -> tileX <= p2.xPX / Window.objectSize;
                case -1 -> tileX >= p2.xPX / Window.objectSize;
                default -> false;
            }; tileX += signX) {

                if (prevX == -1) {
                    prevX = tileX;
                }
                if (prevY == -1) {
                    prevY = tileY;
                }

                if (!ObjectTile.canSeeThrew(objectMap[tileY][tileX], environment)) {
                    return false;
                }

                if (tileX != prevX && tileY != prevY) {
                    if (((tileY + signY >= 0 && tileY + signY < height) && !ObjectTile.canSeeThrew(objectMap[tileY + signY][tileX], environment)) && ((tileX + signX >= 0 && tileX + signX < height) && !ObjectTile.canSeeThrew(objectMap[tileY][tileX + signX], environment))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /*
        generează obiectele de decor a hărții și harta cu coliziuni pentru acestea
     */

    private void generateObjects(ObjectTile[][] objectMap) {

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
                                ok = addObject(objectMap, tile, x, y);
                            }
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
    }

    private boolean addObject(ObjectTile[][] objectMap, ObjectTile tile, int x, int y) {
        if (!addTmpObjects(objectMap, tile, x, y)) {
            return false;
        }
        if (getCorner(1, y / mapScale / Tile.getLayerScale(2), x / mapScale / Tile.getLayerScale(2)) == 0) {
            objectMap[y][x] = tile;
        } else {
            addObjectToCorner(objectMap, tile, x, y);
        }
        return true;
    }

    private boolean addTmpObjects(ObjectTile[][] objectMap, ObjectTile tile, int x, int y) {
        int difX = ObjectTile.getHitBoxSize(tile, environment).getLeft();
        int difY = ObjectTile.getHitBoxSize(tile, environment).getRight();

        for (int i = y; i < y + difY; i++) {
            for (int j = x; j < x + difX; j++) {
                if (i != y || j != x) {

                    if (i >= height - 1 || j >= width - 1) {
                        return false;
                    }

                    if (objectMap[i][j] != null || objectMap[i + 1][j + 1] != null || objectMap[i][j + 1] != null) {
                        return false;
                    }
                    if (j == x && objectMap[i - 1][j + 1] != null) {
                        return false;
                    }
                    if (i == y && objectMap[i + 1][j - 1] != null) {
                        return false;
                    }
                    if (!(map.get(3)[i / mapScale / Tile.getLayerScale(4)][j / mapScale / Tile.getLayerScale(4)] == null && map.get(2)[i / mapScale / Tile.getLayerScale(3)][j / mapScale / Tile.getLayerScale(3)] == null && map.get(1)[i / mapScale / Tile.getLayerScale(2)][j / mapScale / Tile.getLayerScale(2)] != null)) {
                        return false;
                    }
                    if (map.get(1)[i / mapScale / Tile.getLayerScale(2)][j / mapScale / Tile.getLayerScale(2)] == Tile.envGround && getCorner(1, i / mapScale / Tile.getLayerScale(2), j / mapScale / Tile.getLayerScale(2)) != 0 && hasOverlap(1, i / mapScale / Tile.getLayerScale(2), j / mapScale / Tile.getLayerScale(2))) {
                        int groundTileX = j / mapScale / Tile.getLayerScale(2);
                        int groundTileY = i / mapScale / Tile.getLayerScale(2);

                        if (Corner.isInCorner(Tile.getLayerScale(2) * mapScale, RelativeCoordinates.getRelativeCoordinates(new Pair<>(groundTileX * mapScale * Tile.getLayerScale(2), groundTileY * mapScale * Tile.getLayerScale(2)), new Pair<>((j), (i))), getCorner(1, groundTileY, groundTileX))) {
                            return false;
                        }
                    }
                }
            }
        }

        for (int i = y; i < y + difY; i++) {
            for (int j = x; j < x + difX; j++) {
                if (i != y || j != x) {
                    objectMap[i][j] = tmp;
                }
            }
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

    /*
        metoda draw calculează pozițiile și distanțele dintre obiecte pentru a le afișa în ordinea corectă
     */

    public void draw(Graphics graphics, Camera camera, CharacterManager characterManager) {
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

        int characterIndex = 0;
        int collectableIndex = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                while (characterIndex < characterManager.getCharacters().size() && characterManager.getCharacters().get(characterIndex).getPosition().yPX / Window.objectSize == i - 1) {
                    characterManager.getCharacters().get(characterIndex).draw(graphics, camera);
                    characterIndex++;
                }

                if (i == height - 1) {
                    while (characterIndex < characterManager.getCharacters().size() && characterManager.getCharacters().get(characterIndex).getPosition().yPX / Window.objectSize == i) {
                        characterManager.getCharacters().get(characterIndex).draw(graphics, camera);
                        characterIndex++;
                    }
                }

                while (collectableIndex < objectives.size() && objectives.get(collectableIndex).getPosition().yPX / Window.objectSize == i) {
                    objectives.get(collectableIndex).draw(graphics, camera);
                    collectableIndex++;
                }

                Texture texture = ObjectTile.getTexture(objectMap[i][j]);
                if (objectMap[i][j] == tree && !characterManager.getPlayer().isDead()) {
                    if (i > (characterManager.getPlayer().getPosition().yPX / Window.objectSize) + 2 && Math.abs(j - characterManager.getPlayer().getPosition().xPX / Window.objectSize) < 25) {
                        texture = ObjectTile.getTransparentTexture(objectMap[i][j]);

                    }
                }
                if (objectMap[i][j] != null) {
                    Pair<Integer, Integer> printBox = ObjectTile.getPrintBoxSize(objectMap[i][j], environment);
                    if (printBox != null) {
                        Draw.draw(graphics, camera, texture, (j - printBox.getLeft()) * Window.objectSize, (i - printBox.getRight()) * Window.objectSize);
                    }
                }
            }
        }
    }

    public List<Collectable> getObjectives() {
        return objectives;
    }

    public ObjectTile[][] getObjectMap() {
        return  objectMap;
    }

    /*
        verifică dacă un personaj poate exista la o anumită coordonată
     */

    public boolean canWalkOn(int x, int y) {

        // Tile -> layer indexat de la 1
        // Map  -> layer indexat de la 0

        boolean ok = false;
        if (y < 0 || y >= heightPX || x < 0 || x >= widthPX) {
            return false;
        } else if (objectMap[y / Window.objectSize][x / Window.objectSize] != null) {
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
                            } else if (!ok) {
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

    /*
        Calculează dacă un tile este pe marginea zonei în care trebuie afișat sau două tile-uri sunt una deasupra
        celeilalte pentru a încarcă textura potrivită conțului sau marginii respective
     */

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

    public int getNrObjectives() {
        return nrObjectives;
    }

    public int getNrFinishedObjectives() {
        return nrFinishedObjectives;
    }

    public int curentLevel() {
        return curentMap;
    }

    public void incFinalizedObjectives() {
        nrFinishedObjectives++;
        if (nrFinishedObjectives == nrObjectives) {
            exit = true;
        }
    }

    public Position getExit() {
        if (exit) {
            return exitPosition;
        }
        return null;
    }

    public int getCurentMap() {
        return curentMap;
    }
}