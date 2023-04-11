package game.graphics.assets.character;

import game.component.Animation;
import game.graphics.ImageLoader;

import java.awt.image.BufferedImage;

public class GermanCharacterAssets {
    public final static String normalWeapon1Path = "/textures/character/german/normalWeapon1.png";
    public static Animation[][] characterWeapon1;
    public static void load() {
        BufferedImage[] tmp = new BufferedImage[1];
        tmp[0] = ImageLoader.loadImage(normalWeapon1Path);

        characterWeapon1 = new Animation[1][2];
        characterWeapon1[0][0] = Animation.make(tmp, 0,  0, 128);
        characterWeapon1[0][1] = new Animation(characterWeapon1[0][0]);
        characterWeapon1[0][1].flip();
    }
}
