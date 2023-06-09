package game.component;

import game.component.texture.Texture;
import game.graphics.assets.MapAssets;
import game.level.Map;

/*
    Conține tile-uri pentru obiectele generate. Modul de funcționare este identic cu cel al tile-urilor obișnuite cu
    excepția mărimii acestora care depinde de nivel
 */

public enum ObjectTile {
    tmp, tree, envTree, rock, envRock;

    public static int getEnvObjectTileScale(ObjectTile objectTile, int environment) {
        if (objectTile == null) {
            return 0;
        }

        switch (environment) {
            case MapAssets.beach, MapAssets.field -> {
                return switch (objectTile) {
                    case envTree, envRock -> 2;

                    default -> getEnvObjectTileScale(objectTile, MapAssets.base);
                };
            }
            case MapAssets.forest -> {
                return switch (objectTile) {
                    case envTree -> 25;
                    case envRock -> 1;

                    default -> getEnvObjectTileScale(objectTile, MapAssets.base);
                };
            }
        }
        return switch (objectTile) {
            case tree -> 21;
            case rock -> 2;

            default -> 0;
        };
    }

    public static Pair<Integer, Integer> getHitBoxSize(ObjectTile objectTile, int environment) {
        if (objectTile == null) {
            return null;
        }

        switch (environment) {
            case MapAssets.beach, MapAssets.field -> {
                return switch (objectTile) {
                    case envTree, envRock -> new Pair<>(2, 1);

                    default -> getHitBoxSize(objectTile, MapAssets.base);
                };
            }
            case MapAssets.forest -> {
                return switch (objectTile) {
                    case envTree, envRock -> new Pair<>(1, 1);

                    default -> getHitBoxSize(objectTile, MapAssets.base);
                };
            }
        }
        return switch (objectTile) {
            case tree -> new Pair<>(1,1);
            case rock -> new Pair<>(2, 1);

            default -> null;
        };
    }

    public static Pair<Integer, Integer> getPrintBoxSize(ObjectTile objectTile, int environment) {
        if (objectTile == null) {
            return null;
        }

        switch (environment) {
            case MapAssets.beach, MapAssets.field -> {
                return switch (objectTile) {
                    case envTree, envRock -> new Pair<>(0, 1);

                    default -> getPrintBoxSize(objectTile, MapAssets.base);
                };
            }
            case MapAssets.forest -> {
                return switch (objectTile) {
                    case envTree -> new Pair<>(11,24);
                    case envRock -> new Pair<>(0, 0);

                    default -> getPrintBoxSize(objectTile, MapAssets.base);
                };
            }
        }
        return switch (objectTile) {
            case tree -> new Pair<>(10,20);
            case rock -> new Pair<>(0, 1);

            default -> null;
        };
    }

    public static Texture getTexture(ObjectTile objectTile) {
        if (objectTile == null) {
            return null;
        }
        return switch (objectTile) {
            case tree -> MapAssets.tree;
            case envTree -> MapAssets.envTree;
            case rock -> MapAssets.rock;
            case envRock -> MapAssets.envRock;

            default -> null;
        };
    }

    public static boolean canSeeThrew(ObjectTile objectTile, int environment) {
        if (objectTile == null) {
            return true;
        }

        switch (environment) {
            case MapAssets.beach, MapAssets.field -> {
                return switch (objectTile) {
                    case envTree, envRock -> true;

                    default -> canSeeThrew(objectTile, MapAssets.base);
                };
            }
            case MapAssets.forest -> {
                return switch (objectTile) {
                    case envTree -> false;
                    case envRock -> true;

                    default -> canSeeThrew(objectTile, MapAssets.base);
                };
            }
        }
        return switch (objectTile) {
            case tree -> false;

            default -> true;
        };
    }

    public static Texture getTransparentTexture(ObjectTile objectTile) {
        if (objectTile == null) {
            return null;
        }
        return switch (objectTile) {
            case tree -> MapAssets.treeTransparent;
            case envTree -> MapAssets.envTreeTransparent;

            default -> null;
        };
    }

    public static ObjectTile getEnv(ObjectTile objectTile) {
        return switch (objectTile) {
            case tree, envTree -> envTree;
            case rock, envRock -> envRock;

            default -> null;
        };
    }

    public static ObjectTile getNormal(ObjectTile objectTile) {
        return switch (objectTile) {
            case tree, envTree -> tree;
            case rock, envRock -> rock;

            default -> null;
        };
    }
}
