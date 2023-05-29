package game.component;

import game.component.position.Position;

/*
    Obiectul de bază a jocului.
    Conține poziția de pe hartă.
 */

public abstract class Component {
    protected Position position;

    public Component(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
