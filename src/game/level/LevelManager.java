package game.level;

import game.Window;
import game.component.Tile;
import game.component.position.Position;
import game.graphics.assets.MapAssets;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LevelManager {
    //TODO read data from file and database
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;
    private static Vector<Tile[][]> map;
    private static Tile[][] layer1, layer2, layer3, layer4;
    private static int environment;
    private static int nrMaps;

    private static int nrTrees;
    private static int nrEnvTrees;
    private static int nrRocks;
    private static int nrEnvRocks;

    private static Position playerPosition;

    public static void readFile(String file) {
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String[] data = line.split(" ");
            if (data.length != 2) {
                // TODO error
                System.exit(1);
            }
            if (!data[0].equals("nrLevels")) {
                // TODO error
                System.exit(1);
            }
            try {
                nrMaps = Integer.parseInt(data[1]);
            } catch (NumberFormatException e) {
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO error;
        }
    }

    public static boolean loadLevel(int level) {
        if (level > nrMaps) {
            return false;
        }
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int parsedLevel;
                String[] data = TextParser.parse(line);
                if (data.length == 2) {
                    if (data[0].equals("level")) {
                        try {
                            parsedLevel = Integer.parseInt(data[1]);
                            if (parsedLevel == level) {
                                return readLevelData();
                            }
                        } catch (NumberFormatException ignored) {}
                    } else if (data[0].equals("endlevel")) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static boolean readLevelData() {
        boolean readMap = false;
        boolean readEnv = false;
        boolean readObjects = false;
        boolean readPlayer = false;
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // TODO add more tags
                String[] data = TextParser.parse(line);
                if (data.length == 1) {
                    switch (data[0]) {
                        case "endLevel" -> {
                            return readMap && readEnv && readObjects &&  readPlayer;
                        }
                        case "map" -> {
                            if (!readMap) {
                                readMap = readMapData();
                            }
                        }
                        case "objects" -> {
                            readObjects = readObjectsData();
                        }
                        case "env" -> {
                            if (!readEnv) {
                                readEnv = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    environment = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readEnv = false;
                                }
                            }
                        }
                        case "player" -> {
                            if (!readPlayer) {
                                readPlayer = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    playerPosition = new Position(Integer.parseInt(numbers[0]) * Window.objectSize, Integer.parseInt(numbers[1]) * Window.objectSize);
                                } catch (NumberFormatException e) {
                                    readPlayer = false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static boolean readObjectsData() {
        boolean readNrTrees = false;
        boolean readNrEnvTrees = false;
        boolean readNrRocks = false;
        boolean readNrEnvRocks = false;

        try {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = TextParser.parse(line);
                if (data.length == 1) {
                    switch (data[0]) {
                        case "endObjects" -> {
                            return readNrTrees && readNrEnvTrees && readNrRocks && readNrEnvRocks;
                        }
                        case "nrTrees" -> {
                            if (!readNrTrees) {
                                readNrTrees = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    nrTrees = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readNrTrees = false;
                                }
                            }
                        }
                        case "nrEnvTrees" -> {
                            if (!readNrEnvTrees) {
                                readNrEnvTrees = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    nrEnvTrees = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readNrEnvTrees = false;
                                }
                            }
                        }
                        case "nrRocks" -> {
                            if (!readNrRocks) {
                                readNrRocks = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    nrRocks = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readNrRocks = false;
                                }
                            }
                        }
                        case "nrEnvRocks" -> {
                            if (!readNrEnvRocks) {
                                readNrEnvRocks = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    nrEnvRocks = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readNrEnvRocks = false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private static boolean readMapData() {
        boolean readLayer1 = false;
        boolean readLayer2 = false;
        boolean readLayer3 = false;
        boolean readLayer4 = false;

        Tile[][] layer;

        int yMap = Map.height / Map.mapScale;
        int xMap = Map.width / Map.mapScale;

        int x = 0, y = 0;

        Tile tile = null;
        Tile envTile = null;

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = TextParser.parse(line);
                if (data.length == 1) {
                    if (data[0].equals("endMap")) {
                        return readLayer1 && readLayer2 && readLayer3 && readLayer4;
                    }
                } else if (data.length == 2) {
                    if (data[0].equals("layer")) {
                        switch (Integer.parseInt(data[1])) {
                            case 1 -> {
                                readLayer1 = true;
                                x = xMap / Tile.getTileScale(Tile.water);
                                y = yMap / Tile.getTileScale(Tile.water);
                                layer1 = new Tile[y][x];
                                layer = layer1;
                                tile = Tile.water;
                                envTile = Tile.envWater;
                            }
                            case 2 -> {
                                readLayer2 = true;
                                x = xMap / Tile.getTileScale(Tile.ground);
                                y = yMap / Tile.getTileScale(Tile.ground);
                                layer2 = new Tile[y][x];
                                layer = layer2;
                                tile = Tile.ground;
                                envTile = Tile.envGround;
                            }
                            case 3 -> {
                                readLayer3 = true;
                                x = xMap / Tile.getTileScale(Tile.path);
                                y = yMap / Tile.getTileScale(Tile.path);
                                layer3 = new Tile[y][x];
                                layer = layer3;
                                tile = Tile.path;
                                envTile = Tile.envPath;
                            }
                            case 4 -> {
                                readLayer4 = true;
                                x = xMap / Tile.getTileScale(Tile.road);
                                y = yMap / Tile.getTileScale(Tile.road);
                                layer4 = new Tile[y][x];
                                layer = layer4;
                                tile = Tile.road;
                                envTile = Tile.envRoad;
                            }
                            default -> layer = null;
                        }
                        if (layer != null) {
                            try {
                                for (int i = 0; i < y; i++) {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    for (int j = 0; j < x; j++) {
                                        switch (Integer.parseInt(numbers[j])) {
                                            case 1 -> layer[i][j] = tile;
                                            case 2 -> layer[i][j] = envTile;
                                            case 0 -> layer[i][j] = null;
                                            default ->
                                                // TODO add error
                                                    System.exit(1);
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                // TODO add error;
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static Vector<Tile[][]> loadMap() {
        MapAssets.loadEnvironment(environment);

        map = new Vector<>();
        map.add(layer1);
        map.add(layer2);
        map.add(layer3);
        map.add(layer4);

        return map;
    }

    public static int getEnvironment() {
        return environment;
    }

    public static int getNrTrees() {
        return nrTrees;
    }

    public static int getNrEnvTrees() {
        return nrEnvTrees;
    }

    public static int getNrRocks() {
        return nrRocks;
    }

    public static int getNrEnvRocks() {
        return nrEnvRocks;
    }

    public static Position getPlayerPosition() {
        return playerPosition;
    }
}

class TextParser {
    private static final String delimiters = " <>\t";

    public static String[] parse(String text) {
        List<String> words = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (delimiters.indexOf(c) >= 0) {
                if (start < i) {
                    String word = text.substring(start, i);
                    words.add(word);
                }
                start = i + 1;
            }
        }
        if (start < text.length()) {
            String word = text.substring(start);
            words.add(word);
        }
        String[] result = new String[words.size()];
        return words.toArray(result);
    }
}