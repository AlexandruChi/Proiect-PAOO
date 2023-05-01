package game;

import game.component.Pair;
import game.component.position.RelativeCoordinates;
import game.component.texture.Texture;
import java.awt.*;

public class Draw {
    // TODO scaling & fullscreen;
    public static final int logicalWidth = 960;
    public static final int logicalHeight = 540;

    public static void draw(Graphics graphics, Texture texture, int xPX, int yPX) {
        //double scale = (double) Game.game.getHeight() / logicalHeight;
        double scale = 1;

        if (texture != null) {
            graphics.drawImage(texture.texture, (int) (xPX * scale), (int) (yPX * scale), (int) (texture.width * scale), (int) (texture.height * scale), null);
        }
    }

    public static void draw(Graphics graphics, Camera camera, Texture texture, int xPX, int yPX) {
        double scale = 1;

        // TODO make better

        if (texture != null) {

            Pair<Integer, Integer> coords = RelativeCoordinates.getRelativeCoordinates(new Pair<>((camera.getPosition().xPX - Camera.screenX), (camera.getPosition().yPX - Camera.screenY)), new Pair<>((xPX), (yPX)));
            if (Math.abs(coords.getRight()) > logicalWidth || Math.abs(coords.getLeft()) > logicalWidth) {
                return;
            }
            graphics.drawImage(texture.texture, (int) (coords.getLeft() * scale), (int) (coords.getRight() * scale), (int) (texture.width * scale), (int) (texture.height * scale), null);
        }
    }
}
