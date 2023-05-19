package game.graphics.assets;

import game.Window;
import game.component.PrintBox;
import game.graphics.assets.character.GermanCharacterAssets;

public class CharacterAssets {
    public static PrintBox printBox = new PrintBox(Window.objectSize,2 * Window.objectSize);
    public static int characterTextureSize = Window.objectSize * 2;
    public static void load() {
        GermanCharacterAssets.load();
    }
}
