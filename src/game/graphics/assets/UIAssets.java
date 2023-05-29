package game.graphics.assets;

import game.graphics.ImageLoader;
import game.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
    Clasă pentru încărcarea asset-urilor pentru UI
 */

public class UIAssets {

    public static final String UIPath = "res/textures/UI/ui.png";
    public static final String mapPath = "res/textures/UI/minimap.png";

    public static final String fontPath = "res/font.ttf";

    public static final int minimapSize = 200;

    public static Font font;

    public static BufferedImage[] healthBar  = new BufferedImage[6];
    public static BufferedImage[] ranks = new BufferedImage[4];
    public static BufferedImage[] friendly = new BufferedImage[4];
    public static BufferedImage nrAllies;
    public static BufferedImage nrEliminated;
    public static BufferedImage friendlyLocation;
    public static BufferedImage enemyLocation;
    public static BufferedImage unknownEnemy;
    public static BufferedImage enemy;
    public static BufferedImage ammo;
    public static BufferedImage clip;
    public static BufferedImage[] weapons = new BufferedImage[6];
    public static BufferedImage[] medals = new BufferedImage[9];
    public static BufferedImage[] minimap = new BufferedImage[3];
    public static BufferedImage medKit;
    public static BufferedImage collectable;
    public static BufferedImage toolBox;
    public static BufferedImage map;

    public static void load() {

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage(UIPath));
        healthBar[5] = spriteSheet.crop(0, 0, 448, 128);
        healthBar[4] = spriteSheet.crop(0, 1, 448, 128);
        healthBar[3] = spriteSheet.crop(0, 2, 448, 128);
        healthBar[2] = spriteSheet.crop(0, 3, 448, 128);
        healthBar[1] = spriteSheet.crop(0, 4, 448, 128);
        healthBar[0] = spriteSheet.crop(0, 5, 448, 128);

        ranks[0] = spriteSheet.crop(3.5, 0, 128);
        ranks[1] = spriteSheet.crop(3.5, 1, 128);
        ranks[2] = spriteSheet.crop(3.5, 2, 128);
        ranks[3] = spriteSheet.crop(3.5, 3, 128);

        nrAllies = spriteSheet.crop(3.5, 4, 128);
        nrEliminated = spriteSheet.crop(3.5, 5, 128);

        friendly[3] = spriteSheet.crop(4.5, 0, 128);
        friendly[2] = spriteSheet.crop(4.5, 1, 128);
        friendly[1] = spriteSheet.crop(4.5, 2, 128);
        friendly[0] = spriteSheet.crop(4.5, 3, 128);

        friendlyLocation = spriteSheet.crop(4.5, 4, 128);
        unknownEnemy = spriteSheet.crop(4.5, 5, 128);

        enemy = spriteSheet.crop(5.5, 0, 128);
        enemyLocation = spriteSheet.crop(5.5, 1, 128);

        medKit = spriteSheet.crop(5.5, 2, 128);
        collectable = spriteSheet.crop(5.5, 3, 128);
        toolBox = spriteSheet.crop(5.5, 4, 128);
        map = spriteSheet.crop(5.5, 5, 128);

        ammo = spriteSheet.crop(6.5, 0, 128);
        clip = spriteSheet.crop(6.5, 1, 128);

        weapons[0] = spriteSheet.crop(3.75, 0, 256, 128);
        weapons[1] = spriteSheet.crop(3.75, 2, 256, 128);
        weapons[2] = spriteSheet.crop(3.75, 3, 256, 128);
        weapons[3] = spriteSheet.crop(3.75, 4, 256, 128);
        weapons[4] = spriteSheet.crop(3.75, 5, 256, 128);
        weapons[5] = spriteSheet.crop(3.75, 1, 256, 128);

        medals[0] = spriteSheet.cropAbsX(1216, 0, 384, 128);
        medals[1] = spriteSheet.cropAbsX(1216, 1, 384, 128);
        medals[2] = spriteSheet.cropAbsX(1216, 2, 384, 128);
        medals[3] = spriteSheet.cropAbsX(1216, 3, 384, 128);
        medals[4] = spriteSheet.cropAbsX(1216, 4, 384, 128);
        medals[5] = spriteSheet.cropAbsX(1216, 5, 384, 128);
        medals[6] = spriteSheet.cropAbsX(1600, 0, 384, 128);
        medals[7] = spriteSheet.cropAbsX(1600, 1, 384, 128);
        medals[8] = spriteSheet.cropAbsX(1600, 2, 384, 128);

        spriteSheet = new SpriteSheet(ImageLoader.loadImage(mapPath));

        minimap[0] = spriteSheet.crop(0, 0, minimapSize);
        minimap[1] = spriteSheet.crop(1, 0, minimapSize);
        minimap[2] = spriteSheet.crop(2, 0, minimapSize);
    }
}
