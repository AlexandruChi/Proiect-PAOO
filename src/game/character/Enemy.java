package game.character;

import game.Camera;
import game.component.position.Position;
import game.entity.Entity;

import java.awt.*;

public abstract class Enemy implements Character {
    protected Entity entity;

    @Override
    public void update() {
        entity.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        entity.draw(graphics, camera);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Position getPosition() {
        return entity.getPosition();
    }
}
