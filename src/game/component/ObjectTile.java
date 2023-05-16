package game.component;

import game.graphics.assets.MapAssets;

public enum ObjectTile {
    tree, rock;

    public static int getEnvObjectTileScale(ObjectTile objectTile, int environment) {
        if (objectTile == null) {
            return 0;
        }

        switch (environment) {
            case MapAssets.beach -> {
                return switch (objectTile) {
                    case tree, rock -> 1;
                };
            }
            case MapAssets.forest -> {

            }
        }
        return switch (objectTile) {
            case tree -> 5;
            case rock -> 1;
        };
    }
}
