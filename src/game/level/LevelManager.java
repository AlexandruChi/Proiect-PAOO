package game.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelManager {
    //TODO read data from file and database
    private static FileReader file;
    private static BufferedReader bufferedReader;
    private static char[][] map;
    private static int nrMaps;

    public static void readFile(String file) {
        try {
            LevelManager.file = new FileReader(file);
            bufferedReader = new BufferedReader(LevelManager.file);
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
            LevelManager.file.reset();
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
                String[] data = line.split("\t<> ");
                if (data.length == 2) {
                    if (data[0].equals("level")) {
                        try {
                            parsedLevel = Integer.parseInt(data[1]);
                            if (parsedLevel == level) {
                                return readLevelData();
                            }
                        } catch (NumberFormatException ignored) {}
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
        map = new char[Map.height][Map.width];
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // TODO add more tags
                String[] data = line.split("\t<> ");
                if (data.length == 1) {
                    if (data[0].equals("endlevel")) {
                        return readMap;
                    } else if (data[0].equals("map")) {
                        try {
                            for (int i = 0; i < Map.height; i++) {
                                line = bufferedReader.readLine();
                                String[] numbers = line.split("\t ");
                                for (int j = 0; j < Map.width; j++) {
                                    map[i][j] = (char) Integer.parseInt(numbers[j]);
                                }
                            }
                        } catch (NumberFormatException e) {
                            // TODO add error
                            System.exit(1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static char[][] loadMap() {
        return map;
    }
}