package game.character;

import game.Camera;
import game.Window;
import game.component.Direction;
import game.component.Pair;
import game.component.position.Distance;
import game.component.position.Position;
import game.component.weapon.Weapon;
import game.component.weapon.WeaponFactory;
import game.entity.MakeEntity;
import game.level.Map;

import java.awt.*;
import java.util.List;

public class NormalEnemy extends Enemy {
    private static final int followDistance = 25 * Window.objectSize;
    private Character characterToFollow;
    private int searchForEnemyTimer = 0;

    public NormalEnemy(Position position) {
        super(MakeEntity.britishCharacterID, position);

        Weapon[] weapons = getEntity().getWeapons();

        weapons[0] = WeaponFactory.makeWeapon(Weapon.BritishRifle);
        weapons[0].addAmmo(weapons[0].getMaxAmmo() * 10);

        setWeapon(0);
    }

    public Character searchForEnemy() {
        searchForEnemyTimer++;
        if (searchForEnemyTimer >= 30) {

            characterToFollow = null;
            int minDistance = -1;
            for (Character character : CharacterManager.getCharacterManager().getCharacters()) {
                if (!(character instanceof NormalEnemy) && character != null) {
                    int distance = (int) Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(character.getPosition().tmpX, character.getPosition().tmpY));
                    if (Map.getMap().lineOfSight(getPosition(), character.getPosition()) && distance < followDistance) {
                        if (distance < minDistance) {
                            minDistance = distance;
                            characterToFollow = character;
                        } else if (minDistance == -1) {
                            minDistance = distance;
                            characterToFollow = character;
                        }
                    }
                }
            }
            searchForEnemyTimer = 0;
        }
        return characterToFollow;
    }

    @Override
    public void update() {

        if (getEntity().getWeapon() != null) {
            if (getEntity().getWeapon().getNrAmmo() == 0) {
                getEntity().getWeapon().reload();
            }

            if (getEntity().getWeapon().isSelectFire()) {
                getEntity().getWeapon().setMode(false);
            }
        }

        Character enemy = searchForEnemy();

        getEntity().setSprint(false);
        getEntity().setAim(false);

        if (getEntity().getWeapon() != null) {
            if (getEntity().getWeapon().getNrAmmo() == 0) {
                getEntity().getWeapon().reload();
            }

            if (getEntity().getWeapon().isSelectFire()) {
                getEntity().getWeapon().setMode(false);
            }
        }

        if (enemy != null) {
            int range;
            if (getEntity().getWeapon() != null) {
                range = getEntity().getWeapon().getRange();
            } else {
                range = getEntity().getRange();
            }
            if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(enemy.getPosition().tmpX, enemy.getPosition().tmpY)) > 7 * Window.objectSize) {

                if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(enemy.getPosition().tmpX, enemy.getPosition().tmpY)) > range) {
                    getEntity().setSprint(true);
                }

                followCharacter(enemy);

            } else {
                //getEntity().setAim(true);
                getEntity().attack(enemy.getEntity());
            }
        } else {
            moveAround();
        }

        super.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);
    }

    @Override
    public List<Character> getCommanding() {
        return null;
    }

    @Override
    public Ranks getRank() {
        return null;
    }

    @Override
    public void setRank(Ranks rank) {

    }

    @Override
    public void setLeader(Character leader) {

    }

    @Override
    public Character getLeader() {
        return null;
    }

    @Override
    public void setCommanding(List<Character> commanding) {

    }
}
