package game.component;

import game.component.position.Position;

public abstract class Component {
    protected Position position;

    //TODO add screen position getCameraPosition();

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
