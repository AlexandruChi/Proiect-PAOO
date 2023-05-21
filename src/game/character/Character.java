package game.character;

import game.Camera;
import game.component.HitBox;
import game.component.position.Position;
import game.entity.Entity;

import java.awt.*;
import java.util.List;

public interface Character {
    void update();

    void draw(Graphics graphics, Camera camera);

    Position getPosition();
    Entity getEntity();
    HitBox getHitBox();

    void setWeapon(int weapon);

    List<Character> getCommanding();
    Ranks getRank();
    void setRank(Ranks rank);

    void setEntity(Entity entity);

    boolean isDead();

    void setLeader(Character leader);
    Character getLeader();

    void setCommanding(List<Character> commanding);
}
