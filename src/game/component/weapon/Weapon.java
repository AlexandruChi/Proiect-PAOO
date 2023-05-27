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
        static int range = 30 * Window.objectSize;
        static int delay = 15;
        static int damage = 5;
        static int hitRate = 90;
        static int maxAmmo = 5;
        static boolean auto = false;
        static boolean selectFire = false;
    }

    class GermanSMGStats {
        static int range = 20 * Window.objectSize;
        static int delay = 4;
        static int damage = 3;
        static int hitRate = 70;
        static int maxAmmo = 32;
        static boolean auto = true;
        static boolean selectFire = false;
    }

    class GermanARStats {
        static int range = 25 * Window.objectSize;
        static int delay = 6;
        static int damage = 4;
        static int hitRate = 80;
        static int maxAmmo = 30;
        static boolean auto = true;
        static boolean selectFire = true;
    }

    class GermanSidearmStats {
        static int range = 15 * Window.objectSize;
        static int delay = 10;
        static int damage = 2;
        static int hitRate = 60;
        static int maxAmmo = 8;
        static boolean auto = false;
        static boolean selectFire = false;
    }

    class BritishRifleStats {
        static int range = 20 * Window.objectSize;
        static int delay = 10;
        static int damage = 3;
        static int hitRate = 50;
        static int maxAmmo = 10;
        static boolean auto = false;
        static boolean selectFire = false;
    }

    boolean canAim();
    int getHitRate();
    int getDelay();
    boolean hasAmmo();
    int getNrAmmo();
    int getMaxAmmo();
    int getTotalAmmo();
    int getDamage();
    int getRange();
    int getType();
    int getID();

    void reload();

    boolean fireRound();

    boolean isAuto();
    boolean isSelectFire();
    boolean getMode();
    void setMode(boolean mode);

    void addAmmo(int ammo);
    void subAmmo(int ammo);

}
