package game.states;

import game.Game;
import game.graphics.assets.MenuAssets;

import java.awt.*;

public class StartState extends State {
    @Override
    public void update() {
        if (Game.getGame().getInput().hadInput()) {
            Game.getGame().loadMainMenu();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(MenuAssets.startScreen, 0, 0, Game.getGame().getWidth(), Game.getGame().getHeight(), null);
    }
}
