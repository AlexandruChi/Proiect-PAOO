package game.character;

import game.Camera;
import game.Game;
import game.Input;
import game.component.HitBox;
import game.component.position.Position;
import game.entity.Entity;
import game.entity.MakeEntity;

import java.awt.*;

public class Player implements Character {
    protected Entity entity;
    private int score;
    private boolean attack;

    public Player(Position position) {
        // TODO add hitbox
        score = 0;
        entity = MakeEntity.makeEntity(MakeEntity.germanCharacterID, position);
        attack = true;
    }

    public void update() {
        entity.setTravelDir(Game.getGame().getInput().getDirection());
        entity.setSprint(Game.getGame().getInput().getSprint());

        if (attack) {
            Position attackPosition = Game.getGame().getInput().getPosition();
            if (attackPosition != null) {
                entity.attack(Camera.getCamera().getMapPosition(attackPosition));
            }
        }

        // TODO add aim
        // TODO add mouse listener

        entity.update();
    }
    public void draw(Graphics graphics, Camera camera) {
        entity.draw(graphics, camera);
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public HitBox getHitBox() {
        return entity.getHitBox();
    }

    @Override
    public boolean isDead() {
        return entity.isDead();
    }

    public void canAttack(boolean attack) {
        this.attack = attack;
    }

    public void setCharacter(Entity entity) {
        this.entity = entity;
    }

    public Position getPosition() {
        return entity.getPosition();
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void decScore(int score) {
        this.score -= score;
        if (this.score < 0) {
            this.score = 0;
        }
    }
}
