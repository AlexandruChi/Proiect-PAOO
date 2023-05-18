package game.character;

import game.Camera;
import game.component.position.Position;
import game.entity.MakeEntity;

import java.awt.*;

public class NormalEnemy extends Enemy {
    public NormalEnemy(Position position) {
        super(MakeEntity.britishCharacterID, position);
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
