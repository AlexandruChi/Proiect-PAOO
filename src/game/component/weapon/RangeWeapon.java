package game.component.weapon;

import game.Window;

public class RangeWeapon extends WeaponBase {
    private final int ID;
    private int ammo;
    private int curentAmmo;
    private final int maxAmmo;
    private final int hitRate;

    protected RangeWeapon(int weaponID) {
        super(switch (weaponID) {
            case GermanRifle -> GermanRifleStats.damage;
            case GermanAR -> GermanARStats.damage;
            case GermanSMG -> GermanSMGStats.damage;
            case GermanSidearm -> GermanSidearmStats.damage;
            case BritishRifle -> BritishRifleStats.damage;
            default -> 0;
        }, switch (weaponID) {
            case GermanRifle -> GermanRifleStats.range;
            case GermanAR -> GermanARStats.range;
            case GermanSMG -> GermanSMGStats.range;
            case GermanSidearm -> GermanSidearmStats.range;
            case BritishRifle -> BritishRifleStats.range;
            default -> 0;
        }, switch (weaponID) {
            case GermanRifle -> GermanRifleStats.delay;
            case GermanAR -> GermanARStats.delay;
            case GermanSMG -> GermanSMGStats.delay;
            case GermanSidearm -> GermanSidearmStats.delay;
            case BritishRifle -> BritishRifleStats.delay;
            default -> 0;
        });

        ID = weaponID;
        hitRate = switch (weaponID) {
            case GermanRifle -> GermanRifleStats.hitRate;
            case GermanAR -> GermanARStats.hitRate;
            case GermanSMG -> GermanSMGStats.hitRate;
            case GermanSidearm -> GermanSidearmStats.hitRate;
            case BritishRifle -> BritishRifleStats.hitRate;
            default -> 0;
        };

        ammo = maxAmmo = switch (weaponID) {
            case GermanRifle -> GermanRifleStats.maxAmmo;
            case GermanAR -> GermanARStats.maxAmmo;
            case GermanSMG -> GermanSMGStats.maxAmmo;
            case GermanSidearm -> GermanSidearmStats.maxAmmo;
            case BritishRifle -> BritishRifleStats.maxAmmo;
            default -> 0;
        };

    }

    @Override
    public boolean canAim() {
        return true;
    }

    @Override
    public int getHitRate() {
        return hitRate;
    }

    @Override
    public boolean hasAmmo() {
        return false;
    }

    @Override
    public int getNrAmmo() {
        return ammo;
    }

    @Override
    public int getType() {
        return RangeWeapon;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void reload() {
        if (curentAmmo < maxAmmo) {
            if (ammo > (maxAmmo - curentAmmo)) {
                ammo -= (maxAmmo - curentAmmo);
                curentAmmo = maxAmmo;
            } else {
                curentAmmo += ammo;
                ammo = 0;
            }
        }
    }

    @Override
    public boolean fireRound() {
        if (curentAmmo > 0) {
            curentAmmo--;
            return true;
        }
        return false;
    }

    @Override
    public void addAmmo(int ammo) {
        this.ammo += ammo;
    }

    @Override
    public void subAmmo(int ammo) {
        this.ammo -= ammo;
        if (this.ammo < 0) {
            this.ammo = 0;
        }
    }
}
