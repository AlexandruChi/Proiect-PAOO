package game.graphics.assets;

import game.Window;
import game.graphics.assets.character.GermanCharacterAssets;

public class CharacterAssets {
    public static int characterTextureSize = Window.objectSize * 2;
    public static void load() {
        GermanCharacterAssets.load();
    }
}
