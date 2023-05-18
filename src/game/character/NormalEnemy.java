package game.character;

import game.Camera;
import game.component.position.Position;

import java.awt.*;

public class NormalEnemy extends Enemy {

    // TODO make later better

    public NormalEnemy() {
        Position position = new Position();
        position.tmpX = 12300;
        position.tmpY = 13800;
        position.xPX = 12300;
        position.yPX = 13800;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);
    }
}
