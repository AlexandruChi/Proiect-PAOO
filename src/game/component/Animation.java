package game.component;

import game.component.texture.Texture;
import game.graphics.ImageLoader;

import java.awt.image.BufferedImage;

/*
    Clasa Animation conține metodele make pentru crearea unei animații
 */

public class Animation extends Texture {
    private final BufferedImage[] animation;
    private final int length;
    private final int FPS;
    private int curentTexture;
    private final int timerLength;
    private int timer;


    public static Animation make(BufferedImage[] animation, int length, int FPS, int size) {
        return make(animation, length, FPS, size, size);
    }

    public static Animation make(BufferedImage[] animation, int length, int FPS, int width, int height) {
        return new Animation(animation, length, FPS, width, height);
    }

    public static Animation make(Animation animation) {
        BufferedImage[] newAnimation = new BufferedImage[animation.length];
        System.arraycopy(animation.animation, 0, newAnimation, 0, animation.length);
        return new Animation(newAnimation, animation.length, animation.FPS, animation.width, animation.height);
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
        if (FPS != 0) {
            timerLength = 60 / FPS;
        } else {
            timerLength = 0;
        }
        timer = 0;
        curentTexture = 0;
    }

    /*
        metodă folosită pentru afișarea animației
     */

    public BufferedImage getTexture() {
        if (FPS != 0) {
            timer++;
            if (timer > timerLength) {
                timer = 0;
                curentTexture++;
                if (curentTexture >= length) {
                    curentTexture = 0;
                }
                texture = animation[curentTexture];
            }
        }
        return texture;
    }
}
