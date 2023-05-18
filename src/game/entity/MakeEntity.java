package game.entity;

import game.component.position.Position;
import game.graphics.assets.CharacterAssets;
import game.graphics.assets.character.GermanCharacterAssets;

public class MakeEntity {
    public static final int germanCharacterID = 1;
    public static final int britishCharacterID = 2;
    public static final int undeadCharacterID = 3;

    static class germanCharacterStats {
        static double normalSpeed = 1.5;
        static double sprintSpeed = 5;
        static int health = 5;
    }

    // TODO add hitbox

    public static EntityClass makeEntity(int characterID, Position position) {
        return switch (characterID) {

            case 1 -> new EntityClass(position, GermanCharacterAssets.characterWeapon1, null, CharacterAssets.printBox, germanCharacterStats.normalSpeed, germanCharacterStats.sprintSpeed, germanCharacterStats.health);

            default -> throw new IllegalStateException("Unexpected value: " + characterID);
        };
    }
}
