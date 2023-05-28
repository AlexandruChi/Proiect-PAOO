package game.character;

import game.Camera;
import game.Window;
import game.component.Pair;
import game.component.position.Distance;
import game.component.position.Position;
import game.entity.MakeEntity;
import game.level.Map;

import java.awt.*;
import java.util.List;

public class UndeadEnemy extends Enemy {

    private static final int followDistance = 10 * Window.objectSize;
    private Character characterToFollow;
    private int searchForEnemyTimer = 0;

    public UndeadEnemy(Position position) {
        super(MakeEntity.undeadCharacterID, position);
        characterToFollow = null;
    }

    @Override
    public void update() {
        searchForEnemyTimer++;
        if (searchForEnemyTimer >= 30) {
            characterToFollow = null;
            int minDistance = -1;
            for (Character character : CharacterManager.getCharacterManager().getCharacters()) {
                if (!(character instanceof UndeadEnemy) && character != null) {
                    int distance = (int) Distance.calculateDistance(new Pair<>(getPosition().tmpX, getPosition().tmpY), new Pair<>(character.getPosition().tmpX, character.getPosition().tmpY));
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
            }
            searchForEnemyTimer = 0;
        }

        if (characterToFollow != null) {
            followCharacter(characterToFollow);
            getEntity().attack(characterToFollow.getEntity());
        } else {
            moveAround();
        }

        super.update();
    }

    @Override
    public void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);
    }

    @Override
    public List<Character> getCommanding() {
        return null;
    }

    @Override
    public Ranks getRank() {
        return null;
    }

    @Override
    public void setRank(Ranks rank) {

    }

    @Override
    public void setLeader(Character leader) {

    }

    @Override
    public Character getLeader() {
        return null;
    }

    @Override
    public void setCommanding(List<Character> commanding) {

    }

    @Override
    public void setCharacterToFollow(Character character) {
        characterToFollow = character;
    }
}
