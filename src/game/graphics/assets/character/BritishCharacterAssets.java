package game.graphics.assets.character;

import game.component.Animation;
import game.graphics.ImageLoader;
import game.graphics.SpriteSheet;
import game.graphics.assets.CharacterAssets;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BritishCharacterAssets {
    public static final String spriteSheet = "res/textures/character/british/britishSpriteSheet.png";
    public static final int textureSize = 250;

    public static Animation[][] characterWalk;
    public static Animation[][] characterStill;
    public static Animation[][] characterAim;
    public static List<Animation[][]> characterAnimation;

    public static void load() {
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage(BritishCharacterAssets.spriteSheet));

        characterWalk = new Animation[2][1];

        {
            BufferedImage[] tmp = new BufferedImage[3];

            tmp[0] = spriteSheet.crop(0, 0, textureSize);
            tmp[1] = spriteSheet.crop(1, 0, textureSize);
            tmp[2] = spriteSheet.crop(2, 0, textureSize);

            characterWalk[0][0] = Animation.make(tmp, 3, 5, CharacterAssets.characterTextureSize);
            characterWalk[1][0] = Animation.make(characterWalk[0][0]);
            characterWalk[1][0].flip();
        }

        characterStill = new Animation[2][6];

        {

            BufferedImage[] tmp = new BufferedImage[3];

            tmp[0] = spriteSheet.crop(1, 0, textureSize);


            characterStill[0][0] = Animation.make(tmp, 1, 0, CharacterAssets.characterTextureSize);
            characterStill[1][0] = Animation.make(characterStill[0][0]);
            characterStill[1][0].flip();
        }

        characterAim = new Animation[2][6];

        {

            BufferedImage[] tmp = new BufferedImage[3];

            tmp[0] = spriteSheet.crop(3, 0, textureSize);


            characterAim[0][0] = Animation.make(tmp, 1, 0, CharacterAssets.characterTextureSize);
            characterAim[1][0] = Animation.make(characterAim[0][0]);
            characterAim[1][0].flip();
        }

        characterAnimation = new ArrayList<>();
        characterAnimation.add(characterStill);
        characterAnimation.add(characterWalk);
        characterAnimation.add(characterAim);
    }
}
