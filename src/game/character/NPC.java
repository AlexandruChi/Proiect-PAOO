package game.character;

import game.Camera;
import game.component.Direction;
import game.component.RandomNumber;
import game.component.position.Position;
import game.entity.Entity;

import java.awt.*;

public class NPC implements Character{
    protected Entity entity;

    private static final int changeDirectionDelay = 60;
    private int changeDirectionTimer;

    protected NPC(Entity entity) {
        this.entity = entity;
        changeDirectionTimer = 0;
    }

    @Override
    public void update() {
        entity.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        entity.draw(graphics, camera);
    }

    public void moveAround() {
        changeDirectionTimer++;
        if (changeDirectionTimer == changeDirectionDelay) {
            changeDirectionTimer = 0;
            entity.setTravelDir(Direction.values()[RandomNumber.randomNumber(0, 8)]);
        }
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
