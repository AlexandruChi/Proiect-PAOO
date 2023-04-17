package game.component;

import game.component.texture.Texture;
import game.graphics.ImageLoader;

import java.awt.image.BufferedImage;

public class Animation extends Texture {
    public BufferedImage[] animation;
    public int length;
    public int FPS;

    public static Animation make(BufferedImage[] animation, int length, int FPS, int size) {
        return make(animation, length, FPS, size, size);
    }

    public static Animation make(BufferedImage[] animation, int length, int FPS, int width, int height) {
        return new Animation(animation, length, FPS, width, height);
    }

    public void flip() {
        super.flip();
        for (int i = 0; i < length; i++) {
            animation[i] = ImageLoader.flipImage(animation[i]);
        }
    }

    private Animation(BufferedImage[] animation, int length, int FPS, int width, int height) {
        super(animation[0], width, height);
        this.animation = animation;
        this.FPS = FPS;
        this.length = length;
    }

    public Animation(Animation animation) {
        this(animation.animation, animation.length, animation.FPS, animation.width, animation.height);
    }
}
