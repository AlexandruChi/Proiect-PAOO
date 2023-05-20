package game.character;

import game.Camera;
import game.component.position.Position;
import game.entity.Entity;

import java.awt.*;

public interface Character {
    void update();

    void draw(Graphics graphics, Camera camera);

    Position getPosition();
    Entity getEntity();
}
