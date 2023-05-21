package game.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage spriteSheet;

    public SpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x * width, y * height, width, height);
    }

    public BufferedImage cropAbsX(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y * height, width, height);
    }

    public BufferedImage cropAbsX(int x, int y, int size) {
        return spriteSheet.getSubimage(x, y * size, size, size);
    }

    public BufferedImage crop(double x, double y, int width, int height) {
        return spriteSheet.getSubimage((int) (x * width), (int) (y * height), width, height);
    }

    public BufferedImage crop(int x, int y, int size) {
        return crop(x, y, size, size);
    }

    public BufferedImage crop(double x, double y, int size) {
        return crop(x, y, size, size);
    }
}
