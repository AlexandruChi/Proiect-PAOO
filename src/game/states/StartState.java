package game.states;

import game.Game;
import game.graphics.AssetManager;
import game.graphics.assets.MenuAssets;

import java.awt.*;

public class StartState extends State {
    @Override
    public void update() {
        Game.game.loadMainMenu();
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(MenuAssets.startScreen, 0, 0, Game.game.getWidth(), Game.game.getHeight(), null);
    }
}
