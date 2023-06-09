package game.entity;

import game.character.CharacterManager;
import game.component.*;
import game.component.TextureComponent;
import game.component.position.Distance;
import game.component.position.Position;
import game.component.texture.MakeTexture;
import game.component.weapon.Weapon;
import game.level.Map;

import javax.print.attribute.standard.PrinterMoreInfoManufacturer;
import java.util.ArrayList;
import java.util.List;

/*
    Clasa EntityClass reprezintă obiectele care sunt afișate pe ecran a caracterelor
    Conține funcții pentru a primi comenzi de la caractere sau jucător
 */

public class EntityClass extends TextureComponent implements Entity {
    private Direction travelDir;
    private HitBox hitBox;
    private double speed;
    private int damage;
    private int range;
    private int delay;

    private int health, maxHealth;
    private Animation animation;
    private Animation[][] altAnimation;

    private List<Animation[][]> animationList;

    private int curentAnimation;

    private Weapon[] weapons;
    private Weapon curentWeapon;

    private int nrMedKits;
    private int attackTimer;

    private double normalSpeed;
    private boolean sprint;
    private double sprintSpeed;
    private boolean aim;

    private int orientation;

    private Entity attackedBy;

    EntityClass(Position position, List<Animation[][]> animation, HitBox hitbox, PrintBox printBox, double normalSpeed, double sprintSpeed, int health, int damage, int range, int delay, int weaponSlots) {
        super(position, MakeTexture.make(animation.get(0)[0][0].texture, animation.get(0)[0][0].width), printBox);

        this.animation = animation.get(0)[0][0];
        altAnimation = animation.get(0);
        animationList = animation;
        curentAnimation = 0;

        orientation = 0;

        this.hitBox = hitbox;
        this.health = health;
        this.damage = damage;
        this.range  = range;
        this.delay = delay;

        attackedBy = null;

        this.normalSpeed = normalSpeed;
        speed = this.normalSpeed;
        this.sprintSpeed = sprintSpeed;
        sprint = false;

        aim = false;

        nrMedKits = 0;

        maxHealth = health;

        travelDir = Direction.stop;

        attackTimer = 0;

        if (weaponSlots > 0) {
            weapons = new Weapon[weaponSlots];
        } else {
            weapons = null;
        }
        curentWeapon = null;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    @Override
    public Weapon getWeapon() {
        return curentWeapon;
    }

    @Override
    public void setWeapon(int weapon) {
        curentWeapon = weapons[weapon];
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void addMedKit(int nr) {
        nrMedKits += nr;
    }

    public void useMedKit() {
        if (incHealth(1)) {
            nrMedKits--;
        }
    }

    public Entity getAttackedBy() {
        return attackedBy;
    }

    public void setAttackedBy(Entity entity) {
        attackedBy = entity;
    }

    public int getNrMedKits() {
        return nrMedKits;
    }

    public void setTravelDir(Direction direction) {
        if (this.travelDir != direction) {
            position.tmpX = position.xPX;
            position.tmpY = position.yPX;
        }

        travelDir = direction;
    }

    public Direction getTravelDir() {
        return travelDir;
    }

    public void update() {
        updateSpeed();
        move();

        switch (travelDir) {
            case up_right, right, down_right -> orientation = 1;
            case down_left, left, up_left -> orientation = 0;
        }

        refreshTexture();

        if (attackTimer > 0) {
            attackTimer--;
        }
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /*
        funcție pentru actualizarea texturii în funcție de animație
     */

    private void refreshTexture() {
        if (aim) {
            altAnimation = animationList.get(2);
        } else if (travelDir != Direction.stop) {
            altAnimation = animationList.get(1);
        } else {
            altAnimation = animationList.get(0);
        }

        if (curentWeapon == null) {
            curentAnimation = 0;
        } else {
            curentAnimation = curentWeapon.getID() + 1;
            if (curentAnimation == 6) {
                curentAnimation = 0;
            }
        }
        animation = altAnimation[orientation][curentAnimation];
        texture.texture = animation.getTexture();
    }

    /*
        Funcție pentru deplasarea pe hartă

        Folosește funcțiile din clasele Map și CharacterManager pentru a determina dacă se poste mișca pe o anumită
        poziție. În caz contrar selectează o altă poziție din cele valide pentru a se deplasa.
     */

    public void move() {

        boolean canMove = false;
        double tmpSpeed = speed;
        int signX = 0, signY = 0;

        while (tmpSpeed != 0) {

             signX = switch (travelDir) {
                case right, up_right, down_right -> 1;
                case left, up_left, down_left -> -1;
                default -> 0;
            };

             signY = switch (travelDir) {
                case up, up_right, up_left -> -1;
                case down, down_right, down_left -> 1;
                default -> 0;
            };

             if (signX == 0 && signY == 0) {
                 position.tmpX = position.xPX;
                 position.tmpY = position.yPX;
                 return;
             }

            if (
                    Map.getMap().canWalkOn((int) (tmpSpeed * (double) signX + position.tmpX), (int) (tmpSpeed * (double) signY + position.tmpY)) &&
                            CharacterManager.getCharacterManager().canWalkOn(this, (int) (tmpSpeed * (double) signX + position.tmpX), (int) (tmpSpeed * (double) signY + position.tmpY))
            ) {
                canMove = true;
            } else if (
                    Map.getMap().canWalkOn((int) (tmpSpeed * (double) signX + position.tmpX), position.yPX) &&
                            CharacterManager.getCharacterManager().canWalkOn(this, (int) (tmpSpeed * (double) signX + position.tmpX), position.yPX)
            ) {
                canMove = true;
                signY = 0;
            } else if (
                    Map.getMap().canWalkOn(position.xPX, (int) (tmpSpeed * (double) signY + position.tmpY)) &&
                            CharacterManager.getCharacterManager().canWalkOn(this, position.xPX, (int) (tmpSpeed * (double) signY + position.tmpY))
            ) {
                canMove = true;
                signX = 0;
            }

            if (canMove) {
                break;
            }

            if (tmpSpeed == speed && tmpSpeed != (int) speed) {
                tmpSpeed = (int) speed;
            } else {
                tmpSpeed--;
            }
        }

        if (canMove) {
            position.tmpX += tmpSpeed * (double) signX;
            position.tmpY += tmpSpeed * (double) signY;

            position.xPX = (int) position.tmpX;
            position.yPX = (int) position.tmpY;
        }
    }

    public boolean incHealth(int health) {
        if (this.health < maxHealth) {
            this.health = this.health + health;
            return true;
        }
        return false;
    }

    public boolean decHealth(int health) {
        if (this.health > 0) {
            this.health = this.health - health;
            if (this.health < 0) {
                this.health = 0;
            }
            return true;
        }
        return false;
    }

    /*
        Funcțiile attack folosesc funcții din CharacterManager pentru a identifica un caracter care poate fi atacat.
        Caracteru salvează ultimul caracter care l-a atacat pentru a putea calcula scorul.
     */

    public void attack(Position position) {
        try {
            attack(CharacterManager.getCharacterManager().getCharacterAtPosition(position).getEntity());
        } catch (NullPointerException e) {
            attack((Entity)null);
        }
    }

    public void attack(Entity entity) {
        attackEntity(entity);
    }

    private void attackEntity(Entity entity) {
        if (attackTimer == 0) {

            int range, delay, damage, hitRate;

            if (curentWeapon == null) {
                range = this.range;
                delay = this.delay;
                damage = this.damage;
                hitRate = 100;
            } else {
                range = curentWeapon.getRange();
                delay = curentWeapon.getDelay();
                damage = curentWeapon.getDamage();
                hitRate = curentWeapon.getHitRate();

                if (!aim && curentWeapon.canAim()) {
                    hitRate /= 4;
                }
            }

            if (curentWeapon == null || curentWeapon.fireRound() || curentWeapon.getType() == Weapon.MelleWeapon) {
                attackTimer = delay;
                if (entity != null) {
                    if ((int)Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(entity.getPosition().tmpX, entity.getPosition().tmpY)) < range && Map.getMap().lineOfSight(getPosition(), entity.getPosition())) {
                        if (RandomNumber.randomNumber(0, 100) <= hitRate) {
                            entity.decHealth(damage);
                            ((EntityClass)entity).setAttackedBy(this);
                        }
                    }
                }
            }
        }
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    public double getSpeed() {
        return speed;
    }

    private void updateSpeed() {
        if (sprint) {
            speed = sprintSpeed;
        } else if (aim) {
            speed = 0;
        } else {
            speed = normalSpeed;
        }
    }

    @Override
    public void setSprint(boolean sprint) {
        this.sprint = sprint;
    }

    @Override
    public void setAim(boolean aim) {
        if (sprint) {
            this.aim = false;
        } else {
            this.aim = aim;
        }
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    public int getRange() {
        return range;
    }
}