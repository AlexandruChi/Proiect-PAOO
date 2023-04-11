package game.states;

import game.Game;
import game.entity.Character;
import game.entity.Player;

import java.awt.*;

public class GameState extends State {
    Player player;

    //TODO better loading
    public GameState() {
        player = new Player();
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics graphics) {
        player.draw(graphics);
    }
}
