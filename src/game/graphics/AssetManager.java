package game.graphics;

import game.graphics.assets.CharacterAssets;
import game.graphics.assets.MapAssets;
import game.graphics.assets.MenuAssets;

public class AssetManager {
    public static void load() {
        MenuAssets.load();
        CharacterAssets.load();
        MapAssets.load();
    }
}
