package game.component.weapon;

import game.Window;

public interface Weapon {
    int MelleWeapon  = 0;
    int RangeWeapon = 1;

    int Sword = 0;
    int GermanRifle = 1;
    int GermanSMG = 2;
    int GermanAR = 3;
    int GermanSidearm = 4;
    int BritishRifle = 5;

    class GermanRifleStats {
        static int range = 25 * Window.objectSize;
        static int delay = 5;
        static int damage = 5;
        static int hitRate = 90;
        static int maxAmmo = 5;
    }

    class GermanSMGStats {
        static int range = 15 * Window.objectSize;
        static int delay = 3;
        static int damage = 3;
        static int hitRate = 70;
        static int maxAmmo = 32;
    }

    class GermanARStats {
        static int range = 20 * Window.objectSize;
        static int delay = 4;
        static int damage = 4;
        static int hitRate = 80;
        static int maxAmmo = 30;
    }

    class GermanSidearmStats {
        static int range = 10 * Window.objectSize;
        static int delay = 2;
        static int damage = 2;
        static int hitRate = 60;
        static int maxAmmo = 8;
    }

    class BritishRifleStats {
        static int range = 20 * Window.objectSize;
        static int delay = 5;
        static int damage = 5;
        static int hitRate = 33;
        static int maxAmmo = 10;
    }

    boolean canAim();
    int getHitRate();
    int getDelay();
    boolean hasAmmo();
    int getNrAmmo();
    int getDamage();
    int getRange();
    int getType();
    int getID();

    void reload();

    boolean fireRound();

    void addAmmo(int ammo);
    void subAmmo(int ammo);

}
