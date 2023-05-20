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

    private static final int followDistance = 10 * Window.objectSize;

    public UndeadEnemy(Position position) {
        super(MakeEntity.undeadCharacterID, position);
    }

    @Override
    public void update() {
        Character characterToFollow = null;
        int minDistance = -1;
        for (Character character : CharacterManager.getCharacterManager().getCharacters()) {
            if (!(character instanceof UndeadEnemy) && character != null) {
                int distance = (int)Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(character.getPosition().tmpX, character.getPosition().tmpY));
                if (Map.getMap().lineOfSight(getPosition(), character.getPosition()) && distance < followDistance) {
                    if (distance < minDistance) {
                        minDistance = distance;
                        characterToFollow = character;
                    } else if (minDistance == -1) {
                        minDistance = distance;
                        characterToFollow = character;
                    }
                }
            }
            if (characterToFollow != null) {
                getEntity().attack(characterToFollow.getEntity());
            }
        }

        if (characterToFollow != null) {
            followCharacter(characterToFollow);
        } else {
            moveAround();
        }

        super.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);
    }
}
