package game.entity;

import game.Game;
import game.component.Position;
import game.graphics.assets.character.GermanCharacterAssets;

import java.awt.*;

public class Player {
    private Character character;

    public Player() {
        //TODO better thing for position
        //TODO better hitbox
        //TODO const for health and speed
        Position position = new Position();
        position.xPX = 100;
        position.yPX = 100;
        character = new CharacterEntity(position, GermanCharacterAssets.characterWeapon1,null, 1, 5, 5);
    }

    public void update() {
        character.setTravelDir(Game.game.getInput().getDirection());
        character.update();
    }
    public void draw(Graphics graphics) {
        character.draw(graphics);
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
