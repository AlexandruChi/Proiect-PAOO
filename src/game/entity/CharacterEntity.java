package game.entity;

import game.Game;
import game.component.*;
import game.component.TextureComponent;
import game.component.position.Position;
import game.component.texture.MakeTexture;

public class CharacterEntity extends TextureComponent implements Character {

    //TODO stuff

    //TODO better animation management

    private Direction travelDir;
    private HitBox hitBox;
    private double speed;
    private int health, maxHealth;
    private Animation animation;
    private Animation[][] altAnimation;
    private int curentAnimation;

    private double normalSpeed;
    private boolean sprint;
    private double sprintSpeed;
    private boolean aim;
    private double aimSpeed;


    public CharacterEntity(Position position, Animation[][] animation, HitBox hitbox, PrintBox printBox, double normalSpeed, double sprintSpeed, int health, int maxHealth) {
        super(position, MakeTexture.make(animation[0][0].texture, animation[0][0].width), printBox);
        this.animation = animation[0][0];
        altAnimation = animation;
        curentAnimation = 0;

        this.hitBox = hitbox;
        this.health = health;

        this.normalSpeed = normalSpeed;
        speed = this.normalSpeed;
        this.sprintSpeed = sprintSpeed;
        sprint = false;

        this.maxHealth = maxHealth;

        travelDir = Direction.stop;
    }

    public void setTravelDir(Direction direction) {
        travelDir = direction;
    }

    public Direction getTravelDir() {
        return travelDir;
    }

    public void update() {
        updateSpeed();
        move();

        switch (travelDir) {
            case up_right, right, down_right -> animation = altAnimation[curentAnimation][1];
            case down_left, left, up_left -> animation = altAnimation[curentAnimation][0];
        }
        refreshTexture();
    }

    private void refreshTexture() {
        texture.texture = animation.texture;
    }

    public void move() {

        // TODO fix and add fractional movement

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

            if (Game.getGame().getMap().canWalkOn((int) (tmpSpeed * signX + position.xPX), (int) (tmpSpeed * signY + position.yPX))) {
                canMove = true;
            } else if (Game.getGame().getMap().canWalkOn((int) (tmpSpeed * signX + position.xPX), position.yPX)) {
                canMove = true;
                signY = 0;
            } else if (Game.getGame().getMap().canWalkOn(position.xPX, (int) (tmpSpeed * signY + position.yPX))) {
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
            position.tmpX += tmpSpeed * signX;
            position.tmpY += tmpSpeed * signY;

            position.xPX = (int) position.tmpX;
            position.yPX = (int) position.tmpY;

            position.x = position.xPX / 32;
            position.y = position.yPX / 32;
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

    public void attack(Position position) {

    }

    public double getSpeed() {
        return speed;
    }

    private void updateSpeed() {
        if (sprint) {
            speed = sprintSpeed;
        } else {
            speed = normalSpeed;
        }
    }

    public void setSprint(boolean sprint) {
        this.sprint = sprint;

    }
}