package game.character;

import game.Camera;
import game.Window;
import game.component.Direction;
import game.component.Pair;
import game.component.position.Distance;
import game.component.position.Position;
import game.component.weapon.Weapon;
import game.component.weapon.WeaponFactory;
import game.entity.Entity;
import game.entity.MakeEntity;
import game.level.Map;

import java.awt.*;
import java.util.List;

public class Unit extends NPC {

    protected Character leader;
    protected List<Character> commanding;
    protected Ranks rank;

    protected int followDistance;

    protected Position locationToFollow;

    private int searchForEnemyTimer = 0;
    private Character characterToFollow;

    protected Unit(Position position, Character leader, List<Character> commanding, Ranks rank) {
        super(MakeEntity.makeEntity(MakeEntity.germanCharacterID, position));
        this.leader = leader;
        this.commanding = commanding;
        this.rank = rank;

        characterToFollow = null;

        followDistance = switch (rank) {
            case Oberleutnant -> 5 * Window.objectSize;
            case Unterfeldwebel -> 10 * Window.objectSize;
            case Unteroffiziere -> 15 * Window.objectSize;
            case Obergefreiten -> 20 * Window.objectSize;
        };


        Weapon[] weapons = getEntity().getWeapons();

        weapons[0] = WeaponFactory.makeWeapon(Weapon.Sword);
        weapons[1] = WeaponFactory.makeWeapon(switch (rank) {
            case Oberleutnant -> Weapon.GermanSidearm;
            case Unterfeldwebel -> Weapon.GermanAR;
            case Unteroffiziere -> Weapon.GermanSMG;
            case Obergefreiten -> Weapon.GermanRifle;
        });
        weapons[2] = WeaponFactory.makeWeapon(switch (rank) {
            case Oberleutnant -> Weapon.GermanAR;
            case Unterfeldwebel -> Weapon.GermanSMG;
            case Unteroffiziere, Obergefreiten -> Weapon.GermanRifle;
        });

        weapons[1].addAmmo(weapons[1].getMaxAmmo() * 2);
        weapons[2].addAmmo(weapons[2].getMaxAmmo() * 4);

        setWeapon(1);
    }

    protected Unit(Entity entity, Character leader, List<Character> commanding, Ranks rank) {
        super(entity);
        this.leader = leader;
        this.commanding = commanding;
        this.rank = rank;

        followDistance = switch (rank) {
            case Oberleutnant -> 10 * Window.objectSize;
            case Unterfeldwebel -> 20 * Window.objectSize;
            case Unteroffiziere -> 30 * Window.objectSize;
            case Obergefreiten -> 40 * Window.objectSize;
        };
    }

    public void setLeader(Character leader) {
        this.leader = leader;
    }

    @Override
    public Character getLeader() {
        return leader;
    }

    public void setCommanding(List<Character> commanding) {
        this.commanding = commanding;
    }

    @Override
    public void setCharacterToFollow(Character character) {
        characterToFollow = character;
    }

    public List<Character> getCommanding() {
        return commanding;
    }

    @Override
    public Ranks getRank() {
        return rank;
    }

    public void setRank(Ranks rank) {
        this.rank = rank;
    }

    public boolean removeCommanding(Character character) {
        // TODO make
        return true;
    }

    @Override
    public void update() {

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
            if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(enemy.getPosition().tmpX, enemy.getPosition().tmpY)) > range - 2 * Window.objectSize) {

                if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(enemy.getPosition().tmpX, enemy.getPosition().tmpY)) > range) {
                    getEntity().setSprint(true);
                }

                followCharacter(enemy);

            } else {
                getEntity().attack(enemy.getEntity());
            }
        } else if (locationToFollow != null) {
            if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(locationToFollow.tmpX, locationToFollow.tmpY)) > 2 * Window.objectSize) {
                if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(enemy.getPosition().tmpX, enemy.getPosition().tmpY)) > 10 * Window.objectSize) {
                    getEntity().setSprint(true);
                }

                goToLocation(locationToFollow);

            }
        } else if (leader != null) {
            if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(leader.getPosition().tmpX, leader.getPosition().tmpY)) > followDistance) {
                if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(leader.getPosition().tmpX, leader.getPosition().tmpY)) > followDistance * 1.5) {
                    getEntity().setSprint(true);
                }

               followCharacter(leader);

            } else /*if (Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(leader.getPosition().tmpX, leader.getPosition().tmpY)) <= followDistance)*/{
                moveAround();
            }
        } else {
            moveAround();
        }

        super.update();
    }

    public Character searchForEnemy() {
        searchForEnemyTimer++;
        if (searchForEnemyTimer >= 30) {

            characterToFollow = null;
            int minDistance = -1;
            for (Character character : CharacterManager.getCharacterManager().getCharacters()) {
                if (!(character instanceof Player) && !(character instanceof Unit) && character != null) {
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
    public void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);
    }
}
