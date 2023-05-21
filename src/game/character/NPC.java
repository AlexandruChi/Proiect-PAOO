package game.character;

import game.Camera;
import game.Window;
import game.component.Direction;
import game.component.HitBox;
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
        int signX = (character.getPosition().xPX / Window.objectSize) - (getPosition().xPX / Window.objectSize);
        int signY = (character.getPosition().yPX / Window.objectSize) - (getPosition().yPX / Window.objectSize);

        try {
            signX = signX / Math.abs(signX);
        } catch (ArithmeticException e) {
            signX = 0;
        }
        try {
            signY = signY / Math.abs(signY);
        } catch (ArithmeticException e) {
            signY = 0;
        }

        if (signX == 0 && signY == 0) {
            signX = (character.getPosition().xPX / Window.objectSize) - (getPosition().xPX / Window.objectSize);
            signY = (character.getPosition().yPX / Window.objectSize) - (getPosition().yPX / Window.objectSize);

            try {
                signX = signX / Math.abs(signX);
            } catch (ArithmeticException e) {
                signX = 0;
            }
            try {
                signY = signY / Math.abs(signY);
            } catch (ArithmeticException e) {
                signY = 0;
            }
        }

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

    @Override
    public HitBox getHitBox() {
        return entity.getHitBox();
    }

    @Override
    public void setWeapon(int weapon) {

    }

    @Override
    public boolean isDead() {
        return entity.isDead();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Position getPosition() {
        return entity.getPosition();
    }
}
