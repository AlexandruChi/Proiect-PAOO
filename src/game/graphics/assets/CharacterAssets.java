package game.graphics.assets;

import game.Window;
import game.component.HitBox;
import game.component.PrintBox;
import game.graphics.assets.character.BritishCharacterAssets;
import game.graphics.assets.character.GermanCharacterAssets;
import game.graphics.assets.character.UndeadCharacterAssets;

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
}
