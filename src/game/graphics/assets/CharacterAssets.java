package game.graphics.assets;

import game.Game;
import game.Window;
import game.component.Animation;
import game.component.HitBox;
import game.component.PrintBox;
import game.graphics.assets.character.BritishCharacterAssets;
import game.graphics.assets.character.GermanCharacterAssets;
import game.graphics.assets.character.UndeadCharacterAssets;

import java.util.ArrayList;
import java.util.List;

/*
    Clasă pentru încărcarea asset-urilor caracterelor
 */

public class CharacterAssets {
    public static PrintBox printBox = new PrintBox(Window.objectSize,2 * Window.objectSize);
    public static HitBox hitbox = new HitBox((int)(0.75 * Window.objectSize), 2 * Window.objectSize);
    public static int collisionDistance = (int) (0.5 * Window.objectSize);
    public static int characterTextureSize = Window.objectSize * 2;
    public static void load() {
        GermanCharacterAssets.load();
        BritishCharacterAssets.load();
        UndeadCharacterAssets.load();
    }

    public static List<Animation[][]> newAnimation(List<Animation[][]> animations) {
        List<Animation[][]> newAnimation = new ArrayList<>();
        for (Animation[][] animation : animations) {
            Animation[][] newAnimationVector = new Animation[animation.length][animation[0].length];
            for (int i = 0; i < animation.length; i++) {
                for (int j = 0; j < animation[i].length; j++) {
                    if (animation[i][j] != null) {
                        newAnimationVector[i][j] = Animation.make(animation[i][j]);
                    }
                }
            }
            newAnimation.add(newAnimationVector);
        }
        return newAnimation;
    }
}
