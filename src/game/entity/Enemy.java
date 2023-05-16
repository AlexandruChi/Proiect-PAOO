package game.entity;

import game.Camera;
import game.component.position.Position;

import java.awt.*;

public abstract class Enemy {
    protected Character character;

    public void update() {
        character.update();
    }
    public void draw(Graphics graphics, Camera camera) {
        character.draw(graphics, camera);
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Position getPosition() {
        return character.getPosition();
    }
}
