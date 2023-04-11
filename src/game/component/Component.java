package game.component;

import java.awt.*;

public abstract class Component {
    protected Position position;
    protected Texture texture;

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
        graphics.drawImage(texture.texture, position.xPX, position.yPX, texture.width, texture.height, null);
    }
}
