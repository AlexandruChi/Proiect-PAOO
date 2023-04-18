package game.level;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;

public class LevelManager {
    //TODO read data from file and database
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;
    private static char[][] map;
    private static int nrMaps;

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
                String[] data = TextParser.parse(line);
                if (data.length == 1) {
                    if (data[0].equals("endlevel")) {
                        return readMap;
                    } else if (data[0].equals("map")) {
                        readMap = true;
                        try {
                            for (int i = 0; i < Map.height; i++) {
                                do {
                                    line = bufferedReader.readLine();
                                } while (line.equals(""));
                                String[] numbers = TextParser.parse(line);
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