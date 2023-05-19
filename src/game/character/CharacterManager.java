package game.character;

import game.Game;
import game.Window;
import game.component.Pair;
import game.component.RandomNumber;
import game.component.position.Distance;
import game.component.position.Position;
import game.level.LevelManager;
import game.level.Map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CharacterManager {
    private static CharacterManager characterManager = null;
    private List<Character> characters;
    private List<Character> enemyCharacters;
    private List<Character> friendlyCharacters;
    private final List<Character> alliedCharacters = new ArrayList<>();
    private final Player player;

    private final Map map;

    public static CharacterManager getCharacterManager() {
        return characterManager;
    }

    public CharacterManager(Map map) {
        characterManager = this;
        this.map = map;
        player = new Player(LevelManager.getPlayerPosition());
        load();
    }

    public void update(){
        for (Character character : characters) {
            character.update();
        }

        characters.sort(Comparator.comparingInt(o -> o.getPosition().yPX));
    }

    public void load() {
        characters = new ArrayList<>();
        enemyCharacters = new ArrayList<>();

        for (int k = 0; k < 2; k++) {

            int nr = switch (k) {
                case 0 -> LevelManager.getNrEnemiesNormal();
                case 1 -> LevelManager.getNrEnemiesUndead();
                default -> 0;
            };

            for (int i = 0; i < nr; i++) {
                Position position = new Position();

                boolean ok = false;
                int timer = 0;

                while (timer < 100 && !ok) {
                    timer++;

                    position.yPX = RandomNumber.randomNumber(0, Map.heightPX - 1);
                    position.xPX = RandomNumber.randomNumber(0, Map.widthPX - 1);

                    if (map.canWalkOn(position.xPX, position.yPX)) {

                        position.tmpX = position.xPX;
                        position.tmpY = position.yPX;

                        if (Distance.calculateDistance(new Pair<>(player.getPosition().tmpX, player.getPosition().tmpY), new Pair<>(position.tmpX, position.tmpY)) > 10 * Window.objectSize) {
                            ok = true;
                        }
                    }
                }

                if (ok) {
                    enemyCharacters.add(switch (k) {
                        case 0 -> new NormalEnemy(position);
                        case 1 -> new UndeadEnemy(position);
                        default -> null;
                    });
                }
            }
        }

        characters.add(player);
        characters.addAll(alliedCharacters);

        if (friendlyCharacters != null) characters.addAll(friendlyCharacters);
        if (enemyCharacters != null) characters.addAll(enemyCharacters);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
