package game.component.weapon;

import game.Window;

public class MeleeWeapon extends WeaponBase {

    private static final int damage = 2;
    private static final int range = (int) (1.5 * Window.objectSize);
    private static final int hitRate = 100;
    private static final int delay = 3;

    protected MeleeWeapon() {
        super(damage, range, delay);
    }

    @Override
    public boolean canAim() {
        return false;
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
        return 0;
    }

    @Override
    public int getType() {
        return MelleWeapon;
    }

    @Override
    public int getID() {
        return Sword;
    }

    @Override
    public void reload() {}

    @Override
    public boolean fireRound() {
        return false;
    }

    @Override
    public void addAmmo(int ammo) {}

    @Override
    public void subAmmo(int ammo) {}
}
