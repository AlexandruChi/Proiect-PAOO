package game.entity;

import game.component.position.Position;
import game.graphics.assets.CharacterAssets;
import game.graphics.assets.character.GermanCharacterAssets;

public class MakeEntity {
    public static final int germanCharacterID = 1;
    public static final int britishCharacterID = 2;
    public static final int undeadCharacterID = 3;

    static class characterStats {
        static double normalSpeed = 1.5;
        static double sprintSpeed = 5;
        static int health = 5;
        static int damage = 1;
    }

    static class undeadStats {
        static double speed = 1;
        static int health = 2;
        static int damage = 2;
    }

    // TODO add hitbox

    public static EntityClass makeEntity(int characterID, Position position) {
        return switch (characterID) {

            case germanCharacterID -> new EntityClass(position, GermanCharacterAssets.characterWeapon1, null, CharacterAssets.printBox, characterStats.normalSpeed, characterStats.sprintSpeed, characterStats.health, characterStats.damage);
            case britishCharacterID -> new EntityClass(position, GermanCharacterAssets.characterWeapon1, null, CharacterAssets.printBox, characterStats.normalSpeed, characterStats.sprintSpeed, characterStats.health, characterStats.damage);
            case undeadCharacterID -> new EntityClass(position, GermanCharacterAssets.characterWeapon1, null, CharacterAssets.printBox, undeadStats.speed, undeadStats.speed, undeadStats.health, undeadStats.damage);

            default -> throw new IllegalStateException("Unexpected value: " + characterID);
        };
    }
}
