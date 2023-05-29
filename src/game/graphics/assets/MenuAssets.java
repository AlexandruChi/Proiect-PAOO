package game.graphics.assets;

import game.graphics.ImageLoader;
import java.awt.image.BufferedImage;

public class MenuAssets {
    public final static String startScreenPath = "res/textures/menu/start_screen.png";
    public final static String menuScreenPath = "res/textures/menu/start_screen.png";

    public static BufferedImage startScreen;
    public static BufferedImage menuScreen;

    public static void load() {
        startScreen = ImageLoader.loadImage(startScreenPath);
        menuScreen = ImageLoader.loadImage(menuScreenPath);
    }
}
