package game.entity;

import game.Camera;
import game.component.Direction;
import game.component.position.Position;

import java.awt.*;

public interface Character {
    void setTravelDir(Direction direction);
    Direction getTravelDir();
    void update();
    void draw(Graphics graphics, Camera camera);
    void move();
    boolean incHealth(int health);
    boolean decHealth(int health);
    void attack(Position position);
    Position getPosition();
    double getSpeed();
    void setSprint(boolean sprint);
}
