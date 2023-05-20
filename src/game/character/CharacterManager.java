package game.character;

import game.Window;
import game.component.Pair;
import game.component.RandomNumber;
import game.component.position.Distance;
import game.component.position.Position;
import game.entity.Entity;
import game.graphics.assets.CharacterAssets;
import game.level.LevelManager;
import game.level.Map;

import java.util.ArrayList;
import java.util.Collections;
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
            if (character.isDead()) {
                if (character instanceof Player) {
                    System.out.println("DEAD");
                } else {
                    removeCharacter(character);
                }
            }
        }

        characters.removeAll(Collections.singleton(null));
        alliedCharacters.removeAll(Collections.singleton(null));
        if (enemyCharacters != null) enemyCharacters.removeAll(Collections.singleton(null));
        if (friendlyCharacters != null) friendlyCharacters.removeAll(Collections.singleton(null));

        characters.sort(Comparator.comparingInt(o -> o.getPosition().yPX));
    }

    private void removeCharacter(Character characterInstance) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i) == characterInstance) {
                characters.set(i, null);
            }
        }
        if (characterInstance instanceof Enemy) {
            for (int i = 0; i < enemyCharacters.size(); i++) {
                if (enemyCharacters.get(i) == characterInstance) {
                    enemyCharacters.set(i, null);
                }
            }
        }
        // check for frendly and alied ones;
    }

    public boolean canWalkOn(Entity entity, int x, int y) {

        for (Character otherCharacter : characters) {
            if (otherCharacter != null && otherCharacter.getEntity() != entity) {
                if (Distance.calculateDistance(new Pair<>((double) x, (double) y), new Pair<>(otherCharacter.getPosition().tmpX, otherCharacter.getPosition().tmpY)) < CharacterAssets.collisionDistance) {
                    return false;
                }
            }
        }

        return true;
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

    public Character getCharacterAtPosition(Position position) {

        for (Character character : characters) {
            if (character != null) {
                if (!(character instanceof Player) && checkHitBox(character, position)) {
                    return character;
                }
            }
        }

        return null;
    }

    private boolean checkHitBox(Character character, Position position) {
        return position.xPX >= character.getPosition().xPX - character.getHitBox().hitBoxDifX && position.xPX <= character.getPosition().xPX + character.getHitBox().hitBoxDifX && position.yPX >= character.getPosition().yPX - character.getHitBox().hitBoxDifY && position.yPX <= character.getPosition().yPX;
    }
}
