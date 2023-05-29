package game.character;

import game.Camera;
import game.Game;
import game.Window;
import game.component.Collectable;
import game.component.Pair;
import game.component.RandomNumber;
import game.component.position.Distance;
import game.component.position.Position;
import game.component.weapon.Weapon;
import game.entity.Entity;
import game.graphics.assets.CharacterAssets;
import game.level.LevelManager;
import game.level.Map;

import java.util.*;
import java.util.List;

import static game.character.Ranks.*;

/*
    Clasa CharacterManager este folosită pentru a crea și utiliza caracterele aliate, caracterul jucătorului și inamicii
    din joc. Toate caracterele sunt utilizate folosind interfața Character, fiind stocate într-o listă de caractere când
    sunt folosite de funcțiile de update sau de trimise la funcția de draw din clasa Map.

    Caracterele sunt organizate în arbori utilizând șablonul de proiectare Composite care determină caracterul pe care
    îl urmăresc.

    Lista de caractere commanding returnată de metoda getCommanding reprezintă nodurile fiu
    Caracterul leader  returnat de metoda getLeader reprezintă nodul părinte și caracterul pe care îl urmărește pe hartă

    Elementele din listele de caractere și lista de obiective sunt ordonate în funcție de poziția de pe hartă pentru a
    fi afișate.
 */

public class CharacterManager {
    private static CharacterManager characterManager = null;
    private List<Character> characters;
    private List<Character> enemyCharacters;
    private final List<Character> alliedCharacters = new ArrayList<>();
    private Player player;

    private final List<Character> changeableCharacters = new ArrayList<>();
    int curentCharacter;

    private boolean hasPlayer;

    private final Map map;

    public static CharacterManager getCharacterManager() {
        return characterManager;
    }

    public CharacterManager(Map map) {
        characterManager = this;
        this.map = map;
        loadAlliedCharacters();
        loadCharacters();
    }

    private void loadAlliedCharacters() {
        Position playerPosition;

        playerPosition = LevelManager.getPlayerPosition();
        playerPosition.xPX *= Window.objectSize;
        playerPosition.tmpX = playerPosition.xPX;
        playerPosition.yPX *= Window.objectSize;
        playerPosition.tmpY = playerPosition.yPX;

        player = new Player(playerPosition);
        hasPlayer = true;

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

        position = getPositionNextToPlayer();
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

        commanding.get(1).setCommanding(commanding2);
        alliedCharacters.addAll(commanding2);

        changeableCharacters.add(player);
        changeableCharacters.addAll(commanding);

    }

    public void update(){

        if (hasPlayer) {
            for (Character character : characters) {
                character.update();
                if (character.isDead()) {
                    if (character instanceof Player) {
                        if (!changeCharacter(1)) {
                            hasPlayer = false;
                        }
                    } else {
                        if (character instanceof Unit) {
                            player.decScore(100);
                        } else {
                            if (character.getEntity().getAttackedBy() == player.getEntity()){
                                player.addKill();
                            } else for (Character allied : alliedCharacters) {
                                if (character.getEntity().getAttackedBy() == allied.getEntity()) {
                                    player.addKill();
                                }
                            }
                        }
                        removeCharacter(character);
                    }
                }

                for (Collectable objective : map.getObjectives()) {
                    if (Distance.calculateDistance(new Pair<>(player.getPosition().tmpX, player.getPosition().tmpY), new Pair<>(objective.getPosition().tmpX, objective.getPosition().tmpY)) < 2 * Window.objectSize) {
                        Pair<Integer, Integer> collected = objective.collect();

                        switch (collected.getLeft()) {
                            case Collectable.map -> map.incFinalizedObjectives();
                            case Collectable.ammo -> {
                                if (player.getEntity().getWeapon() != null && player.getEntity().getWeapon().getType() == Weapon.RangeWeapon) {
                                    player.getEntity().getWeapon().addAmmo(collected.getRight() * player.getEntity().getWeapon().getMaxAmmo());
                                } else if (player.getEntity().getWeapons() != null && player.getEntity().getWeapons()[1] != null) {
                                    player.getEntity().getWeapons()[1].addAmmo(collected.getRight() * player.getEntity().getWeapons()[1].getMaxAmmo());
                                }
                            }
                            case Collectable.medKit -> player.getEntity().addMedKit(collected.getRight());
                            case Collectable.toolBox -> player.addScore(collected.getRight());
                        }
                    }
                }

                for (int i = 0; i < map.getObjectives().size(); i++) {
                    if (map.getObjectives().get(i).isCollected()) {
                        map.getObjectives().set(i, null);
                    }
                }

                map.getObjectives().removeAll(Collections.singleton(null));
                map.getObjectives().sort(Comparator.comparingInt(o -> o.getPosition().yPX));
            }

            if (!hasPlayer) {
                player.setEntity(null);
                characters.remove(player);
                changeableCharacters.remove(player);
            }

            characters.removeAll(Collections.singleton(null));
            alliedCharacters.removeAll(Collections.singleton(null));
            changeableCharacters.removeAll(Collections.singleton(null));
            if (enemyCharacters != null) enemyCharacters.removeAll(Collections.singleton(null));

            characters.sort(Comparator.comparingInt(o -> o.getPosition().yPX));

            if (map.getExit() != null) {
                if (Distance.calculateDistance(new Pair<>(player.getPosition().tmpX, player.getPosition().tmpY), new Pair<>(map.getExit().tmpX, map.getExit().tmpY)) < 5 * Window.objectSize) {
                    if (map.loadNextMap())  {
                        refreshAlliedPosition();
                        loadCharacters();
                        LevelManager.saveLevelStart();
                    } else {
                        Game.getGame().startScreen();
                    }
                }
            }

        } else {
            Game.getGame().startScreen();
        }
    }

