package game.states;

import game.Game;
import game.graphics.assets.MenuAssets;

import java.awt.*;

/*
    State-ul de început al jocului care este afișat după încărcarea texturilor
    Dacă este apăsată o tastă urmează a fi încărcate nivelele jocului
 */

public class StartState extends State {
    @Override
    public void update() {
        if (Game.getGame().getInput().hadInput()) {
            Game.getGame().loadGame();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(MenuAssets.startScreen, 0, 0, Game.getGame().getWidth(), Game.getGame().getHeight(), null);
    }
}
