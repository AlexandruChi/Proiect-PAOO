package game.states;

import game.entity.Player;
import game.level.Map;

import java.awt.*;

public class GameState extends State {
    Player player;
    Map map;

    //TODO better loading
    public GameState() {
        player = new Player();
        // TODO save file
        map = new Map("campaign.blksave");
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics graphics) {
        // TODO better print
        map.draw(graphics);
        player.draw(graphics);
    }
}
