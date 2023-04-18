package game;

import game.component.texture.Texture;
import java.awt.*;

public class Draw {
    // TODO better scaling;
    public static final int logicalWidth = 960;
    public static final int logicalHeight = 540;

    public static void draw(Graphics graphics, Texture texture, int x, int y) {
        //double scale = (double) Game.game.getHeight() / logicalHeight;
        double scale = 1;
        graphics.drawImage(texture.texture, (int)(x * scale), (int)(y * scale), (int)(texture.width * scale), (int)(texture.height * scale), null);
    }

    public static void draw(Graphics graphics, Camera camera, Texture texture, int x, int y) {
        //double scale = (double) Game.game.getHeight() / logicalHeight;
        double scale = 1;
        graphics.drawImage(texture.texture, (int)(x * scale), (int)(y * scale), (int)(texture.width * scale), (int)(texture.height * scale), null);
    }
}
