package game.character;

import game.Camera;
import game.component.Direction;
import game.component.RandomNumber;
import game.component.position.Position;
import game.entity.Entity;

import java.awt.*;

import static game.component.Direction.*;

public class NPC implements Character{
    private Entity entity;

    private static final int changeDirectionDelay = 180;
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

    public void followCharacter(Character character) {
        int signX = character.getPosition().xPX - getPosition().xPX;
        int signY = character.getPosition().yPX - getPosition().yPX;

        signX = signX / Math.abs(signX);
        signY = signY / Math.abs(signY);

        getEntity().setTravelDir(switch (signX) {
            case 1 -> switch (signY) {
                case 1 -> down_right;
                case -1 -> up_right;
                default -> right;
            };
            case -1 -> switch (signY) {
                case 1 -> down_left;
                case -1 -> up_left;
                default -> left;
            };
            default -> switch (signY) {
                case 1 -> down;
                case -1 -> up;
                default -> stop;
            };
        });
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