    /*
        metoda refreshAlliedPosition recentrează caracterele aliate în jurul jucătorului la schimbarea nivelului
     */

    private void refreshAlliedPosition() {
        Position position;

        Position playerPosition = LevelManager.getPlayerPosition();
        playerPosition.xPX *= Window.objectSize;
        playerPosition.tmpX = playerPosition.xPX;
        playerPosition.yPX *= Window.objectSize;
        playerPosition.tmpY = playerPosition.yPX;

        player.setPosition(playerPosition);
        Camera.getCamera().setPosition(player.getPosition());

        for (Character character : alliedCharacters) {
             do {
                position = getPositionNextToPlayer();
            } while (position == null);
            character.setPosition(position);
        }
    }

    private void removeCharacter(Character characterInstance) {

        sortCommanding();
        removeCommandNode(characterInstance);
        sortCommanding();

        if (characters != null) {
            for (Character character : characters) {
                if (character != null) {
                    if (character.getLeader() == characterInstance) {
                        character.setLeader(null);
                    }
                    for (int i = 0; character.getCommanding() != null && i < character.getCommanding().size(); i++) {
                        if (character.getCommanding().get(i) == characterInstance) {
                            character.getCommanding().set(i, null);
                        }
                    }
                }
            }
        }

        for (int i = 0; characters != null && i < characters.size(); i++) {
            if (characters.get(i) == characterInstance) {
                characters.set(i, null);
            }
        }
        for (int i = 0; enemyCharacters != null && i < enemyCharacters.size(); i++) {
            if (enemyCharacters.get(i) == characterInstance) {
                enemyCharacters.set(i, null);
            }
        }
        for (int i = 0; i < alliedCharacters.size(); i++) {
            if (alliedCharacters.get(i) == characterInstance) {
                alliedCharacters.set(i, null);
            }
        }
        for (int i = 0; i < changeableCharacters.size(); i++) {
            if (changeableCharacters.get(i) == characterInstance) {
                changeableCharacters.set(i, null);
            }
        }
    }

    public boolean canWalkOn(Entity entity, int x, int y) {
        List<Character> characterList;

        characterList = Objects.requireNonNullElse(characters, alliedCharacters);

        for (Character otherCharacter : characterList) {
            if (otherCharacter != null && otherCharacter.getEntity() != entity) {
                if (Distance.calculateDistance(new Pair<>((double) x, (double) y), new Pair<>(otherCharacter.getPosition().tmpX, otherCharacter.getPosition().tmpY)) < CharacterAssets.collisionDistance) {
                    return false;
                }
            }
        }

        return true;
    }

    /*
        metoda changeCharacter modifică referințele la obiectul Entity și Rank dintre două caracter aflate în lista de
        caractere jucabile și actualizează arborele în care se află caracterul jucătorului
     */

    public boolean changeCharacter(int character) {
        Character newPlayer = null;

        changeableCharacters.removeAll(Collections.singleton(null));

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

            swapCommandNode(player, newPlayer);

            Camera.getCamera().setPosition(player.getPosition());

            return true;
        }

