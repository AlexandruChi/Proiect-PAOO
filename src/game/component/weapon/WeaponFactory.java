package game.component.weapon;

public class WeaponFactory {
    public static Weapon makeWeapon(int WeaponID) {
        return switch (WeaponID) {
            case Weapon.Sword -> new MeleeWeapon();
            default -> new RangeWeapon(WeaponID);
        };
    }
}
