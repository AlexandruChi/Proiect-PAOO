package game.level;

import game.Window;
import game.character.Character;
import game.character.CharacterManager;
import game.character.NormalEnemy;
import game.character.UndeadEnemy;
import game.component.Tile;
import game.component.position.Position;
import game.graphics.assets.MapAssets;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import java.sql.*;

public class LevelManager {
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

    private static int nrEnemiesNormal;
    private static int nrEnemiesUndead;

    private static int nrObjectives;

    private static Position playerPosition;

    private static Position exitPosition;

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

    public static void saveGame() {
        SaveManager.save(SaveManager.gameSave);
    }

    public static void saveLevelStart() {
        SaveManager.save(SaveManager.startSave);
    }

    public static void loadGame() {
        SaveManager.load(SaveManager.gameSave);
    }

    public static void loadLevelStart() {
        SaveManager.load(SaveManager.startSave);
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
        boolean readEnemies = false;
        boolean readObjectives = false;
        boolean readExitLocation = false;
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // TODO add more tags
                String[] data = TextParser.parse(line);
                if (data.length == 1) {
                    switch (data[0]) {
                        case "endLevel" -> {
                            return readMap && readEnv && readObjects && readPlayer && readEnemies && readExitLocation;
                        }
                        case "map" -> {
                            if (!readMap) {
                                readMap = readMapData();
                            }
                        }
                        case "objects" -> {
                            if (!readObjects) {
                                readObjects = readObjectsData();
                            }
                        }
                        case "enemies" -> {
                            if (!readEnemies) {
                                readEnemies = readEnemiesData();
                            }
                        }
                        case "exit" -> {
                            if (!readExitLocation) {
                                readExitLocation = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    exitPosition = new Position(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
                                } catch (NumberFormatException e) {
                                    readExitLocation = false;
                                }
                            }
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
                        case "objective" -> {
                            if (!readObjectives) {
                                readObjectives = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    nrObjectives = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readObjectives = false;
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
                                    playerPosition = new Position(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
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

    private static boolean readEnemiesData() {
        boolean readNrNormal = false;
        boolean readNrUndead = false;

        try {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = TextParser.parse(line);
                if (data.length == 1) {
                    switch (data[0]) {
                        case "endEnemies" -> {
                            if (!readNrNormal) {
                                nrEnemiesNormal = 0;
                            }
                            if (!readNrUndead) {
                                nrEnemiesUndead = 0;
                            }
                            return true;
                        }
                        case "normal" -> {
                            if (!readNrNormal) {
                                readNrNormal = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    nrEnemiesNormal = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readNrNormal = false;
                                }
                            }
                        }
                        case "undead" -> {
                            if (!readNrUndead) {
                                readNrUndead = true;
                                try {
                                    do {
                                        line = bufferedReader.readLine();
                                    } while (line.equals(""));
                                    String[] numbers = TextParser.parse(line);
                                    nrEnemiesUndead = Integer.parseInt(numbers[0]);
                                } catch (NumberFormatException e) {
                                    readNrUndead = false;
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

    public static int getNrObjectives() {
        return nrObjectives;
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

    public static int getNrEnemiesNormal() {
        return nrEnemiesNormal;
    }

    public static int getNrEnemiesUndead() {
        return nrEnemiesUndead;
    }
    public static Position getExitPosition() {
        return exitPosition;
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

class SaveManager {

    public static final int startSave = 0;
    public static final int gameSave = 1;

    public static void save(int id) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:save.db");
            c.setAutoCommit(false);
            Statement s = c.createStatement();


            ResultSet r = s.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='GAME'");

            boolean hasGameTable = r.next();

            if (!hasGameTable) {
                String createTable = "CREATE TABLE GAME (" +
                        "ID INT PRIMARY KEY NOT NULL, " +
                        "LEVEL INT NOT NULL, " +
                        "SCORE INT NOT NULL, " +
                        "MEDALS INT NOT NULL, " +
                        "NRCHARACTERS INT NOT NULL, " +
                        "NRENEMY INT NOT NULL, " +
                        "NRCOMPLETEDOBJECTIVES INT NOT NULL" +
                        ")";

                s.execute(createTable);
            }

            r = s.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='COLLECTABLES" + id + "'");
            boolean hasCollectablesTable = r.next();
            if (hasCollectablesTable) {
                s.execute("DROP TABLE COLLECTABLES" + id);
            }

            r = s.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='OBJECTS" + id + "'");
            boolean hasObjectsTable = r.next();
            if (hasObjectsTable) {
                s.execute("DROP TABLE OBJECTS" + id);
            }

            r = s.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='ENEMY" + id + "'");
            boolean hasEnemyTable = r.next();
            if (hasEnemyTable) {
                s.execute("DROP TABLE ENEMY" + id);
            }

            r = s.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='CHARACTER" + id + "'");
            boolean hasCharactersTable = r.next();
            if (hasCharactersTable) {
                s.execute("DROP TABLE CHARACTER" + id);
            }

            r = s.executeQuery("SELECT ID FROM GAME WHERE ID = " + id);

            boolean hasSave = r.next();

            if (!hasSave) {
                String insertSave = "INSERT INTO GAME (ID, LEVEL, SCORE, MEDALS, NRCHARACTERS, NRENEMY, NRCOMPLETEDOBJECTIVES)" +
                        "VALUES (" +
                        id + ", " +
                        Map.getMap().getCurentMap() + ", " +
                        CharacterManager.getCharacterManager().getPlayer().getScore() + ", " +
                        CharacterManager.getCharacterManager().getPlayer().getMedal() + ", " +
                        CharacterManager.getCharacterManager().getAlliedCharacters().size() + ", " +
                        CharacterManager.getCharacterManager().getEnemyCharacters().size() + ", " +
                        Map.getMap().getNrFinishedObjectives() +
                        ")";

                s.execute(insertSave);
            } else {
                String updateSave = "UPDATE GAME set " +
                        "LEVEL = " + Map.getMap().getCurentMap() + ", " +
                        "SCORE = " + CharacterManager.getCharacterManager().getPlayer().getScore() + ", " +
                        "MEDALS = " + CharacterManager.getCharacterManager().getPlayer().getMedal() + ", " +
                        "NRCHARACTERS = " + CharacterManager.getCharacterManager().getAlliedCharacters().size() + ", " +
                        "NRENEMY = " + CharacterManager.getCharacterManager().getEnemyCharacters().size() + ", " +
                        "NRCOMPLETEDOBJECTIVES = " + Map.getMap().getNrFinishedObjectives() + " " +
                        "where ID=" + id;

                s.execute(updateSave);
            }

            String createCollectables = "CREATE TABLE COLLECTABLES" + id + " (" +
                    "ID INT PRIMARY KEY NOT NULL, " +
                    "TYPE INT NOT NULL, " +
                    "X INT NOT NULL, " +
                    "Y INT NOT NULL" +
                    ")";

            s.execute(createCollectables);

            for (int i = 0; i < Map.getMap().getObjectives().size(); i++) {
                String insertCollectable = "INSERT INTO COLLECTABLES" + id + " (ID, TYPE, X, Y)" +
                        "VALUES (" +
                        i + ", " +
                        Map.getMap().getObjectives().get(i).getType() + ", " +
                        Map.getMap().getObjectives().get(i).getPosition().xPX + ", " +
                        Map.getMap().getObjectives().get(i).getPosition().yPX +
                        ")";

                s.execute(insertCollectable);
            }

            String createObjects = "CREATE TABLE OBJECTS" + id + " (" +
                    "ID INT PRIMARY KEY NOT NULL, " +
                    "TYPE INT NOT NULL, " +
                    "X INT NOT NULL, " +
                    "Y INT NOT NULL" +
                    ")";

            s.execute(createObjects);

            int idObjects = 0;
            for (int i = 0; i < Map.height; i++) {
                for (int j = 0; j < Map.width; j++) {
                    if (Map.getMap().getObjectMap()[i][j] != null) {
                        String insertObjects = "INSERT INTO OBJECTS" + id + " (ID, TYPE, X, Y)" +
                                "VALUES (" +
                                idObjects + ", " +
                                Map.getMap().getObjectMap()[i][j].ordinal() + ", " +
                                j + ", " +
                                i +
                                ")";

                        s.execute(insertObjects);
                        idObjects++;
                    }
                }
            }

            String createEnemy = "CREATE TABLE ENEMY" + id + " (" +
                    "ID INT PRIMARY KEY NOT NULL, " +
                    "TYPE INT NOT NULL, " +
                    "X INT NOT NULL, " +
                    "Y INT NOT NULL, " +
                    "AMMO INT NOT NULL" +
                    ")";

            s.execute(createEnemy);

            for (int i = 0; i < CharacterManager.getCharacterManager().getEnemyCharacters().size(); i++) {
                int enemyType = -1;
                int ammo = 0;
                if (CharacterManager.getCharacterManager().getEnemyCharacters().get(i) instanceof NormalEnemy) {
                    enemyType = 0;
                    if (CharacterManager.getCharacterManager().getEnemyCharacters().get(i).getEntity() != null && CharacterManager.getCharacterManager().getEnemyCharacters().get(i).getEntity().getWeapon() != null && CharacterManager.getCharacterManager().getEnemyCharacters().get(i).getEntity().getWeapon().hasAmmo()) {
                        ammo = CharacterManager.getCharacterManager().getEnemyCharacters().get(i).getEntity().getWeapon().getTotalAmmo() + CharacterManager.getCharacterManager().getEnemyCharacters().get(i).getEntity().getWeapon().getNrAmmo();
                    }
                } else if (CharacterManager.getCharacterManager().getEnemyCharacters().get(i) instanceof UndeadEnemy) {
                    enemyType = 1;
                } else {
                    throw new IllegalStateException("Unexpected value: " + enemyType);
                }
                String insertEnemy = "INSERT INTO ENEMY" + id + " (ID, TYPE, X, Y, AMMO)" +
                        "VALUES (" +
                        i + ", " +
                        enemyType + ", " +
                        CharacterManager.getCharacterManager().getEnemyCharacters().get(i).getPosition().xPX + ", " +
                        CharacterManager.getCharacterManager().getEnemyCharacters().get(i).getPosition().yPX + ", " +
                        ammo +
                        ")";

                s.execute(insertEnemy);
            }

            String createCharacter = "CREATE TABLE CHARACTER" + id + " (" +
                    "ID INT PRIMARY KEY NOT NULL, " +
                    "RANK INT NOT NULL, " +
                    "X INT NOT NULL, " +
                    "Y INT NOT NULL, " +
                    "AMMOW1 INT NOT NULL, " +
                    "AMMOW2 INT NOT NULL, " +
                    "MEDKIT INT NOT NULL, " +
                    "LEADER INT NOT NULL, " +
                    "ISPLAYER INT NOT NULL" +
                    ")";

            s.execute(createCharacter);

            int playerAmmoW1 = 0, playerAmmoW2 = 0, playerMedKit = 0, playerLeader = -1;

            for (int i = 1; i <= 2; i++) {
                if (CharacterManager.getCharacterManager().getPlayer().getEntity() != null && CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapons() != null) {
                    if (CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapons()[i] != null && CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapons()[i].hasAmmo()) {
                        int ammo = CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapons()[i].getTotalAmmo() + CharacterManager.getCharacterManager().getPlayer().getEntity().getWeapons()[i].getNrAmmo();
                        switch (i) {
                            case 1 -> playerAmmoW1 = ammo;
                            case 2 -> playerAmmoW2 = ammo;
                        }
                    }
                }
            }

            playerMedKit = CharacterManager.getCharacterManager().getPlayer().getEntity().getNrMedKits();

            if (CharacterManager.getCharacterManager().getPlayer().getLeader() != null) {
                for (int i = 0; i < CharacterManager.getCharacterManager().getAlliedCharacters().size(); i++) {
                    if (CharacterManager.getCharacterManager().getAlliedCharacters().get(i) == CharacterManager.getCharacterManager().getPlayer().getLeader()) {
                        playerLeader = i + 1;
                    }
                }
            }

            String insertPlayer = "INSERT INTO CHARACTER" + id + " (ID, RANK, X, Y, AMMOW1, AMMOW2, MEDKIT, LEADER, ISPLAYER)" +
                    "VALUES (" +
                    0 + ", " +
                    CharacterManager.getCharacterManager().getPlayer().getRank().ordinal() + ", " +
                    CharacterManager.getCharacterManager().getPlayer().getPosition().xPX + ", " +
                    CharacterManager.getCharacterManager().getPlayer().getPosition().yPX + ", " +
                    playerAmmoW1 + ", " +
                    playerAmmoW2 + ", " +
                    playerMedKit + ", " +
                    playerLeader + ", " +
                    1 +
                    ")";

            s.execute(insertPlayer);

            for (int characterID = 1; characterID <= CharacterManager.getCharacterManager().getAlliedCharacters().size(); characterID++) {

                int ammoW1 = 0, ammoW2 = 0, medKit = 0, leader = -1;

                for (int i = 1; i <= 2; i++) {
                    if (CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getEntity() != null && CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getEntity().getWeapons() != null) {
                        if (CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getEntity().getWeapons()[i] != null && CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getEntity().getWeapons()[i].hasAmmo()) {
                            int ammo = CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getEntity().getWeapons()[i].getTotalAmmo() + CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getEntity().getWeapons()[i].getNrAmmo();
                            switch (i) {
                                case 1 -> ammoW1 = ammo;
                                case 2 -> ammoW2 = ammo;
                            }
                        }
                    }
                }

                medKit = CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getEntity().getNrMedKits();

                if (CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getLeader() != null) {
                    if (CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getLeader() == CharacterManager.getCharacterManager().getPlayer()) {
                        leader = 0;
                    } else {
                        for (int i = 0; i < CharacterManager.getCharacterManager().getAlliedCharacters().size(); i++) {
                            if (CharacterManager.getCharacterManager().getAlliedCharacters().get(i) == CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getLeader()) {
                                leader = i + 1;
                            }
                        }
                    }
                }

                String insertCharacter = "INSERT INTO CHARACTER" + id + " (ID, RANK, X, Y, AMMOW1, AMMOW2, MEDKIT, LEADER, ISPLAYER)" +
                        "VALUES (" +
                        characterID + ", " +
                        CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getRank().ordinal() + ", " +
                        CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getPosition().xPX + ", " +
                        CharacterManager.getCharacterManager().getAlliedCharacters().get(characterID - 1).getPosition().yPX + ", " +
                        ammoW1 + ", " +
                        ammoW2 + ", " +
                        medKit + ", " +
                        leader + ", " +
                        0 +
                        ")";

                s.execute(insertCharacter);
            }

            if (id == startSave) {
                r = s.executeQuery("SELECT ID FROM GAME WHERE ID = " + gameSave);

                boolean hasGameSave = r.next();

                if (hasGameSave) {
                    String deleteGameSave = "DELETE from GAME WHERE ID=" + gameSave;
                    s.execute(deleteGameSave);
                    s.execute("DROP TABLE COLLECTABLES" + gameSave);
                    s.execute("DROP TABLE OBJECTS" + gameSave);
                    s.execute("DROP TABLE ENEMY" + gameSave);
                    s.execute("DROP TABLE CHARACTER" + gameSave);
                }
            }

            r.close();
            s.close();
            c.commit();
            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void load(int id) {

    }
}