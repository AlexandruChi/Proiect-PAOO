package game.states;

import game.Camera;
import game.entity.Player;
import game.level.Map;

import java.awt.*;

public class GameState extends State {
    private Player player;
    private Map map;
    private Camera camera;

    //TODO better loading
    public GameState() {
        player = new Player();
        camera = new Camera(player);
        // TODO save file
        map = new Map("res/campaign.blwk");
    }

    @Override
    public void update() {
        player.update();
        camera.update();
    }

    @Override
    public void draw(Graphics graphics) {
        // TODO better print
        map.draw(graphics, camera);
        player.draw(graphics, camera);
    }

    public Map getMap() {
        return map;
    }
}
