package game.graphics.assets;

import game.Window;
import game.component.ObjectTile;
import game.component.Tile;
import game.component.texture.MakeTexture;
import game.component.texture.Texture;
import game.graphics.ImageLoader;
import game.graphics.SpriteSheet;
import game.level.Map;

public class MapAssets {
    public static final int waterTileSize = Window.objectSize * Map.mapScale * Tile.getTileScale(Tile.water);
    public static final int groundTileSize = Window.objectSize * Map.mapScale * Tile.getTileScale(Tile.ground);
    public static final int pathTileSize = Window.objectSize * Map.mapScale * Tile.getTileScale(Tile.path);
    public static final int roadTileSize = Window.objectSize * Map.mapScale * Tile.getTileScale(Tile.road);

    public static final int textureSize = 500;

    public static final int base = 0;
    public static final int beach = 1;
    public static final int forest = 2;
    public static final int city = 3;

    public static final String basePath = "res/textures/map/base.png";
    public static final String beachPath = "res/textures/map/beach.png";
    public static Texture water;
    public static Texture ground;
    public static Texture path;
    public static Texture road;
    public static Texture tree;
    public static Texture rock;
    public static Texture envWater;
    public static Texture envGround;
    public static Texture envPath;
    public static Texture envRoad;
    public static Texture envTree;
    public static Texture envRock;

    public static Texture[] waterCorner;
    public static Texture[] groundCorner;
    public static Texture[] pathCorner;
    public static Texture[] roadCorner;
    public static Texture[] envWaterCorner;
    public static Texture[] envGroundCorner;
    public static Texture[] envPathCorner;
    public static Texture[] envRoadCorner;

    public static void load() {
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage(basePath));
        water = MakeTexture.make(spriteSheet.crop(0, 0, textureSize), waterTileSize);
        ground = MakeTexture.make(spriteSheet.crop(1, 0, textureSize), groundTileSize);
        path = MakeTexture.make(spriteSheet.crop(2, 0, textureSize), pathTileSize);
        road = MakeTexture.make(spriteSheet.crop(3, 0, textureSize), roadTileSize);

        tree = MakeTexture.make(spriteSheet.crop(0, 1, textureSize), ObjectTile.getEnvObjectTileScale(ObjectTile.tree, base) * Window.objectSize);
        rock = MakeTexture.make(spriteSheet.crop(1, 1, textureSize), ObjectTile.getEnvObjectTileScale(ObjectTile.rock, base) * Window.objectSize);

        waterCorner = new Texture[4];
        groundCorner = new Texture[4];
        pathCorner = new Texture[4];
        roadCorner = new Texture[4];

        for (int i = 1; i <= 4; i++) {
            waterCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(water.texture, i), waterTileSize);
            groundCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(ground.texture, i), groundTileSize);
            pathCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(path.texture, i), pathTileSize);
            roadCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(road.texture, i), roadTileSize);
        }
    }

    public static void loadEnvironment(int environment) {
        String path = switch (environment) {
            case beach -> beachPath;
            default -> basePath;
        };
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage(path));
        envWater = MakeTexture.make(spriteSheet.crop(0, 0, textureSize), waterTileSize);
        envGround = MakeTexture.make(spriteSheet.crop(1, 0, textureSize), groundTileSize);
        envPath = MakeTexture.make(spriteSheet.crop(2, 0, textureSize), pathTileSize);
        envRoad = MakeTexture.make(spriteSheet.crop(3, 0, textureSize), roadTileSize);

        envTree = MakeTexture.make(spriteSheet.crop(0, 1, textureSize), ObjectTile.getEnvObjectTileScale(ObjectTile.envTree, environment) * Window.objectSize);
        envRock = MakeTexture.make(spriteSheet.crop(1, 1, textureSize), ObjectTile.getEnvObjectTileScale(ObjectTile.envRock, environment) * Window.objectSize);

        envWaterCorner = new Texture[4];
        envGroundCorner = new Texture[4];
        envPathCorner = new Texture[4];
        envRoadCorner = new Texture[4];

        for (int i = 1; i <= 4; i++) {
            envWaterCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(envWater.texture, i), waterTileSize);
            envGroundCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(envGround.texture, i), groundTileSize);
            envPathCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(envPath.texture, i), pathTileSize);
            envRoadCorner[i - 1] = MakeTexture.make(ImageLoader.removeCorner(envRoad.texture, i), roadTileSize);
        }
    }
}
