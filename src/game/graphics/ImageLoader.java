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

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (corner == 1 && x > y) {
                    imageWithAlpha.setRGB(x, y, 0x00FFFFFF);
                } else if (corner == 4 && x > height - y - 1) {
                    imageWithAlpha.setRGB(x, y, 0x00FFFFFF);
                } else if (corner == 3 && x < y) {
                    imageWithAlpha.setRGB(x, y, 0x00FFFFFF);
                } else if (corner == 2 && x < width - y - 1) {
                    imageWithAlpha.setRGB(x, y, 0x00FFFFFF);
                }
            }
        }

        return imageWithAlpha;
    }
}
