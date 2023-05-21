package game.character;

import game.Camera;
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

import static game.character.Ranks.*;

public class CharacterManager {
    private static CharacterManager characterManager = null;
    private List<Character> characters;
    private List<Character> enemyCharacters;
    private List<Character> friendlyCharacters;
    private final List<Character> alliedCharacters = new ArrayList<>();
    private final Player player;

    private List<Character> changeableCharacters;
    int curentCharacter;

    private boolean hasPlayer;

    private final Map map;

    public static CharacterManager getCharacterManager() {
        return characterManager;
    }

    public CharacterManager(Map map) {
        characterManager = this;
        this.map = map;
        player = new Player(LevelManager.getPlayerPosition());
        hasPlayer = true;
        load();
    }

    public void update(){

        if (hasPlayer) {
            for (Character character : characters) {
                character.update();
                if (character.isDead()) {
                    if (character instanceof Player) {
                        hasPlayer = false;
                    } else {
                        removeCharacter(character);
                        player.addKill();
                    }
                }
            }

            characters.removeAll(Collections.singleton(null));
            alliedCharacters.removeAll(Collections.singleton(null));
            if (enemyCharacters != null) enemyCharacters.removeAll(Collections.singleton(null));
            if (friendlyCharacters != null) friendlyCharacters.removeAll(Collections.singleton(null));

            characters.sort(Comparator.comparingInt(o -> o.getPosition().yPX));

        } else {
            player.setEntity(null);
            characters.remove(player);
        }
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
        // TODO check for friendly and allied ones and remove references inside character class
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

    public void changeCharacter(int character) {
        Character newPlayer = null;

        int availableCharacters = changeableCharacters.size();
        int nextCharacter = curentCharacter + character;

        if (availableCharacters > 1) {
            if (nextCharacter < 0) {
                nextCharacter = availableCharacters - 1;
            }
            if (nextCharacter > availableCharacters - 1) {
                nextCharacter = 0;
            }

            newPlayer = changeableCharacters.get(nextCharacter);
        }

        if (newPlayer != null) {

            curentCharacter = nextCharacter;

            Entity playerEntity = player.getEntity();
            player.setEntity(newPlayer.getEntity());
            newPlayer.setEntity(playerEntity);

            Ranks rank = player.getRank();
            player.setRank(newPlayer.getRank());
            newPlayer.setRank((rank));

            Character leader = player.getLeader();
            player.setLeader(newPlayer.getLeader());
            newPlayer.setLeader(leader);

            List<Character> commanding = player.getCommanding();
            if (commanding != null) {
                for (Character characterCommanding : commanding) {
                    characterCommanding.setLeader(newPlayer);
                }
            }
            List<Character> commanding2 = newPlayer.getCommanding();
            if (commanding2 != null) {
                for (Character characterCommanding : commanding2) {
                    characterCommanding.setLeader(player);
                }
            }
            player.setCommanding(commanding2);
            newPlayer.setCommanding(commanding);
        }

        Camera.getCamera().setPosition(player.getPosition());
    }

    public void load() {
        characters = new ArrayList<>();
        enemyCharacters = new ArrayList<>();
        changeableCharacters = new ArrayList<>();
        curentCharacter = 0;

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

        Position position;

        // main character

        List<Character> commanding = new ArrayList<>();

        position = getPositionNextToPlayer();
        if (position != null) {
            commanding.add(new Unit(position, player, null, Unterfeldwebel));
        }

        position = getPositionNextToPlayer();
        if (position != null) {
            commanding.add(new Unit(position, player, null, Unterfeldwebel));
        }

        player.setCommanding(commanding);
        alliedCharacters.addAll(commanding);

        // secondary character 1

        List<Character> commanding1 = new ArrayList<>();
        position = getPositionNextToPlayer();
        if (position != null) {
            commanding1.add(new Unit(position, commanding.get(0), null, Unteroffiziere));
        }
        if (position != null) {
            commanding1.add(new Unit(position, commanding.get(0), null, Obergefreiten));
        }
        if (position != null) {
            commanding1.add(new Unit(position, commanding.get(0), null, Obergefreiten));
        }
        if (position != null) {
            commanding1.add(new Unit(position, commanding.get(0), null, Obergefreiten));
        }
        commanding.get(0).setCommanding(commanding1);
        alliedCharacters.addAll(commanding1);


        // secondary character 2

        List<Character> commanding2 = new ArrayList<>();
        position = getPositionNextToPlayer();
        if (position != null) {
            commanding2.add(new Unit(position, commanding.get(1), null, Unteroffiziere));
        }
        position = getPositionNextToPlayer();
        if (position != null) {
            commanding2.add(new Unit(position, commanding.get(1), null, Obergefreiten));
        }
        if (position != null) {
            commanding2.add(new Unit(position, commanding.get(1), null, Obergefreiten));
        }
        if (position != null) {
            commanding2.add(new Unit(position, commanding.get(1), null, Obergefreiten));
        }
        commanding.get(1).setCommanding(commanding2);
        alliedCharacters.addAll(commanding2);





        characters.add(player);
        characters.addAll(alliedCharacters);

        changeableCharacters.add(player);
        changeableCharacters.addAll(commanding);

        if (friendlyCharacters != null) characters.addAll(friendlyCharacters);
        if (enemyCharacters != null) characters.addAll(enemyCharacters);
    }

    public Position getPositionNextToPlayer() {
        int generateTimer = 0;
        boolean ok = false;

        Position position = null;

        while (generateTimer < 100 && !ok) {
            generateTimer++;

            int x = RandomNumber.randomNumber(player.getPosition().xPX - 5 * Window.objectSize, player.getPosition().xPX + 5 * Window.objectSize);
            int y = RandomNumber.randomNumber(player.getPosition().yPX - 5 * Window.objectSize, player.getPosition().yPX + 5 * Window.objectSize);

            if (Map.getMap().canWalkOn(x, y)) {
                position = new Position(x, y);
            }
        }

        return position;
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

    public int getNrSoldiers() {
        int nrSoldiers = alliedCharacters.size();
        if (!player.isDead()) {
            nrSoldiers++;
        }
        return nrSoldiers;
    }
}
