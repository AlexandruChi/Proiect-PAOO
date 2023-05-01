package game.graphics.assets;

import game.Window;
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

    public static final int beach = 1;
    public static final int forest = 2;
    public static final int city = 3;

    public static final String basePath = "res/textures/map/base.png";
    public static final String beachPath = "res/textures/map/beach.png";
    public static Texture water;
    public static Texture ground;
    public static Texture path;
    public static Texture road;
    public static Texture envWater;
    public static Texture envGround;
    public static Texture envPath;
    public static Texture envRoad;

    public static void load() {
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage(basePath));
        water = MakeTexture.make(spriteSheet.crop(0, 0, textureSize), waterTileSize);
        ground = MakeTexture.make(spriteSheet.crop(1, 0, textureSize), groundTileSize);
        path = MakeTexture.make(spriteSheet.crop(2, 0, textureSize), pathTileSize);
        road = MakeTexture.make(spriteSheet.crop(3, 0, textureSize), roadTileSize);
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
    }
}
