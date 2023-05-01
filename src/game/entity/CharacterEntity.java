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

    public CharacterEntity(Position position, Animation[][] animation, HitBox hitbox, PrintBox printBox, double speed, int health, int maxHealth) {
        super(position, MakeTexture.make(animation[0][0].texture, animation[0][0].width), printBox);
        this.animation = animation[0][0];
        altAnimation = animation;
        curentAnimation = 0;

        this.hitBox = hitbox;
        this.speed = speed;
        this.health = health;

        this.maxHealth = maxHealth;

        travelDir = Direction.stop;
    }

    public void setTravelDir(Direction direction) {
        travelDir = direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Direction getTravelDir() {
        return travelDir;
    }

    public void update() {
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

        int signX = switch (travelDir) {
            case right, up_right, down_right -> 1;
            case left, up_left, down_left -> -1;
            default -> 0;
        };

        int signY = switch (travelDir) {
            case up, up_right, up_left -> -1;
            case down, down_right, down_left -> 1;
            default -> 0;
        };

        if (Game.getGame().getMap().canWalkOn(position.xPX + signX, position.yPX + signY)) {
            canMove = true;
        } else if (Game.getGame().getMap().canWalkOn(position.xPX + signX, position.yPX)) {
            canMove = true;
            signY = 0;
        } else if (Game.getGame().getMap().canWalkOn(position.xPX, position.yPX + signY)) {
            canMove = true;
            signX = 0;
        }


        if (canMove) {
            position.tmpX += speed * signX;
            position.tmpY += speed * signY;

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
}