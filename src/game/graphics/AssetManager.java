package game.graphics;

import game.graphics.assets.CharacterAssets;
import game.graphics.assets.MapAssets;
import game.graphics.assets.MenuAssets;
import game.graphics.assets.UIAssets;

/*
    Clasă pentru încărcarea asset-urilor
 */

public class AssetManager {
    public static void load() {
        MenuAssets.load();
        CharacterAssets.load();
        MapAssets.load();
        UIAssets.load();
    }
}
