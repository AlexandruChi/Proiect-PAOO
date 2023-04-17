package game.component;

import game.Draw;
import game.component.texture.Texture;

import java.awt.*;

public abstract class Component {
    protected Position position;
    protected Texture texture;

    //TODO add screen position getCameraPosition();

    public Component(Position position, Texture texture) {
        this.position = position;
        this.texture = texture;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void draw(Graphics graphics) {
        Draw.draw(graphics, texture, position.xPX, position.yPX);
    }
}
