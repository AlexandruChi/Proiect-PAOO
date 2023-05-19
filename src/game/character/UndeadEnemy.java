package game.character;

import game.Camera;
import game.Window;
import game.component.Pair;
import game.component.position.Distance;
import game.component.position.Position;
import game.entity.MakeEntity;
import game.level.Map;

import java.awt.*;

public class UndeadEnemy extends Enemy{

    private static final int followDistance = 5 * Window.objectSize;

    public UndeadEnemy(Position position) {
        super(MakeEntity.undeadCharacterID, position);
    }

    @Override
    public void update() {
        for (Character character : CharacterManager.getCharacterManager().getCharacters()) {
            if (!(character instanceof UndeadEnemy)) {
                if (Map.getMap().lineOfSight(getPosition(), character.getPosition()) && Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(character.getPosition().tmpX, character.getPosition().tmpY)) < followDistance) {
                    followCharacter(character);
                }
            } else {
            moveAround();
            }
        }
        super.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);
    }
}
