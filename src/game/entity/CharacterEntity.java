package game.entity;

import game.component.*;
import game.component.Component;

import java.awt.*;

public class CharacterEntity extends Component implements Character {

    //TODO stuff

    //TODO better animation management

    private Direction travelDir;
    private HitBox hitBox;
    private double speed;
    private int health, maxHealth;
    private Animation animation;
    private Animation[][] altAnimation;
    private int curentAnimation;

    public CharacterEntity(Position position, Animation[][] animation, HitBox hitbox, double speed, int health, int maxHealth) {
        super(position, Texture.make(animation[0][0].texture, animation[0][0].width));
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
            case up, up_right, right, down_right -> animation = altAnimation[curentAnimation][1];
            case down, down_left, left, up_left -> animation = altAnimation[curentAnimation][0];
        }

        refreshTexture();
    }

    private void refreshTexture() {
        texture.texture = animation.texture;
    }

    public void move() {
        int signX = 0, signY = 0;

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

        position.tmpX += speed * signX;
        position.tmpY += speed * signY;

        position.xPX = (int)position.tmpX;
        position.yPX = (int)position.tmpY;
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
}