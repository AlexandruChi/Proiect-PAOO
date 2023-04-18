package game.states;

import game.Camera;
import game.entity.Player;
import game.level.Map;

import java.awt.*;

public class GameState extends State {
    Player player;
    Map map;
    Camera camera;

    //TODO better loading
    public GameState() {
        player = new Player();
        camera = new Camera(player);
        // TODO save file
        map = new Map("res/campaign.txt");
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics graphics) {
        // TODO better print
        map.draw(graphics, camera);
        player.draw(graphics, camera);
    }
}
