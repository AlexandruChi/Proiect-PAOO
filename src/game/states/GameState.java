package game.states;

import game.Camera;
import game.UI;
import game.character.Character;
import game.character.CharacterManager;
import game.character.Player;
import game.level.LevelManager;
import game.level.Map;

import java.awt.*;

public class GameState extends State {
    private final Map map;
    private final Camera camera;
    private final CharacterManager characterManager;

    //TODO better loading
    public GameState() {
        LevelManager.readFile("res/campaign.blwk");
        map = new Map();
        characterManager = new CharacterManager(map);
        camera = new Camera(characterManager.getPlayer());

        boolean newGame = true;
        if (newGame) {
            LevelManager.saveLevelStart();
        }
    }

    @Override
    public void update() {
        characterManager.update();
        camera.update();
    }

    @Override
    public void draw(Graphics graphics) {
        map.draw(graphics, camera, characterManager);
        UI.draw(graphics);
    }
}
