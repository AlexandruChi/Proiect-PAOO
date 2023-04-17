package game.component.texture;

import game.graphics.ImageLoader;

import java.awt.image.BufferedImage;

public class Texture {
    public BufferedImage texture;
    public int width;
    public int height;

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
