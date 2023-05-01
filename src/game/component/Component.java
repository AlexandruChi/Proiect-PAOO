package game.component;

import game.Camera;
import game.Draw;
import game.component.position.Position;
import game.component.texture.Texture;

import java.awt.*;

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
