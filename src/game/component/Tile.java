package game.component;

import game.component.texture.Texture;
import game.graphics.assets.MapAssets;

/*
    Tile conține tile-urile care pot fi afișate.
 */

public enum Tile {
    water, envWater, ground, envGround, path, envPath, road, envRoad;

    /*
        returnează mărimea unui tile sau a unui layer
     */

    public static int getTileScale(Tile tile) {
        if (tile == null) {
            return 0;
        }
        return switch (tile) {
            case water, envWater -> 5;
            case ground, envGround -> 10;
            case path, envPath -> 1;
            case road, envRoad -> 2;
        };
    }

    public static int getLayerScale(int layer) {
        return switch (layer) {
            case 1 -> getTileScale(water);
            case 2 -> getTileScale(ground);
            case 3 -> getTileScale(path);
            case 4 -> getTileScale(road);
            default -> 0;
        };
    }

    /*
        returnează tipul de tile de pe un layer
     */

    public static Tile getLayerTile(int layer) {
        return switch (layer) {
            case 1 -> water;
            case 2 -> ground;
            case 3 -> path;
            case 4 -> road;
            default -> null;
        };
    }

    /*
        returnează texturile tile-urilor în funcție de nivel și proprietățile acestora
     */

    public static Texture getTileTexture(Tile tile) {
        if (tile == null) {
            return null;
        }
        return switch (tile) {
            case water -> MapAssets.water;
            case envWater -> MapAssets.envWater;
            case ground -> MapAssets.ground;
            case envGround -> MapAssets.envGround;
            case path -> MapAssets.path;
            case envPath -> MapAssets.envPath;
            case road -> MapAssets.road;
            case envRoad -> MapAssets.envRoad;
        };
    }

    public static boolean hasCorner(Tile tile) {
        if (tile == null) {
            return false;
        }
        return switch (tile) {
            case water -> false;
            case envWater, ground, envGround, path, envPath, road, envRoad -> true;
        };
    }

    public static Texture[] getTileCornerTexture(Tile tile) {
        if (tile == null) {
            return null;
        }
        return switch (tile) {
            case water -> MapAssets.waterCorner;
            case envWater -> MapAssets.envWaterCorner;
            case ground -> MapAssets.groundCorner;
            case envGround -> MapAssets.envGroundCorner;
            case path -> MapAssets.pathCorner;
            case envPath -> MapAssets.envPathCorner;
            case road -> MapAssets.roadCorner;
            case envRoad -> MapAssets.envRoadCorner;
        };
    }

    public static Tile getEnv(Tile tile) {
        return getTile(tile, envWater, envGround, envPath, envRoad);
    }

    public static Tile getNormal(Tile tile) {
        return getTile(tile, water, ground, path, road);
    }

    private static Tile getTile(Tile tile, Tile tile2, Tile tile3, Tile tile4, Tile tile5) {
        if (tile == null) {
            return null;
        }
        return switch (tile) {
            case water, envWater -> tile2;
            case ground, envGround -> tile3;
            case path, envPath -> tile4;
            case road, envRoad -> tile5;
        };
    }

    public static boolean canWalkOn(Tile tile) {
        if (tile == null) {
            return false;
        }
        return switch (tile) {
            case ground, envGround, path, envPath, road, envRoad -> true;
            default -> false;
        };
    }
}
