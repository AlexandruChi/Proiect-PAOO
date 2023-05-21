package game.character;

import game.Camera;
import game.component.position.Position;
import game.entity.MakeEntity;

import java.awt.*;
import java.util.List;

public class Unit extends NPC {

    protected Character leader;
    protected List<Character> commanding;

    protected Ranks rank;

    protected Unit(Position position, Character leader, List<Character> commanding, Ranks rank) {
        super(MakeEntity.makeEntity(MakeEntity.germanCharacterID, position));
        this.leader = leader;
        this.commanding = commanding;
        this.rank = rank;
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
