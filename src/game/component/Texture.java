package game.component;

import game.graphics.ImageLoader;

import java.awt.image.BufferedImage;

public class Texture {
    public BufferedImage texture;
    public int width;
    public int height;

    public static Texture make(BufferedImage texture, int size) {
        return make(texture, size, size);
    }

    public static Texture make(BufferedImage texture, int width, int height) {
        return new Texture(texture, width, height);
    }

    public void flip() {
        texture = ImageLoader.flipImage(texture);
    }

    protected Texture(BufferedImage texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public Texture(Texture texture) {
        this(texture.texture, texture.width, texture.height);
    }
}
