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
}
