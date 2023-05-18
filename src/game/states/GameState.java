package game.states;

import game.Camera;
import game.character.Character;
import game.character.CharacterManager;
import game.character.Player;
import game.level.Map;

import java.awt.*;

public class GameState extends State {
    private final Map map;
    private final Camera camera;
    private final CharacterManager characterManager;

    //TODO better loading
    public GameState() {
        map = new Map("res/campaign.blwk");
        characterManager = new CharacterManager();
        camera = new Camera(characterManager.getPlayer());
        // TODO save file
    }

    @Override
    public void update() {
        characterManager.update();
        camera.update();
    }

    @Override
    public void draw(Graphics graphics) {
        map.draw(graphics, camera, characterManager);
    }

    public Map getMap() {
        return map;
    }
}
