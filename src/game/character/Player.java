package game.character;

import game.Camera;
import game.Game;
import game.component.position.Position;
import game.entity.Entity;
import game.entity.MakeEntity;

import java.awt.*;

public class Player implements Character {
    protected Entity entity;

    private int score;

    public Player(Position position) {
        // TODO add hitbox
        score = 0;
        entity = MakeEntity.makeEntity(MakeEntity.germanCharacterID, position);
    }

    public void update() {
        entity.setTravelDir(Game.getGame().getInput().getDirection());
        entity.setSprint(Game.getGame().getInput().getSprint());
        entity.update();
    }
    public void draw(Graphics graphics, Camera camera) {
        entity.draw(graphics, camera);
    }

    public Entity getCharacter() {
        return entity;
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