        return false;
    }

    /*
        metode pentru schimbarea a 2 noduri, ștergerea unui nod și sortarea nodurilor dintr-un arbore de caracter
     */

    private void swapCommandNode(Character character1, Character character2) {
        List<Character> commandingCharacter1 = character1.getCommanding();
        character1.setCommanding(character2.getCommanding());
        character2.setCommanding(commandingCharacter1);

        Character character1Leader = character1.getLeader();
        character1.setLeader(character2.getLeader());
        character2.setLeader(character1Leader);

        for (int i = 0; character1.getCommanding() != null && i < character1.getCommanding().size(); i++) {
            if (character1.getCommanding().get(i) == character1) {
                character1.getCommanding().set(i, character2);
            }
        }

        for (int i = 0; character2.getCommanding() != null && i < character2.getCommanding().size(); i++) {
            if (character2.getCommanding().get(i) == character2) {
                character2.getCommanding().set(i, character1);
            }
        }

        if (character1.getCommanding() != null) {
            for (Character character : character1.getCommanding()) {
                if (character != null) {
                    character.setLeader(character1);
                }
            }
        }

        if (character2.getCommanding() != null) {
            for (Character character : character2.getCommanding()) {
                if (character != null) {
                    character.setLeader(character2);
                }
            }
        }
    }

    private Character removeCommandNode(Character character) {
        if (character != null) {
            Character oldCharacter = character;
            character = removeCommandNode(getRemoveCommandNodeCommandingLeft(character));
            if (character != null) {
                List<Character> newCharacterCommanding = new ArrayList<>();
                newCharacterCommanding.add(getRemoveCommandNodeCommandingLeft(character));
                newCharacterCommanding.addAll(getRemoveCommandNodeCommandingRight(oldCharacter));
                character.setCommanding(newCharacterCommanding);
                for (Character commandedCharacter : character.getCommanding()) {
                    if (commandedCharacter != null) {
                        commandedCharacter.setLeader(character);
                    }
                }
                character.setLeader(oldCharacter.getLeader());
                character.getCommanding().removeAll(Collections.singleton(null));
                character.getCommanding().sort(Comparator.comparingInt(o -> o.getRank().ordinal()));
                for (int i = 0; character.getLeader() != null && character.getLeader().getCommanding() != null && i < character.getLeader().getCommanding().size(); i++) {
                    if (character.getLeader().getCommanding().get(i) == oldCharacter) {
                        character.getLeader().getCommanding().set(i, character);
                    }
                }
            }
            return oldCharacter;
        }
        return null;
    }

    private Character getRemoveCommandNodeCommandingLeft(Character character) {
        if (character != null && character.getCommanding() != null) {
            character.getCommanding().removeAll(Collections.singleton(null));
            if (character.getCommanding().size() > 0) {
                return character.getCommanding().get(0);
            }
        }
        return null;
    }

    private List<Character> getRemoveCommandNodeCommandingRight(Character character) {
        List<Character> commandingRight = new ArrayList<>();
        if (character != null && character.getCommanding() != null && character.getCommanding().size() > 0) {
            character.getCommanding().removeAll(Collections.singleton(null));
            commandingRight.addAll(character.getCommanding());
            commandingRight.set(0, null);
            commandingRight.removeAll(Collections.singleton(null));
        }
        return commandingRight;
    }

    public void sortCommanding() {
        Character character = player;
        while (character.getLeader() != null) {
            character = character.getLeader();
        }
        sortCommandGraph(character);
    }

    private void sortCommandGraph(Character character) {
        if (character.getCommanding() != null) {
            character.getCommanding().removeAll(Collections.singleton(null));
            character.getCommanding().sort(Comparator.comparingInt(o -> o.getRank().ordinal()));
            for (Character commandedCharacter : character.getCommanding()) {
                if (commandedCharacter != null) {
                    sortCommandGraph(commandedCharacter);
                }
            }
        }
    }

    public void loadCharacters() {
        characters = new ArrayList<>();
        enemyCharacters = new ArrayList<>();;
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

        characters.add(player);
        characters.addAll(alliedCharacters);

        if (enemyCharacters != null) characters.addAll(enemyCharacters);
    }

    public Position getPositionNextToPlayer() {
        int generateTimer = 0;
        boolean ok = false;

        Position position = null;

        while (generateTimer < 100 && !ok) {
            generateTimer++;

            int x = RandomNumber.randomNumber(player.getPosition().xPX - 10 * Window.objectSize, player.getPosition().xPX + 10 * Window.objectSize);
            int y = RandomNumber.randomNumber(player.getPosition().yPX - 10 * Window.objectSize, player.getPosition().yPX + 10 * Window.objectSize);

            if (Map.getMap().canWalkOn(x, y) && CharacterManager.characterManager.canWalkOn(null, x, y)) {
                position = new Position(x, y);
                ok = true;
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
    public List<Character> getEnemyCharacters() {
        return enemyCharacters;
    }

    public List<Character> getAlliedCharacters() {
        return alliedCharacters;
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
