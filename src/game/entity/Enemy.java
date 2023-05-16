package game.entity;

import game.Camera;
import game.Game;
import game.component.PrintBox;
import game.component.position.Position;
import game.graphics.assets.character.GermanCharacterAssets;

import java.awt.*;

public abstract class Enemy {
    protected Character character;

    public Enemy() {
        //TODO better thing for position
        //TODO better hitbox
        //TODO const for health and speed
        Position position = new Position();
        position.tmpX = 12300;
        position.tmpY = 13800;
        position.xPX = 12300;
        position.yPX = 13800;
        PrintBox printBox = new PrintBox();
        printBox.difY = 64;
        printBox.difX = 32;
        character = new CharacterEntity(position, GermanCharacterAssets.characterWeapon1,null, printBox, 1, 10, 5, 5);
    }

    public abstract void update();
    public void draw(Graphics graphics, Camera camera) {
        character.draw(graphics, camera);
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Position getPosition() {
        return character.getPosition();
    }
}
