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
}
