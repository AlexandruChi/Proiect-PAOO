package game.entity;

import game.Window;
import game.component.position.Position;
import game.graphics.assets.CharacterAssets;
import game.graphics.assets.character.GermanCharacterAssets;

public class MakeEntity {
    public static final int germanCharacterID = 1;
    public static final int britishCharacterID = 2;
    public static final int undeadCharacterID = 3;

    static class characterStats {
        static double normalSpeed = 1.5 * ((double) Window.objectSize / 32);
        static double sprintSpeed = 5 * ((double) Window.objectSize / 32);
        static int health = 5;
        static int damage = 2;
        static int delay = 10;
    }

    static class undeadStats {
        static double speed = 1 * ((double) Window.objectSize / 32);
        static int health = 2;
        static int damage = 2;
        static int delay = 20;
    }

    public static EntityClass makeEntity(int characterID, Position position) {
        return switch (characterID) {

            case germanCharacterID -> new EntityClass(position, GermanCharacterAssets.characterWeapon1, CharacterAssets.hitbox, CharacterAssets.printBox, characterStats.normalSpeed, characterStats.sprintSpeed, characterStats.health, characterStats.damage, 2 * CharacterAssets.collisionDistance, characterStats.delay, 3);
            case britishCharacterID -> new EntityClass(position, GermanCharacterAssets.characterWeapon1, CharacterAssets.hitbox, CharacterAssets.printBox, characterStats.normalSpeed, characterStats.sprintSpeed, characterStats.health, characterStats.damage, 2 * CharacterAssets.collisionDistance, characterStats.delay, 1);
            case undeadCharacterID -> new EntityClass(position, GermanCharacterAssets.characterWeapon1, CharacterAssets.hitbox, CharacterAssets.printBox, undeadStats.speed, undeadStats.speed, undeadStats.health, undeadStats.damage, 2 * CharacterAssets.collisionDistance, undeadStats.delay, 0);

            default -> throw new IllegalStateException("Unexpected value: " + characterID);
        };
    }
}
