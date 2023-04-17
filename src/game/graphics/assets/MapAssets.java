package game.graphics.assets;

import game.component.texture.MakeTexture;
import game.component.texture.Texture;
import game.graphics.ImageLoader;

public class MapAssets {
    public static final int tileSize = 64;

    public static final String groundPath = "/textures/map/base/ground.png";
    public static final String waterPath = "/textures/map/base/water.png";
    public static final String roadPath = "/textures/map/base/road.png";
    public static Texture ground;
    public static Texture water;
    public static Texture road;

    public static void load() {
        ground = MakeTexture.make(ImageLoader.loadImage(groundPath), tileSize);
        water = MakeTexture.make(ImageLoader.loadImage(waterPath), tileSize);
        road = MakeTexture.make(ImageLoader.loadImage(roadPath), tileSize);
    }
}
