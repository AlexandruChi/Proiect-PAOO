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
    private final List<Character> characters = new ArrayList<>();
    private final Player player;

    public CharacterManager() {
        player = new Player(LevelManager.getPlayerPosition());
        characters.add(player);
        load();
    }

    public void update(){
        for (Character character : characters) {
            character.update();
        }

        characters.sort(Comparator.comparingInt(o -> o.getPosition().yPX));
    }

    public void load() {
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

                    if (Game.getGame().getMap().canWalkOn(position.xPX, position.yPX)) {

                        position.tmpX = position.xPX;
                        position.tmpY = position.yPX;

                        if (Distance.calculateDistance(new Pair<>(player.getPosition().tmpX, player.getPosition().tmpY), new Pair<>(position.tmpX, position.tmpY)) < 10 * Window.objectSize) {
                            ok = true;
                        }
                    }
                }

                if (ok) {
                    characters.add(switch (k) {
                        case 0 -> new NormalEnemy(position);
                        case 1 -> new UndeadEnemy(position);
                        default -> null;
                    });
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
