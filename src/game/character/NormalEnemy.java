package game.character;

import game.Camera;
import game.component.position.Position;
import game.entity.MakeEntity;

import java.awt.*;
import java.util.List;

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
}
