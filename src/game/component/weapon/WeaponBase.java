package game.component.weapon;

public abstract class WeaponBase implements Weapon {
    protected int damage;
    protected int range;
    protected int delay;

    protected WeaponBase(int damage, int range, int delay) {
        this.damage = damage;
        this.range = range;
        this.delay = delay;
    }

    public int getDamage() {
        return damage;
    }
    public int getRange() {
        return range;
    }

    public int getDelay() {
        return delay;
    }
}
