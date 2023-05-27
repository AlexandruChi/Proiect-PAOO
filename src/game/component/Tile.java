package game.component;

import game.component.texture.Texture;
import game.graphics.assets.MapAssets;

public enum Tile {
    water, envWater, ground, envGround, path, envPath, road, envRoad;

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

    public static Tile getLayerTile(int layer) {
        return switch (layer) {
            case 1 -> water;
            case 2 -> ground;
            case 3 -> path;
            case 4 -> road;
            default -> null;
        };
    }

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
        if (tile == null) {
            return null;
        }
        return switch (tile) {
            case water, envWater -> envWater;
            case ground, envGround -> envGround;
            case path, envPath -> envPath;
            case road, envRoad -> envRoad;
        };
    }

    public static Tile getNormal(Tile tile) {
        if (tile == null) {
            return null;
        }
        return switch (tile) {
            case water, envWater -> water;
            case ground, envGround -> ground;
            case path, envPath -> path;
            case road, envRoad -> road;
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
