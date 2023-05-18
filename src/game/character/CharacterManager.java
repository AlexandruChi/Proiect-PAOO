package game.character;

import game.level.LevelManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CharacterManager {
    private final List<Character> characters = new ArrayList<>();
    private final Player player;

    public CharacterManager() {
        player = new Player(LevelManager.getPlayerPosition());
        characters.add(player);
    }

    public void update(){
        for (Character character : characters) {
            character.update();
        }

        characters.sort(Comparator.comparingInt(o -> o.getPosition().yPX));
    }

    public void updatePositions() {

    }

    public Player getPlayer() {
        return player;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
