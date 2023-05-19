package game;

import game.component.Pair;
import game.component.position.RelativeCoordinates;
import game.component.texture.Texture;
import java.awt.*;

public class Draw {
    public static void draw(Graphics graphics, Texture texture, int xPX, int yPX) {
        if (texture != null) {
            graphics.drawImage(texture.texture, xPX, yPX, texture.width, texture.height, null);
        }
    }

    public static void draw(Graphics graphics, Camera camera, Texture texture, int xPX, int yPX) {
        if (texture != null) {
            Pair<Integer, Integer> coords = RelativeCoordinates.getRelativeCoordinates(new Pair<>((camera.getPosition().xPX - Camera.screenX), (camera.getPosition().yPX - Camera.screenY)), new Pair<>((xPX), (yPX)));
            if (coords.getRight() >= Game.getGame().getHeight() || coords.getLeft() >= Game.getGame().getWidth() || coords.getRight() <= -texture.height || coords.getLeft() <= -texture.width) {
                return;
            }
            graphics.drawImage(texture.texture, coords.getLeft(), coords.getRight(), texture.width, texture.height, null);
        }
    }
}
