package game.component;

import game.Camera;
import game.Draw;
import game.component.position.Position;
import game.component.texture.Texture;

import java.awt.*;

public abstract class TextureComponent extends Component {
    protected Texture texture;
    protected PrintBox printBox;

    //TODO add screen position getCameraPosition();

    public TextureComponent(Position position, Texture texture, PrintBox printBox) {
        super(position);
        this.texture = texture;
        if (printBox == null) {
            this.printBox = new PrintBox();
            this.printBox.difX = 0;
            this.printBox.difY = 0;
        } else {
            this.printBox = printBox;
        }
    }

    public void draw(Graphics graphics, Camera camera) {
        Draw.draw(graphics, camera, texture, position.xPX - printBox.difX, position.yPX - printBox.difY);
    }
}
