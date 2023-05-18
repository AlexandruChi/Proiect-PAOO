package game.character;

import game.Camera;
import game.component.Direction;
import game.component.RandomNumber;
import game.component.position.Position;
import game.entity.Entity;
import game.entity.MakeEntity;

import java.awt.*;

public abstract class Enemy extends NPC {
    public Enemy(int entityType, Position position) {
        super(MakeEntity.makeEntity(entityType, position));
    }

    @Override
    public void update() {
        moveAround();
        super.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);
    }
}
