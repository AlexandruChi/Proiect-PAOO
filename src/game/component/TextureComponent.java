package game.component;

import game.Camera;
import game.Draw;
import game.component.position.Position;
import game.component.texture.Texture;

import java.awt.*;
import java.util.Objects;

public abstract class TextureComponent extends Component {
    protected Texture texture;
    protected PrintBox printBox;

    public TextureComponent(Position position, Texture texture, PrintBox printBox) {
        super(position);
        this.texture = texture;
        this.printBox = Objects.requireNonNullElseGet(printBox, () -> new PrintBox(0, 0));
    }

    public void draw(Graphics graphics, Camera camera) {
        Draw.draw(graphics, camera, texture, position.xPX - printBox.difX, position.yPX - printBox.difY);
    }
}
