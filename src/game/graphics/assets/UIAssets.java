package game.graphics.assets;

import game.component.texture.MakeTexture;
import game.graphics.ImageLoader;
import game.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIAssets {

    public static final String UIPath = "res/textures/UI/ui.png";

    public static final String fontPath = "res/font.ttf";

    public static Font font;

    public static BufferedImage[] healthBar  = new BufferedImage[6];
    public static BufferedImage[] ranks = new BufferedImage[4];
    public static BufferedImage[] orders = new BufferedImage[3];
    public static BufferedImage nrAllies;
    public static BufferedImage nrEliminated;
    public static BufferedImage friendlyLocation;
    public static BufferedImage unknownLocation;
    public static BufferedImage enemyLocation;
    public static BufferedImage unknownEnemy;
    public static BufferedImage undeadEnemy;
    public static BufferedImage enemy;
    public static BufferedImage inventory;
    public static BufferedImage build;
    public static BufferedImage map;
    public static BufferedImage ammo;
    public static BufferedImage magazine;
    public static BufferedImage mKey;
    public static BufferedImage iKey;
    public static BufferedImage bKey;
    public static BufferedImage eKey;
    public static BufferedImage[] weapons = new BufferedImage[6];
    public static BufferedImage[] medals = new BufferedImage[9];

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

        orders[2] = spriteSheet.crop(4.5, 0, 128);
        orders[1] = spriteSheet.crop(4.5, 1, 128);
        orders[0] = spriteSheet.crop(4.5, 2, 128);

        friendlyLocation = spriteSheet.crop(4.5, 3, 128);
        unknownEnemy = spriteSheet.crop(4.5, 4, 128);
        unknownLocation = spriteSheet.crop(4.5, 5, 128);

        undeadEnemy = spriteSheet.crop(5.5, 0, 128);
        enemyLocation = spriteSheet.crop(5.5, 1, 128);
        enemy = spriteSheet.crop(5.5, 2, 128);

        inventory = spriteSheet.crop(5.5, 3, 128);
        build = spriteSheet.crop(5.5, 4, 128);
        map = spriteSheet.crop(5.5, 5, 128);

        ammo = spriteSheet.cropAbsX(953 - 128, 0, 128);
        magazine = spriteSheet.cropAbsX(953 - 128, 1, 128);
        mKey = spriteSheet.cropAbsX(953 - 128, 2, 128);
        iKey = spriteSheet.cropAbsX(953 - 128, 3, 128);
        bKey = spriteSheet.cropAbsX(953 - 128, 4, 128);
        eKey = spriteSheet.cropAbsX(953 - 128, 5, 128);

        weapons[0] = spriteSheet.cropAbsX(953, 0, 256, 128);
        weapons[1] = spriteSheet.cropAbsX(953, 5, 256, 128);
        weapons[2] = spriteSheet.cropAbsX(953, 4, 256, 128);
        weapons[3] = spriteSheet.cropAbsX(953, 3, 256, 128);
        weapons[4] = spriteSheet.cropAbsX(953, 2, 256, 128);
        weapons[5] = spriteSheet.cropAbsX(953, 1, 256, 128);

        medals[0] = spriteSheet.cropAbsX(953 + 256, 0, 384, 128);
        medals[1] = spriteSheet.cropAbsX(953 + 256, 1, 384, 128);
        medals[2] = spriteSheet.cropAbsX(953 + 256, 2, 384, 128);
        medals[3] = spriteSheet.cropAbsX(953 + 256, 3, 384, 128);
        medals[4] = spriteSheet.cropAbsX(953 + 256, 4, 384, 128);
        medals[5] = spriteSheet.cropAbsX(953 + 256, 5, 384, 128);
        medals[6] = spriteSheet.cropAbsX(953 + 256 + 384, 0, 384, 128);
        medals[7] = spriteSheet.cropAbsX(953 + 256 + 384, 1, 384, 128);
        medals[8] = spriteSheet.cropAbsX(953 + 256 + 384, 2, 384, 128);
    }
}
