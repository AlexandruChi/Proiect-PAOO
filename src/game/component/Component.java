package game.component;

import game.Camera;
import game.Draw;
import game.component.position.Position;
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

    public void draw(Graphics graphics, Camera camera) {
        Draw.draw(graphics, camera, texture, position.xPX, position.yPX);
    }
}
