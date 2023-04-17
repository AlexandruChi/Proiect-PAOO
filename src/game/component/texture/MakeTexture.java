package game.component.texture;

import java.awt.image.BufferedImage;

public class MakeTexture {
    public static Texture make(BufferedImage texture, int size) {
        return make(texture, size, size);
    }

    public static Texture make(BufferedImage texture, int width, int height) {
        return new Texture(texture, width, height);
    }
}
