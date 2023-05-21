package game.entity;

import game.Camera;
import game.component.Direction;
import game.component.HitBox;
import game.component.position.Position;
import game.component.weapon.Weapon;

import java.awt.*;

public interface Entity {
    void setTravelDir(Direction direction);
    Direction getTravelDir();
    void update();
    void draw(Graphics graphics, Camera camera);
    void move();
    boolean incHealth(int health);
    boolean decHealth(int health);
    void attack(Position position);
    void attack(Entity entity);
    Position getPosition();
    HitBox getHitBox();
    double getSpeed();
    void setSprint(boolean sprint);
    boolean isDead();
    Weapon[] getWeapons();
    Weapon getWeapon();
    void setOrientation(int orientation);
    void setWeapon(int weapon);
    int getHealth();
    void setAim(boolean aim);
    int getRange();
}
