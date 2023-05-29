package game.graphics.assets.character;

import game.component.Animation;
import game.graphics.ImageLoader;
import game.graphics.SpriteSheet;
import game.graphics.assets.CharacterAssets;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/*
    Clasă pentru încărcarea asset-urilor și crearea animaților caracterelor
 */

public class GermanCharacterAssets {
    public static final String spriteSheet = "res/textures/character/german/germanSpriteSheet.png";
    public static final int textureSize = 250;

    public static Animation[][] characterWalk;
    public static Animation[][] characterStill;
    public static Animation[][] characterAim;
    public static List<Animation[][]> characterAnimation;

    public static void load() {
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage(GermanCharacterAssets.spriteSheet));

        characterWalk = new Animation[2][6];

        for (int i = 0; i < 6; i++) {

            BufferedImage[] tmp = new BufferedImage[3];

            tmp[0] = spriteSheet.crop(i, 0, textureSize);
            tmp[1] = spriteSheet.crop(i, 1, textureSize);
            tmp[2] = spriteSheet.crop(i, 2, textureSize);

            characterWalk[0][i] = Animation.make(tmp, 3, 10, CharacterAssets.characterTextureSize);
            characterWalk[1][i] = Animation.make(characterWalk[0][i]);
            characterWalk[1][i].flip();
        }

        characterStill = new Animation[2][6];

        for (int i = 0; i < 6; i++) {

            BufferedImage[] tmp = new BufferedImage[3];

            tmp[0] = spriteSheet.crop(i, 1, textureSize);


            characterStill[0][i] = Animation.make(tmp, 1, 0, CharacterAssets.characterTextureSize);
            characterStill[1][i] = Animation.make(characterStill[0][i]);
            characterStill[1][i].flip();
        }

        characterAim = new Animation[2][6];

        for (int i = 0; i < 6; i++) {

            BufferedImage[] tmp = new BufferedImage[3];

            tmp[0] = spriteSheet.crop(i, 3, textureSize);


            characterAim[0][i] = Animation.make(tmp, 1, 0, CharacterAssets.characterTextureSize);
            characterAim[1][i] = Animation.make(characterAim[0][i]);
            characterAim[1][i].flip();
        }

        characterAnimation = new ArrayList<>();
        characterAnimation.add(characterStill);
        characterAnimation.add(characterWalk);
        characterAnimation.add(characterAim);
    }
}
