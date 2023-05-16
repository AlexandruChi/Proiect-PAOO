package game.entity;

import game.component.PrintBox;
import game.component.position.Position;
import game.graphics.assets.character.GermanCharacterAssets;

public class NormalEnemy extends Enemy{

    // TODO make later better

    public NormalEnemy() {
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

    @Override
    public void update() {
        super.update();
    }
}
