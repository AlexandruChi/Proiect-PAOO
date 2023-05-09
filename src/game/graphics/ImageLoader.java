package game.graphics;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage flipImage(BufferedImage originalImage) {
        AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
        affineTransform.translate(-originalImage.getWidth(null), 0);
        AffineTransformOp flipOperation = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BICUBIC);
        return flipOperation.filter(originalImage, null);
    }

    public static BufferedImage removeCorner(BufferedImage image, int corner) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage imageWithAlpha = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        imageWithAlpha.getGraphics().drawImage(image, 0, 0, null);

        int startX = 0;
        int startY = 0;
        int endX = width;
        int endY = height;

        // TODO kill chatGPT and fix

        if (corner == 1 || corner == 2) {
            endX -= height;
        }
        if (corner == 2 || corner == 3) {
            endY -= height;
        }
        if (corner == 3 || corner == 4) {
            startY += height;
        }
        if (corner == 4 || corner == 1) {
            startX += height;
        }

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                imageWithAlpha.setRGB(x, y, imageWithAlpha.getRGB(x, y) & 0x00FFFFFF);
            }
        }

        return imageWithAlpha;
    }
}
