package game.entity;

import game.component.Direction;
import game.component.Position;

import java.awt.*;

public interface Character {
    void setTravelDir(Direction direction);
    void setSpeed(double speed);
    Direction getTravelDir();
    void update();
    void draw(Graphics graphics);
    void move();
    boolean incHealth(int health);
    boolean decHealth(int health);
    void attack(Position position);
}
