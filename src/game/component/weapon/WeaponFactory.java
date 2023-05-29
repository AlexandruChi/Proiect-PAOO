package game.component.weapon;

/*
    Clasă de tip factory pentru a genera un obiect Weapon în funcție de ID.
 */

public class WeaponFactory {
    public static Weapon makeWeapon(int WeaponID) {
        return switch (WeaponID) {
            case Weapon.Sword -> new MeleeWeapon();
            default -> new RangeWeapon(WeaponID);
        };
    }
}
